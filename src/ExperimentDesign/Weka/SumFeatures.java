package ExperimentDesign.Weka;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 对特征的综合，然后进行格式的调整。
 * gogogo
 */
public class SumFeatures 
{
	//根据两个用户id得到最终的特征集 
	public void SummrizeFeatures(String user_one, String user_two) throws SQLException
	{
		FeaturesFromMysql fm = new FeaturesFromMysql();
		MysqlUserDes one = fm.getUserInfo(user_one);
		MysqlUserDes two = fm.getUserInfo(user_two);
		 /*
		  * Followee and Follower:
		 * num_of_follower
		 * num_of_friends
		 * num_of_status ?
		 * num_of_favorites ?
		 * num_of_bi_followers ?
		 * url ?
		 * verification
		 * language 全是中文，没必要
		 * length_of_name
		 * length_of_desp
		 * days
		 * provinces
		 * city //这俩合成一个location？
		 * gender
		 * user_influence
		  */
		//第一部分的特征值
		int num_of_follower_one = one.follower;
		int num_of_follower_two = two.follower;
		
		int num_of_friends_one = one.followee;
		int num_of_friends_two = two.followee;
		
		boolean ver_one = one.verify;
		boolean ver_two = two.verify;
		
		int len_of_name_one = one.name.length();
		int len_of_name_two = two.name.length();
		
		int len_of_dep_one = one.des.length();
		int len_of_dep_two = two.des.length();
		
		String location_one = one.location;
		String location_two = two.location;
		
		int gender_one = one.gender;
		int gender_two = two.gender;
		
		String time_one = one.account_time;
		String time_two = two.account_time;
		
		double user_influence_one = one.follower/(double)(one.followee+1);
		double user_influence_two = two.follower/(double)(two.followee+1);
		
		//第二部分特征,这一部分的特征需要一定的统计
		/*
		 * 对用户时间窗口内的所有微博而言
		 * * num_of_hashtags
		 * num_of_URLS
		 * num_of_pics ?
		 * num_of_@
		 * tweet_length
		 */
		ArrayList<String> content_one = this.Content(user_one);
		int[] sum_one = this.TweetFeatures(content_one);
		ArrayList<String> content_two = this.Content(user_two);
		int[] sum_two = this.TweetFeatures(content_two);
		
		int num_of_hash_one = sum_one[0];
		int num_of_hash_two = sum_two[0];
		
		int num_of_url_one = sum_one[1];
		int num_of_url_two = sum_two[1];
		
		int num_of_at_one = sum_one[2];
		int num_of_at_two = sum_two[2];
		
		int tweet_len_one = sum_one[3];
		int tweet_len_two = sum_two[3];
		
		/*
		 * 第三部分特征值，交互特征 one/two
		 * Interaction Features:
		 * count_of_status_ratio  什么是status根本没说
		 * count_of_follower_ratio  
		 * count_of_friends_ratio
		 * followers_friends_ratio 具体是什么也不清楚
		 * count_of_bi_followers_ratio 相互关注的比例，拿不到数据
		 * length_of_name_ratio
		 * lang_both
		 * distance
		 * verified_both
		 * count_of_favourites_ratio 没有数据
		 * is_bi_follower
		 * url_both
		 * count_of_share_ratio
		 * gender_both
		 * days_ratio
		 */
		double count_of_follower_ratio = num_of_follower_one/(double)num_of_follower_two;
		double count_of_friends_ratio = num_of_friends_one/(double)num_of_friends_two;
		double follower_friends_ratio = num_of_follower_two/(double)num_of_friends_two;
		
		double length_of_name_ratio = len_of_name_one/(double)len_of_name_two;
		
		int lang_both = 1;
		
		String ver_both = String.valueOf(ver_one)+String.valueOf(ver_two);
		
		String gender_both = String.valueOf(gender_one)+String.valueOf(gender_two);
		
		double days_ratio = Integer.parseInt(time_one)/(double)Integer.parseInt(time_two);
		
		String[] items_one = one.location.split("\\s");
		String[] items_two = one.location.split("\\s");
		String distance = "far";
		int size_one = items_one.length;
		int size_two = items_two.length;
		if(size_one >= 1 && size_two >=1 &&items_one[size_one-1].equals(items_two[size_two-1]))
			distance = "near";
		else if(size_one >= 2 && size_two >=2 &&items_one[size_one-2].equals(items_two[size_two-2]))
			distance = "medium"; 
		
		
		
		
		
		
		
		

		
		
		
	}
	private ArrayList<String> Content(String user_id)
	{
		ArrayList<String> content = new ArrayList<String>();
		return content;
	}
	private int[] TweetFeatures(ArrayList<String> content)//不能直接调用之前的，因为已经去除了@以及url
	{
		int[] sum = new int[]{0,0,0,0};//hashtag,url,@,length
		for(String con:content)
		{
			String no_ha = con.replaceAll("#", "");
			sum[0] += (con.length()-no_ha.length())/2;
			//replaceAll("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "").replaceAll("@.*? ", "");
			String url_rex = "http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
			Pattern p_u = Pattern.compile(url_rex);
			Matcher m = p_u.matcher(con);
			while(m.find())
			{
				sum[1] += 1;
			}
			//@ symbol
			String at_rex = "@.*? ";
			p_u = Pattern.compile(at_rex);
			 m = p_u.matcher(con);
			while(m.find())
			{
				sum[2] += 1;
			}
			con = con.replaceAll(url_rex, "").replaceAll(at_rex, "");
			sum[3] += con.length();
		}
		return sum;
	}

}

package ExperimentDesign.Weka;
/*
 * ����mysql��user������ݽṹ
 * ���������������ݿ�Ķ�����
 */
public class MysqlUserDes 
{
	String id;
	String name;
	int gender;
	String location;
	String account_time;
	int weibo_num;
	int followee;
	int follower;
	boolean verify;
	String des;
	public MysqlUserDes()
	{
		
	}
	public MysqlUserDes(
			String id,
			String name,
			int gender,
			String location,
			String account_time,
			int weibo_num,
			int followee,
			int follower,
			boolean verify,
			String des
			)
	{
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.location = location;
		this.account_time = account_time;
		this.weibo_num = weibo_num;
		this.followee = followee;
		this.follower = follower;
		this.verify = verify;
		this.des = des;
	}
	

}

package ExperimentDesign.Weka;
/*
 * 描述mysql中user表的数据结构
 * 这样尽量减少数据库的读操作
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
	public String toString()
	{
		return this.id+"\t"+name+"\t"+gender+"\t"+location+"\t"+this.account_time+"\t"+this.weibo_num+"\t"+
	this.followee+"\t"+this.follower+"\t"+this.verify+"\t"+this.des;
		
	}
	

}

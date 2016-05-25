package ExperimentDesign.Weka;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 读取数据库，得到特征文件
 * 。arff文件
 */
public class FeaturesFromMysql 
{
	java.sql.Connection conn;
	java.sql.Statement stmt;
	String sql;
	String url;
	public void Init() throws SQLException, ClassNotFoundException
	{
		this.conn = null;   
    	this.url = "jdbc:mysql://localhost:3306/weibo_user?"
                + "user=root&password=zijidelu&useUnicode=true&characterEncoding=UTF8";
    	  Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
          
    	  System.out.println("成功加载MySQL驱动程序");
          // 一个Connection代表一个数据库连接
          conn = DriverManager.getConnection(url);
          // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
          this.stmt = conn.createStatement();
	}
	public void Close() throws SQLException
	{
		this.conn.close();
	}
	//从数据库中读取user信息
	public MysqlUserDes getUserInfo(String user_id) throws SQLException
	{
		
		MysqlUserDes mysql_user = null;;
        sql = "select * from users_pool where id='"+user_id+"'";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			String id = rs.getString(1);
			String name = rs.getString(2);
			int gender = rs.getInt(3);
			String location = rs.getString(4);
			String account_time = rs.getString(5);
			int weibo_num = rs.getInt(6);
			int followee = rs.getInt(7);
			int follower = rs.getInt(8);
			int verify = rs.getInt(9);
			String des = rs.getString(11);
			boolean v = false;
			if(verify == 1)
				v = true;
				
			
			
			mysql_user = new MysqlUserDes(id,name,gender,location,account_time,weibo_num,followee,
					follower,v,des);
			
		}
		return mysql_user;
	}

}

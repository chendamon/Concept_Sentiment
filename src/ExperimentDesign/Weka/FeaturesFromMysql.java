package ExperimentDesign.Weka;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * ��ȡ���ݿ⣬�õ������ļ�
 * ��arff�ļ�
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
    	  Class.forName("com.mysql.jdbc.Driver");// ��̬����mysql����
          
    	  System.out.println("�ɹ�����MySQL��������");
          // һ��Connection����һ�����ݿ�����
          conn = DriverManager.getConnection(url);
          // Statement������кܶ෽��������executeUpdate����ʵ�ֲ��룬���º�ɾ����
          this.stmt = conn.createStatement();
	}
	public void Close() throws SQLException
	{
		this.conn.close();
	}
	//�����ݿ��ж�ȡuser��Ϣ
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

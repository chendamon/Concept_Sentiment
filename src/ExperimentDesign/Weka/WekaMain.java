package ExperimentDesign.Weka;

import java.io.File;
import java.sql.SQLException;

public class WekaMain 
{
	public static void main(String[] args) throws Exception
	{
		FeaturesFromMysql mysql = new FeaturesFromMysql();
		SumFeatures weka_fea = new SumFeatures();
		mysql.Init();
		File file = new File("active_user");
		
		if(!file.exists())
			throw new Exception("path not found!");
		String[] user_list = file.list();
		MysqlUserDes first = null;
		MysqlUserDes second = null;
		for(int i = 0; i < user_list.length; i++)
		{
			MysqlUserDes user_des = mysql.getUserInfo(user_list[i]);
			if(user_des != null)
			{
				if(first == null)
				{
					first = user_des;
					continue;
				}
				else if(second == null)
				{
					second = user_des;
					break;
				}
			}
		}
		
		System.out.println("first user: "+first.toString());
		System.out.println("second user: "+second.toString());
		
		weka_fea.Write(first.id, second.id,mysql);
		mysql.Close();
		
	}

}

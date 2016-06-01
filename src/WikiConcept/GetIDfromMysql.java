package WikiConcept;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import FanJian.Content;
import FanJian.LangUtils;

public class GetIDfromMysql 
{
	java.sql.Connection conn;
	java.sql.Statement stmt;
	String sql;
	String url;
	LangUtils LU;
	public void Init(java.sql.Connection conn, java.sql.Statement stat) throws ClassNotFoundException, SQLException
    {
    	this.conn = conn;   
//    	this.url = "jdbc:mysql://localhost:3306/wiki?"
//                + "user=root&password=zijidelu&useUnicode=true&characterEncoding=UTF8";
//    	  Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
//          
//    	  System.out.println("成功加载MySQL驱动程序");
//          // 一个Connection代表一个数据库连接
          //conn = DriverManager.getConnection(url);
          // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
          //this.stmt = conn.createStatement();
    	this.stmt = stat;
        LU = new LangUtils();
    	
    }
	public void close() throws SQLException
    {
    	this.conn.close();
    }
	String inputStream2String(InputStream in)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));   

        StringBuilder sb = new StringBuilder();   
        String line = null;   

        try {   

            while ((line = reader.readLine()) != null) {   

                sb.append(line + "/n");   

            }   

        } catch (IOException e) {   

            e.printStackTrace();   

        } finally {   

            try {   

                in.close();   

            } catch (IOException e) {   

                e.printStackTrace();   

            }   

        }  
        return sb.toString();
	}
	//从mysql模糊查询进行查找，需要返回的id，以及对对应的title
	//在这里也把消歧做了
	//过滤数字字母下划线的标识符
	public IDTitleMatch getID(String keyword,ArrayList<String> entity, Content con, Category_merge cm) throws Exception
	{
		String regex = "[0-9]+_[0-9A-Z]+";
//		Content con = new Content();
//		con.Init();
		String ac = "";// extension?
		if(keyword.length() > 1)
		{
			ac = "%";
			System.out.println("extension needed.");
		}
			
		ArrayList<IDTitleMatch> id = new ArrayList<IDTitleMatch>();
		ArrayList<String> id_mark = new ArrayList<String>();
		String jian_keyword = LU.T2S(keyword);
		String fan_keyword = LU.S2T(keyword);
		System.out.println("words: "+fan_keyword+"\t"+jian_keyword);
		sql = "select page_id,page_title,page_is_redirect from page where page_namespace = 0 and UPPER(page_title) LIKE UPPER('"+jian_keyword+"')";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			if(rs.getString(2).replaceAll(regex, "").length() == 0)
				continue;
			
			InputStream in = rs.getBinaryStream(2);
			String title = this.inputStream2String(in);
			title = title.replace("/n", "");
			id.add(new IDTitleMatch(rs.getString(1),title));
			id_mark.add(rs.getString(1));
			
			int redirect = rs.getInt(3);
			System.out.println("redirect ?:"+redirect +"\t"+rs.getString(1)+"\t"+title+"\t"+keyword);
			if(redirect == 1)//redirect
			{
				System.out.println("wiki redirect");
				return this.Redirect(rs.getString(1));
			}
		}
		if(!fan_keyword.equals(jian_keyword))
		{
			System.out.println("fanword processing...");
			sql = "select page_id,page_title,page_is_redirect from page where page_namespace = 0 and UPPER(page_title) LIKE UPPER('"+fan_keyword+"')";
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				if(!id_mark.contains(rs.getString(1)))
				{
					if(rs.getString(2).replaceAll(regex, "").length() == 0)
						continue;
					InputStream in = rs.getBinaryStream(2);
					String title = this.inputStream2String(in);
					title = title.replace("/n", "");
					id_mark.add(rs.getString(1));
					id.add(new IDTitleMatch(rs.getString(1),title));
					
					int redirect = rs.getInt(3);
					if(redirect == 1)//redirect
					{
						System.out.println("wiki redirect");
						return this.Redirect(rs.getString(1));
					}
				}
			}
		}
		if(keyword.length() > 1)
		{
			boolean needed = true;
			for(String idm:id_mark)
			{
				if(cm.father_category_sql(idm).size() > 0)//单个entity映射过去是有意义的，没有必要进行扩展
				{
					needed = false;
					break;
				}
			}
			if(needed)
			{
				sql = "select page_id,page_title from page where page_namespace = 0 and UPPER(page_title) LIKE UPPER('"+jian_keyword+"%')";
				//rs = stmt.executeQuery(sql);
				rs = stmt.executeQuery(sql);
				while(rs.next())
				{
					if(rs.getString(2).replaceAll(regex, "").length() == 0)
						continue;
					
					InputStream in = rs.getBinaryStream(2);
					String title = this.inputStream2String(in);
					title = title.replace("/n", "");
					id.add(new IDTitleMatch(rs.getString(1),title));
					id_mark.add(rs.getString(1));
					
					
				}
				if(!fan_keyword.equals(jian_keyword))
				{
					sql = "select page_id,page_title from page where page_namespace = 0 and UPPER(page_title) LIKE UPPER('"+fan_keyword+"%')";
					rs = stmt.executeQuery(sql);
					while(rs.next())
					{
						if(!id_mark.contains(rs.getString(1)))
						{
							if(rs.getString(2).replaceAll(regex, "").length() == 0)
								continue;
							InputStream in = rs.getBinaryStream(2);
							String title = this.inputStream2String(in);
							title = title.replace("/n", "");
							id_mark.add(rs.getString(1));
							id.add(new IDTitleMatch(rs.getString(1),title));
						}
					}
				}
			}
		}
		//进行消除歧义
		String id_final = null;
		String keyword_final = null;
		if(id.size() > 1)
		{
			System.out.println("需要进行消除歧义");
			int max = 0;
			for(IDTitleMatch idt:id)
			{
				System.out.println("idt: "+idt.title+"\t"+idt.page_id+"\t"+keyword);
				int count = con.CountFromMongodb(entity, idt.page_id, idt.title, keyword);
				System.out.println("count: "+count);
				if(count > max)
				{
					max = count;
					id_final = idt.page_id;
					keyword_final = idt.title;
				}
			}
			if(id_final == null)
				return null;
			IDTitleMatch final_match = new IDTitleMatch(id_final,keyword_final);
			System.out.println("final_match "+keyword+"\t"+id_final+"\t"+keyword_final);
			//con.close();
			return final_match;
		}
		else if(id.size() > 0)
		{
			return id.get(0);
		}
		else
		{
			return null;
		}
	}
	private IDTitleMatch Redirect(String id) throws SQLException
	{
		sql = "select rd_title from redirect where rd_from = '"+id+"'";
		ResultSet rs = stmt.executeQuery(sql);
		String rd_title = "";
		while(rs.next())
		{
			InputStream in = rs.getBinaryStream(1);
			rd_title = this.inputStream2String(in);
			rd_title = rd_title.replace("/n", "");
		}
		sql = "select page_id from page where page_namespace = 0 and page_title = '"+rd_title+"'";
		rs = stmt.executeQuery(sql);
		String rd_id = "";
		while(rs.next())
		{
			InputStream in = rs.getBinaryStream(1);
			rd_id = this.inputStream2String(in);
			rd_id = rd_id.replace("/n", "");
		}
		IDTitleMatch idt = new IDTitleMatch(rd_id,rd_title);
		System.out.println("redirect id and title: "+rd_id+"\t"+rd_title);
		return idt;
		
	}

}

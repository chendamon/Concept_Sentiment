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
	public void Init() throws ClassNotFoundException, SQLException
    {
    	this.conn = null;   
    	this.url = "jdbc:mysql://localhost:3306/wiki?"
                + "user=root&password=zijidelu&useUnicode=true&characterEncoding=UTF8";
    	  Class.forName("com.mysql.jdbc.Driver");// ��̬����mysql����
          
    	  System.out.println("�ɹ�����MySQL��������");
          // һ��Connection����һ�����ݿ�����
          conn = DriverManager.getConnection(url);
          // Statement������кܶ෽��������executeUpdate����ʵ�ֲ��룬���º�ɾ����
          this.stmt = conn.createStatement();
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
	//��mysqlģ����ѯ���в��ң���Ҫ���ص�id���Լ��Զ�Ӧ��title
	//������Ҳ����������
	//����������ĸ�»��ߵı�ʶ��
	public IDTitleMatch getID(String keyword,ArrayList<String> entity) throws SQLException
	{
		String regex = "[0-9]+_[0-9A-Z]+";
		Content con = new Content();
		con.Init();
		ArrayList<IDTitleMatch> id = new ArrayList<IDTitleMatch>();
		ArrayList<String> id_mark = new ArrayList<String>();
		String jian_keyword = LU.T2S(keyword);
		String fan_keyword = LU.S2T(keyword);
		System.out.println("words: "+fan_keyword+"\t"+jian_keyword);
		sql = "select page_id,page_title from page where page_namespace = 0 and UPPER(page_title) LIKE UPPER('"+jian_keyword+"')";
		ResultSet rs = stmt.executeQuery(sql);
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
		
		sql = "select page_id,page_title from page where page_namespace = 0 and UPPER(page_title) LIKE UPPER('"+fan_keyword+"')";
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
		//������������
		String id_final = null;
		String keyword_final = null;
		if(id.size() > 1)
		{
			System.out.println("��Ҫ������������");
			int max = 0;
			for(IDTitleMatch idt:id)
			{
				System.out.println("idt: "+idt.title+"\t"+idt.page_id+"\t"+keyword);
				int count = con.CountFromMongodb(entity, idt.page_id, idt.title);
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
			con.close();
			return final_match;
		}
		else if(id.size() > 0)
		{
			con.close();
			return id.get(0);
		}
		else
		{
			con.close();
			return null;
		}
	}

}

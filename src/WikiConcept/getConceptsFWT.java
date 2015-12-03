package WikiConcept;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * ʵ��Ӱٶȷ����������
 * ��Ҫ��̽������������ϼ�������
 * author biront
 * 2015/11/30
 */
public class getConceptsFWT 
{
	//�洢���������url,�Լ�����
	//2015/12/1
	//wiki suls�洢����category��url
	HashMap<String,String> Surls;
	public getConceptsFWT()
	{
		this.Surls = new HashMap<String,String>();
	}
	public void clear()
	{
		this.Surls.clear();
	}
	public HashMap<String,String> getSUrls()
	{
		return this.Surls;
	}
	//���ٶȰٿ�һ����������
	//�����ݷ��ؽ���ҵ�������ǩ���ٶ���Ҳ���е�
	//��������������������������һ���б�
	//2015/11/30
	public void getReFB(String keyword,String urls) throws IOException
	{
		ArrayList<String> Tags = new ArrayList<String>();
		//baidu.com
		//URL url = new URL("http://baike.baidu.com/search/word?word="+keyword);
		//zh.wikipedia.com
		URL url = null;
		if(urls == null)
		{
		    url = new URL("https://zh.wikipedia.org/wiki/"+keyword);
		}
		else
			url = new URL(urls);
		HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();           
		urlcon.connect();         //��ȡ���� 
		System.out.println("get connection...");
		InputStream is = urlcon.getInputStream();           
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is,"utf-8"));                  
		String l = null;   
		boolean zone = false;
		boolean s_zone = false;
		System.out.println("Reading pages");
		//wikipedia
		//category get
		//2012/12/2
		while((l = buffer.readLine()) != null)
		{
			//ֱ����һ���������ֱ��������ʽƥ��
			if(l.contains("Special:ҳ�����"))
			{
				System.out.println("wiki found");
				String t = l.substring(l.indexOf("<ul>"),l.indexOf("</ul>"));
				String regex ="href=.*?>";
				Pattern p_u = Pattern.compile(regex);
				Matcher m = p_u.matcher(t);
				
				while(m.find())
				{
					String temp = m.group(0);
					String[] items = temp.split("\\s");
					String category_url = "http://zh.wikipedia.org"+items[0].replace("href=", "").replaceAll("\"", "");
					String category_dep = items[1].replace("title=\"Category:", "").replaceAll("\">", "");
					//���˵�һЩ���Բ������õ�category
					//��ʲôʱ�����
					if(category_dep.contains("����"))
						continue;
					this.Surls.put(category_dep, category_url);
				}
			}
			
		}
		//baike.baidu.com
		while((l=buffer.readLine())!=null)
		{
			//����ʴ������
			//2015/12/01
			if(l.contains("�����"))
			{
				s_zone = true;
			}
			else if(s_zone)
			{
				
				String url_regex ="href=.*?</a>";
				//String des_regex = "��.*?</a>";
				Pattern p_u = Pattern.compile(url_regex);
				Matcher m = p_u.matcher(l);
				while(m.find())
				{
					String t = m.group(0);
					String c_url = "www.baike.baidu.com"+t.substring(t.indexOf("\"")+1, t.lastIndexOf("\""));
				    String c_des = t.substring(t.indexOf(">")+1, t.lastIndexOf("<")).replaceAll(keyword+"��", "");
				    this.Surls.put(c_url, c_des);
				}
				if(l.contains("</ul>"))
					break;
			}
			if(l.contains("open-tag-item"))
			{
				System.out.println("dinfya");
				 zone = true;
			}
			else if(zone)
			{
				if(l.contains("div"))
					break;
				else if(!l.contains("<")&&!l.contains("��"))
					Tags.add(l);
			} 
	     }    
		 buffer.close();
		 if(Tags.size()>0)
		 {
			 System.out.println("we found something.");
			 System.out.println(Tags.toString());
		 }
	}
	//����ʵ����������ٶȰٿ��е�Τ��
	//author biront
	//2015/12/01
	//��δ�����ά���ٿƶ����ǲ��ǿ�����infobox���������е���ҳ���ݣ�
	//������ѶȻ����еģ���������������꣬�����ҵ���
	public void getSy(ArrayList<String> entities)
	{
		
	}

}

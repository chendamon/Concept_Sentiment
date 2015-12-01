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
 * 实验从百度返回搜索结果
 * 主要是探索如何在连接上加在内容
 * author biront
 * 2015/11/30
 */
public class getConceptsFWT 
{
	//存储相近词条的url,以及描述
	//2015/12/1
	HashMap<String,String> Surls;
	public getConceptsFWT()
	{
		this.Surls = new HashMap<String,String>();
	}
	//给百度百科一个搜索请求
	//并根据返回结果找到词条标签，百度这也是有的
	//待解决的问题是如果搜索出来是一堆列表
	//2015/11/30
	public void getReFB(String keyword) throws IOException
	{
		ArrayList<String> Tags = new ArrayList<String>();
		URL url = new URL("http://baike.baidu.com/search/word?word="+keyword);
		HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();           
		urlcon.connect();         //获取连接 
		System.out.println("get connection...");
		InputStream is = urlcon.getInputStream();           
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is,"utf-8"));                  
		String l = null;   
		boolean zone = false;
		boolean s_zone = false;
		System.out.println("Reading pages");
		while((l=buffer.readLine())!=null)
		{
			//多义词处理情况
			//2015/12/01
			if(l.contains("多义词"))
			{
				s_zone = true;
			}
			else if(s_zone)
			{
				
				String url_regex ="href=.*?</a>";
				//String des_regex = "：.*?</a>";
				Pattern p_u = Pattern.compile(url_regex);
				Matcher m = p_u.matcher(l);
				while(m.find())
				{
					String t = m.group(0);
					String c_url = "www.baike.baidu.com"+t.substring(t.indexOf("\"")+1, t.lastIndexOf("\""));
				    String c_des = t.substring(t.indexOf(">")+1, t.lastIndexOf("<")).replaceAll(keyword+"：", "");
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
				else if(!l.contains("<")&&!l.contains("，"))
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
	//近义词的情况，比如百度百科中的韦德
	//author biront
	//2015/12/01
	//如何处理？对维基百科而言是不是可以用infobox而不是所有的网页内容？
	//编码的难度还是有的，今天把它尽量搞完，用寝室的网
	public void getSy(ArrayList<String> entities)
	{
		
	}

}

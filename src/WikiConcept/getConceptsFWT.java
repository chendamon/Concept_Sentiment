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
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 实验从百度返回搜索结果
 * 主要是探索如何在连接上加在内容
 * author biront
 * 2015/11/30
 * 语义消歧还是需要整个网页的文本内容 
 */
public class getConceptsFWT 
{
	//存储相近词条的url,以及描述
	//2015/12/1
	//wiki suls存储的是category：url
	//HashMap<String,String> Surls;
	int app_time;
	public getConceptsFWT()
	{
		//this.Surls = new HashMap<String,String>();
		this.app_time = 0;
	}
	public void clear()
	{
		//this.Surls.clear();
		this.app_time = 0;
	}
//	public HashMap<String,String> getSUrls()
//	{
//		return this.Surls;
//	}
	//给百度百科一个搜索请求
	//并根据返回结果找到词条标签，百度这也是有的
	//待解决的问题是如果搜索出来是一堆列表
	//2015/11/30
	//2015/12/4
	//实体出现计数也放到这里边吧
	//那些没有出现的实体，或者根本不是实体的内容
	//2015/12/5
	
	//3.15 判断如果是小气处理的话 不对结果进行存储
	
	//3。17返回消歧后的结果 给出wikipedia中的页面标题
	public String getReFB(String keyword,String urls,ArrayList<String> entitys, boolean is_sy) throws IOException
	{
		HashMap<String,String> Surls = 	new HashMap<String,String>();
		if(urls!= null)
			System.out.println("sons url"+urls);
		//实体出现次数的计数变量
		app_time = 0;
		ArrayList<String> Tags = new ArrayList<String>();
		//baidu.com
		//URL url = new URL("http://baike.baidu.com/search/word?word="+keyword);
		//zh.wikipedia.com
		URL url = null;
		if(urls == null)
		{
			//中文格式转换
			//String encode = java.net.URLEncoder.encode(keyword);
		    url = new URL("https://zh.wikipedia.org/wiki/"+keyword);
			//url = new URL("https://zh.wikipedia.org/wiki/喜欢");
		    System.out.println("keyword: "+keyword+"concept url: "+url.toString());
		}
		else
			url = new URL(urls);
		HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();           
		urlcon.connect();//获取连接
		System.out.println("responsecode"+urlcon.getResponseCode());
		
		//判断返回代码 3/15
		//如果返回代码不是200直接不管了
		if(urlcon.getResponseCode() != 200)
		{
			return null;
		}
		System.out.println("get connection...");
		InputStream is = urlcon.getInputStream(); 
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is,"utf-8"));                  
		String l = null;   
		boolean zone = false;
		boolean s_zone = false;
		//System.out.println("Reading pages");
		//wikipedia
		//category get
		//2012/12/2
		//sy 存储待消气的网页
		HashMap<String,String> sy = new HashMap<String,String>();
		sy.clear();
		while((l = buffer.readLine()) != null)
		{
			
			//实体出现计数
			//316 去掉当前的要
			if(entitys != null)
			{
				for(int i = 0; i < entitys.size(); i++)
				{
					if(entitys.get(i).equals(keyword))
						continue;
					else if(l.contains(entitys.get(i)))
						app_time++;
				}
			}
			//如果根本不是实体直接返回 316已经不需要进行判断
//			if(l.contains("mw-search-result-heading"))
//				break;
			//这里有问题！！！！4/4 直接sy有什么用?
			if(l.contains("id=\"mw-content-text\"")&&!keyword.contains("Category"))
				s_zone = true;
			//进行列表的提取
			/*
			 * 2016/2/16
			 *直接先进行title的判别，因为很多差一个字就差很多
			 */
			if(s_zone&&l.contains("div"))
				break;
			else if(s_zone&&l.contains("<li><a href="))//添加一个<li>标签的判别，不是的话说明是一个准确地页面 不需要进行消除歧义
			{
				
				//扩充正则表达式否则会有其他乱七八糟的内容 3/15
				//直接对提取的连接过滤就行了
				String regex ="href=.*?>";
				Pattern p_u = Pattern.compile(regex);
				Matcher m = p_u.matcher(l);
				while(m.find())
				{
					String temp = m.group(0);
					//内容过滤
					if(!temp.contains(keyword))
						continue;
					String[] items = temp.split("\\s");
					String category_url = "http://zh.wikipedia.org"+items[0].replace("href=", "").replaceAll("\"", "");
					//正则表达式提取title
					String title = "title=\".*?\"";
					Pattern p_t = Pattern.compile(title);
					Matcher m_t = p_t.matcher(temp);
					String category_dep = null;
					while(m_t.find())
					{
						String t = m_t.group(0);
						t = t.replaceAll("title=\"", "");
						category_dep = t.substring(0, t.length()-1);
					}
					//String category_dep = items[1].replace("title=\"Category:", "").replaceAll("\">", "");
					
					sy.put(category_url, category_dep);
				}
				
			}
			//存在待小气选项，且出现了div直接删
			if(sy.size()>0&&l.contains("div"))
				break;
//			else if(s_zone&&l.contains("<table"))
//			{
//				break;
//			}
			
			//System.out.println("sy.size"+sy.size());	
//			if(l.contains("Special:页面分类"))
//				System.out.println("should have...........");
//			//直接是一行所有类别，直接正则表达式匹配
//			boolean cate_zone = false;
//			if(l.contains("Special:页面分类")&&!is_sy&&sy.size() == 0)
//			{
//				cate_zone = true;
//				System.out.println("wiki found");
//				String t = l.substring(l.indexOf("<ul>"),l.indexOf("</ul>"));
//				String regex ="href=.*?>";
//				Pattern p_u = Pattern.compile(regex);
//				Matcher m = p_u.matcher(t);
//				
//				while(m.find())
//				{
//					String temp = m.group(0);
//					String[] items = temp.split("\\s");
//					String category_url = "http://zh.wikipedia.org"+items[0].replace("href=", "").replaceAll("\"", "");
//					String category_dep = items[1].replace("title=\"", "").replaceAll("\">", "");
//					
//					//过滤掉一些明显不起作用的category
//					//如什么时候出生
//					//过滤最终的页面，即页面分类总页面
//					//date 2015/12/9
//					if(category_dep.contains("出生")||category_dep.contains("在世人物"))
//						continue;
//					System.out.println("category: "+category_dep);
//					Surls.put(category_dep, category_url);
//				}
//			}
//			if(cate_zone&&l.contains("div"))
//				break;
			
			
		}
		//baike.baidu.com
//		while((l=buffer.readLine())!=null)
//		{
//			//多义词处理情况
//			//2015/12/01
//			if(l.contains("多义词"))
//			{
//				s_zone = true;
//			}
//			else if(s_zone)
//			{
//				
//				String url_regex ="href=.*?</a>";
//				//String des_regex = "：.*?</a>";
//				Pattern p_u = Pattern.compile(url_regex);
//				Matcher m = p_u.matcher(l);
//				while(m.find())
//				{
//					String t = m.group(0);
//					String c_url = "www.baike.baidu.com"+t.substring(t.indexOf("\"")+1, t.lastIndexOf("\""));
//				    String c_des = t.substring(t.indexOf(">")+1, t.lastIndexOf("<")).replaceAll(keyword+"：", "");
//				    this.Surls.put(c_url, c_des);
//				}
//				if(l.contains("</ul>"))
//					break;
//			}
//			if(l.contains("open-tag-item"))
//			{
//				System.out.println("dinfya");
//				 zone = true;
//			}
//			else if(zone)
//			{
//				if(l.contains("div"))
//					break;
//				else if(!l.contains("<")&&!l.contains("，"))
//					Tags.add(l);
//			} 
//	     }    
		 buffer.close();
//		 if(Tags.size()>0)
//		 {
//			 System.out.println("we found something.");
//			 System.out.println(Tags.toString());
//		 }
		 if(sy.size() > 1)
		 {
			 System.out.println("待消除歧义部分："+sy.toString());
			 //对歧义进行处理的部分
			 String re = this.getSy(entitys, sy, Surls);
			 return re;
		 }
		return keyword;
	}
	//近义词的情况，比如百度百科中的韦德
	//author biront
	//2015/12/01
	//如何处理？对维基百科而言是不是可以用infobox而不是所有的网页内容？
	//编码的难度还是有的，今天把它尽量搞完，用寝室的网
	//2015/12/4
	//句子中其他实体出现的次数，直接统计次数好了
	public String getSy(ArrayList<String> entities,HashMap<String,String> url_title, HashMap<String,String> Surls) throws IOException
	{
		int max_time = -1;
		String the_url = null;
		String the_title = null;
		//空间换时间了，为了不进行第二遍扫描
		//HashMap<String,String> backup = new HashMap<String,String>();
		Iterator iter = url_title.entrySet().iterator();
		String url_max = "";
		String dep_max  = "";
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			//this.Surls.clear();
			System.out.println("new key:"+key);
			System.out.println("new title:"+val);
			this.getReFB(val, null, entities,true);
			System.out.println("出现次数"+app_time);
			//3.15 change
			if(app_time >= max_time)
			{
//				backup.clear();
//				max_time = temp;
//				Iterator it = this.Surls.entrySet().iterator();
//				while(it.hasNext())
//				{
//					Map.Entry en = (Map.Entry) it.next();
//					String k = (String) en.getKey();
//					String v = (String) en.getValue();
//					backup.put(k, v);
//				}
				max_time = app_time;
				url_max = key;
				dep_max = val;
			}
		}
//		Surls.put(dep_max, url_max);
//		this.Surls.clear();
//		Iterator it =backup.entrySet().iterator();
//		while(it.hasNext())
//		{
//			Map.Entry en = (Map.Entry) it.next();
//			String k = (String) en.getKey();
//			String v = (String) en.getValue();
//			//backup.put(k, v);
//			this.Surls.put(k, v);
//		}
		System.out.println("xiaoqi done!");
//		System.out.println("结果"+Surls.toString());
		return dep_max;
		
	}
	

}

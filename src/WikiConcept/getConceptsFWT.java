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
 * ʵ��Ӱٶȷ����������
 * ��Ҫ��̽������������ϼ�������
 * author biront
 * 2015/11/30
 * �������绹����Ҫ������ҳ���ı����� 
 */
public class getConceptsFWT 
{
	//�洢���������url,�Լ�����
	//2015/12/1
	//wiki suls�洢����category��url
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
	//���ٶȰٿ�һ����������
	//�����ݷ��ؽ���ҵ�������ǩ���ٶ���Ҳ���е�
	//��������������������������һ���б�
	//2015/11/30
	//2015/12/4
	//ʵ����ּ���Ҳ�ŵ�����߰�
	//��Щû�г��ֵ�ʵ�壬���߸�������ʵ�������
	//2015/12/5
	
	//3.15 �ж������С������Ļ� ���Խ�����д洢
	
	//3��17���������Ľ�� ����wikipedia�е�ҳ�����
	public String getReFB(String keyword,String urls,ArrayList<String> entitys, boolean is_sy) throws IOException
	{
		HashMap<String,String> Surls = 	new HashMap<String,String>();
		if(urls!= null)
			System.out.println("sons url"+urls);
		//ʵ����ִ����ļ�������
		app_time = 0;
		ArrayList<String> Tags = new ArrayList<String>();
		//baidu.com
		//URL url = new URL("http://baike.baidu.com/search/word?word="+keyword);
		//zh.wikipedia.com
		URL url = null;
		if(urls == null)
		{
			//���ĸ�ʽת��
			//String encode = java.net.URLEncoder.encode(keyword);
		    url = new URL("https://zh.wikipedia.org/wiki/"+keyword);
			//url = new URL("https://zh.wikipedia.org/wiki/ϲ��");
		    System.out.println("keyword: "+keyword+"concept url: "+url.toString());
		}
		else
			url = new URL(urls);
		HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();           
		urlcon.connect();//��ȡ����
		System.out.println("responsecode"+urlcon.getResponseCode());
		
		//�жϷ��ش��� 3/15
		//������ش��벻��200ֱ�Ӳ�����
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
		//sy �洢����������ҳ
		HashMap<String,String> sy = new HashMap<String,String>();
		sy.clear();
		while((l = buffer.readLine()) != null)
		{
			
			//ʵ����ּ���
			//316 ȥ����ǰ��Ҫ
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
			//�����������ʵ��ֱ�ӷ��� 316�Ѿ�����Ҫ�����ж�
//			if(l.contains("mw-search-result-heading"))
//				break;
			//���������⣡������4/4 ֱ��sy��ʲô��?
			if(l.contains("id=\"mw-content-text\"")&&!keyword.contains("Category"))
				s_zone = true;
			//�����б����ȡ
			/*
			 * 2016/2/16
			 *ֱ���Ƚ���title���б���Ϊ�ܶ��һ���־Ͳ�ܶ�
			 */
			if(s_zone&&l.contains("div"))
				break;
			else if(s_zone&&l.contains("<li><a href="))//���һ��<li>��ǩ���б𣬲��ǵĻ�˵����һ��׼ȷ��ҳ�� ����Ҫ������������
			{
				
				//����������ʽ��������������߰�������� 3/15
				//ֱ�Ӷ���ȡ�����ӹ��˾�����
				String regex ="href=.*?>";
				Pattern p_u = Pattern.compile(regex);
				Matcher m = p_u.matcher(l);
				while(m.find())
				{
					String temp = m.group(0);
					//���ݹ���
					if(!temp.contains(keyword))
						continue;
					String[] items = temp.split("\\s");
					String category_url = "http://zh.wikipedia.org"+items[0].replace("href=", "").replaceAll("\"", "");
					//������ʽ��ȡtitle
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
			//���ڴ�С��ѡ��ҳ�����divֱ��ɾ
			if(sy.size()>0&&l.contains("div"))
				break;
//			else if(s_zone&&l.contains("<table"))
//			{
//				break;
//			}
			
			//System.out.println("sy.size"+sy.size());	
//			if(l.contains("Special:ҳ�����"))
//				System.out.println("should have...........");
//			//ֱ����һ���������ֱ��������ʽƥ��
//			boolean cate_zone = false;
//			if(l.contains("Special:ҳ�����")&&!is_sy&&sy.size() == 0)
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
//					//���˵�һЩ���Բ������õ�category
//					//��ʲôʱ�����
//					//�������յ�ҳ�棬��ҳ�������ҳ��
//					//date 2015/12/9
//					if(category_dep.contains("����")||category_dep.contains("��������"))
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
//			//����ʴ������
//			//2015/12/01
//			if(l.contains("�����"))
//			{
//				s_zone = true;
//			}
//			else if(s_zone)
//			{
//				
//				String url_regex ="href=.*?</a>";
//				//String des_regex = "��.*?</a>";
//				Pattern p_u = Pattern.compile(url_regex);
//				Matcher m = p_u.matcher(l);
//				while(m.find())
//				{
//					String t = m.group(0);
//					String c_url = "www.baike.baidu.com"+t.substring(t.indexOf("\"")+1, t.lastIndexOf("\""));
//				    String c_des = t.substring(t.indexOf(">")+1, t.lastIndexOf("<")).replaceAll(keyword+"��", "");
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
//				else if(!l.contains("<")&&!l.contains("��"))
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
			 System.out.println("���������岿�֣�"+sy.toString());
			 //��������д���Ĳ���
			 String re = this.getSy(entitys, sy, Surls);
			 return re;
		 }
		return keyword;
	}
	//����ʵ����������ٶȰٿ��е�Τ��
	//author biront
	//2015/12/01
	//��δ�����ά���ٿƶ����ǲ��ǿ�����infobox���������е���ҳ���ݣ�
	//������ѶȻ����еģ���������������꣬�����ҵ���
	//2015/12/4
	//����������ʵ����ֵĴ�����ֱ��ͳ�ƴ�������
	public String getSy(ArrayList<String> entities,HashMap<String,String> url_title, HashMap<String,String> Surls) throws IOException
	{
		int max_time = -1;
		String the_url = null;
		String the_title = null;
		//�ռ任ʱ���ˣ�Ϊ�˲����еڶ���ɨ��
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
			System.out.println("���ִ���"+app_time);
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
//		System.out.println("���"+Surls.toString());
		return dep_max;
		
	}
	

}

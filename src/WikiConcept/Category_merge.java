package WikiConcept;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 2016 3 16
 * �ϲ�����wikipedia����ҵ��������С����father
 * �㷨˼·��
 * �ɵ����ϵ�����
 * 
 * 4.10 û�н���son�����
 */
public class Category_merge 
{
	java.sql.Connection conn;
	java.sql.Statement stmt;
	String sql;
	String url;
	ArrayList<String> cate_stop;
	//��¼�ӵ�ǰ�ڵ㵽���ȵ�·��������
	ArrayList<String> path_a;
	ArrayList<String> path_b;
	public Category_merge() throws Exception
	{
		this.path_a = new ArrayList<String>();
		this.path_b = new ArrayList<String>();
		StopCate sc = new StopCate();
		
		this.cate_stop = sc.InitCstop("category_stop.txt");
	}
	//�����ǵ���
	//��Ӹ��ӵĽṹ��ϵ
	public void c_merge(Tree_C tree, String cate_b, int depth, ArrayList<String> entities, HashMap<String,Integer> entity_senti) throws Exception
	{
		Tree_Processing tp = new Tree_Processing();
		//����ǲ����Ѿ������˴����
		//������1!!!!
	    if(tree.contain(cate_b))
	    	return;
	    else if(depth == tree.max_depth+1)//��߲�ĸ���ǲ���
		{
	    	System.out.println("new concept needed");
			Node top = tree.top;
			this.path_a.clear();
			this.path_b.clear();
			this.path_b.add(cate_b);
			//����ͬһ��ĺö���
			String mu_as = this.mu_as_hie(top.getName(), cate_b, entities);
			int dep_count = 0;
			String son = top.getName();
			//path Ҫ���з�ת
			int a_size = this.path_a.size();
			for(int i = a_size-1; i >= 0; i--)
			{
				String t = this.path_a.get(i);
				if(t.equals(top.getName()))
					continue;
				System.out.println("t_a_t"+t);
				dep_count++;
				//ÿ���ڵ�����ǽ����ж��Ƿ��ظ�
				Node tee = tp.IR_byname(tree, t);
				Node te = null;
				if(tee != null)
					te = tee;
				else te = new Node(t,null);
				//���sons
				if(i == a_size-2&&!te.sons.contains(top.getName()))
				{
					te.sons.add(top.getName());
				}
				else if(!te.sons.contains(this.path_a.get(i+1)))
				{
					te.sons.add(this.path_a.get(i+1));
				}
				
				//�ж��Ƿ�Ϊcategory��Ȼ�����apptime
				if(!t.contains("Category"))
				{
					te.app_time += 1;
				}
				if(entity_senti.containsKey(t))
				{
					int sentiment = entity_senti.get(t);
					te.sentiment = (double)sentiment;
				}
				te.depth = tree.max_depth+dep_count;
				
				//������߲�Ľڵ�
				if(t.equals(mu_as))
					tree.top = te;
				tree.nodes.put(te, tree.max_depth+dep_count);
				System.out.println("new node"+t);
			}
			tree.max_depth += dep_count; //1
			System.out.println("max_depth"+tree.max_depth);
			
			//����top��ֵ
			top = tree.top;
			dep_count = 0;
			son = cate_b;
			int b_size = this.path_b.size();
			for(int i = b_size-1; i >= 0; i--)
			{
				String t = this.path_b.get(i);
				System.out.println("t_b_t"+t);
				if(t.equals(mu_as)&&!top.sons.contains(this.path_b.get(i+1)))
				{
					top.sons.add(this.path_b.get(i+1));
					continue;
				}
					
				//ÿ���ڵ�����ǽ����ж��Ƿ��ظ�
				Node tee = tp.IR_byname(tree, t);
				Node te = null;
				if(tee != null)
					te = tee;
				else te = new Node(t,null);
				
				//���sons
				if(i < b_size-1&&!te.sons.contains(this.path_b.get(i+1)))
				{
					te.sons.add(this.path_b.get(i+1));
				}
				
				if(!t.contains("Category"))
				{
					te.app_time += 1;
				}
				if(entity_senti.containsKey(t))
				{
					int sentiment = entity_senti.get(t);
					te.sentiment = (double)sentiment;
				}
				te.depth = dep_count;
				
				tree.nodes.put(te, dep_count);
				dep_count++;
			}
			System.out.println("#############");
			System.out.println(tree.toString());
			System.out.println("#############");
		}
	    //�����ڲ��ڵ�ı���,��������û�п��Ǽ��Ͻڵ��ĸ��ӹ�ϵ���Ժ�Ҫ��
	    else
	    {
	    	//System.out.println("��ÿһ��tree�нڵ���б�����succeed node cate_b");
	    	String temp_name = null;
			for(Entry<Node,Integer> entry:tree.nodes.entrySet())
			{
				Node temp = entry.getKey();
				int dep = entry.getValue();
				if(dep == depth&&temp.getName().contains("Category"))
				{
					System.out.println("��ÿһ��tree�нڵ���б�����"+temp.getName()+"\t"+cate_b);
					this.path_a.clear();
					this.path_b.clear();
					path_b.add(cate_b);
					String as = this.mu_as_hie(temp.getName(), cate_b, entities);
					System.out.println("mu_father:"+ as);//����ĸ��ڵ㲢�������յĸ��ڵ�
					
					if(as.equals(temp.getName()))//˵��temp�Ǹ��ڵ㣬������ӣ�����ֱ����һ��
					{
						System.out.println("I'm your fahter!!!!!");
						int dep_count = 0;
						for(int i = 0; i < this.path_b.size() ; i++)
						{
							String name = this.path_b.get(i);
							dep_count++;
							if(name.equals(temp_name))
								continue;
							//ÿ���ڵ�����ǽ����ж��Ƿ��ظ�
							Node tee = tp.IR_byname(tree, name);
							Node t = null;
							if(tee != null)
								t = tee;
							else t = new Node(name,null);
							
							if(i < this.path_b.size()-1&&i>0&&!t.sons.contains(this.path_b.get(i+1)))
								t.sons.add(this.path_b.get(i+1));
							if(i == 1&&!temp.sons.contains(name))
							{
								temp.sons.add(name);
							}
							if(!name.contains("Category"))
							{
								t.app_time++;;
							}
							if(entity_senti.containsKey(name))
							{
								int sentiment = entity_senti.get(name);
								t.sentiment = (double)sentiment;
							}
							t.depth = depth-dep_count;
							tree.nodes.put(t, depth-dep_count);
							System.out.println("new node"+name);
						}
						
						System.out.println("#############");
						System.out.println(tree.toString());
						System.out.println("#############");
						return;
					}
				}
			}
			
			System.out.println("next level");
			this.c_merge(tree, cate_b, depth+1, entities, entity_senti);
			
	    }
		
		
	}
	//���ݿ���غ���
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
    	
    }
	public void close() throws SQLException
    {
    	this.conn.close();
    }
	public String InputStreamTOString(InputStream in, String encoding)  throws Exception 
	{  
		int BUFFER_SIZE = 4096; 
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
  
        byte[] data = new byte[BUFFER_SIZE];  
  
        int count = -1;  
  
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)  
  
            outStream.write(data, 0, count);  
  
        data = null;  
  
        return new String(outStream.toByteArray(), encoding);  
  
    }  
	//�������Ƚڵ��Լ��ӽڵ㹹��·������
	public boolean build_category_path(String cate_f, String cate_s, ArrayList<String> path) throws Exception
	{
		System.out.println("find sons:"+cate_f+"\t"+cate_s);
		if(this.son_category_sql(cate_f).contains(cate_s))
		{
			path.add(cate_f);
			return true;
		}
		else
		{
			ArrayList<String> sons = this.son_category_sql(cate_f);
			if(sons.size() > 0)
			{
				for(String fa:sons)
				{
					path.add(fa);
					if(this.build_category_path(fa, cate_s, path))
						return true;
					int path_index = path.size();
					path.remove(path_index-1);
				}
			}
		}
		return false;
	}
	//����·����ʱ���õ�
	public ArrayList<String> son_category_sql(String category) throws Exception
	{
		category = category.replace("Category:", "");
		ArrayList<String> category_son = new ArrayList<String>();
		ArrayList<Integer> page_id = new ArrayList<Integer>();
		sql = "select cl_from from categorylinks where cl_to='"+category+"'";
		
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next())
		{
			page_id.add(rs.getInt(1));
			//page_id = rs.getInt(1);
			//System.out.println("page_id: "+page_id);
		}
		//category links
		for(int i = 0; i < page_id.size(); i++)
		{
			
			sql = "select page_title from page where page_id='"+page_id.get(i)+"'";
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String temp = this.InputStreamTOString(rs.getBinaryStream(1), "utf-8");
				if(temp.contains("Ӣ��")||temp.contains("����Ӣ��")||temp.contains("Articles")||temp.contains("����")||temp.contains("��������")||temp.contains("��ҳ��")||temp.contains("��Ŀ"))
					continue;
				System.out.println("fa:"+category+"saons:"+temp);
				category_son.add(temp);
			}
		}
		
		return category_son;
	}
	//�����ݿ��ж�ȡ����
	public ArrayList<String> father_category_sql(String category) throws Exception
	{
		//System.out.println("father of: "+category);
		ArrayList<String> category_father = new ArrayList<String>();
		int page_id = -1;
		//ʵ��
		if(!category.contains("Category"))
		{
			sql = "select page_id from page where page_namespace = 0 and page_title='"+category+"'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				page_id = rs.getInt(1);
				//System.out.println("page_id: "+page_id);
			}
			//category links
			sql = "select cl_to from categorylinks where cl_from='"+page_id+"'";
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String temp = this.InputStreamTOString(rs.getBinaryStream(1), "utf-8");
				//System.out.println("category: "+temp);
				boolean c = false;
				for(String ca:this.cate_stop)
				{
					if(temp.contains(ca))
					{
						c = true;
						break;
					}
						
				}
				if(c)
					continue;
				category_father.add("Category:"+temp);
				//System.out.println(temp);
			}
			
			return category_father;
		}
		else
		{
			category = category.replace("Category:", "");
			sql = "select page_id from page where page_namespace = 14 and page_title='"+category+"'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				page_id = rs.getInt(1);
				//System.out.println("page_id: "+page_id);
			}
			//category links
			sql = "select cl_to from categorylinks where cl_from='"+page_id+"'";
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String temp = this.InputStreamTOString(rs.getBinaryStream(1), "utf-8");
				//System.out.println("category: "+temp);
				boolean c = false;
				for(String ca:this.cate_stop)
				{
					if(temp.contains(ca))
					{
						c = true;
						break;
					}
						
				}
				if(c)
					continue;
				category_father.add("Category:"+temp);
			}
			
			return category_father;
		}
	}
	
	//�õ�һ������ĸ������ 
	public ArrayList<String> father_category(String category) throws IOException
	{
		//System.out.println("find father"+category);
		ArrayList<String> father_category = new ArrayList<String>();
		URL url = new URL("https://zh.wikipedia.org/wiki/"+category);
	    HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();           
	    urlcon.connect();//��ȡ����
	    InputStream is = urlcon.getInputStream(); 
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is,"utf-8"));                  
		String l = null;   
		while((l = buffer.readLine()) != null)
		{
//			if(l.contains("Special:ҳ�����"))
//				System.out.println("should have...........");
			//ֱ����һ���������ֱ��������ʽƥ��
			boolean cate_zone = false;
			if(l.contains("Special:ҳ�����"))
			{
				cate_zone = true;
				//System.out.println("wiki found");
				String t = l.substring(l.indexOf("<ul>"),l.indexOf("</ul>"));
				String regex ="href=.*?>";
				Pattern p_u = Pattern.compile(regex);
				Matcher m = p_u.matcher(t);
				
				while(m.find())
				{
					String temp = m.group(0);
					String[] items = temp.split("\\s");
					//String category_url = items[0].replace("href=", "").replaceAll("\"", "");
					String cat_regex = "\"Category:.*?\"";
					Pattern p_u1 = Pattern.compile(cat_regex);
					Matcher mn = p_u1.matcher(items[1]);
					while(mn.find())
					{
						String category_dep = mn.group(0).replaceAll("\"", "");
						//System.out.println("cate_dep:"+category_dep);
						boolean c = false;
						for(String ca:this.cate_stop)
						{
							if(category_dep.contains(ca))
							{
								c = true;
								break;
							}
								
						}
						if(c)
							continue;
						father_category.add(category_dep);
					}
					//String category_dep = items[1].replace("title=\"", "").replaceAll("\">", "");
					
					//���˵�һЩ���Բ������õ�category
					//��ʲôʱ�����
					//�������յ�ҳ�棬��ҳ�������ҳ��
					//date 2015/12/9
//					if(category_dep.contains("����")||category_dep.contains("��������"))
//						continue;
//					father_category.add(category_dep);
					//System.out.println("category: "+category_dep);
					
				}
			}
			if(cate_zone&&l.contains("div"))
				break;
		}
		buffer.close();
		return father_category;
	}
	public ArrayList<String> toPath(HashMap<String,String> re, String category)
	{
		ArrayList<String> p = new ArrayList<String>();
		//p.add(category);
		while(true)
		{
			p.add(category);
			category = re.get(category);
			if(category == null)
				break;
		}
		return p;
		
	}
	//�ֲ�Σ�
	public String mu_as_hie(String cate_a, String cate_b, ArrayList<String> context) throws Exception
	{
		ArrayList<String> p_here = new ArrayList<String>();
		
		System.out.println("cate"+cate_a+"\t"+"cate"+cate_b);
		String p = this.zero_one(cate_a, cate_b, context);
		System.out.println("zero_one result"+p);
		if(p != null)
			return p;
		//��һ��Ϊ��ǰcate���ڶ���Ϊ���ӽڵ�
		HashMap<String,String> re_a = new HashMap<String,String>();
		HashMap<String,String> re_b = new HashMap<String,String>();
		
		re_a.put(cate_a, null);
		re_b.put(cate_b, null);
		for(int i = 1; i <= 10; i++)
		{
			this.father_cate_hie(re_a, cate_a, 1,i+1);
			this.father_cate_hie(re_b, cate_b, 1,i+1);
			System.out.println("re_a size: "+re_a.size()+"re_b size: "+re_b.size());
			
			
			Iterator iter = re_a.entrySet().iterator();
			while (iter.hasNext()) 
			{
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				if(re_b.containsKey(key))
				{
					System.out.println("mu_father: "+key);
					System.out.println("path a:"+this.toPath(re_a, key));
					System.out.println("path b:"+this.toPath(re_b, key));
					
					this.path_a = this.toPath(re_a, key);
					this.path_b = this.toPath(re_b, key);
					//��ʱ�Ȳ���������Ҳ����ɶ����������
					//p_here.add(key);
					return key;
				}
			}
		}
		return null;
		
		
	}
	//ֱ�ӷ��ز����Լ���Ӧ�ĸ����10��
	void father_cate_hie(HashMap<String,String> re, String category, int depth, int max_dep) throws Exception
	{
		if(depth >= max_dep)
			return;
		ArrayList<String> father_c = this.father_category_sql(category);
		if(father_c.size() == 0)
		{
			//System.out.println("size zero");
			return;
		}
		while(father_c.size() > 0)
		{
			for(String fa:father_c)
			{
				if(!re.containsKey(fa))
					re.put(fa, category);
			}
			depth ++;
			if(depth == max_dep)
				break;
			for(String fa:father_c)
				this.father_cate_hie(re, fa, depth, max_dep);
		}
		return;
	}
	//test try ֻ��һ�����0�����������ӽڵ�
	//�򵥵Ĺ�ͬ���ڵ�Ҳ���������,��������ҵ��ĸ���ʵ���ϲ�̫���
	//����ǩҲ�Ǵ���˳���
	//���Ա���ö�ڵ� ֱ�ӷ��أ� �����ٽ���remove
	public String zero_one(String cate_a, String cate_b, ArrayList<String> context) throws Exception
	{
		ArrayList<String> mu_c = new ArrayList<String>();
		
		ArrayList<String> father_a = this.father_category_sql(cate_a);
		//System.out.println("father_a"+father_a.toString());
		
		ArrayList<String> father_b = this.father_category_sql(cate_b);
		//System.out.println("father_b"+father_b.toString());
		if(father_a.contains(cate_b))
		{
//			mu_c.add(cate_b);
//			return mu_c;
			return cate_b;
		}
		else if(father_b.contains(cate_a))
		{
//			mu_c.add(cate_a);
//			return mu_c;
			return cate_a;
		}
			
		else 
		{
			for(String fa:father_a)
			{
				if(father_b.contains(fa))
				{
					mu_c.add(fa);
				}
			}
			
		}
		//����context����ɸѡ
		String max = null;
		int max_count = -1;
		for(String mu:mu_c)
		{
			int count = 0;
			for(String c:context)
			{
				if(this.father_category_sql(c).contains(mu))
					count++;
			}
			if(count > max_count)
			{
				max = mu;
				max_count = count;
			}
		}
		this.path_a.add(max);
		this.path_b.add(max);
		return max;
	}
	//һ��һ����
	public String mutural_as_hie(String cate_a,String cate_b,ArrayList<String> context) throws Exception
	{
		String f = this.zero_one(cate_a, cate_b,context);
		//����0,1��ֱ�ӽ������
		if(f != null)
		{
			System.out.println("find solution!");
			return f;
		}
		else
		{
			ArrayList<String> father_a = this.father_category_sql(cate_a);
			ArrayList<String> father_b = this.father_category_sql(cate_b);
			//ѭ�����м�1��
			boolean find = false;
			while((father_a.size() > 0||father_b.size() > 0)&&!find)
			{
				if(father_a.size() > 0)
				{
					for(String af:father_a)
					{
						String p = this.mutural_as_hie(af, cate_b, context);// a+1,b
						if(p != null)
						{
							find = true;
							return p;
						}
						if(father_b.size() > 0)
						{
							for(String fb:father_b)
							{
								String pp = this.mutural_as_hie(af, fb,context);//a+1,b+1
								if(pp != null)
								{
									find = true;
									return pp;
								}
								
							}
						}
					}
					
				}
				if(father_b.size() > 0)
				{
					for(String fb:father_b)
					{
						String pp = this.mutural_as_hie(cate_a, fb,context);//a,b+1
						if(pp != null)
						{
							find = true;
							return pp;
						}
						
					}
				}
			}
			
		}
		return null;
	}
	//�ж�b�Ƿ�Ϊa�ĺ�̣�������򷵻�һ��·��
	//һ�������͹�����
	//����ΪBFS
	public ArrayList<String> succeed(String cate_a, String cate_b) throws Exception
	{
		System.out.println("succeed "+cate_a+"\t"+cate_b);
		//���������ʵ��ֱ�ӷ��ؿ�
		if(!cate_a.contains("Category"))
		{
			return null;
		}
		//cate_a father��֦��
		ArrayList<String> a_father = this.father_category_sql(cate_a);
		ArrayList<String> path = new ArrayList<String>();
		//a��b�ĸ���
		if(this.father_category(cate_b).contains(cate_a))
		{
			path.add(cate_a);
			return path;
		}
		else 
		{
			ArrayList<String> mid = this.father_category_sql(cate_b);  
			//System.out.println("mid"+mid.toString());
			if(mid == null)
				return null;
			int size = mid.size();
			//����˵���a��b��ǰ���ֵܽڵ�ֱ������
			for(int i = 0; i < size; i++)
			{
				if(a_father.contains(mid.get(i)))
				{
					System.out.println("succeed return null");
					return null;
				}
			}
			final String ca = cate_a;
			//System.out.println("ca"+ca);
			//��ض�����
			Collections.sort(mid, new Comparator() {

			  @Override
               public int compare(Object o1, Object o2) {

			       if(Similarity(ca,o1.toString()) >= Similarity(ca,o2.toString()))
			    	   return -1;
			       else return 1;

			      }

			    });
			//System.out.println("sort: "+mid.toString());
			if(size == 1&&(mid.get(0).equals("ҳ�����")||mid.get(0).equals("�����")))
			{
				path.add("all");
				return path;
			}
			//���м�֦
			for(int i = 0; i < size; i++)
			{
				System.out.println("cate_b.father "+mid.get(i));
				//a �ĸ�������˵�ǰ��b�ĸ��࣬Ҳ����˵��b������һ���Ѿ���a���ˣ�û��Ҫ������ȥ
				if(a_father.contains(mid.get(i)))
				{
					System.out.println("null");
					return null;
				}
				else
				{
					ArrayList<String> p = this.succeed(cate_a, mid.get(i));
					if(p == null)
						return null;
					else if(path.size() == 1&&path.get(0).equals("all"))
					{
						for(String pp:p)
						{
							path.add(pp);
						}
						return path;
					}
				}
			}
			//���������������Ҫ���ݽṹ���д洢
			
		}
		return path;
		
	}
	//�ҵ������������С�������࣬·������һ���������ؾͿ�����
	public String mutural_as(String cate_a, String cate_b) throws Exception
	{
		System.out.println("mutural_as "+cate_a+"\t"+cate_b);
		
		
		ArrayList<String> a_father = this.father_category(cate_a);
		for(int i = 0; i < a_father.size(); i++)
		{
			System.out.println("succeed a_father"+a_father.get(i));
			if(this.succeed(a_father.get(i), cate_b).size() > 0)
			{
				System.out.println("find mutural father"+a_father.get(i));
				return a_father.get(i);
			}
		}
		//��Ϊbfs
		for(int i = 0; i < a_father.size(); i++)
		{
			
			return this.mutural_as(a_father.get(i), cate_b);
		}
		
		return null;
	}
	//����Ҳûʲô�ã������ȷ����������
	int Similarity(String cate_a, String cate_b)
	{
		cate_a = cate_a.replaceAll("Category", "");
		cate_b = cate_b.replaceAll("Category", "");
		
		int len = cate_a.length();
		int si_count = 0;
		for(int i = 0; i < len; i++)
		{
			String s = String.valueOf(cate_a.charAt(i));
			if(cate_b.contains(s))
				si_count++;
		}
		//System.out.print(si_count);
		return si_count;
	}
	

}

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
 * 合并两个wikipedia概念，找到概念的最小公共father
 * 算法思路：
 * 由底向上的扩充
 * 
 * 4.10 没有进行son的添加
 */
public class Category_merge 
{
	java.sql.Connection conn;
	java.sql.Statement stmt;
	String sql;
	String url;
	ArrayList<String> cate_stop;
	//记录从当前节点到祖先的路径的数组
	ArrayList<String> path_a;
	ArrayList<String> path_b;
	public Category_merge() throws Exception
	{
		this.path_a = new ArrayList<String>();
		this.path_b = new ArrayList<String>();
		StopCate sc = new StopCate();
		
		this.cate_stop = sc.InitCstop("category_stop.txt");
	}
	//依旧是迭代
	//添加父子的结构关系
	public void c_merge(Tree_C tree, String cate_b, int depth, ArrayList<String> entities, HashMap<String,Integer> entity_senti) throws Exception
	{
		Tree_Processing tp = new Tree_Processing();
		//检查是不是已经包含了此类别
		//计数加1!!!!
	    if(tree.contain(cate_b))
	    	return;
	    else if(depth == tree.max_depth+1)//最高层的概念还是不行
		{
	    	System.out.println("new concept needed");
			Node top = tree.top;
			this.path_a.clear();
			this.path_b.clear();
			this.path_b.add(cate_b);
			//就是同一层的好多结果
			String mu_as = this.mu_as_hie(top.getName(), cate_b, entities);
			int dep_count = 0;
			String son = top.getName();
			//path 要进行反转
			int a_size = this.path_a.size();
			for(int i = a_size-1; i >= 0; i--)
			{
				String t = this.path_a.get(i);
				if(t.equals(top.getName()))
					continue;
				System.out.println("t_a_t"+t);
				dep_count++;
				//每个节点加入是进行判定是否重复
				Node tee = tp.IR_byname(tree, t);
				Node te = null;
				if(tee != null)
					te = tee;
				else te = new Node(t,null);
				//添加sons
				if(i == a_size-2&&!te.sons.contains(top.getName()))
				{
					te.sons.add(top.getName());
				}
				else if(!te.sons.contains(this.path_a.get(i+1)))
				{
					te.sons.add(this.path_a.get(i+1));
				}
				
				//判断是否为category，然后加上apptime
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
				
				//更新最高层的节点
				if(t.equals(mu_as))
					tree.top = te;
				tree.nodes.put(te, tree.max_depth+dep_count);
				System.out.println("new node"+t);
			}
			tree.max_depth += dep_count; //1
			System.out.println("max_depth"+tree.max_depth);
			
			//更新top的值
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
					
				//每个节点加入是进行判定是否重复
				Node tee = tp.IR_byname(tree, t);
				Node te = null;
				if(tee != null)
					te = tee;
				else te = new Node(t,null);
				
				//添加sons
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
	    //对于内部节点的遍历,现在依旧没有考虑加上节点间的父子关系，以后要加
	    else
	    {
	    	//System.out.println("对每一个tree中节点进行遍历，succeed node cate_b");
	    	String temp_name = null;
			for(Entry<Node,Integer> entry:tree.nodes.entrySet())
			{
				Node temp = entry.getKey();
				int dep = entry.getValue();
				if(dep == depth&&temp.getName().contains("Category"))
				{
					System.out.println("对每一个tree中节点进行遍历，"+temp.getName()+"\t"+cate_b);
					this.path_a.clear();
					this.path_b.clear();
					path_b.add(cate_b);
					String as = this.mu_as_hie(temp.getName(), cate_b, entities);
					System.out.println("mu_father:"+ as);//这里的父节点并不是最终的父节点
					
					if(as.equals(temp.getName()))//说明temp是父节点，进行添加，负责直接下一层
					{
						System.out.println("I'm your fahter!!!!!");
						int dep_count = 0;
						for(int i = 0; i < this.path_b.size() ; i++)
						{
							String name = this.path_b.get(i);
							dep_count++;
							if(name.equals(temp_name))
								continue;
							//每个节点加入是进行判定是否重复
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
	//数据库相关函数
	 public void Init() throws ClassNotFoundException, SQLException
    {
    	this.conn = null;   
    	this.url = "jdbc:mysql://localhost:3306/wiki?"
                + "user=root&password=zijidelu&useUnicode=true&characterEncoding=UTF8";
    	  Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
          
    	  System.out.println("成功加载MySQL驱动程序");
          // 一个Connection代表一个数据库连接
          conn = DriverManager.getConnection(url);
          // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
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
	//根据祖先节点以及子节点构造路径出来
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
	//构造路径的时候用的
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
				if(temp.contains("英文")||temp.contains("包含英语")||temp.contains("Articles")||temp.contains("出生")||temp.contains("在世人物")||temp.contains("的页面")||temp.contains("条目"))
					continue;
				System.out.println("fa:"+category+"saons:"+temp);
				category_son.add(temp);
			}
		}
		
		return category_son;
	}
	//从数据库中读取父类
	public ArrayList<String> father_category_sql(String category) throws Exception
	{
		//System.out.println("father of: "+category);
		ArrayList<String> category_father = new ArrayList<String>();
		int page_id = -1;
		//实体
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
	
	//得到一个概念的父类概念 
	public ArrayList<String> father_category(String category) throws IOException
	{
		//System.out.println("find father"+category);
		ArrayList<String> father_category = new ArrayList<String>();
		URL url = new URL("https://zh.wikipedia.org/wiki/"+category);
	    HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();           
	    urlcon.connect();//获取连接
	    InputStream is = urlcon.getInputStream(); 
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is,"utf-8"));                  
		String l = null;   
		while((l = buffer.readLine()) != null)
		{
//			if(l.contains("Special:页面分类"))
//				System.out.println("should have...........");
			//直接是一行所有类别，直接正则表达式匹配
			boolean cate_zone = false;
			if(l.contains("Special:页面分类"))
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
					
					//过滤掉一些明显不起作用的category
					//如什么时候出生
					//过滤最终的页面，即页面分类总页面
					//date 2015/12/9
//					if(category_dep.contains("出生")||category_dep.contains("在世人物"))
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
	//分层次，
	public String mu_as_hie(String cate_a, String cate_b, ArrayList<String> context) throws Exception
	{
		ArrayList<String> p_here = new ArrayList<String>();
		
		System.out.println("cate"+cate_a+"\t"+"cate"+cate_b);
		String p = this.zero_one(cate_a, cate_b, context);
		System.out.println("zero_one result"+p);
		if(p != null)
			return p;
		//第一个为当前cate，第二个为儿子节点
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
					//暂时先不不把这里也处理成多个结果的情况
					//p_here.add(key);
					return key;
				}
			}
		}
		return null;
		
		
	}
	//直接返回层数以及相应的概念，找10层
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
	//test try 只差一层或者0层的情况，父子节点
	//简单的共同父节点也在这儿好了,避免出现找到的父类实际上不太相关
	//类别标签也是存在顺序的
	//尝试保存好多节点 直接返回， 后期再进行remove
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
		//根据context进行筛选
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
	//一层一层来
	public String mutural_as_hie(String cate_a,String cate_b,ArrayList<String> context) throws Exception
	{
		String f = this.zero_one(cate_a, cate_b,context);
		//就是0,1层直接解决问题
		if(f != null)
		{
			System.out.println("find solution!");
			return f;
		}
		else
		{
			ArrayList<String> father_a = this.father_category_sql(cate_a);
			ArrayList<String> father_b = this.father_category_sql(cate_b);
			//循环进行加1层
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
	//判断b是否为a的后继，如果是则返回一条路径
	//一个函数就够用了
	//更改为BFS
	public ArrayList<String> succeed(String cate_a, String cate_b) throws Exception
	{
		System.out.println("succeed "+cate_a+"\t"+cate_b);
		//如果是两个实体直接返回空
		if(!cate_a.contains("Category"))
		{
			return null;
		}
		//cate_a father剪枝用
		ArrayList<String> a_father = this.father_category_sql(cate_a);
		ArrayList<String> path = new ArrayList<String>();
		//a是b的父类
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
			//就是说如果a，b当前是兄弟节点直接舍弃
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
			//相关度排序
			Collections.sort(mid, new Comparator() {

			  @Override
               public int compare(Object o1, Object o2) {

			       if(Similarity(ca,o1.toString()) >= Similarity(ca,o2.toString()))
			    	   return -1;
			       else return 1;

			      }

			    });
			//System.out.println("sort: "+mid.toString());
			if(size == 1&&(mid.get(0).equals("页面分类")||mid.get(0).equals("頁面分類")))
			{
				path.add("all");
				return path;
			}
			//进行剪枝
			for(int i = 0; i < size; i++)
			{
				System.out.println("cate_b.father "+mid.get(i));
				//a 的父类包含了当前的b的父类，也就是说，b现在这一层已经比a大了，没必要再找下去
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
			//宽带优先搜索，需要数据结构进行存储
			
		}
		return path;
		
	}
	//找到两个概念的最小公共父类，路径用上一个函数返回就可以了
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
		//改为bfs
		for(int i = 0; i < a_father.size(); i++)
		{
			
			return this.mutural_as(a_father.get(i), cate_b);
		}
		
		return null;
	}
	//好像也没什么用，不过先放在这儿好了
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

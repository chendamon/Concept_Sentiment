package Thread_pool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import Date.showtime;
import NER.Hanlp_seg;
import Sentiment.Parse;
import Sentiment.Sent_enti;
import WikiConcept.StopCate;

/*
 * 每个用户的pipe是一个线程
 * 不知道这样写到底会不会出问题。
 * 明调研一下。
 */
public class user_pool 
{
	//options 4.19
	@Option(name="-threadnum", usage="Specify how many thread we will use")
	public int thread_num;
	@Option(name="-usernum", usage="Specify how many user we will process")
	public int user_num;
	@Option(name="-weibonum", usage="Specify how many weibo per user we will process")
	public int weibo_num;
	
	public static void main(String[] args) throws Exception
	{
		File profile = new File("user.profile");
		if(profile.exists())
			profile.delete();
		new user_pool().domain(args);
	}
	public void domain(String[] args) throws Exception
	{
		//cmd option
		ThreadCmdOption option = new ThreadCmdOption();
		CmdLineParser parser = new CmdLineParser(option);
		
		try {
            // parse the arguments.
            parser.parseArgument(args);
            thread_num = option.thread_num;
            user_num  = option.user_num;
            weibo_num = option.weibo_num;
 
            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");
 
            // after parsing arguments, you should check
            // if enough arguments are given.
//            if( arguments.isEmpty() )
//                throw new CmdLineException("No argument is given");
 
        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
 
            // print option sample. This is useful some time
           // System.err.println(" Example: java SampleMain"+parser.printExample(ALL));
 
            return;
        }
        
		//参数调试
		System.out.println("线程数: "+thread_num+" 用户数: "+user_num+" 微博数: "+weibo_num);

		//测试用例的数目
		showtime st = new showtime();
		st.show_time();
//		int number = Integer.parseInt(args[0]);
//		int count = Integer.parseInt(args[1]);
//		int pool_num = Integer.parseInt(args[2]);
		
		//ExecutorService pool = Executors.newFixedThreadPool(thread_num); 
		//change to normal thread pool without output
		ThreadPoolExecutor executor = new ThreadPoolExecutor(thread_num, 50, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(thread_num));
		
//		Parse par = new Parse();
//		par.Init();
		//情感词的提前准备
		Sent_enti sentiment_table = new Sent_enti();
		sentiment_table.Init();
		Hanlp_seg hanlp = new Hanlp_seg();
		HashMap<String,Integer> p = sentiment_table.getP();
		HashMap<String,Integer> n = sentiment_table.getN();
        //读取用户列表 
		String[] user_list = get_user_list("active_user");
		System.out.println("user_list read done.");
		int length = user_list.length;
		ArrayList<String> cw = new ArrayList<String>();
		
		//categorty stop类别
        StopCate sc = new StopCate();
		ArrayList<String> cate_stop = sc.InitCstop("category_stop.txt");
		
		//处理-1参数
		if(user_num == -1)
			user_num = user_list.length;
		System.out.println("user_num: "+user_num);
		
        for(int i = 0; i < user_num; i++)
        {
	    	String user_id = user_list[i];
	    	user_profile_fmt fmt = new user_profile_fmt();
	    	//分词进行外提 分词 要在整个县城之外进行， 否则hanlp的错误
//	    	//ArrayList<String> weibo_content = fmt.get_user_weibo("active_user/"+user_id);
//	    	int size = weibo_content.size();
//	    	ArrayList<String> p_seg = null;
//	    	ArrayList<String> f_seg = null;
//	    	ArrayList<String> p_result = null;
//	    	
//	    	//ArrayList<ArrayList<String>> pp_seg = new ArrayList<ArrayList<String>>();
//	    	ArrayList<ArrayList<String>> ff_seg = new ArrayList<ArrayList<String>>();
//	    	
//	    	ArrayList<ArrayList<String>> parse_result = new ArrayList<ArrayList<String>>();
//	    	for(int j = 0; j < size; j++)
//			{
//				//System.out.println("Now processing the "+j);
//				String weibo = weibo_content.get(j);
//				weibo = weibo.replaceAll("\\s+", "");
//				System.out.println("weibo"+weibo);
//				//hanlp seg
//				p_seg = hanlp.pure_seg(weibo);
//				System.out.println("pseg"+p_seg.toString());
//				f_seg = hanlp.filter_seg(weibo);
//				for(int k = 0; k < p_seg.size(); k++)
//				{
//					if(p_seg.get(k).length() == 0)
//					{
//						p_seg.remove(k);
//						k--;
//					}
//				}
//				if(p_seg.size() == 0)
//					continue;
//			    System.out.println(p_seg.size()+"\t"+p_seg.toString());
//				p_result = par.
//						Parse(p_seg);
//				
//				//pp_seg.add(p_seg);
//				ff_seg.add(f_seg);
//				parse_result.add(p_result);
//				
//				
//			}
	    	
	    	
	    	
	      //  System.out.println("seg done, This thread processing user: "+user_id);
	        try
	        {
	        	executor.execute(new user_profile(user_id,weibo_num,p,n,fmt,cate_stop,hanlp));
	        	//st.show_time();
	        }
	        catch(Exception e)
	        {
	        	System.out.println("index: "+i+", pool full, trying...");
	        	Thread.sleep(2000);
	        	i--;
	        }
	        
	        System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
            executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
            
        }
        st.show_time();
        executor.shutdown();
        //结果写入文件
//        File file = new File("user.profile");
//        if(file.exists())
//        	file.delete();
//        file.createNewFile();
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"utf-8"));
//        int size = cw.size();
//        System.out.println("cw's size: "+size);
//        for(String c:cw)
//        {
//        	writer.append(c);
//		    writer.flush();
//        }
//		
//		writer.close();
		//st.show_time();
        
	}
	
	static String[] get_user_list(String dirname) throws Exception
	{
		File file = new File(dirname);
		if(!file.exists())
			throw new Exception("path not found!");
		String[] user_list = file.list();
		return user_list;
	}
}
class user_profile implements Runnable
{
	user_profile_fmt fmt;
	String id;
	//Parse par;
	int count;
	HashMap<String,Integer> p;
	HashMap<String,Integer> n;
	ArrayList<String> cate_stop;
	Hanlp_seg hanlp;
	Parse par;
	int weibo_size;
	ArrayList<ArrayList<String>> parse_result;
	ArrayList<ArrayList<String>> ff_seg;
	ArrayList<String> weibo_content;
	
	user_profile(String id,int count,HashMap<String,Integer> p,HashMap<String,Integer> n, 
			user_profile_fmt fmt, ArrayList<String> cate_stop, Hanlp_seg hanlp)
	{
		this.fmt = fmt;
		this.id = id;
		//this.par = par;
		this.count = count;
		this.p = p;
		this.n = n;
		//this.p_seg = p_seg;
		//this.f_seg = f_seg;
		//this.weibo_size = weibo_size;
		this.cate_stop = cate_stop;
		//this.parse_result = parse_result;
		this.hanlp = hanlp;
		//this.par = par;
	}
//	void Init() throws Exception
//	{
//		//write file process here
//		ArrayList<String> weibo_content = fmt.get_user_weibo("active_user/"+id);
//		this.weibo_size = weibo_content.size();
//		int size = weibo_content.size();
//		ArrayList<String> p_seg = null;
//		ArrayList<String> f_seg = null;
//		ArrayList<String> p_result = null;
//		
//		//ArrayList<ArrayList<String>> pp_seg = new ArrayList<ArrayList<String>>();
//		this.ff_seg = new ArrayList<ArrayList<String>>();
//		
//		this.parse_result = new ArrayList<ArrayList<String>>();
//		for(int j = 0; j < size; j++)
//		{
//			String weibo = weibo_content.get(j);
//			weibo = weibo.replaceAll("\\s+", "");
//			//System.out.println("weibo"+weibo);
//			//hanlp seg
//			p_seg = hanlp.pure_seg(weibo);
//			f_seg = hanlp.filter_seg(weibo);
//			for(int k = 0; k < p_seg.size(); k++)
//			{
//				if(p_seg.get(k).length() == 0)
//				{
//					p_seg.remove(k);
//					k--;
//				}
//			}
//			if(p_seg.size() == 0)
//				continue;
//		    //System.out.println(p_seg.size()+"\t"+p_seg.toString());
//			p_result = par.Parse(p_seg);
//			
//			//pp_seg.add(p_seg);
//			ff_seg.add(f_seg);
//			parse_result.add(p_result);
//		}
//	}
//		
//		
//	}

//	@Override
//	public Object call() throws Exception 
//	{
//		// TODO Auto-generated method stub
//		return fmt.user_profile_create(id,par,count,p,n);
//	}

	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		try {
			// Init();
			//user profile构建主函数
			//String user_id, int number,HashMap<String,Integer> p,HashMap<String,Integer> n,
			// ArrayList<String> cate_stop,Hanlp_seg hanlp, Parse par,int weibo_size,ArrayList<ArrayList<String>> parse_re,
			// ArrayList<ArrayList<String>> ff_seg,ArrayList<String> weibo_content
			this.fmt.user_profile_create(id,count, p, n,cate_stop,hanlp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("user: "+id+" processing done.");
		
		
	}
	
}

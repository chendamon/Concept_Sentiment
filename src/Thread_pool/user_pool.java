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

/*
 * 每个用户的pipe是一个线程
 * 不知道这样写到底会不会出问题。
 * 明调研一下。
 */
public class user_pool 
{
	//options 4.19
	@Option(name="-threadnum", usage="Specify how many thread we will use")
	public int thread_num = 10;
	@Option(name="-usernum", usage="Specify how many user we will process")
	public int user_num = -1;
	@Option(name="-weibonum", usage="Specify how many weibo per user we will process")
	public int weibo_num = -1;
	
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
		
		Parse par = new Parse();
		par.Init();
		
		Sent_enti sentiment_table = new Sent_enti();
		sentiment_table.Init();
		Hanlp_seg hanlp = new Hanlp_seg();
		HashMap<String,Integer> p = sentiment_table.getP();
		HashMap<String,Integer> n = sentiment_table.getN();
         
		String[] user_list = get_user_list("active_user");
		System.out.println("user_list read done.");
		int length = user_list.length;
		ArrayList<String> cw = new ArrayList<String>();
		//处理-1
		if(user_num == -1)
			user_num = user_list.length;
		System.out.println("user_num: "+user_num);
		
        for(int i = 0; i < user_num; i++)
        {
	    	String user_id = user_list[i];
	    	user_profile_fmt fmt = new user_profile_fmt();
	    	//分词进行外提
	    	ArrayList<String> weibo_content = fmt.get_user_weibo("active_user/"+user_id);
	    	int size = weibo_content.size();
	    	ArrayList<String> p_seg = null;
	    	ArrayList<String> f_seg = null;
	    	
	    	for(int j = 0; j < size; j++)
			{
				//System.out.println("Now processing the "+j);
				String weibo = weibo_content.get(j);
				weibo = weibo.replaceAll("\\s+", "");
				//hanlp seg
				//ArrayList<String> weibo_seg = seg.jieba_Seg(weibo_content.get(j));
				p_seg = hanlp.pure_seg(weibo);
				f_seg = hanlp.filter_seg(weibo);
			}
	    	
	    	
	    	
	        System.out.println("seg done, This thread processing user: "+user_id);
	        try
	        {
	        	executor.execute(new user_profile(user_id,par,weibo_num,p,n,p_seg,f_seg,size,fmt));
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
//		st.show_time();
        
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
	Parse par;
	int count;
	HashMap<String,Integer> p;
	HashMap<String,Integer> n;
	ArrayList<String> p_seg;
	ArrayList<String> f_seg;
	int weibo_size;
	user_profile(String id, Parse par,int count,HashMap<String,Integer> p,HashMap<String,Integer> n, ArrayList<String> p_seg, ArrayList<String> f_seg, int weibo_size,
			user_profile_fmt fmt)
	{
		this.fmt = fmt;
		this.id = id;
		this.par = par;
		this.count = count;
		this.p = p;
		this.n = n;
		this.p_seg = p_seg;
		this.f_seg = f_seg;
		this.weibo_size = weibo_size;
	}

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
			this.fmt.user_profile_create(id, par, count, p, n,p_seg,f_seg,weibo_size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("user: "+id+" processing done.");
		
		
	}
	
}

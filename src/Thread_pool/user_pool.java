package Thread_pool;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * 每个用户的pipe是一个线程
 * 不知道这样写到底会不会出问题。
 * 明调研一下。
 */
public class user_pool 
{
	public static void main(String[] args) throws Exception
	{
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
//                new ArrayBlockingQueue<Runnable>(5));
		ExecutorService pool = Executors.newFixedThreadPool(5); 
         
		String[] user_list = get_user_list("active_user");
		System.out.println("user_list read done.");
		int length = user_list.length;
        for(int i = 0; i < length; i++)
        {
	    	String user_id = user_list[i];
	    	Callable c1 = new user_profile(user_id); 
	        Future f = pool.submit(c1);
	        String re = (String)f.get();
            
        }
        pool.shutdown();
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
class user_profile implements Callable
{
	user_profile_fmt fmt;
	String id;
	user_profile(String id)
	{
		fmt = new user_profile_fmt();
		this.id = id;
		
	}

	@Override
	public Object call() throws Exception 
	{
		// TODO Auto-generated method stub
		return fmt.user_profile_create(id);
	}
	
}

package Thread_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class thread_test 
{
	public static void main(String[] args)
	{
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
         
        for(int i=0;i<15;i++)
        {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
            executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
        }
        executor.shutdown();
	}
}
	class MyTask implements Runnable
	{
		private int tasknum;
		public MyTask(int num)
		{
			this.tasknum = num;
		}
		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			  System.out.println("正在执行task "+tasknum);
		        try {
		            Thread.currentThread().sleep(4000);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		        System.out.println("task "+tasknum+"执行完毕");
			
		}
	}



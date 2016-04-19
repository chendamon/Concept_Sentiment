package Thread_pool;

import org.kohsuke.args4j.Option;

public class ThreadCmdOption 
{
	@Option(name="-threadnum", usage="Specify how many thread we will use")
	public int thread_num = 10;
	@Option(name="-usernum", usage="Specify how many user we will process")
	public int user_num = 10;
	@Option(name="-weibonum", usage="Specify how many weibo per user we will process")
	public int weibo_num = 10;
	
	
	
}

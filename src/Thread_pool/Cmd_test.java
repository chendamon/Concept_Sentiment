package Thread_pool;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Cmd_test 
{
	@Option(name="-threadnum", usage="Specify how many thread we will use")
	public int thread_num;
	@Option(name="-usernum", usage="Specify how many user we will process")
	public int user_num;
	@Option(name="-weibonum", usage="Specify how many weibo per user we will process")
	public int weibo_num;
	@Argument  
    private List<String> arguments = new ArrayList<String>();
	public void domain(String[] args)
	{
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

		
		
	}
	public static void main(String[] args)
	{
		for(int i = 0; i < args.length; i++)
		{
			System.out.println(args[i]);
		}
		String[] argg = new String[]{"-threadnum", "30", "-usernum", "30", "-weibonum", "1"};
		new Cmd_test().domain(argg);
	}

}

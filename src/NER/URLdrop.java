package NER;

public class URLdrop 
{
	/*
	 * 3/8
	 * 去除微博中的url等一些其他无意义的符号
	 */
	public static String url_drop(String weibo)
	{
		//去除网页中的url
		String rex = "http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	    String re = weibo.replaceAll(rex, "");
		
		return re;
	}
	public static void main(String[] args)
	{
		String text = "3749780490863988        1409500799000   3903190124      蜗牛的身体路飞的心      0       1       大学以前，每年的今天都在补作业 http://t.cn/RhwledM ";
		String tt = url_drop(text);
		System.out.println(tt);
		
	}

}

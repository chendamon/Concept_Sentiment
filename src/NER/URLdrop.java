package NER;

public class URLdrop 
{
	/*
	 * 3/8
	 * ȥ��΢���е�url��һЩ����������ķ���
	 */
	public static String url_drop(String weibo)
	{
		//ȥ����ҳ�е�url
		String rex = "http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	    String re = weibo.replaceAll(rex, "");
		
		return re;
	}
	public static void main(String[] args)
	{
		String text = "3749780490863988        1409500799000   3903190124      ��ţ������·�ɵ���      0       1       ��ѧ��ǰ��ÿ��Ľ��춼�ڲ���ҵ http://t.cn/RhwledM ";
		String tt = url_drop(text);
		System.out.println(tt);
		
	}

}

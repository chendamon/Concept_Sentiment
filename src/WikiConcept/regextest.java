package WikiConcept;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regextest 
{
	public static void main(String[] args)
	{
		String input = "href=\"/subview/40566/9763638.htm\">韦德：美国篮球运动员</a></div></li><li class=\"list-dot list-dot-paddingleft\"><div class=\"para\"><a target=_blank ";
		String url_regex ="href=.*?</a>";
		//String des_regex = "：.*?</a>";
		Pattern p_u = Pattern.compile(url_regex);
		Matcher m = p_u.matcher(input);
		while(m.find())
		{
			String t = m.group(0);
			String c_url = "www.baike.baidu.com"+t.substring(t.indexOf("\"")+1, t.lastIndexOf("\""));
		    String c_des = t.substring(t.indexOf(">")+1, t.lastIndexOf("<")).replaceAll("韦德"+"：", "");
		    System.out.println(c_url+"\n"+c_des);
		}
	}

}

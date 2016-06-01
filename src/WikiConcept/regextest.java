package WikiConcept;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regextest 
{
	public static void main(String[] args)
	{
//		String t = "nsubj(认为-2, 你-1)";
//		String f_r = ",.*?\\)";
//		Pattern p_u = Pattern.compile(f_r);
//		Matcher m = p_u.matcher(t);
//		while(m.find())
//		{
//			String fi = m.group(0);
//			System.out.println(fi);
//			//System.out.println(fi.substring(1, fi.length()-1));
//		}
//		String weibo = "@fdfd 欢迎 //@WangYuan源少:[微风]大大的欢迎//@源man请让我帮您擦鞋: huany[熊猫] //@全世界我源最帅:欢迎[微风]//@带源源去德国:welcome//@念只念王源: 欢迎[微风] //@源直:欢迎[熊猫][奥特曼]";
//		weibo = weibo.replaceAll("@.*?[: ]", "");
//		System.out.println(weibo.replaceAll("\\[.*?\\]", ""));
		String ddd = "df33sf1232df";
		System.out.println(ddd.replaceAll("[0-9].*?", ""));
		
		
		
	}

}

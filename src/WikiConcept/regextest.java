package WikiConcept;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regextest 
{
	public static void main(String[] args)
	{
//		String t = "nsubj(��Ϊ-2, ��-1)";
//		String f_r = ",.*?\\)";
//		Pattern p_u = Pattern.compile(f_r);
//		Matcher m = p_u.matcher(t);
//		while(m.find())
//		{
//			String fi = m.group(0);
//			System.out.println(fi);
//			//System.out.println(fi.substring(1, fi.length()-1));
//		}
//		String weibo = "@fdfd ��ӭ //@WangYuanԴ��:[΢��]���Ļ�ӭ//@Դman�����Ұ�����Ь: huany[��è] //@ȫ������Դ��˧:��ӭ[΢��]//@��ԴԴȥ�¹�:welcome//@��ֻ����Դ: ��ӭ[΢��] //@Դֱ:��ӭ[��è][������]";
//		weibo = weibo.replaceAll("@.*?[: ]", "");
//		System.out.println(weibo.replaceAll("\\[.*?\\]", ""));
		String ddd = "df33sf1232df";
		System.out.println(ddd.replaceAll("[0-9].*?", ""));
		
		
		
	}

}

package WikiConcept;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regextest 
{
	public static void main(String[] args)
	{
		String t = "nsubj(��Ϊ-2, ��-1)";
		String f_r = ",.*?\\)";
		Pattern p_u = Pattern.compile(f_r);
		Matcher m = p_u.matcher(t);
		while(m.find())
		{
			String fi = m.group(0);
			System.out.println(fi);
			//System.out.println(fi.substring(1, fi.length()-1));
		}
		
		
		
	}

}

package Sentiment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * �ж��������ǲ��Ǵ�����һ���־䣬parse֮�������ж���д���ʵ��Ĺ�ϵ
 * author biront
 * 2015/12/2
 * �����﷨�ɷְɣ�����һ��
 * ccomp(ϲ��-3, �Ʊ�-4)
 * advmod(ղķ˹-8, ����-7)
 * ������ʵ����о䷨�ṹ��ϵ��word
 */
public class StruW 
{
	public String Stru(String entity,ArrayList<String> parse)
	{
		int size = parse.size();
		for(int i = 0; i < size; i++)
		{
			String temp = parse.get(i);
			if(temp.contains(entity))
			{
				String fi = null;
				String f_r = "\\(.*?-";
				Pattern p_u = Pattern.compile(f_r);
				Matcher m = p_u.matcher(temp);
				while(m.find())
				{
					fi = m.group(0).replaceAll("(", "").replaceAll("-", "");
				}
				f_r = ",.*?-";
				p_u = Pattern.compile(f_r);
				m = p_u.matcher(temp);
				String se = null;
				while(m.find())
				{
					se = m.group(0).replaceAll(",", "").replaceAll("-", "").replaceAll("\\s", "");
				}
				if(fi.equals(entity))
					return se;
				else return fi;
			}
		}
		return null;
	}

}

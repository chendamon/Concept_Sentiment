package Sentiment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 判断两个词是不是从属于一个分句，parse之后，用来判断情感词与实体的关系
 * author biront
 * 2015/12/2
 * 考虑语法成分吧，尝试一下
 * ccomp(喜欢-3, 科比-4)
 * advmod(詹姆斯-8, 讨厌-7)
 * 返回与实体具有句法结构关系的word
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

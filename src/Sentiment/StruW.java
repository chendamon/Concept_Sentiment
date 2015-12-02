package Sentiment;

import java.util.ArrayList;

/*
 * 判断两个词是不是从属于一个分句，parse之后，用来判断情感词与实体的关系
 * author biront
 * 2015/12/2
 * 考虑语法成分吧，尝试一下
 * ccomp(喜欢-3, 科比-4)
 * advmod(詹姆斯-8, 讨厌-7)
 */
public class StruW 
{
	public boolean Stru(String s,String entity,ArrayList<String> parse)
	{
		int size = parse.size();
		for(int i = 0; i < size; i++)
		{
			String temp = parse.get(i);
			if(temp.contains(s)&&temp.contains(entity))
				return true;
		}
		return false;
	}

}

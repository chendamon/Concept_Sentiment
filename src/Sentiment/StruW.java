package Sentiment;

import java.util.ArrayList;

/*
 * �ж��������ǲ��Ǵ�����һ���־䣬parse֮�������ж���д���ʵ��Ĺ�ϵ
 * author biront
 * 2015/12/2
 * �����﷨�ɷְɣ�����һ��
 * ccomp(ϲ��-3, �Ʊ�-4)
 * advmod(ղķ˹-8, ����-7)
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

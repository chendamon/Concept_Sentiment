package NER;

import java.util.ArrayList;
import java.util.HashMap;

import Sentiment.Sent_enti;

/*
 * ����һ��ʵ���ܹ��������������
 * author biront
 * date 2015/12/7
 * ʵ������а�
 */
public class Entity_Sent 
{
	public HashMap<String,Integer> gule(ArrayList<String> weibo_seg_s,ArrayList<String> parse) throws Exception
	{
		HashMap<String,Integer> eS = new HashMap<String,Integer>();
		Sent_enti se = new Sent_enti();
		se.Init();
		int size = weibo_seg_s.size();
		for(int i = 0; i < size; i++)
		{
			String entity = weibo_seg_s.get(i);
			eS.put(entity, se.entity_SP(entity, parse));
		}
		return eS;
	}

}

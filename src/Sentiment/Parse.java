package Sentiment;

import java.util.ArrayList;
/*
 * parse����
 * Ϊ�ҵ�ʵ���Ӧ����д���׼��
 * author biront
 * 2015/12/1
 */
public class Parse 
{
	public void Parse(ArrayList<String> seg_result)
	{
		//�ع��ִ�֮��ľ���
		int size = seg_result.size();
		String sentence = "";
		for(int i = 0; i < size-1; i++)
			sentence += seg_result.get(i)+"\t";
		sentence += seg_result.get(size-1);
	}

}

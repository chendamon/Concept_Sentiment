package Sentiment;

import java.util.ArrayList;
/*
 * parse部分
 * 为找到实体对应的情感词做准备
 * author biront
 * 2015/12/1
 */
public class Parse 
{
	public void Parse(ArrayList<String> seg_result)
	{
		//重构分词之后的句子
		int size = seg_result.size();
		String sentence = "";
		for(int i = 0; i < size-1; i++)
			sentence += seg_result.get(i)+"\t";
		sentence += seg_result.get(size-1);
	}

}

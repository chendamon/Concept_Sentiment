package NER;

import java.util.ArrayList;
import main.java.com.huaban.analysis.jieba.viterbi.Seg;

/*
 * 4/13
 * 要生成包含POS的分词结果，然后进行实体的过滤
 * 一共两个版本
 */
public class jieba_seg 
{
	public ArrayList<String> jieba_Seg(String weibo)
	{
		System.out.println("Processing...");
		Seg seg = new Seg();
		ArrayList<String> seg_result = seg.jieba_seg(weibo);
		///List<Term> seg_result = NlpAnalysis.parse(weibo);

		//test output
		//System.out.println(seg_result.toString());
		return seg_result;
	}

}

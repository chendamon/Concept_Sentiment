package NER;

import java.util.ArrayList;
import java.util.List;

import main.java.com.huaban.analysis.jieba.*;
import main.java.com.huaban.analysis.jieba.viterbi.Seg;


public class jieba_seg 
{
	public String jieba_Seg(String weibo)
	{
		System.out.println("Processing...");
		Seg seg = new Seg();
		ArrayList<String> seg_result = seg.jieba_seg(weibo);
		///List<Term> seg_result = NlpAnalysis.parse(weibo);

		//test output
		System.out.println(seg_result.toString());
		return null;
	}

}

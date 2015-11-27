package NER;

import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

/*
 * 使用ansj进行分词
 * 作为NER的语料
 * author biront
 * data 2015/11/27
 */
public class Ansj_seg 
{
	public String Seg(String weibo)
	{
		System.out.println("Processing...");
		List<Term> seg_result = ToAnalysis.parse(weibo);
		///List<Term> seg_result = NlpAnalysis.parse(weibo);

		//test output
		System.out.println(seg_result);
		return null;
	}
	public void main(String[] args)
	{
		Seg("【“港独”核心成员被捕 涉替诈骗内地公司案洗黑钱】“港独”组织核心成员中出羊子，其名下银行户口被揭为一宗电邮诈骗案洗黑钱。警方调查大半年后昨日上门将他拘捕，探员正在调查中出羊子的电邮纪录，倘证实诈骗电邮与他有关，则有极大机会控告他“诈骗”。");
	}

}

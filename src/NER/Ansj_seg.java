package NER;

import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

/*
 * ʹ��ansj���зִ�
 * ��ΪNER������
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
		Seg("ϲ����Ϸ�е�Τ�¡�");
	}

}

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
		Seg("�����۶������ĳ�Ա���� ����թƭ�ڵع�˾��ϴ��Ǯ�����۶�����֯���ĳ�Ա�г����ӣ����������л��ڱ���Ϊһ�ڵ���թƭ��ϴ��Ǯ����������������������Ž����в���̽Ա���ڵ����г����ӵĵ��ʼ�¼����֤ʵթƭ���������йأ����м������ظ�����թƭ����");
	}

}

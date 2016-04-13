package NER;
/*
 * 4/13
 * ��hanlp���зִ�����Ա�ע
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

public class Hanlp_seg 
{
	public void Seg()
	{
		List<Term> termList = NLPTokenizer.segment("����Ϊtfboysӵ����ǿ��ķ�˿Ⱥ��");
		System.out.println(termList);
	}
	//���ֻ�зִ� û�д��Ա�ע
	public ArrayList<String> pure_seg(String weibo)
	{
		List<Term> termList = NLPTokenizer.segment(weibo);
		//System.out.println("ori: "+termList.toString());
		int size = termList.size();
		ArrayList<String> ps = new ArrayList<String>();
		for(int i = 0; i < size; i++)
		{
			String regex = "/[a-z]*[0-9]*";
			ps.add(termList.get(i).toString().replaceAll(regex, ""));
			
		}
		return ps;
	}
	public ArrayList<String> filter_seg(String weibo)
	{
		List<Term> termList = NLPTokenizer.segment(weibo);
		int size = termList.size();
		ArrayList<String> ps = new ArrayList<String>();
		for(int i = 0; i < size; i++)
		{
			String regex = "/[a-z]*[0-9]*";
			Pattern p_t = Pattern.compile(regex);
			Matcher m_t = p_t.matcher(termList.get(i).toString());
			String pos_tag = null;
			while(m_t.find())
			{
				pos_tag = m_t.group(0);
			}
			if(pos_tag.contains("n"))
			{
				ps.add(termList.get(i).toString().replaceAll(regex, ""));
			}
			
		}
		return ps;
	}
	

}

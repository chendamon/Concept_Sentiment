package NER;
/*
 * 4/13
 * 用hanlp进行分词与词性标注
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
		NLPTokenizer nlp = new NLPTokenizer();
		List<Term> termList = nlp.segment("要开始虐了讨厌..//“面对她一步步的靠近，他却只能一次又一次的逃离。这场执恋，磨砺的是她的情，更是她的心”这版预告片[泪]~还没大甜就要开虐了T-T下周见[泪]");
		System.out.println(termList);
	}
	//结果只有分词 没有词性标注
	public ArrayList<String> pure_seg(String weibo)
	{
		List<Term> termList = null;
		NLPTokenizer nlp = new NLPTokenizer();
		try
		{
			termList = nlp.segment(weibo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("seg error: "+weibo);
		}
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
		NLPTokenizer nlp = new NLPTokenizer();
		List<Term> termList = nlp.segment(weibo);
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
				String conta = termList.get(i).toString().replaceAll(regex, "");
				//单个数字，字母的情况要进行去除
				//String reg = "[0-9A-Za-z]([0-9]*)";
				String re = "<U.+>";
//				if(conta.replaceAll(reg, "").length() == 0)
//					continue;
				if(conta.replaceAll(re, "").length() == 0)
					continue;
				ps.add(conta);
			}
			
		}
		return ps;
	}
	public static void main(String[] args)
	{
		Hanlp_seg se = new Hanlp_seg();
		se.Seg();
	}
	

}

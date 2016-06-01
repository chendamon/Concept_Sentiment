package WikiConcept;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

/*
 * 16/3/29
 * ���Ǹ���parse�Ľ�����ҵ���Ӧ����дʣ�����ʵ���ϣ������ܵ���ֻ��һ�ֹ�ϵ���������ӵľ��ӻ�����Ҫһ�������Ե�
 * ������patch
 * �ٿ�һ���ǲ����б�Ҫ
 * ���ڸ��ӵľ��Ӷ��ԣ������ǵ�����ֱ�������ι�ϵ��
 * û�����Ҫ������
 * 
 */
public class Sentiment_parse_pathch 
{
	ArrayList<String> p_map;
	ArrayList<String> n_map;
	public Sentiment_parse_pathch()
	{
		this.p_map = new ArrayList<String>();
		this.n_map = new ArrayList<String>();
	}
	//��parse�Ľ������ת����ʽ��ת��hashmap
	//[nsubj(��-4, Ȩ������Ϸ-1), advmod(��-4, ��ֱ-2), advmod(��-4, ̫-3), root(ROOT-0, ��-4), dep(��-4, ��-5)]
	//[Ⱥ ��˿] [Ⱥ ǿ��]
	public void Init_p(ArrayList<String> parse_re)
	{
		int size = parse_re.size();
		for(int i = 0; i < size; i++)
		{
			String temp = parse_re.get(i);
			//
			
			//System.out.println(temp);
			String fi = null;
			String f_r = "\\(.*?-";
			Pattern p_u = Pattern.compile(f_r);
			Matcher m = p_u.matcher(temp);
			while(m.find())
			{
				fi = m.group(0);
				fi = fi.substring(1, fi.length()-1);
				//System.out.println("fi:" +fi);
			}
			f_r = ",.*?-";
			p_u = Pattern.compile(f_r);
			m = p_u.matcher(temp);
			String se = null;
			while(m.find())
			{
				se = m.group(0);
				se = se.substring(2, se.length()-1);
				//System.out.println("se:"+se);
			}
			p_map.add(fi);
			n_map.add(se);
		}
		//return pm;
	}
	//hanlp parse result process
	public void Init(CoNLLSentence sen)
	{
		String[][] edges = sen.getEdgeArray();
		CoNLLWord[] words = sen.getWordArrayWithRoot();
		int m_size = edges.length;
		for(int i = 0; i < m_size; i++)
		{
			int n_size = edges[i].length;
			for(int j = 0; j < n_size; j++)
			{
				if(!(edges[i][j] == null))
				{
					if(j == 0)//root
					{
						p_map.add(words[i-1].LEMMA);
						n_map.add("ROOT");
					}
					else
					{
						p_map.add(words[i-1].LEMMA);
						n_map.add(words[j-1].LEMMA);
					}
				}
			}
		}
		
	}
	//�ж��Ƿ�Ϊ��д�
	public int is_eword(String word, HashMap<String,Integer> p, HashMap<String,Integer> n)
	{
		if(p.containsKey(word))
			return 1;
		else if(n.containsKey(word))
			return -1;
		return 0;
	}
	//for the stanford parser seg not acc
	public ArrayList<Integer> Indexof(HashMap<String, Integer> p, String entity)
	{
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		Iterator<Map.Entry<String, Integer>> iterator = p.entrySet().iterator();
		int index = 0;
		while (iterator.hasNext()) 
		{
			Entry<String, Integer> entry = iterator.next();
			index++;
			String key = entry.getKey();
			if(key.contains(entity))
				indexs.add(index);
			//entry.getValue();
		}
		return indexs;
		
	}
	//���һ��entity ���в���
	//���������˫��ģ��ѵ���Ҫ������map��
	public int eword_find(String entity, HashMap<String,Integer> p, HashMap<String,Integer> n, HashMap<String,Integer> scaned)
	{
		//System.out.println("sentiment"+entity);
		//HashMap<String,Integer> scaned = new HashMap<String,Integer>();//��¼�Ѿ������Ĵʣ����⻷· �ĳ���
		scaned.put(entity, 0);
		double sentiment = 0.0;
		int s = 0;
		ArrayList<Integer> p_index = null;
		ArrayList<Integer> n_index  =null;
		if(this.p_map.indexOf(entity)!=-1)
		{
			for(int i = 0; i < this.p_map.size(); i++)
			{
				if(this.p_map.get(i).equals(entity))
				{
					if(scaned.containsKey(this.n_map.get(i)))
						continue;
					else if(this.n_map.get(i).contains("ROOT"))
						return 0;
					else if((s = this.is_eword(this.n_map.get(i), p, n)) != 0)
					{
						System.out.println("sentiment"+this.n_map.get(i));
						return s;
					}
					else 
						return this.eword_find(this.n_map.get(i), p,n,scaned);
				}
			}
		}
		else if(this.n_map.indexOf(entity)!=-1)
		{
			for(int i = 0; i < this.n_map.size(); i++)
			{
				if(this.n_map.get(i).equals(entity))
				{
					if(scaned.containsKey(this.p_map.get(i)))
						continue;
					else if(this.p_map.get(i).contains("ROOT"))
						return 0;
					else if((s = this.is_eword(this.p_map.get(i), p, n)) != 0)
					{
						System.out.println("sentiment"+this.p_map.get(i));
						return s;
					}
					else 
						return this.eword_find(this.p_map.get(i), p,n,scaned);
				}
			}
		}
		
		return 0;
	}

}

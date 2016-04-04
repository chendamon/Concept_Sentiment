package WikiConcept;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	HashMap<String,String> p_map;
	HashMap<String,String> n_map;
	public Sentiment_parse_pathch()
	{
		this.p_map = new HashMap<String,String>();
		this.n_map = new HashMap<String,String>();
	}
	//��parse�Ľ������ת����ʽ��ת��hashmap
	//[nsubj(��-4, Ȩ������Ϸ-1), advmod(��-4, ��ֱ-2), advmod(��-4, ̫-3), root(ROOT-0, ��-4), dep(��-4, ��-5)]
	public void Init(ArrayList<String> parse_re)
	{
		int size = parse_re.size();
		for(int i = 0; i < size; i++)
		{
			String temp = parse_re.get(i);
			String fi = null;
			String f_r = "\\(.*?-";
			Pattern p_u = Pattern.compile(f_r);
			Matcher m = p_u.matcher(temp);
			while(m.find())
			{
				fi = m.group(0).replaceAll("(", "").replaceAll("-", "");
			}
			f_r = ",.*?-";
			p_u = Pattern.compile(f_r);
			m = p_u.matcher(temp);
			String se = null;
			while(m.find())
			{
				se = m.group(0).replaceAll(",", "").replaceAll("-", "").replaceAll("\\s", "");
			}
			this.p_map.put(fi, se);
			this.n_map.put(se, fi);
		}
		//return pm;
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
	//���һ��entity ���в���
	//���������˫��ģ��ѵ���Ҫ������map��
	public int eword_find(String entity, ArrayList<String> parse_re, HashMap<String,Integer> p, HashMap<String,Integer> n)
	{
		HashMap<String,Integer> scaned = new HashMap<String,Integer>();//��¼�Ѿ������Ĵʣ����⻷· �ĳ���
		while(true)
		{
			if(this.p_map.containsKey(entity))
				entity = this.p_map.get(entity);
			else entity = this.n_map.get(entity);
			
			if(scaned.containsKey(entity))
				break;
			scaned.put(entity, 0);
			
			int e = this.is_eword(entity, p, n);
			if(e != 0)
				return e;
			else if(entity.contains("ROOT"))
				return 0;
				
		}
		return 0;
	}

}

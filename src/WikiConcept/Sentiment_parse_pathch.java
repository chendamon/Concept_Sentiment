package WikiConcept;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 16/3/29
 * 就是根据parse的结果来找到相应的情感词，但是实际上，并不能单纯只看一种关系来定，复杂的句子还是需要一定传递性的
 * 添加这个patch
 * 再看一遍是不是有必要
 * 对于复杂的句子而言，并不是单纯的直接有修饰关系的
 * 没错就是要传播的
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
	//把parse的结果进行转换形式，转成hashmap
	//[nsubj(棒-4, 权力的游戏-1), advmod(棒-4, 简直-2), advmod(棒-4, 太-3), root(ROOT-0, 棒-4), dep(棒-4, 了-5)]
	//[群 粉丝] [群 强大]
	public void Init(ArrayList<String> parse_re)
	{
		int size = parse_re.size();
		for(int i = 0; i < size; i++)
		{
			String temp = parse_re.get(i);
			//
			
			System.out.println(temp);
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
	//判断是否为情感词
	public int is_eword(String word, HashMap<String,Integer> p, HashMap<String,Integer> n)
	{
		if(p.containsKey(word))
			return 1;
		else if(n.containsKey(word))
			return -1;
		return 0;
	}
	//针对一个entity 进行查找
	//马丹，这个是双向的，难道我要用两个map？
	public int eword_find(String entity, HashMap<String,Integer> p, HashMap<String,Integer> n, HashMap<String,Integer> scaned)
	{
		//System.out.println("sentiment"+entity);
		//HashMap<String,Integer> scaned = new HashMap<String,Integer>();//记录已经看过的词，避免环路 的出现
		scaned.put(entity, 0);
		double sentiment = 0.0;
		int s = 0;
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
						//System.out.println("sentiment"+this.n_map.get(i));
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
						//System.out.println("sentiment"+this.p_map.get(i));
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

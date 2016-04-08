package WikiConcept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * 从tree里直接找到分值最大的category进行排序，取前k个
 * 作为之后的相似度计算的数据准备
 * author biront
 * date 2015/12/7
 */
public class Con_final 
{
	HashMap<String,Double[]> CP;//category point
	public Con_final()
	{
		this.CP = new HashMap<String,Double[]>();
	}
	//返回前k个值
	public HashMap<String,Double[]> getTopK(int k)
	{
		HashMap<String,Double[]> top_k = new HashMap<String,Double[]>();
		int count = 0;
		Iterator iter = CP.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			Double[] val = (Double[]) entry.getValue();
			top_k.put(key, val);
			count++;
			if(count == k)
				break;
		}
		return top_k;	
	}
	//4.8 计算每个概念的权重
	public void cal_CP(Tree_C t, Tree_Processing tp)
	{
		//计算概念的权重
		Concept_Weight CW = new Concept_Weight();
		Iterator it = t.getTNodes().entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			Node temp = (Node) entry.getKey();
			String category = temp.category_name;
			//Double sentiment = temp.getSentiment();
			//Double weight = CW.cal_Weight(category, t);
			double weight = tp.Rank(category, t);
			double sentiment = temp.sentiment;
			Double[] d = new Double[2];
			d[0] = weight;
			d[1] = sentiment;
			this.CP.put(category, d);
		}
		//排序，方便去前K个
		List<Map.Entry<String, Double[]>> fi =
			    new ArrayList<Map.Entry<String, Double[]>>(CP.entrySet());
		Collections.sort(fi, new Comparator<Map.Entry<String, Double[]>>() 
		{   
		    public int compare(Map.Entry<String, Double[]> o1, Map.Entry<String, Double[]> o2) 
		    {      
		        if((o2.getValue()[0] - o1.getValue()[0]) < 0)
		        	return -1;
		        else return 1;
		        
		        //return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
		});
	}

}

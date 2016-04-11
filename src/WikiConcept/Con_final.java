package WikiConcept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * ��tree��ֱ���ҵ���ֵ����category��������ȡǰk��
 * ��Ϊ֮������ƶȼ��������׼��
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
	//����ǰk��ֵ
	//4.11 ��д
	public ArrayList<Point> getTopK(int k)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		
		int count = 0;
		Iterator iter = CP.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			Double[] val = (Double[]) entry.getValue();
			points.add(new Point(key,val[0],val[1]));
			count++;
			if(count == k)
				break;
		}
		//��������
		Comparator<Point> comparator = new Comparator<Point>()
		{
			public int compare(Point s1, Point s2) 
			{
				if(s1.weight-s2.weight < 0)
					return 1;
				else if(s1.weight-s2.weight == 0)
					return -1;
				else return 0;
			}
		};
		Collections.sort(points,comparator);
		return points;	
	}
	//4.8 ����ÿ�������Ȩ��
	public void cal_CP(Tree_C t, Tree_Processing tp)
	{
		//��������Ȩ��
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
	}

}

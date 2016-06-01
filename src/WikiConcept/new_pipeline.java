package WikiConcept;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;

import FanJian.Content;

public class new_pipeline //ArrayList<String> parse_re
{
	public void pipe(ArrayList<String> ca_entities, Tree_C tree, CoNLLSentence sen, HashMap<String,Integer> p, HashMap<String,Integer> n,Category_merge cm,
			Content con, GetIDfromMysql mysql, double emoij_score) throws Exception
	{
		//Tree_C tree = new Tree_C();
		//getConceptsFWT fwt = new getConceptsFWT();
		//GetIDfromMysql gfm = new GetIDfromMysql();

		
		HashMap<String, Double> es_map = new HashMap<String,Double>();
		Sentiment_parse_pathch sentiment = new Sentiment_parse_pathch();
		sentiment.Init(sen);
		
		
		
		//gfm.Init();
		//�Ƚ�������
	    //����������ݿ���ֻ��һ����ѡ���û�б�Ҫ������ҳ�����ˡ�����һ��������û��Ӧ����������
		ArrayList<String> wiki_title = new ArrayList<String>();
		ArrayList<String> page_id = new ArrayList<String>();
		System.out.println("emoij_score: "+emoij_score);
		for(String entity:ca_entities)// total sentiment  (emoij )
		{
			
			System.out.println("entity now:"+entity);
			//String title = fwt.getReFB(entity, null, ca_entities, false);
			IDTitleMatch title = mysql.getID(entity, ca_entities,con,cm);
			if(title != null)
			{
				System.out.println("the final match: page_id "+title.page_id+"\ttilte"+title.title);
			}
			//��������������Ϣ
			//need to change
			if(title != null)
			{
				wiki_title.add(title.title);
				page_id.add(title.page_id);
				HashMap<String,Integer> scaned = new HashMap<String,Integer>();
				
				es_map.put(title.title, sentiment.eword_find(entity, p, n, scaned)+emoij_score);
			}
		}
		//esmap to string 
//		System.out.println("es_map:");
//		Iterator iter = es_map.entrySet().iterator();
//		while (iter.hasNext()) 
//		{
//			Map.Entry entry = (Map.Entry) iter.next();
//			Object key = entry.getKey();
//			Object val = entry.getValue();
//			System.out.println(key+"\t"+val);
//		}
		//gfm.close();
		if(wiki_title.size() == 0)
		{
			System.out.println("No entity.");
			return;
		}
		//��ʾ��д���title�İ󶨽��
		//System.out.println("es_map"+es_map.toString());
		
		
		//�ٴν���check ������ ��˿ ��Ҷ�� �������� ��ʱ���뷨
		//����Ҫ����title�Ĺ��� �������ʼ�Ĺؼ��� 4.8
		for(int i = 0; i < wiki_title.size(); i++)
		{
			//int count = c.CountWebPage(wiki_title, wiki_title.get(i));
			System.out.println("title_now: "+wiki_title.get(i)+"\t"+page_id.get(i));
			int count = con.CountFromMongodb(wiki_title, page_id.get(i), wiki_title.get(i), wiki_title.get(i));
			System.out.println("count: "+count);
			if(count < 3)//Ҳֻ��Ȩ��֮��
			{
				wiki_title.remove(i);
				page_id.remove(i);
				i--;
			}
		}
		//c.close();
		if(wiki_title.size() == 0)
		{
			System.out.println("second filter no entity");
			return;
		}
		System.out.println("size: "+wiki_title.size()+"final wiki_title: "+wiki_title.toString());
		
		
		
		for(String title:wiki_title)
		{
			if(wiki_title == null)
				continue;
			else 
			{
				if(tree.nodes.size() == 0)
				{
					double senti = es_map.get(title);
					Node leaf = new Node(title,null);
					leaf.sentiment = senti;
					
					leaf.app_time = 1;
					tree.nodes.put(leaf, 0);
					tree.max_depth = 0;
					tree.top = leaf;
					System.out.println("new leaf "+title+"\t"+senti);
				}
				else//������������
				{
					System.out.println("not one node:");
					//��Լ�㷨Ч�ʵ�ƿ��
					cm.c_merge(tree, title, 0, wiki_title, es_map);
				}
			}
		}
		//cm.close();
		//got category tree
		if(tree.nodes.size() > 0)
			System.out.println("top"+tree.top.category_name+"\n"+tree.toString());
		
		
	}

}

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
		//先进行消歧
	    //首先如果数据库里只有一个候选项就没有必要进行网页访问了。并不一定，所以没有应用这条规则
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
			//在这里添加情感信息
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
		//显示情感词与title的绑定结果
		//System.out.println("es_map"+es_map.toString());
		
		
		//再次进行check 以消除 粉丝 四叶草 这种歧义 暂时的想法
		//这里要进行title的过滤 而不是最开始的关键词 4.8
		for(int i = 0; i < wiki_title.size(); i++)
		{
			//int count = c.CountWebPage(wiki_title, wiki_title.get(i));
			System.out.println("title_now: "+wiki_title.get(i)+"\t"+page_id.get(i));
			int count = con.CountFromMongodb(wiki_title, page_id.get(i), wiki_title.get(i), wiki_title.get(i));
			System.out.println("count: "+count);
			if(count < 3)//也只是权宜之计
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
				else//进行树的扩充
				{
					System.out.println("not one node:");
					//制约算法效率的瓶颈
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

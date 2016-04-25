package WikiConcept;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import FanJian.Content;

public class new_pipeline 
{
	public void pipe(ArrayList<String> ca_entities, Tree_C tree, ArrayList<String> parse_re, HashMap<String,Integer> p, HashMap<String,Integer> n,Category_merge cm) throws Exception
	{
		//Tree_C tree = new Tree_C();
		getConceptsFWT fwt = new getConceptsFWT();

		
		HashMap<String, Integer> es_map = new HashMap<String,Integer>();
		Sentiment_parse_pathch sentiment = new Sentiment_parse_pathch();
		sentiment.Init(parse_re);
		
		
		//先进行消歧
	    //首先如果数据库里只有一个候选项就没有必要进行网页访问了。
		
		
		ArrayList<String> wiki_title = new ArrayList<String>();
		for(String entity:ca_entities)
		{
			String title = fwt.getReFB(entity, null, ca_entities, false);
			//在这里添加情感信息
			if(title != null)
			{
				wiki_title.add(title);
				HashMap<String,Integer> scaned = new HashMap<String,Integer>();
				es_map.put(title, sentiment.eword_find(entity, p, n, scaned));
			}
		}
		//显示情感词与title的绑定结果
		//System.out.println("es_map"+es_map.toString());
		
		Content c = new Content();
		//再次进行check 以消除 粉丝 四叶草 这种歧义 暂时的想法
		//这里要进行title的过滤 而不是最开始的关键词 4.8
		for(int i = 0; i < wiki_title.size(); i++)
		{
			int count = c.CountWebPage(wiki_title, wiki_title.get(i));
			if(count < 3)//也只是权宜之计
			{
				wiki_title.remove(i);
				i--;
			}
		}
		System.out.println("final wiki_title: "+wiki_title.toString());
		
		
		
		
		//添加情感词的绑定
		
		for(String title:wiki_title)
		{
			if(wiki_title == null)
				continue;
			else 
			{
				if(tree.nodes.size() == 0)
				{
					int senti = es_map.get(title);
					Node leaf = new Node(title,null);
					leaf.sentiment = (double)senti;
					leaf.app_time = 1;
					tree.nodes.put(leaf, 0);
					tree.max_depth = 0;
					tree.top = leaf;
					System.out.println("new leaf "+title);
				}
				else//进行树的扩充
				{
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

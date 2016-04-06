package WikiConcept;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import FanJian.Content;

public class new_pipeline 
{
	public void pipe(ArrayList<String> ca_entities, Tree_C tree) throws Exception
	{
		//Tree_C tree = new Tree_C();
		getConceptsFWT fwt = new getConceptsFWT();
		Category_merge cm = new Category_merge();
		cm.Init();
//		cm.mutural_as_hie("Catefory:美国篮球运动员", "姚明");
//		String as = cm.mu_as_hie("Category:篮球运动员", "姚明");
		
		
		//System.out.println(cm.father_category_sql("Category:中国篮球运动员").toString());
		//先进行消歧
		ArrayList<String> wiki_title = new ArrayList<String>();
		for(String entity:ca_entities)
		{
			String title = fwt.getReFB(entity, null, ca_entities, false);
			if(title != null)
				wiki_title.add(title);
		}
		Content c = new Content();
		//再次进行check 以消除 粉丝 四叶草 这种歧义 暂时的想法
		for(int i = 0; i < wiki_title.size(); i++)
		{
			int count = c.CountWebPage(wiki_title, wiki_title.get(i));
			if(count == 0)
			{
				wiki_title.remove(i);
				i--;
			}
		}
		System.out.println("final wiki_title: "+wiki_title.toString());
		for(String title:wiki_title)
		{
			if(wiki_title == null)
				continue;
			else 
			{
				if(tree.nodes.size() == 0)
				{
					Node leaf = new Node(title,null);
					tree.nodes.put(leaf, 0);
					tree.max_depth = 0;
					tree.top = leaf;
					System.out.println("new leaf "+title);
				}
				else//进行树的扩充
				{
					cm.c_merge(tree, title, 0, wiki_title);
				}
			}
		}
		cm.close();
		//got category tree
		System.out.println(tree.toString());
		
		
	}

}

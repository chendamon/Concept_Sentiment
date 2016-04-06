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
//		cm.mutural_as_hie("Catefory:���������˶�Ա", "Ҧ��");
//		String as = cm.mu_as_hie("Category:�����˶�Ա", "Ҧ��");
		
		
		//System.out.println(cm.father_category_sql("Category:�й������˶�Ա").toString());
		//�Ƚ�������
		ArrayList<String> wiki_title = new ArrayList<String>();
		for(String entity:ca_entities)
		{
			String title = fwt.getReFB(entity, null, ca_entities, false);
			if(title != null)
				wiki_title.add(title);
		}
		Content c = new Content();
		//�ٴν���check ������ ��˿ ��Ҷ�� �������� ��ʱ���뷨
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
				else//������������
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

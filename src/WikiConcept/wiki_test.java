package WikiConcept;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class wiki_test 
{
	public static void main(String[] args) throws Exception
	{
		//String one = "ϲ��";
		String two = "�Ʊȡ���������";
		String three = "ղķ˹������";
		String four = "���ġ�Τ��";
		String five = "Ҧ��";
		ArrayList<String> weibo_no_stopwords = new ArrayList<String>();
		//weibo_no_stopwords.add(one);
		weibo_no_stopwords.add(two);
		weibo_no_stopwords.add(three);
		weibo_no_stopwords.add(four);
		weibo_no_stopwords.add(five);
		Tree_C tree = new Tree_C();
		new_pipeline pp = new new_pipeline();
		pp.pipe(weibo_no_stopwords,tree,null,null,null,null);
		
//		Tree_C the_tree = new Tree_C();
//		buildCTree BC_tree = new buildCTree();
//		int size = weibo_no_stopwords.size();
//		for(int j = 0; j < size; j++)
//		{
//			String name = weibo_no_stopwords.get(j);
//			//���һ�������������ʱ���õ�
//			Tree_C t = BC_tree.buildTree(name, null, 0, null,weibo_no_stopwords);
//			the_tree.addTree(t);
//		}
//		System.out.println("�����������"+the_tree.toString());
//		//�õ��û���и�����the_tree,����Ȩ��
//		Con_final con_f = new Con_final();
//		con_f.cal_CP(the_tree);
//		HashMap<String,Double> cs_re = con_f.getTopK(50);
//		System.out.println("����:��ֵ�����"+cs_re.toString());
	}

}

package WikiConcept;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
//16/3/28



public class Tree_Processing 
{
	//�����Լ���еĴ���
	public Tree_C Tree_propagate(Tree_C tree)
	{
		Iterator iter = tree.nodes.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
	        Node node_temp = (Node) entry.getKey();
	        int depth = (int) entry.getValue();
	        
	        if(node_temp.sons.size() > 0&&node_temp.app_time == 0)
	        {
	        	this.process_Node(tree, node_temp.getName());
	        }
	        
	    }
		return tree;
	}
	public void process_Node(Tree_C tree, String name)
	{
		Node t_n = this.IR_byname(tree, name);
		if(t_n != null)
		{
			int app_time = 0;
			double sentiment = 0;
			int count = 0;
			ArrayList<String> sons = t_n.sons;
			for(String son:sons)
			{
				Node son_temp = this.IR_byname(tree, son);
				if(son_temp != null&&(son_temp.app_time == 0))//��еĳ�ʼ��
					this.process_Node(tree, son);
				app_time += son_temp.app_time;
			    sentiment += son_temp.sentiment;
				count++;
			}
			t_n.app_time = app_time;
			t_n.sentiment = sentiment/count;// �費��Ҫ����Ū��
		}
	}
	public Node IR_byname(Tree_C tree, String name)
	{
		Iterator iter = tree.nodes.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
	        Node node_temp = (Node) entry.getKey();
	        if(node_temp.equals(name))
	        {
	        	return node_temp;
	        }
	    }
		return null;
	}
	
	//�ڵ�Ȩ�صļ���,�����Ȱ���֮ǰ����������ݽ���
	//depth sons
	public double Rank(String category, Tree_C tree)
	{
		Node node = this.IR_byname(tree, category);
		int fre = node.app_time;
		int depth = node.depth;
		int bra = node.sons.size();
		
		double rank = fre*(1/Math.pow(bra, depth));
		return rank;
			
	}
	

}

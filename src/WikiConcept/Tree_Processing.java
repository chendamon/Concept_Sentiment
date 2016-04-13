package WikiConcept;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
//16/3/28



public class Tree_Processing 
{
	//次数以及情感的传播
	public Tree_C Tree_propagate(Tree_C tree)
	{
//		Iterator iter = tree.nodes.entrySet().iterator();
//		while (iter.hasNext()) 
//		{
//			Map.Entry entry = (Map.Entry) iter.next();
//	        Node node_temp = (Node) entry.getKey();
//	        int depth = (int) entry.getValue();
//	        
//	        if(node_temp.sons.size() > 0&&node_temp.app_time == 0)
//	        {
//	        	this.process_Node(tree, node_temp.getName());
//	        }
//	        
//	    }
		String top_name = tree.top.category_name;
		this.process_Node(tree, top_name);
		return tree;
	}
	public void process_Node(Tree_C tree, String name)
	{
		System.out.println("process_Node: "+name);
		Node t_n = this.IR_byname(tree, name);
		if(t_n != null)
		{
			int app_time = 0;
			double sentiment = 0;
			int count = 0;
			ArrayList<String> sons = t_n.sons;
			if(sons.size() > 0)
			{
				for(String son:sons)
				{
					Node son_temp = this.IR_byname(tree, son);
					if(son_temp != null&&(son_temp.app_time == 0))//情感的初始化
						this.process_Node(tree, son);
					app_time += son_temp.app_time;
				    sentiment += son_temp.sentiment;
					count++;
				}
			}
			t_n.app_time = app_time;
			if(count != 0)
				t_n.sentiment = sentiment/(double)count;// 需不需要重新弄？
			else t_n.sentiment = 0.0;
		}
	}
	public Node IR_byname(Tree_C tree, String name)
	{
		Iterator iter = tree.nodes.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
	        Node node_temp = (Node) entry.getKey();
	        if(node_temp.getName().equals(name))
	        {
	        	return node_temp;
	        }
	    }
		return null;
	}
	
	//节点权重的计算,还是先按照之前论文里的内容进行
	//depth sons
	public double Rank(String category, Tree_C tree)
	{
		
		Node node = this.IR_byname(tree, category);
		int fre = node.app_time;
		System.out.println("rank: "+category+" apptime: "+fre);
		int depth = node.depth;
		int bra = node.sons.size()+1;//确保非0
		
		double rank = fre*(1/Math.pow(bra, depth));
		return rank;
			
	}
	

}

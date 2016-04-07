package WikiConcept;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/*
 * 构建某一个category的category树
 * author biront
 * date:2015/12/3
 */
public class Tree_C 
{
	//一堆节点集合
	//改成hashmap，映射到自己的层数（倒置的）
	HashMap<Node,Integer> nodes;
	Node root;
	int max_depth;//最大深度
	Node top;//top category
	public Tree_C()
	{
		this.nodes = new HashMap<Node,Integer>();
		this.root = null;
	}
	public HashMap<Node,Integer> getTNodes()
	{
		return this.nodes;
	}
	public void addNode(Node node, int dep)
	{
		this.nodes.put(node,dep);
		if(this.nodes.size() == 1)
			this.root = node;
	}
	//判断是不是已经包含了某一类别
	public boolean contain(String name)
	{
		Node t = null;
		int depth = 0;
		boolean is_c = false;
		Iterator it = nodes.entrySet().iterator();  
		while(it.hasNext())
		{
			Map.Entry<Node, Integer> entry = (Entry<Node, Integer>) it.next();
		    t = entry.getKey();
			depth = entry.getValue();
			if(t.getName().equals(name))
			{
				t.app_time++;//出现次数的计数，已经做完了
				is_c = true;
				it.remove();
				break;
			}
			
		}
		if(is_c)
			nodes.put(t, depth);
		return is_c;
	}
	public String toString()
	{
		String line = "";
		for(int i = 0; i <= max_depth; i++)
		{
			line += "depth: "+i+"\n";
			for(Entry<Node,Integer> entry:nodes.entrySet())
			{
				Node temp = entry.getKey();
				int de = entry.getValue();
				if(de == i)
					line += temp.getName()+"\t"+temp.getSentiment();
			}
		}
		return line;
	}

}

package WikiConcept;

import java.util.HashSet;
import java.util.Iterator;

/*
 * 构建某一个category的category树
 * author biront
 * date:2015/12/3
 */
public class Tree_C 
{
	//一堆节点集合
	HashSet<Node> nodes;
	Node root;
	public Tree_C()
	{
		this.nodes = new HashSet<Node>();
		this.root = null;
	}
	public HashSet<Node> getTNodes()
	{
		return this.nodes;
	}
	public void addNode(Node node)
	{
		this.nodes.add(node);
		if(this.nodes.size() == 1)
			this.root = node;
	}
	//概念树扩展
	public void addTree(Tree_C t)
	{
		Iterator it = t.getTNodes().iterator();
		while(it.hasNext())
		{
			Node temp = (Node) it.next();
			this.nodes.add(temp);
		}
	}

}

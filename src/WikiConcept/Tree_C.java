package WikiConcept;

import java.util.HashSet;
import java.util.Iterator;

/*
 * ����ĳһ��category��category��
 * author biront
 * date:2015/12/3
 */
public class Tree_C 
{
	//һ�ѽڵ㼯��
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
	//��������չ
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

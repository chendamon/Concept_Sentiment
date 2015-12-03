package WikiConcept;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
 * 根据一个category得到真个关于category的概念树
 * author biront
 * date 2015/12/3
 */
public class buildCTree 
{
	getConceptsFWT fwt;
	public buildCTree()
	{
		this.fwt = new getConceptsFWT();
	}
	public Tree_C buildTree(String name, String url) throws IOException
	{
		//深度优先搜索来做,迭代
		Tree_C tree = new Tree_C();
		Node node = new Node(name,url);
		tree.addNode(node);
		fwt.clear();
		fwt.getReFB(null, url);
		HashMap<String,String> sons = fwt.getSUrls();
		int size = sons.size();
		
		
		Iterator iter = sons.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			Node t = new Node(key,val);
			node.addSon(t);
			Tree_C tree_t = buildTree(key,val);
			tree.addTree(tree_t);
		}
		return tree;
		
	}

}

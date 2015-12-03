package WikiConcept;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
 * ����һ��category�õ��������category�ĸ�����
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
	public Tree_C buildTree(String name, String url,int dep) throws IOException
	{
		//���������������,����
		Tree_C tree = new Tree_C();
		Node node = new Node(name,url);
		node.setDep(dep);
		tree.addNode(node);
		fwt.clear();
		fwt.getReFB(null, url);
		HashMap<String,String> sons = fwt.getSUrls();
		int size = sons.size();
		node.setBra(size);
		
		Iterator iter = sons.entrySet().iterator();
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			Node t = new Node(key,val);
			node.addSon(t);
			Tree_C tree_t = buildTree(key,val,dep++);
			tree.addTree(tree_t);
		}
		return tree;
		
	}

}

package WikiConcept;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import Sentiment.findSent;

/*
 * 根据一个category得到真个关于category的概念树
 * author biront
 * date 2015/12/3
 */
public class buildCTree 
{
	getConceptsFWT fwt;
	findSent fS;
	public buildCTree()
	{
		this.fwt = new getConceptsFWT();
		this.fS = new findSent();
	}
	public Tree_C buildTree(String name, String url,int dep,HashMap<String,Integer> eS,ArrayList<String> entitys) throws IOException
	{
		//深度优先搜索来做,迭代
		Tree_C tree = new Tree_C();
		Node node = new Node(name,url);
		node.setDep(dep);
		tree.addNode(node);
		
		//如果是根节点，则需要读取一个情感词值
		if(tree.root == node)
		{
			node.setSen((double)this.fS.getS(eS, name));
		}
		fwt.clear();
		fwt.getReFB(name, url,entitys);
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
			//衰减函数暂时定为1/2^d,d为深度
			double new_s = tree.root.getSentiment()*Math.pow(2, 0-dep);
			t.setSen(new_s);
			node.addSon(t);
			
			
			Tree_C tree_t = buildTree(key,val,dep++,eS,entitys);
			tree.addTree(tree_t);
		}
		return tree;
		
	}

}

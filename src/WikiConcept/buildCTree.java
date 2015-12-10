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
 * ����һ��category�õ��������category�ĸ�����
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
		//���������������,����
		Tree_C tree = new Tree_C();
		Node node = new Node(name,url);
		node.setDep(dep);
		tree.addNode(node);
		
		//����Ǹ��ڵ㣬����Ҫ��ȡһ����д�ֵ
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
			//˥��������ʱ��Ϊ1/2^d,dΪ���
			double new_s = tree.root.getSentiment()*Math.pow(2, 0-dep);
			t.setSen(new_s);
			node.addSon(t);
			
			
			Tree_C tree_t = buildTree(key,val,dep++,eS,entitys);
			tree.addTree(tree_t);
		}
		return tree;
		
	}

}

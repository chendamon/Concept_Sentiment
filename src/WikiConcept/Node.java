package WikiConcept;

import java.util.ArrayList;

/*
 * Tree的节点类
 * 包含category以及url
 * author:biront
 * date:2015/12/3
 */
public class Node 
{
	String category_name;
	String category_url;
	//存储子节点，暂时用这样的方式
	ArrayList<Node> sons;
	public Node(String name, String url)
	{
		this.category_name = name;
		this.category_url = url;
		this.sons = new ArrayList<Node>();
	}
	public String getName()
	{
		return this.category_name;
	}
	public String getUrl()
	{
		return this.category_url;
	}
	public void addSon(Node node)
	{
		this.sons.add(node);
	}
	public ArrayList<Node> getSons()
	{
		return this.sons;
	}
	
	

}

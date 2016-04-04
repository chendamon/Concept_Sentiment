package WikiConcept;

import java.util.ArrayList;

/*
 * Tree的节点类
 * 包含category以及url
 * author:biront
 * date:2015/12/3
 * 
 *需要在node里加入情感，并进行衰减计算，找到合适的衰减函数
 *2015/12/7
 */
public class Node 
{
	String category_name;
	String category_url;
	int depth;
	int branch;
	int app_time;//出现次数计算权重的时候很有必要
	double sentiment;
	//存储子节点，暂时用这样的方式
	ArrayList<String> sons;
	public Node(String name, String url)
	{
		this.category_name = name;
		this.category_url = url;
		this.sons = new ArrayList<String>();
		this.depth = -1;
		this.branch = -1;
		this.sentiment = -99;
	}
	public void setSen(Double s)
	{
		this.sentiment = s;
	}
	public double getSentiment()
	{
		return this.sentiment;
	}
	public void setDep(int dep)
	{
		this.depth = dep;
	}
	public void setBra(int bra)
	{
		this.branch = bra;
	}
	public int getBra()
	{
		return this.branch;
	}
	public int getDep()
	{
		return this.depth;
	}
	public String getName()
	{
		return this.category_name;
	}
	public String getUrl()
	{
		return this.category_url;
	}
	public void addSon(String node)
	{
		this.sons.add(node);
	}
	public ArrayList<String> getSons()
	{
		return this.sons;
	}
	
	

}

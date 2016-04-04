package WikiConcept;

import java.util.ArrayList;

/*
 * Tree�Ľڵ���
 * ����category�Լ�url
 * author:biront
 * date:2015/12/3
 * 
 *��Ҫ��node�������У�������˥�����㣬�ҵ����ʵ�˥������
 *2015/12/7
 */
public class Node 
{
	String category_name;
	String category_url;
	int depth;
	int branch;
	int app_time;//���ִ�������Ȩ�ص�ʱ����б�Ҫ
	double sentiment;
	//�洢�ӽڵ㣬��ʱ�������ķ�ʽ
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

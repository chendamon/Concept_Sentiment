package WikiConcept;
/*
 * 4.11
 * hashmap不能排序 
 * 用数组来进行排序
 */
public class Point 
{
	String name;
	double weight;
	double sentiment;
	public Point()
	{
		this.name = null;
		this.weight = 0.0;
		this.sentiment = 99.0;
	}
	public Point(String name, double weight, double sentiment)
	{
		this.name = name;
		this.weight = weight;
		this.sentiment = sentiment;
	}
	public String getName()
	{
		return this.name;
	}
	public double getWeight()
	{
		return this.weight;
	}
	public double getSen()
	{
		return this.sentiment;
	}

}

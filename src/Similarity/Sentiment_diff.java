package Similarity;
/*
 * 2016/2/16
 * 衡量两个个体针对同一概念情感之间的相似性
 * 两种假设：
 *1.情感趋紧完全相同或者相反更容易转发  e-(正态分布）
 *2.情感只有趋紧完全相同更容易转发 更改正态分布u的值

 */
public class Sentiment_diff 
{
	//第一种假设
	public double diff_abs(double s1, double s2)
	{
		double abs = 0;
		if(s1 <= s2)
			abs = s2-s1;
		else abs = s1-s2;
		
		double max = (1/Math.sqrt(2*Math.PI));
		return max-this.gaussian(abs);
		
	}
	//计算正态分布的值 u=1 theta=1
	double gaussian(double x)
	{
		double fx = (1/Math.sqrt(2*Math.PI))*Math.exp(-0.5*Math.pow(x-1, 2));
		return fx;
	}
	double gaussian_2(double x)
	{
		double fx = (1/Math.sqrt(2*Math.PI))*Math.exp(-0.5*Math.pow(x-2, 2));
		return fx;
	}
    //第二种假设 u=2
	public double diff(double s1, double s2)
	{
		double abs = 0;
		if(s1 <= s2)
			abs = s2-s1;
		else abs = s1-s2;
		
		double max = (1/Math.sqrt(2*Math.PI));
		return max-this.gaussian_2(abs);
	}
}

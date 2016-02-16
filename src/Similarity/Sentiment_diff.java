package Similarity;
/*
 * 2016/2/16
 * ���������������ͬһ�������֮���������
 * ���ּ��裺
 *1.���������ȫ��ͬ�����෴������ת��  e-(��̬�ֲ���
 *2.���ֻ��������ȫ��ͬ������ת�� ������̬�ֲ�u��ֵ

 */
public class Sentiment_diff 
{
	//��һ�ּ���
	public double diff_abs(double s1, double s2)
	{
		double abs = 0;
		if(s1 <= s2)
			abs = s2-s1;
		else abs = s1-s2;
		
		double max = (1/Math.sqrt(2*Math.PI));
		return max-this.gaussian(abs);
		
	}
	//������̬�ֲ���ֵ u=1 theta=1
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
    //�ڶ��ּ��� u=2
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

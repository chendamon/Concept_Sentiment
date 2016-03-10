package Similarity;

import java.util.ArrayList;

import org.python.core.PyObject;
import org.python.util.*;;
/*
 * �����û�֮�����ƶȵģ�Ҫ����word2vec��ʵ��
 * author biront
 * date 2015/12/7
 * ֻ��һ���ˣ�����ģ�Ϳ�����
 * ���ݵ�����
 * �ü����µ�΢�����ݽ���word2vec��ѵ����Ȼ����΢��ת�����Ƽ�������������ĵ�ʵ�鲿�֣�
 * ��һ���ֵļ����㷨�����word2vec�õ��Ľ���ܹ������û��ʵĴ����������ܹ��������������ʵ����ƶ�
 * �������û����ƶ��㷨
 * user category:weight cate
 * 2016/2/24
 * word2vec����������֮������ƶ�
 */
public class SModel 
{
	PythonInterpreter pe;
	public void SModel()
	{
		this.pe = new PythonInterpreter();
	}
	//����word2vecģ��
	void Init()
	{  
		pe.exec("import gensim");
		pe.exec("model = gensim.models.Word2Vec.load(\"wiki.zh.text.model\")");
		System.out.println("word2vec model load done.");
	}
	//��μ������ƶȣ�
	//������0.5 0.7 ���֣�0.6 0.8 �������0.9 -0.9 && ���֣�0.7 0.5 ������0.5 0.4 �������0.4 0.9
	//how to cal?
	//�������������ʵ����ƶ�
	double Sfw2v(String a, String b)
	{
		PyObject re = pe.eval("model.similarity(u\""+a+"\", u\""+b+"\")");
		return re.asDouble();
	}
	//��μ������еĹؼ��ʣ�
	//����������û����û�֮�䣬΢�����û�֮�䣨΢���Ļ�Ĭ�ϸ������Ϊ0��
	
	//�����û�֮��Ļ�����е����ƶȣ�����һ��concept��weight top-k
	//��������ƽ����
	public double UU_Similarity(ArrayList<String> c_a, ArrayList<Double> w_a, ArrayList<String> c_b, ArrayList<Double> w_b, int k)
	{
		Sentiment_diff s_d = new Sentiment_diff();
		//��Ϊb������Ŀǰ��ע���û�
		double score = 0;
		for(int i = 0; i < k; i++)
		{
			double max = 0;
			
			for(int j = 0; j < k; j++)
			{
				double temp = this.Sfw2v(c_b.get(i), c_a.get(j))*w_a.get(j)*w_b.get(i);
				double a = 0; double b = 0;//a,b ��Ӧ��sentiment
				double sdiff = s_d.diff_abs(a, b);
				if(max < temp*sdiff)
					max = temp*sdiff;
			}
			score += max;
		}
		return score/k;
	}
	
	//weibo���û�֮������ƶ�
	//һ��conceptû��weight ��sentimentΪ0����һ��Ϊ��������
	public double TU_Similarity(ArrayList<String> c_t, ArrayList<String> c_b, ArrayList<Double> w_b, int k)
	{
		Sentiment_diff s_d = new Sentiment_diff();
		int tc_size = c_t.size();
		double score = 0;
		for(int i = 0; i < k; i++)
		{
			double max = 0;
			
			for(int j = 0; j < tc_size; j++)
			{
				double temp = this.Sfw2v(c_b.get(i), c_t.get(j))*1*w_b.get(i);
				double b = 0;//a,b ��Ӧ��sentiment
				double sdiff = s_d.diff_abs(0, b);
				if(max < temp*sdiff)
					max = temp*sdiff;
			}
			score += max;
		}
		return score/k;
	}
	

}

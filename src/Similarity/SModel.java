package Similarity;

import java.util.ArrayList;

import org.python.core.PyObject;
import org.python.util.*;;
/*
 * 衡量用户之间相似度的，要加入word2vec来实现
 * author biront
 * date 2015/12/7
 * 只差一步了！加油模型快完了
 * 数据的问题
 * 用几个月的微博数据进行word2vec的训练？然后，再微博转发，推荐（看看相关论文的实验部分）
 * 这一部分的计算算法假设从word2vec得到的结果能够返回用户词的词向量，并能够计算任意两个词的相似度
 * 来构建用户相似度算法
 * user category:weight cate
 * 2016/2/24
 * word2vec返回两个词之间的相似度
 */
public class SModel 
{
	PythonInterpreter pe;
	public void SModel()
	{
		this.pe = new PythonInterpreter();
	}
	//加载word2vec模型
	void Init()
	{  
		pe.exec("import gensim");
		pe.exec("model = gensim.models.Word2Vec.load(\"wiki.zh.text.model\")");
		System.out.println("word2vec model load done.");
	}
	//如何计算相似度？
	//体育：0.5 0.7 音乐：0.6 0.8 计算机：0.9 -0.9 && 音乐：0.7 0.5 体育：0.5 0.4 计算机：0.4 0.9
	//how to cal?
	//单纯返回两个词的相似度
	double Sfw2v(String a, String b)
	{
		PyObject re = pe.eval("model.similarity(u\""+a+"\", u\""+b+"\")");
		return re.asDouble();
	}
	//如何计算带情感的关键词？
	//两种情况：用户与用户之间，微博与用户之间（微博的话默认概念情感为0）
	
	//两个用户之间的基于情感的相似度，都有一组concept：weight top-k
	//最大的算术平均？
	public double UU_Similarity(ArrayList<String> c_a, ArrayList<Double> w_a, ArrayList<String> c_b, ArrayList<Double> w_b, int k)
	{
		Sentiment_diff s_d = new Sentiment_diff();
		//认为b是我们目前关注的用户
		double score = 0;
		for(int i = 0; i < k; i++)
		{
			double max = 0;
			
			for(int j = 0; j < k; j++)
			{
				double temp = this.Sfw2v(c_b.get(i), c_a.get(j))*w_a.get(j)*w_b.get(i);
				double a = 0; double b = 0;//a,b 对应的sentiment
				double sdiff = s_d.diff_abs(a, b);
				if(max < temp*sdiff)
					max = temp*sdiff;
			}
			score += max;
		}
		return score/k;
	}
	
	//weibo与用户之间的相似度
	//一组concept没有weight 且sentiment为0，另一组为正常数据
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
				double b = 0;//a,b 对应的sentiment
				double sdiff = s_d.diff_abs(0, b);
				if(max < temp*sdiff)
					max = temp*sdiff;
			}
			score += max;
		}
		return score/k;
	}
	

}

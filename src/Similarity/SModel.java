package Similarity;

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

}

package Similarity;

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

}

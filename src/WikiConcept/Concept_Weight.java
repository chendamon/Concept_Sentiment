package WikiConcept;
import java.util.Iterator;

/*
 * ������Ҫ����merge
 * ֱ����ĳ��category�ķ�ֵ�������category������Ȩ����ֽ���Ű棬���ܻ�Ҫ����ʵ����չ��
 * ������д���Ժ��������ٽ��з���
 * author biront
 * date 2015/12/3
 * ���㹫ʽΪ��rank(c)=freq(c)*w_c,w_c=1/b^d b ��֦ d depth
 * ÿ�ζ�����Ȼ��ȡ��ֵ
 * ##��û�м����������
 */
public class Concept_Weight 
{
	public double cal_Weight(String category, Tree_C tree)
	{
//		int freq = 0;
//		double sum = 0;
//		Iterator it = tree.getTNodes().iterator();
//		while(it.hasNext())
//		{
//			Node temp = (Node) it.next();
//			if(temp.getName().equals(category))
//			{
//				freq++;
//				sum += 1/(Math.pow(temp.getBra(), temp.getDep()));
//			}
//		}
//		return sum/freq;
		return 0.0;
	}

}

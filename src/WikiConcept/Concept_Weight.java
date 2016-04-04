package WikiConcept;
import java.util.Iterator;

/*
 * 并不需要进行merge
 * 直接算某个category的分值（这里的category可以是权威报纸的排版，可能还要进行实体扩展）
 * 先这样写，以后有问题再进行反馈
 * author biront
 * date 2015/12/3
 * 计算公式为：rank(c)=freq(c)*w_c,w_c=1/b^d b 分枝 d depth
 * 每次都计算然后取均值
 * ##并没有加入情感现在
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

package Pipe;

import java.util.ArrayList;
import java.util.HashMap;

import NER.Entity_Sent;
import NER.StopWords;
import NER.jieba_seg;
import Sentiment.Parse;
import WikiConcept.Tree_C;
import WikiConcept.buildCTree;

/*
 * ���һ��΢��������һ���ײ������̣�����
 * �ִʣ�ȥͣ�ôʣ�parse����дʷ��֣�ʵ���wiki���ң����Ĺ�����
 * author biront
 * date 2015/12/10
 */
public class weibo_P 
{
	public Tree_C Weibo_P(String weibo) throws Exception
	{
		//seg
		jieba_seg seg = new jieba_seg();
		ArrayList<String> seg_result = seg.jieba_Seg(weibo);
		//sentiment
		Parse par = new Parse();
		ArrayList<String> parse_result = par.Parse(seg_result);
		//remove stopwords
		StopWords sw = new StopWords();
		sw.Init("res/����ͣ�ôʿ�.txt");
		ArrayList<String> weibo_seg_s = sw.rmStopW(seg_result);
		//entity sentiment bangding
		Entity_Sent en_s = new Entity_Sent();
		HashMap<String,Integer> eS = en_s.gule(weibo_seg_s, parse_result);
		
		//��ÿ��ʵ�幹��concept tree
		Tree_C the_tree = new Tree_C();
		buildCTree BC_tree = new buildCTree();
		int size = weibo_seg_s.size();
		for(int i = 0; i < size; i++)
		{
			String name = weibo_seg_s.get(i);
			//���һ�������������ʱ���õ�
			Tree_C t = BC_tree.buildTree(name, null, 0, eS,weibo_seg_s);
			the_tree.addTree(t);
		}
		
		return the_tree;
		
		
		
		
		
	}

}

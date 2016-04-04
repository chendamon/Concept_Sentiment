package Sentiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import edu.stanford.nlp.parser.lexparser.*;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseGrammaticalStructure;
/*
 * parse部分
 * 为找到实体对应的情感词做准备
 * author biront
 * 2015/12/1
 */
public class Parse 
{
	LexicalizedParser lp;
	public Parse()
	{
		this.lp = null;
	}
	public void Init()
	{
		String model = "edu/stanford/nlp/models/lexparser/chineseFactored.ser.gz";
		lp = LexicalizedParser.loadModel(model);
	}
	public ArrayList<String> Parse(ArrayList<String> seg_result)
	{
		//重构分词之后的句子
		int size = seg_result.size();
		String sentence = "";
		for(int i = 0; i < size-1; i++)
		{
			sentence += seg_result.get(i)+"\t";
//			if(seg_result.get(i).endsWith("\n"))
//				sentence += "\n";
//			else sentence += "\t";
			
		}
		sentence += seg_result.get(size-1);
		System.out.println("sentence: "+sentence);
		//parse part
		//2015/12/1
//		String model = "edu/stanford/nlp/models/lexparser/chineseFactored.ser.gz";
//		LexicalizedParser lp = LexicalizedParser.loadModel(model);
		Tree t = lp.parse(sentence);
		System.out.println(t);
		//变换其他格式,依赖格式
		ChineseGrammaticalStructure gs = new ChineseGrammaticalStructure(t);
	    Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
	    System.out.println(tdl);
	    
	    ArrayList<String> ps = new ArrayList<String>();
	    for (Iterator<TypedDependency> iterator = tdl.iterator(); iterator.hasNext();) 
	    {
            String arr = iterator.next().toString();
            ps.add(arr);
        }
	    return ps;
	//return null;
	}

}

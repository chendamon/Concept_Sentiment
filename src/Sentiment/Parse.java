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
		String[] options = {"-MAX_ITEMS", "500000"};
		String model = "edu/stanford/nlp/models/lexparser/chinesePCFG.ser.gz";
		lp = LexicalizedParser.loadModel(model,options);
	}
	public ArrayList<String> ps(String weibo)
	{
		//String[] options = {"-MAX_ITEMS", "500000"};
		Tree t = lp.parse(weibo);
		
		System.out.println(t);
		if(!t.toString().contains("ROOT"))
			return null;
		//变换其他格式,依赖格式
		ChineseGrammaticalStructure gs = new ChineseGrammaticalStructure
				(t);
		
	    Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
	   // System.out.println(tdl);
	    
	    ArrayList<String> ps = new ArrayList<String>();
	    for (Iterator<TypedDependency> iterator = tdl.iterator(); iterator.hasNext();) 
	    {
            String arr = iterator.next().toString();
            ps.add(arr);
        }
	    return ps;
	}
	public  ArrayList<String> Parse(ArrayList<String> seg_result)
	{
		Tree t = lp.parseStrings(seg_result);
		//System.out.println(t);
		//变换其他格式,依赖格式
		ChineseGrammaticalStructure gs = new ChineseGrammaticalStructure(t);
	    Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
	   // System.out.println(tdl);
	    
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

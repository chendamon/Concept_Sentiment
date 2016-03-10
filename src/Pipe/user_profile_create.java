package Pipe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import NER.Entity_Sent;
import NER.StopWords;
import NER.URLdrop;
import NER.jieba_seg;
import Sentiment.Parse;
import WikiConcept.Con_final;
import WikiConcept.Tree_C;
import WikiConcept.buildCTree;

/*
 * 3/9
 * 用户profile构建的主控室
 * 好中二zzzzz
 */
public class user_profile_create 
{
	//主控函数
	void crush_it() throws Exception
	{
		//获得用户列表
		String[] user_list = this.get_user_list("active_user");
		System.out.println("user_list read done.");
		int length = user_list.length;
		for(int i = 0; i < length; i++)
		{
			System.out.println("user id: "+user_list[i]+"possing...");
			//对每个用户进行循环
			String weibo_content = this.get_user_weibo("active_user/"+user_list[i]);
			System.out.println("user "+user_list[i]+" weibo read done.");
			//去除URL
			URLdrop url_drop = new URLdrop();
			String weibo_no_url = url_drop.url_drop(weibo_content);
			System.out.println("user "+user_list[i]+" weibo url drop done");
			//分词
			jieba_seg seg = new jieba_seg();
			ArrayList<String> weibo_seg = seg.jieba_Seg(weibo_no_url);
			System.out.println("user "+user_list[i]+" weibo seg done.");
			//parse词法分析,进行情感的标注
			Parse par = new Parse();
			ArrayList<String> parse_result = par.Parse(weibo_seg);
			//去停用词
			StopWords sw = new StopWords();
			sw.Init("stop file path");//stopword file path
			ArrayList<String> weibo_no_stopwords = sw.rmStopW(weibo_seg);
			
			//entity sentimen绑定
			Entity_Sent en_s = new Entity_Sent();
			HashMap<String,Integer> eS = en_s.gule(weibo_no_stopwords, parse_result);
			
			break;
			//开始进行entity tree的构建
			//对每个实体构造concept tree
//			Tree_C the_tree = new Tree_C();
//			buildCTree BC_tree = new buildCTree();
//			int size = weibo_no_stopwords.size();
//			for(int j = 0; j < size; j++)
//			{
//				String name = weibo_no_stopwords.get(j);
//				//最后一个参数是消歧的时候用的
//				Tree_C t = BC_tree.buildTree(name, null, 0, eS,weibo_no_stopwords);
//				the_tree.addTree(t);
//			}
//			//得到用户情感概念树the_tree,计算权重
//			Con_final con_f = new Con_final();
//			con_f.cal_CP(the_tree);
//			HashMap<String,Double> cs_re = con_f.getTopK(50);
//			//将结果写入磁盘文件中
//			File cs_file = new File("user_concept_sentiment");
//			if(!cs_file.exists())
//				cs_file.createNewFile();
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cs_file,true),"utf-8"));
//			writer.append(user_list[i]+":");
//			writer.flush();
//			Iterator iter = cs_re.keySet().iterator();
//			while (iter.hasNext()) 
//			{
//				String concept_name = (String) iter.next();
//				double concept_score = cs_re.get(concept_name);
//				writer.append("\t"+concept_name+":"+concept_score);
//				writer.flush();
//			}
//			writer.newLine();
//			writer.close();
			
		}
		
	}
	//返回用户列表
	String[] get_user_list(String dirname) throws Exception
	{
		File file = new File(dirname);
		if(!file.exists())
			throw new Exception("path not found!");
		String[] user_list = file.list();
		return user_list;
	}
	//返回用户某个月的微博合集
	//将所有微博成一个string，减少分词次数
	String get_user_weibo(String  path) throws Exception
	{
		File user_file = new File(path);
		if(!user_file.exists())
			throw new Exception("user file not found!");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(user_file),"utf-8"));
		String line = "";
		String weibo_content = "";
		while((line = reader.readLine()) != null)
		{
			String[] sp = line.split("\t");
			weibo_content += sp[6];
		}
		reader.close();
		System.out.println("user weibo read done.");
		return weibo_content;
	}

}

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
import WikiConcept.new_pipeline;

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
		//循环的时候，如果直接对用户所有微博同时进行处理可能会有问题，主要是parse
		//出现不对的依存关系
		//加\n尝试一下 失败
		for(int i = 0; i < length; i++)
		{
			System.out.println("user id: "+user_list[i]+"possing...");
			//对每个用户进行循环
			//微博换成数组 先只考虑自己的内容 如果自己内容为转发微博在考虑 转发原微博的内容
			ArrayList<String> weibo_content = this.get_user_weibo("active_user/"+user_list[i]);
			System.out.println("user "+user_list[i]+" weibo read done.");
			int weibo_size = weibo_content.size();
			jieba_seg seg = new jieba_seg();
			StopWords sw = new StopWords();
			Parse par = new Parse();
			par.Init();
			sw.Init("stopwords.txt");//stopword file path
			ArrayList<String> weibo_seg_total = new ArrayList<String>();//整合微博分词结果
			ArrayList<String> parse_total = new ArrayList<String>();//整合句法分析结果
			
			//3 30有问题，并不能直接所有的都进行合并，还是要一条一条过，否则影响实体消歧的效果
			
			Tree_C c_tree = new Tree_C();
			new_pipeline new_p = new new_pipeline();
			for(int j = 0; j < weibo_size; j++)
			{
				//去除URL 换到微博提取的部分进行
	//			URLdrop url_drop = new URLdrop();
	//			String weibo_no_url = url_drop.url_drop(weibo_content);
	//			System.out.println("user "+user_list[i]+" weibo url drop done");
				//分词
				ArrayList<String> weibo_seg = seg.jieba_Seg(weibo_content.get(j));
				//System.out.println("user "+user_list[i]+" weibo seg done.");
				//parse词法分析,进行情感的标注
				ArrayList<String> parse_result = par.Parse(weibo_seg);
				//去停用词
				ArrayList<String> weibo_no_stopwords = sw.rmStopW(weibo_seg);
				ArrayList<String> weibo_no_po = this.rmPo(weibo_no_stopwords);
				
				//进行wiki概念树的构建
				new_p.pipe(weibo_no_po,c_tree);
				
				break;
				//进行merge
//				this.merge(weibo_seg_total, weibo_no_po);
//				this.merge(parse_total, parse_result);
			}
			//所有微博分词，分析模块结束
		//	System.out.println(user_list[i]+"分词结果为: "+weibo_seg_total.toString());
			//System.out.println(user_list[i]+"句法分析结果为: "+parse_total.toString());
			//entity sentimen绑定
			Entity_Sent en_s = new Entity_Sent();
			//HashMap<String,Integer> eS = en_s.gule(weibo_seg_total, parse_total);
			//3 30还是有问题这个地方，之后用后边的patch
			
			//进行用户profile的构建
			
			
			
			//情感词以及分析
			
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
	ArrayList<String> get_user_weibo(String  path) throws Exception
	{
		
		File user_file = new File(path);
		if(!user_file.exists())
			throw new Exception("user file not found!");
		ArrayList<String> weibo_content_array = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(user_file),"utf-8"));
		String line = "";
		String weibo_content = "";
		while((line = reader.readLine()) != null)
		{
			String[] sp = line.split("\t");
			//3 30去除用户id
			String weibo_user = sp[6].replaceAll("转发微博", "").replaceAll("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "").replaceAll("@.*? ", "");
			if(weibo_user.length() > 0)
				weibo_content_array.add(weibo_user);
			else
			{
				String weibo_src = sp[10].replaceAll("转发微博", "").replaceAll("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "").replaceAll("@.*? ", "");
				weibo_content_array.add(weibo_src);
			}
//			weibo_content += sp[6]+sp[10];
//			weibo_content = weibo_content.replaceAll("转发微博", "");
		}
		reader.close();
		System.out.println("user weibo read done.");
		//return weibo_content;
		return weibo_content_array;
	}
	ArrayList<String> merge(ArrayList<String> a,ArrayList<String> b)
	{
		int size = b.size();
		for(int i = 0; i < size; i++)
			a.add(b.get(i));
		return a;
	}
	//去掉标点符号
	ArrayList<String> rmPo(ArrayList<String> words)
	{
		for(int i = 0; i < words.size(); i++)
		{
			String word = words.get(i);
			//System.out.println("word: "+word);
			word = word.replaceAll("\\pP|\\pS", "");
			//System.out.println("word after: "+word);
			word = word.replaceAll(" ", "");
			word = word.replaceAll(" ", "");
			if(word.length() == 0)
			{
				words.remove(i);
				i--;
			}
		}
		return words;
	}
	
	

}

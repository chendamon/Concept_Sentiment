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
 * �û�profile������������
 * ���ж�zzzzz
 */
public class user_profile_create 
{
	//���غ���
	void crush_it() throws Exception
	{
		//����û��б�
		String[] user_list = this.get_user_list("active_user");
		System.out.println("user_list read done.");
		int length = user_list.length;
		//ѭ����ʱ�����ֱ�Ӷ��û�����΢��ͬʱ���д�����ܻ������⣬��Ҫ��parse
		//���ֲ��Ե������ϵ
		//��\n����һ�� ʧ��
		for(int i = 0; i < length; i++)
		{
			System.out.println("user id: "+user_list[i]+"possing...");
			//��ÿ���û�����ѭ��
			//΢���������� ��ֻ�����Լ������� ����Լ�����Ϊת��΢���ڿ��� ת��ԭ΢��������
			ArrayList<String> weibo_content = this.get_user_weibo("active_user/"+user_list[i]);
			System.out.println("user "+user_list[i]+" weibo read done.");
			int weibo_size = weibo_content.size();
			jieba_seg seg = new jieba_seg();
			StopWords sw = new StopWords();
			Parse par = new Parse();
			par.Init();
			sw.Init("stopwords.txt");//stopword file path
			ArrayList<String> weibo_seg_total = new ArrayList<String>();//����΢���ִʽ��
			ArrayList<String> parse_total = new ArrayList<String>();//���Ͼ䷨�������
			
			//3 30�����⣬������ֱ�����еĶ����кϲ�������Ҫһ��һ����������Ӱ��ʵ�������Ч��
			
			Tree_C c_tree = new Tree_C();
			new_pipeline new_p = new new_pipeline();
			for(int j = 0; j < weibo_size; j++)
			{
				//ȥ��URL ����΢����ȡ�Ĳ��ֽ���
	//			URLdrop url_drop = new URLdrop();
	//			String weibo_no_url = url_drop.url_drop(weibo_content);
	//			System.out.println("user "+user_list[i]+" weibo url drop done");
				//�ִ�
				ArrayList<String> weibo_seg = seg.jieba_Seg(weibo_content.get(j));
				//System.out.println("user "+user_list[i]+" weibo seg done.");
				//parse�ʷ�����,������еı�ע
				ArrayList<String> parse_result = par.Parse(weibo_seg);
				//ȥͣ�ô�
				ArrayList<String> weibo_no_stopwords = sw.rmStopW(weibo_seg);
				ArrayList<String> weibo_no_po = this.rmPo(weibo_no_stopwords);
				
				//����wiki�������Ĺ���
				new_p.pipe(weibo_no_po,c_tree);
				
				break;
				//����merge
//				this.merge(weibo_seg_total, weibo_no_po);
//				this.merge(parse_total, parse_result);
			}
			//����΢���ִʣ�����ģ�����
		//	System.out.println(user_list[i]+"�ִʽ��Ϊ: "+weibo_seg_total.toString());
			//System.out.println(user_list[i]+"�䷨�������Ϊ: "+parse_total.toString());
			//entity sentimen��
			Entity_Sent en_s = new Entity_Sent();
			//HashMap<String,Integer> eS = en_s.gule(weibo_seg_total, parse_total);
			//3 30��������������ط���֮���ú�ߵ�patch
			
			//�����û�profile�Ĺ���
			
			
			
			//��д��Լ�����
			
			break;
			//��ʼ����entity tree�Ĺ���
			//��ÿ��ʵ�幹��concept tree
//			Tree_C the_tree = new Tree_C();
//			buildCTree BC_tree = new buildCTree();
//			int size = weibo_no_stopwords.size();
//			for(int j = 0; j < size; j++)
//			{
//				String name = weibo_no_stopwords.get(j);
//				//���һ�������������ʱ���õ�
//				Tree_C t = BC_tree.buildTree(name, null, 0, eS,weibo_no_stopwords);
//				the_tree.addTree(t);
//			}
//			//�õ��û���и�����the_tree,����Ȩ��
//			Con_final con_f = new Con_final();
//			con_f.cal_CP(the_tree);
//			HashMap<String,Double> cs_re = con_f.getTopK(50);
//			//�����д������ļ���
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
	//�����û��б�
	String[] get_user_list(String dirname) throws Exception
	{
		File file = new File(dirname);
		if(!file.exists())
			throw new Exception("path not found!");
		String[] user_list = file.list();
		return user_list;
	}
	//�����û�ĳ���µ�΢���ϼ�
	//������΢����һ��string�����ٷִʴ���
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
			//3 30ȥ���û�id
			String weibo_user = sp[6].replaceAll("ת��΢��", "").replaceAll("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "").replaceAll("@.*? ", "");
			if(weibo_user.length() > 0)
				weibo_content_array.add(weibo_user);
			else
			{
				String weibo_src = sp[10].replaceAll("ת��΢��", "").replaceAll("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "").replaceAll("@.*? ", "");
				weibo_content_array.add(weibo_src);
			}
//			weibo_content += sp[6]+sp[10];
//			weibo_content = weibo_content.replaceAll("ת��΢��", "");
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
	//ȥ��������
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

package Thread_pool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.hankcs.hanlp.dependency.CRFDependencyParser;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.common.Term;

import Date.TimeFormatChange;
import FanJian.Content;
import NER.Hanlp_seg;
import Sentiment.Parse;
import Sentiment.Sent_enti;
import WikiConcept.Category_merge;
import WikiConcept.Con_final;
import WikiConcept.GetIDfromMysql;
import WikiConcept.Point;
import WikiConcept.Tree_C;
import WikiConcept.Tree_Processing;
import WikiConcept.new_pipeline;

public class user_profile_fmt 
{
	public void user_profile_create(String user_id, int number,HashMap<String,Integer> p,HashMap<String,Integer> n,
			 ArrayList<String> cate_stop,Hanlp_seg hanlp
		) throws Exception
	{
		//get user weibo 
//		ArrayList<String> weibo_content = this.get_user_weibo("active_user/"+user_id);
//        int weibo_size = weibo_content.size();
		//�ж��ǲ����ڶ��߳�
		System.out.println("NOW processing user: "+user_id);
		
		//���ݿ��ʼ��
		Category_merge cm = new Category_merge();
		cm.set_CS(cate_stop);
		cm.Init();//����ǵ�close
		
		Content con = new Content();
		con.Init();
		
		GetIDfromMysql mysql = new GetIDfromMysql();
		
		mysql.Init(cm.conn,cm.stmt);
		
		
		
		//ѭ����ʱ�����ֱ�Ӷ��û�����΢��ͬʱ���д�����ܻ������⣬��Ҫ��parse
		//���ֲ��Ե������ϵ
		//��\n����һ�� ʧ��
		System.out.println("user id: "+user_id+"possing... "+number);
			//��ÿ���û�����ѭ��
		//΢���������� ��ֻ�����Լ������� ����Լ�����Ϊת��΢���ڿ��� ת��ԭ΢��������
		ArrayList<String> weibo_content = this.get_user_weibo("active_user/"+user_id);
		ArrayList<String> weibo_pure = this.get_pure_weibo("active_user/"+user_id);
//		System.out.println("user "+user_id+" weibo read done.");
		int weibo_size = weibo_content.size();
//		//jieba_seg seg = new jieba_seg();
//		Hanlp_seg h_seg = new Hanlp_seg();
//		ArrayList<String> weibo_seg_total = new ArrayList<String>();//����΢���ִʽ��
//		ArrayList<String> parse_total = new ArrayList<String>();//���Ͼ䷨�������
		
		//3 30�����⣬������ֱ�����еĶ����кϲ�������Ҫһ��һ����������Ӱ��ʵ�������Ч��
		
		Tree_C c_tree = new Tree_C();
		new_pipeline new_p = new new_pipeline();
		//����weibo_count
		if(number == -1)
			number = weibo_size;
		System.out.println("weibo_num: "+number);
		
		Segment segment = new CRFSegment();
		segment.enablePartOfSpeechTagging(false);
		for(int j = 0; j < number; j++)
		{
			
			System.out.println("Now processing the "+j);
			String weibo = weibo_content.get(j);
			weibo = weibo.replaceAll("\\s+", "");
			//emoij process
			String e_re = "\\[.*?\\]";
			Pattern p_u = Pattern.compile(e_re);
			Matcher m = p_u.matcher(weibo);
			int emoij_sum = 0;
			int emoij_count = 0;
			while(m.find())
			{
				String emoij = m.group(0);
				int sp = this.SentimentPN(emoij, p, n);
				if(sp != 0)
					emoij_count++;
				emoij_sum += sp;
			}
			
			double emoij_score = 0.0;
			if(emoij_count != 0)
				emoij_score = emoij_sum/(double)emoij_count;
			System.out.println("real emoij score: "+emoij_score);
			//emoij process done
			weibo = weibo.replaceAll("\\[.*?\\]", "");
			System.out.println("pure_weibo: "+weibo_pure.get(j));
			System.out.println("weibo: "+weibo);
			if(weibo.length() == 0)
				continue;
//			hanlp seg
			List<Term> weibo_p_seg = segment.seg(weibo);
			System.out.println("seg"+weibo_p_seg);
			ArrayList<String> weibo_f_seg = new ArrayList<String>();
//			for(int k = 0; k < weibo_p_seg.size(); k++)
//			{
//				if(weibo_p_seg.get(k).length() == 0)
//				{
//					weibo_p_seg.remove(k);
//					k--;
//				}
//			}
//			if(weibo_p_seg.size() == 0)
//				continue;
			//parse�ʷ�����,������еı�ע
			
			CoNLLSentence sen = CRFDependencyParser.compute(weibo_p_seg);
			System.out.println(sen);
			//filter based on pos_tag
			CoNLLWord[] words = sen.getWordArray();
			for(int k = 0; k < words.length; k++)
			{
				System.out.println(words[k].LEMMA.toString()+"\t"+words[k].POSTAG);
				if(words[k].POSTAG.toString().equals("n")) 
				{
					String d = words[k].LEMMA.toString();
					if(d.replaceAll("[0-9].*?", "").length() == 0)//filter number
						continue;
					weibo_f_seg.add(words[k].LEMMA.toString());
				}
					//weibo_f_seg.add(words[k].LEMMA.toString());
			}
			System.out.println("filter_seg: "+weibo_f_seg.toString());
			//ȥͣ�ô�
			//ArrayList<String> weibo_no_stopwords = sw.rmStopW(weibo_f_seg);
			//ȥ��΢��������
			ArrayList<String> weibo_no_po = this.rmPo(weibo_f_seg);
			//System.out.println("weibo_no_po: "+weibo_no_po.toString());
			
			
			System.out.println("weibo_no_po" + weibo_no_po.toString());
			//����wiki�������Ĺ���
			//�ɴ�ֱ���Ȱ����дʵ���д��Һã�Ȼ���ٴ�������ȥ�������μ���
			//���� ��Ϊ tfboys TFBOYS��������� drop it
			//HashMap<String,Integer> entity_senti = this.map_e_sentiment(weibo_no_po, parse_result, p, n);
			if(weibo_no_po.size() == 0)
				continue;
			//process every weibo
			//####################################
			//new_p.pipe(weibo_no_po,c_tree,sen,p,n,cm,con,mysql,emoij_score);
			//break;
			
			
		}
		
		
		System.out.println("ctree: "+c_tree.toString());
		//�����û�profile�Ĺ���
		//4.8 ������Ȩ�صļ���
		String re = "";
		if(c_tree.getTNodes().size() == 0)
			return;
		Tree_Processing tp = new Tree_Processing();
		tp.Tree_propagate(c_tree);
		Con_final cf = new Con_final();
		cf.cal_CP(c_tree, tp);
		int size = c_tree.getTNodes().size();
		ArrayList<Point> concept_result = cf.getTopK(size);
		re = this.C2File(concept_result);
		
		//�ر����ݿ�����
		cm.close();
		con.close();
		//mysql.close();
		//return user_id+":\n"+re;
		//write to file
		this.write2profile(re, user_id);
//		File file = new File("user.profile");
//        if(!file.exists())
//        	file.createNewFile();
//		synchronized{
//		
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"utf-8"));
//        
//       
//        writer.append(user_id+":\n"+re);
//	    writer.flush();
//        
//		
//		writer.close();
//		}
	}
	public synchronized void write2profile(String re,String user_id) throws IOException
	{
		File file = new File("user.profile");
        if(!file.exists())
        	file.createNewFile();
		
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"utf-8"));
        
       
        writer.append(user_id+":\n"+re);
	    writer.flush();
        
		
		writer.close();
		
	}
	ArrayList<String> get_user_weibo(String  path) throws Exception
	{
		
		TimeFormatChange time_format = new TimeFormatChange();
		File user_file = new File(path);
		if(!user_file.exists())
			throw new Exception("user file not found!");
		ArrayList<String> weibo_content_array = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(user_file),"utf-8"));
		String line = "";
		String weibo_content = "";
		while((line = reader.readLine()) != null)
		{
			//����ʱ���ϵĹ��� 
			//Ҫ��ʱ�������ת����������ʱ���ʽ
			//System.out.println(line);
			String[] sp = line.split("\t");
			String unix_time = sp[1];
			String time = time_format.format(unix_time);
			String[] items = time.split("-");
			if(Integer.parseInt(items[2]) > 20)
				continue;
				
			//3 30ȥ���û�id
			String weibo_user = sp[6].replaceAll("ת��΢��", "").replaceAll("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "").replaceAll("@.*?[: ]", "").replaceAll("//", "");
			//weibo_user = weibo_user.replaceAll("\\[.*?\\]", "");
			if(weibo_user.length() > 0)
				weibo_content_array.add(weibo_user);
			else
			{
				String weibo_src = sp[10].replaceAll("ת��΢��", "").replaceAll("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "").replaceAll("@.*?[: ]", "").replaceAll("//", "");
				//weibo_src = weibo_src.replaceAll("\\[.*?\\]", "");
				weibo_content_array.add(weibo_src);
			}
//			weibo_content += sp[6]+sp[10];
//			weibo_content = weibo_content.replaceAll("ת��΢��", "");
		}
		reader.close();
		System.out.println("user weibo read done. "+weibo_content_array.size());
		//return weibo_content;
		int index = 0;
		if(weibo_content_array.size() > 100)
		{
			ArrayList<String> one = new ArrayList<String>();
			for(int i = 0; i < 100; i++)
				one.add(weibo_content_array.get(i));
			weibo_content_array = one;
		}
		return weibo_content_array;
	}
	ArrayList<String> get_pure_weibo(String  path) throws Exception
	{
		
		TimeFormatChange time_format = new TimeFormatChange();
		File user_file = new File(path);
		if(!user_file.exists())
			throw new Exception("user file not found!");
		ArrayList<String> weibo_content_array = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(user_file),"utf-8"));
		String line = "";
		String weibo_content = "";
		while((line = reader.readLine()) != null)
		{
			//����ʱ���ϵĹ��� 
			//Ҫ��ʱ�������ת����������ʱ���ʽ
			//System.out.println(line);
			String[] sp = line.split("\t");
			String unix_time = sp[1];
			String time = time_format.format(unix_time);
			String[] items = time.split("-");
			if(Integer.parseInt(items[2]) > 20)
				continue;
				
			//3 30ȥ���û�id
			String weibo_user = sp[6];//.replaceAll("ת��΢��", "").replaceAll("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "").replaceAll("@.*?", "").replaceAll("//", "");
			if(weibo_user.length() > 0)
				weibo_content_array.add(weibo_user);
			else
			{
				String weibo_src = sp[10];//.replaceAll("ת��΢��", "").replaceAll("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "").replaceAll("@.*?", "").replaceAll("//", "");
				weibo_content_array.add(weibo_src);
			}
//			weibo_content += sp[6]+sp[10];
//			weibo_content = weibo_content.replaceAll("ת��΢��", "");
		}
		reader.close();
		System.out.println("user weibo read done. "+weibo_content_array.size());
		//return weibo_content;
		if(weibo_content_array.size() > 100)
		{
			ArrayList<String> one = new ArrayList<String>();
			for(int i = 0; i < 100; i++)
				one.add(weibo_content_array.get(i));
			weibo_content_array = one;
		}
		return weibo_content_array;
	}
	//ȥ��������
	ArrayList<String> rmPo(ArrayList<String> words)
	{
		for(int i = 0; i < words.size(); i++)
		{
			String word = words.get(i);
			
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
	//4.8 ��profile���д���ĵ����ߴ�ӡ����
	String C2File(ArrayList<Point> result)
	{
		String re = "";
		int size = result.size();
		for(int i = 0; i < size; i++ )
		{
			Point temp = result.get(i);
			System.out.print(temp.getName()+":"+temp.getWeight()+","+temp.getSen()+"\t");
			re += temp.getName()+":"+temp.getWeight()+","+temp.getSen()+"\t";
		}
		System.out.println("\n");
		re += "\n";
		return re;
		
	}
	//return emoij's sentiment score based on emotion-word dict
	int SentimentPN(String emoij, HashMap<String,Integer> p,HashMap<String,Integer> n)
	{
		if(p.containsKey(emoij))
			return 1;
		else if(n.containsKey(emoij))
			return -1;
		return 0;
	}

}

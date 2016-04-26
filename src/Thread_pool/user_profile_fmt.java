package Thread_pool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import NER.Hanlp_seg;
import Sentiment.Parse;
import Sentiment.Sent_enti;
import WikiConcept.Category_merge;
import WikiConcept.Con_final;
import WikiConcept.Point;
import WikiConcept.Tree_C;
import WikiConcept.Tree_Processing;
import WikiConcept.new_pipeline;

public class user_profile_fmt 
{
	public void user_profile_create(String user_id, Parse par, int number,HashMap<String,Integer> p,HashMap<String,Integer> n, ArrayList<String> weibo_p_seg, ArrayList<String> weibo_f_seg
			, int weibo_size) throws Exception
	{

		//�ж��ǲ����ڶ��߳�
		System.out.println("NOW processing user: "+user_id);
		//���ݿ��ʼ��
		Category_merge cm = new Category_merge();
		cm.Init();//����ǵ�close
		
		//ѭ����ʱ�����ֱ�Ӷ��û�����΢��ͬʱ���д�����ܻ������⣬��Ҫ��parse
		//���ֲ��Ե������ϵ
		//��\n����һ�� ʧ��
		System.out.println("user id: "+user_id+"possing... "+number);
			//��ÿ���û�����ѭ��
		//΢���������� ��ֻ�����Լ������� ����Լ�����Ϊת��΢���ڿ��� ת��ԭ΢��������
//		ArrayList<String> weibo_content = this.get_user_weibo("active_user/"+user_id);
//		System.out.println("user "+user_id+" weibo read done.");
//		int weibo_size = weibo_content.size();
//		//jieba_seg seg = new jieba_seg();
//		Hanlp_seg h_seg = new Hanlp_seg();
//		ArrayList<String> weibo_seg_total = new ArrayList<String>();//����΢���ִʽ��
//		ArrayList<String> parse_total = new ArrayList<String>();//���Ͼ䷨�������
		
		//3 30�����⣬������ֱ�����еĶ����кϲ�������Ҫһ��һ����������Ӱ��ʵ�������Ч��
		
		Tree_C c_tree = new Tree_C();
		new_pipeline new_p = new new_pipeline();
		int count = 0;
		//����weibo_count
		if(number == -1)
			number = weibo_size;
		System.out.println("weibo_num: "+number);
		for(int j = 0; j < weibo_size; j++)
		{
			
			System.out.println("Now processing the "+j);
//			String weibo = weibo_content.get(j);
//			weibo = weibo.replaceAll("\\s+", "");
//			//hanlp seg
//			//ArrayList<String> weibo_seg = seg.jieba_Seg(weibo_content.get(j));
//			ArrayList<String> weibo_p_seg = h_seg.pure_seg(weibo);
//			ArrayList<String> weibo_f_seg = h_seg.filter_seg(weibo);
			//System.out.println("user "+user_list[i]+" weibo seg done.");
			//parse�ʷ�����,������еı�ע
			ArrayList<String> parse_result = par.Parse(weibo_p_seg);
			//ȥͣ�ô�
			//ArrayList<String> weibo_no_stopwords = sw.rmStopW(weibo_f_seg);
			ArrayList<String> weibo_no_po = this.rmPo(weibo_f_seg);
			System.out.println("weibo_no_po: "+weibo_no_po.toString());
			
			
			
			//����wiki�������Ĺ���
			//�ɴ�ֱ���Ȱ����дʵ���д��Һã�Ȼ���ٴ�������ȥ�������μ���
			//���� ��Ϊ tfboys TFBOYS��������� drop it
			//HashMap<String,Integer> entity_senti = this.map_e_sentiment(weibo_no_po, parse_result, p, n);
			new_p.pipe(weibo_no_po,c_tree,parse_result,p,n,cm);
			//break;
			
			count++;
			if(count == number)
				break;
			
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
		//return user_id+":\n"+re;
		//write to file
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
		System.out.println("user weibo read done. "+weibo_content_array.size());
		//return weibo_content;
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

}

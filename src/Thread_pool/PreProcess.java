package Thread_pool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.hankcs.hanlp.dependency.CRFDependencyParser;
import com.hankcs.hanlp.seg.common.Term;

import Date.showtime;
import NER.Hanlp_seg;
import Sentiment.Parse;

public class PreProcess 
{
	//pre processing, seg and parse all the users weibo and for build user profile
	public static void main(String[] args) throws Exception
	{
		
		//hanlp test
		//CRFDependencyParser crf = new CRFDependencyParser();
		//System.out.println(CRFDependencyParser.compute(words));
		String[] user_list = get_user_list("active_user");
		Hanlp_seg hanlp = new Hanlp_seg();
		Parse par = new Parse();
		par.Init();
		user_profile_fmt fmt = new user_profile_fmt();
		showtime time = new showtime();
		time.show_time();
		for(int i = 0; i < user_list.length; i++)
		{
			if(user_list[i].contains("pre"))
				continue;
			ArrayList<String> p_seg = null;
			ArrayList<String> f_seg = null;
			ArrayList<String> p_result = null;
			
			ArrayList<String> weibo_content = fmt.get_user_weibo("active_user/"+user_list[i]);//100 per
			int size = weibo_content.size();
			//·Ö´Ê
			for(int j = 0; j < size; j++)
			{
				String weibo = weibo_content.get(j);
				weibo = weibo.replaceAll("\\s+", "");
				CoNLLSentence sen = CRFDependencyParser.compute(weibo);
				System.out.println(sen);
				//System.out.println(sen.getEdgeArray());
				String[][] l = sen.getEdgeArray();
				int lsize = l.length;
				CoNLLWord[] w = sen.getWordArray();
				System.out.println("wlen"+w.length);
				for(int z = 0; z < w.length; z++)
				{
					System.out.println(w[z].LEMMA);
				}
				for(int z = 0; z < lsize; z++)
				{
					int nlen = l[z].length;
					for(int d = 0; d< nlen;d++)
					{
						String s = l[z][d];
						System.out.print(s+"\t");
					}
					System.out.println("\n");
				}
				for(int z = 0; z < lsize; z++)
				{
					int nlen = l[z].length;
					for(int d = 0; d< nlen;d++)
					{
						String s = l[z][d];
						//System.out.print(s+"\t");
						if(!(s == null))
						{
							if(d == 0)
							{
								System.out.println(z+"\t"+d+"\t"+w[z-1].LEMMA+"\t"+"root");
							}
							else
							System.out.println(z+"\t"+d+"\t"+w[z-1].LEMMA+"\t"+w[d-1].LEMMA);
						}
					}
					System.out.println("\n");
				}
				
				System.out.println("conll:\n"+sen);
				//ArrayList<String> s = (ArrayListCRFDependencyParser.compute(weibo);
				p_seg = hanlp.pure_seg(weibo);
				f_seg = hanlp.filter_seg(weibo);
				System.out.println("seg done");
		
				//segre
				p_result = par.ps(weibo);
				//p_result = par.Parse(p_seg);
//				time.show_time();
				String content = "";
				content += "f_seg:";
				
				for(String f:f_seg)
				{
					content += f+"\t";
				}
//				fsegre.add(content);
//				String sed = toString(p_seg);
//				segre.add(sed);
				System.out.println("index: "+j);
				//par.Parse(seg_result)
//			
				content += "\nparse_result:";
				if(p_result == null)
					content += "null";
				else
				{
					for(String p:p_result)
					{
						content += p+"\t";
					}
				}
				//System.out.println(content);
				//pp_seg.add(p_seg);
				Write2File(user_list[i],content);
				break;
				
			}
			//parse
//			for(int j = 0; j < segre.size(); j++)
//			{
//				String sen = segre.get(j);
//				
//				if(sen.equals("null"))
//					parsere.add("parse_result:null");
//				else
//				{
//					String content = "parse_result:";
//					ArrayList<String> p = par.ps(sen);
//					for(String pp:p)
//					{
//						content += pp+"\t";
//					}
//					parsere.add(content);
//				}
//			}

			
			System.out.println("user :"+user_list[i]+" done.");
			time.show_time();
			break;
			
			
		}
		
		
	}
	public static String toString(ArrayList<String> seg)
	{
		for(int k = 0; k < seg.size(); k++)
		{
			if(seg.get(k).length() == 0)
			{
				seg.remove(k);
				k--;
			}
		}
		if(seg.size() == 0)
			return "null";
		String sentence = "";
		int size = seg.size();
		for(int i = 0; i < size-1; i++)
		{
			sentence += seg.get(i)+"";
			
		}
		sentence += seg.get(size-1);
		return sentence;
	}
	static void Write2File(String id, String content) throws IOException
	{
		File file = new File("active_user/"+id+".pre");
		if(file.exists())
			file.delete();
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"utf-8"));
	    writer.append(content);
	    writer.newLine();
	    writer.flush();
	    
	    writer.close();
	}
	static String[] get_user_list(String dirname) throws Exception
	{
		File file = new File(dirname);
		if(!file.exists())
			throw new Exception("path not found!");
		String[] user_list = file.list();
		return user_list;
	}

}

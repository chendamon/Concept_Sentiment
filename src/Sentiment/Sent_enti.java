package Sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*
 * 将实体与情感词关联
 * 初步打算，每个情感词为1或者-1
 * 应用之前的正负情感词表
 * author biront
 * date 2015/12/3
 * 下一步的工作，实体与category tree结合，用户profile构建完成
 * 还有去歧义的工作
 */
public class Sent_enti 
{
	HashMap<String,Integer> pos_s;
	HashMap<String,Integer> neg_s;
	//读情感词
	public void readF(File f,int type) throws Exception
	{
		if(!f.exists())
			throw new Exception("sentiment word file not found!");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f),"utf-8"));
		String line = "";
		//int iterator = 0;
		while((line = reader.readLine())!=null)
		{
			if(line.length() == 0)
				continue;
			else
			{
				line = line.substring(0,line.length()-1);
				//System.out.println("line"+line+"end");
				if(type == 0)
					this.pos_s.put(line,0);
				else
					this.neg_s.put(line,1);
			}
		}
		reader.close();
	}
	public void Init() throws Exception
	{
		this.neg_s = new HashMap<String,Integer>();
		this.pos_s = new HashMap<String,Integer>();
		File pfile = new File("pos.txt");
		this.readF(pfile, 0);
		
		pfile = new File("neg.txt");
		this.readF(pfile, 1);
		
		System.out.println("情感词加载完毕,pos: "+this.pos_s.size()+";neg: "+this.neg_s.size());
	}
	public HashMap<String,Integer> getP()
	{
		return this.pos_s;
	}
	public HashMap<String,Integer> getN()
	{
		return this.neg_s;
	}
	//计算每个实体的打分
	public int entity_SP(String entity,ArrayList<String> parse)
	{
		int size = parse.size();
		StruW sw = new StruW();
	    String sp = sw.Stru(entity, parse);
	    if(sp == null)
	    	return 0;
	    else
	    {
	    	if(this.pos_s.containsKey(sp))
	    		return 1;
	    	else if(this.neg_s.containsKey(sp))
	    		return -1;
	    }
		return 0;
	}

}

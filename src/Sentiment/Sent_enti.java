package Sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*
 * ��ʵ������дʹ���
 * �������㣬ÿ����д�Ϊ1����-1
 * Ӧ��֮ǰ��������дʱ�
 * author biront
 * date 2015/12/3
 * ��һ���Ĺ�����ʵ����category tree��ϣ��û�profile�������
 * ����ȥ����Ĺ���
 */
public class Sent_enti 
{
	HashMap<String,Integer> pos_s;
	HashMap<String,Integer> neg_s;
	//����д�
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
		
		System.out.println("��дʼ������,pos: "+this.pos_s.size()+";neg: "+this.neg_s.size());
	}
	public HashMap<String,Integer> getP()
	{
		return this.pos_s;
	}
	public HashMap<String,Integer> getN()
	{
		return this.neg_s;
	}
	//����ÿ��ʵ��Ĵ��
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

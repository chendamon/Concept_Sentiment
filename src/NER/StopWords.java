package NER;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
/*
 * 对于分词之后的微博去除停用词
 * author: biront
 * 2015/11/29
 */
public class StopWords 
{
	HashMap<String,Integer> stopwords;
	public void Init(String filename) throws Exception
	{
		System.out.println("停用词表准备中...");
		this.stopwords = new HashMap<String,Integer>();
		File file = new File(filename);
		if(!file.exists())
			throw new Exception("Stop words' file can't find!");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
		String line = "";
		int iterator = 0;
		while((line = reader.readLine())!=null)
		{
			//System.out.println(line);
			this.stopwords.put(line, iterator++);
		}
		reader.close();
		System.out.println("停用词表加载完毕."+this.stopwords.size());
	}
	public ArrayList<String> rmStopW(ArrayList<String> weibo_seg)
	{
		for(int i = 0; i < weibo_seg.size(); i++)
		{
			if(this.stopwords.containsKey(weibo_seg.get(i)))
			{
				weibo_seg.remove(i);
				i--;
			}
		}
		return weibo_seg;
	}

}

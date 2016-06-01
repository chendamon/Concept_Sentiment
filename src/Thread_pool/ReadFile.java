package Thread_pool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//read .pre file and get seg,parse result
public class ReadFile 
{
	public ArrayList<ArrayList<String>> seg_result;
	public ArrayList<ArrayList<String>> parse_result;
	public ReadFile()
	{
		this.seg_result = new ArrayList<ArrayList<String>>();
		this.parse_result = new ArrayList<ArrayList<String>> ();
	}
	public void clear()
	{
		this.seg_result.clear();
		this.parse_result.clear();
	}
	
	public void ReadPre(String user_id) throws Exception
	{
		File file = new File(user_id+".pre");
		if(!file.exists())
			throw new Exception("pre file not found");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
		String line = "";
		while((line = reader.readLine()) != null)
		{
			String[] items = line.split(":");
			if(line.startsWith("f_seg"))
			{
				String f_seg = items[1];
				String[] seg = f_seg.split("\t");
				ArrayList<String> s = new ArrayList<String>();
				for(String se:seg)
					s.add(se);
				this.seg_result.add(s);
					
			}
			else if(line.startsWith("parse_result"))
			{
				String p_re = items[1];
				String[] par = p_re.split("\t");
				ArrayList<String> p = new ArrayList<String>();
				for(String pa:par)
					p.add(pa);
				this.parse_result.add(p);
			}
		}
		reader.close();
		
	}
	
	

}

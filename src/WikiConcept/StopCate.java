package WikiConcept;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StopCate 
{
	//用来处理数据库中的无用选项
	public ArrayList<String> InitCstop(String filename) throws Exception
	{
		ArrayList<String> cate = new ArrayList<String>();
		File scfile = new File(filename);
		if(!scfile.exists())
			throw new Exception("Stop cates' file can't find!");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(scfile),"utf-8"));
		String line = "";
		int iterator = 0;
		while((line = reader.readLine())!=null)
		{
			//System.out.println(line);
			cate.add(line);
		}
		reader.close();
		return cate;
	}

}

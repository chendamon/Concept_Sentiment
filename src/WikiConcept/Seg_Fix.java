package WikiConcept;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/*
 * 16/2/26
 * 修正分词结果，包括如下几种情况
 * 本来是实体，但是由于分词不是实体（科比）
 * 本来是实体，但是由于分词导致分为两个意义差距大的实体（钢铁侠，美国队长）
 */
public class Seg_Fix 
{
	public ArrayList<String> Fix(ArrayList<String> weibo_seg) throws IOException
	{
		ArrayList<String> entity = new ArrayList<String>();
		//对第一个词，只看后一个
		int size = weibo_seg.size();
		for(int i = 0; i < size; i++)
		{
			if(i+1 < size)
			{
				if(this.isEntity(weibo_seg.get(i)+weibo_seg.get(i+1)))
				{
					entity.add(weibo_seg.get(i)+weibo_seg.get(i+1));
					i++;
				}
			}
			else if(this.isEntity(weibo_seg.get(i)))
				entity.add(weibo_seg.get(i));
		}
		return entity;
		
	}
    boolean isEntity(String keyword) throws IOException
    {
    	URL url = new URL("https://zh.wikipedia.org/wiki/"+keyword);
    	HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();           
		urlcon.connect(); 
    	InputStream is = urlcon.getInputStream(); 
    	BufferedReader buffer = new BufferedReader(new InputStreamReader(is,"utf-8"));                  
		String l = null;  
		boolean en = true;
    	while((l = buffer.readLine()) != null)
		{
			//如果根本不是实体直接返回
			if(l.contains("mw-search-result-heading"))
			{
				en = false;
				break;
			}
		}
    	buffer.close();
    	return en;
    }
}

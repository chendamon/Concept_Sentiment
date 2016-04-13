package FanJian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Content 
{
	public String ConfWebPage(String u) throws IOException
	{
		String context = null;
		URL url = new URL(u);
		HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();           
		urlcon.connect();//获取连接
		//System.out.println("responsecode"+urlcon.getResponseCode());
		
		if(urlcon.getResponseCode() != 200)
		{
			return null;
		}
		//System.out.println("get connection...");
		InputStream is = urlcon.getInputStream(); 
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is,"utf-8"));    
		String l = null;
		while((l = buffer.readLine()) != null)
		{
			context += l.replaceAll("<.*?>", "");
		}
		buffer.close();
		return context;
	}
	public int CountWebPage(ArrayList<String> entities,String keyword) throws IOException
	{
		System.out.println("title now: "+keyword);
		int count = 0;
		String context = null;
		URL url = new URL("https://zh.wikipedia.org/wiki/"+keyword);
		HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();      
        
		boolean disconnect = true;
		while(disconnect)
		{
			try {
				urlcon.connect();
				disconnect = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}//获取连接
		}
		//System.out.println("responsecode"+urlcon.getResponseCode());
		
		if(urlcon.getResponseCode() != 200)
		{
			return 0;
		}
		System.out.println("get connection...");
		InputStream is = urlcon.getInputStream(); 
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is,"utf-8"));    
		String l = null;
		boolean context_zone = false;
		while((l = buffer.readLine()) != null)
		{
			if(l.contains("mw-content-text"))//进入正文区域
				context_zone = true;
			if(!context_zone)
				continue;
			if(l.contains("mw-navigation"))
				break;
			context = l.replaceAll("<.*?>", "");
			for(String en:entities)
				if(context.contains(en)&&!en.equals(keyword))
				{
					//System.out.println("entity: "+en);
					count++;
				}
		}
		buffer.close();
		return count;
	}

}

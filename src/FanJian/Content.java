package FanJian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Content 
{
	MongoClient mongo;
	MongoDatabase db;
	public Content()
	{
		this.mongo = null;
		this.db = null;
	}
	public void Init()
	{
		this.mongo = new MongoClient("172.19.104.24",27017);
		this.db = mongo.getDatabase("wiki");
		System.out.println("mongodb init done");
	}
	public void close()
	{
		this.mongo.close();
	}
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
	//从mongodb中得到相应的计数
	public int CountFromMongodb(ArrayList<String> entity, String page_id, String keyword, String entity_now)
	{
		MongoCollection<Document> table = db.getCollection("page");
		//System.out.println("collection: "+table.toString());
		//Document query = new Document();
		//query.put("id", user_id);
		Document d = table.find(Filters.eq("id",page_id)).first();
		if(d == null)
			return 0;
		else
		{
			ArrayList<String> terms = (ArrayList<String>) d.get("content");
			
			//System.out.println("docunment_size: "+d.size()+"\t"+d.toJson());
			int sum = 0;
			for(String ent:entity)
			{
				//算法的可优化部分,但是事实上很难
				if(!entity_now.contains(ent))//entity_now is the entity to be done
				{
					for(String term:terms)
					{
							if(term.contains(ent))
								sum++;
					}
					//if(!ent.equals(keyword)&&d.containsValue(ent))
					//sum++;
				}
			}
			//System.out.println("mark: "+d.containsValue(".*希望.*"));
			return sum;
		}
	}
	public int CountWebPage(ArrayList<String> entities,String keyword) throws IOException
	{
		System.out.println("title now: "+keyword);
		int count = 0;
		String context = null;
		URL url = new URL("https://zh.wikipedia.org/zh/"+keyword);
		HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();      
        
		boolean disconnect = true;
		int loop_num = 10;
		while(disconnect)
		{
			loop_num --;
			if(loop_num == 0)
			{
				return 0;
			}
			try {
				urlcon.connect();
				disconnect = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				disconnect = true;
				continue;
			}//获取连接
		}
		//System.out.println("responsecode"+urlcon.getResponseCode());
		int code = 0;
		loop_num = 10;
	    disconnect = true;
	    while(disconnect&&loop_num >= 0)
	    {
	    	loop_num--;
	    	try 
			{
				code = urlcon.getResponseCode();
				//System.out.println("responsecode"+code);
				disconnect = false;
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("still trying");
			}
	    }
		if(code != 200)
		{
			return 0;
		}
		//System.out.println("get connection...");
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

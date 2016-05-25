package WikiConcept;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//从mongodb中查找看是否为一个实体
public class PageFromMongodb 
{
	MongoClient mongo;
	MongoDatabase db;
	public PageFromMongodb()
	{
		this.mongo = null;
		this.db = null;
	}
	public void Init()
	{
		this.mongo = new MongoClient("172.19.104.24",27017);
		this.db = mongo.getDatabase("wiki");
	}
	public void close()
	{
		this.mongo.close();
	}
	//模糊查询，包括繁简体，大小写等
	public String getTitle(String keyword)
	{
		MongoCollection<Document> table = db.getCollection("page");
		//查询实例
		
		mongo.close();
		return null;
	}

}

package WikiConcept;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//��mongodb�в��ҿ��Ƿ�Ϊһ��ʵ��
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
	//ģ����ѯ�����������壬��Сд��
	public String getTitle(String keyword)
	{
		MongoCollection<Document> table = db.getCollection("page");
		//��ѯʵ��
		
		mongo.close();
		return null;
	}

}

package WikiConcept;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/*
 * ʵ��Ӱٶȷ����������
 * ��Ҫ��̽������������ϼ�������
 * author biront
 * 2015/11/30
 */
public class getConceptsFWT 
{
	//���ٶȰٿ�һ����������
	//�����ݷ��ؽ���ҵ�������ǩ���ٶ���Ҳ���е�
	//��������������������������һ���б�
	//2015/11/30
	public void getReFB(String keyword) throws IOException
	{
		ArrayList<String> Tags = new ArrayList<String>();
		URL url = new URL("http://baike.baidu.com/search/word?word="+keyword);
		HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();           
		urlcon.connect();         //��ȡ���� 
		System.out.println("get connection...");
		InputStream is = urlcon.getInputStream();           
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is,"utf-8"));                  
		String l = null;   
		boolean zone = false;
		System.out.println("Reading pages");
		while((l=buffer.readLine())!=null)
		{               
			if(l.contains("open-tag-item"))
			{
				System.out.println("dinfya");
				 zone = true;
			}
			else if(zone)
			{
				if(l.contains("div"))
					break;
				else if(!l.contains("<")&&!l.contains("��"))
					Tags.add(l);
			} 
	     }    
		 buffer.close();
		 if(Tags.size()>0)
		 {
			 System.out.println("we found something.");
			 System.out.println(Tags.toString());
		 }
	}

}

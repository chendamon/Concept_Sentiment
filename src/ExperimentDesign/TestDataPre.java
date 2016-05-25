package ExperimentDesign;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

import Date.TimeFormatChange;
//׼���������ݣ����ﻹ���кܶ���Ҫע��ĵط���
//�������뷨��ÿ���û���8 20 ֮���ת��΢���е�һ�����ŵ����յ��ĵ��У���Ϊ��ѡ�Ĳ�������
//Ӧ�ñ���Ķ���ת�����ӵ�user id�����ǲ���id
public class TestDataPre 
{
	public String[] getuserList(String dirname) throws Exception
	{
		File file = new File(dirname);
		if(!file.exists())
			throw new Exception("path not found!");
		String[] user_list = file.list();
		return user_list;
	}
	public void test_data_pre() throws Exception
	{
		String[] user_list = this.getuserList("active_user");
		for(String user:user_list)
		{
			String user_test = this.user_chosen_one(user);
			this.write2file(user_test);
		}
		System.out.println("user id for test done");
	}
	public void write2file(String user_id) throws IOException
	{
		File file = new File("user.test");
        if(!file.exists())
        	file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"utf-8"));
        writer.append(user_id);
        writer.newLine();
	    writer.flush();
        writer.close();
	}
	public String user_chosen_one(String user_id) throws Exception
	{
		TimeFormatChange time_format = new TimeFormatChange();
		File user_file = new File("active_user/"+user_id);
		if(!user_file.exists())
			throw new Exception("user file not found!");
		ArrayList<String> weibo_content_array = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(user_file),"utf-8"));
		String line = "";
		String weibo_content = "";
		Random random = new Random();
		String last_one = null;
		while((line = reader.readLine()) != null)
		{
			//����ʱ���ϵĹ��� 
			//Ҫ��ʱ�������ת����������ʱ���ʽ
			String[] sp = line.split("\t");
			String unix_time = sp[1];
			String time = time_format.format(unix_time);
			String[] items = time.split("-");
			if(Integer.parseInt(items[2]) <= 20)
				continue;
			//ת��΢��id��Ϊ��
			if(sp.length > 9&&sp[8] != null&&!sp[8].equals("null"))
			{
				last_one = sp[8];
				if(random.nextInt(10) == 6)
					return sp[8];
			}
				
			
		}
		reader.close();
		return last_one;
		//System.out.println("user weibo read done. "+weibo_content_array.size());
		//return weibo_content;
		//return weibo_content_array;
	}
	

}

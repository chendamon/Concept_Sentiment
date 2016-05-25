package ExperimentDesign.Weka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * 使用word2vec计算两个用户profile的相似度
 */
public class Word2VecSimilarity 
{
	HashMap<String,ArrayList<ProfileElement>> users;
	//比较相似度的核心算法
	public double Similarity(String user_one, String user_two) throws Exception
	{
		ArrayList<ProfileElement> elements_one = this.users.get(user_one);
		ArrayList<ProfileElement> elements_two = this.users.get(user_two);
		
		double sim_sum = 0;
		for(ProfileElement ele_one:elements_one)
		{
			double max_sim = 0;
			for(ProfileElement ele_two:elements_two)
			{
				double sim_little = this.SimFWord2Vec(ele_one.category, ele_two.category);
				sim_little = sim_little*ele_one.weight*ele_two.weight*this.SimFromSentiment(ele_one.sentiment, ele_two.sentiment);
				if(sim_little > max_sim)
					max_sim = sim_little;
			}
			sim_sum += max_sim;
		}
		sim_sum = sim_sum/elements_one.size();
		return sim_sum;

	}
	private double SimFromSentiment(double s1, double s2)
	{
		double abs = 0;
		if(s1 <= s2)
			abs = s2-s1;
		else abs = s1-s2;
		
		double max = (1/Math.sqrt(2*Math.PI));
		double fx = (1/Math.sqrt(2*Math.PI))*Math.exp(-0.5*Math.pow(abs-1, 2));
		//return fx;
		return max-fx;
	}
	private double SimFWord2Vec(String word_one, String word_two)
	{
		return 0;
	}
	public void Init() throws Exception
	{
		this.getProfileFromPath("user.profile");
	}
	//根据文件读取用户profile数据 进行存储
	public void getProfileFromPath(String path) throws Exception
	{
		File file = new File(path);
		if(!file.exists())
			throw new Exception("File not exists!");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
		String line = "";
		ArrayList<ProfileElement> elements = new ArrayList<ProfileElement>();
		//HashMap<String,ArrayList<ProfileElement>> users = new HashMap<String,ArrayList<ProfileElement>>();
		String user_now = null;
		while((line = reader.readLine()) != null)
		{
			if(line.contains(":"))
				user_now = line.replace(":", "");
			elements.clear();
			String[] truple = line.split("\t");
			for(String tur:truple)
			{
				String[] items = tur.split(":");
				elements.add(new ProfileElement(items[0],Double.valueOf(items[1]),Double.valueOf(items[2])));
			}
			users.put(user_now, elements);
		}
		//return users;
	}
	

}

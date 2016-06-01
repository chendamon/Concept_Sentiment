package ExperimentDesign.Weka;

import java.util.ArrayList;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/*
 * caulcate the similarity of two user's profile, 
 * using the Stable Matching Problem's algorithm Gale-Shapley
 * 
 * need two group category:weight:sentiment value class:ProfileElement
 * 
 */
public class StableMatchingProblem 
{
	PythonInterpreter pe;
	public void StableMatchingProblem()
	{
		this.pe = new PythonInterpreter();
	}
	//load word2vec model
	void Init()
	{  
		pe.exec("import gensim");
		pe.exec("model = gensim.models.Word2Vec.load(\"wiki.zh.text.model\")");
		System.out.println("word2vec model load done.");
	}
	public double StableMatching(ArrayList<ProfileElement> user_a, ArrayList<ProfileElement> user_b)
	{
		ArrayList<ProfileElement> men = null;
		ArrayList<ProfileElement> women = null;
		if(user_a.size() < user_b.size())
		{
			men = user_a;
			women = user_b;
		}
		else
		{
			men = user_b;
			women = user_a;
		}
		
		int[] men_mark = new int[men.size()];//use this array to remember the index of his wife
		int[] women_mark = new int[women.size()];//the same with top
		
		for(int i = 0; i< men_mark.length; i++)
			men_mark[i] = -1;
		for(int i = 0; i < women_mark.length; i++)
			women_mark[i] = -1;
		
		while(!this.IsStable(men_mark))
		{
			for(int i = 0; i < men.size(); i++)
			{
				if(men_mark[i] == -1)//still single
				{
					int woman_index = this.MostLikeIndex(men.get(i), women, women_mark);
					if(women_mark[woman_index] == -1)
					{
						women_mark[woman_index] = i;
						men_mark[i] = woman_index;
						continue;
					}
					else //compare the new man with her husband
					{
						double new_one = this.Similarity(men.get(i), women.get(woman_index));
						double old_one = this.Similarity(men.get(women_mark[woman_index]), women.get(woman_index));
						if(new_one > old_one)
						{
							men_mark[women_mark[woman_index]] = -1;//re single
							women_mark[woman_index] = i;//re marry
							men_mark[i] = woman_index;// got a wife
						}
					}
				}
			}
		}
		//calcatute the final score
		double sum = 0.0;
		for(int i = 0; i < men.size(); i++)
		{
			sum += this.Similarity(men.get(i), women.get(men_mark[i]));
		}
		
		
		return sum;
		
	}
	boolean IsStable(int[] men)
	{
		for(int man:men)
		{
			if(man == -1)
				return true;
		}
		return false;
	}
	double Similarity(ProfileElement a, ProfileElement b)
	{
		double sim_w = 0;
		if(a.category.equals(b.category))
			sim_w = 1;
		else
		{
			try
			{
			PyObject re = pe.eval("model.similarity(u\""+a.category.replace("Category:", "")+"\", u\""+b.category.replace("Category:", "")+"\")");
			sim_w = re.asDouble();
			}
			catch(Exception e)
			{
				System.out.println("word2vec does not contain that word.");
			}
			if(sim_w == 0)//word2vec does not work,
				sim_w = 0.5;
		}
		
		double abs = 0;
		if(a.sentiment <= b.sentiment)
			abs = b.sentiment-a.sentiment;
		else abs = a.sentiment-b.sentiment;
		
		double max = (1/Math.sqrt(2*Math.PI));
		double senti_sim = max-(1/Math.sqrt(2*Math.PI))*Math.exp(-0.5*Math.pow(abs-1, 2));
		
		double ans = sim_w*a.weight*b.weight*senti_sim;
		return ans;
	}
	//find the most like one
	int MostLikeIndex(ProfileElement e, ArrayList<ProfileElement> b, int[] b_mark)
	{
		double max = -1;
		int index = -1;
		for(int i = 0; i < b.size(); i++)
		{
			if(b_mark[i] == -1)//not married
			{
				double sim = this.Similarity(e, b.get(i));
				if(sim > max)
				{
					max = sim;
					index = i;
				}
			}
		}
		return index;
	}

}

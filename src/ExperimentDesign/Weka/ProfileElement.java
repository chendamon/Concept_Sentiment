package ExperimentDesign.Weka;

/*
 * user profile单元数据结构
 * category weight sentiment
 */
public class ProfileElement 
{
	String category;
	double weight;
	double sentiment;
	public ProfileElement()
	{
		this.category = null;
		this.weight = 0.0;
		this.sentiment = 0.0;
	}
	public ProfileElement(String category, double weight, double sentiment)
	{
		this.category = category;
		this.weight = weight;
		this.sentiment = sentiment;
	}

}

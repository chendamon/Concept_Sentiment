package NER;
//����ͣ�ôʵ�
public class stop_test 
{
	public static void main(String[] args) throws Exception
	{
		StopWords st = new StopWords();
		st.Init("stopwords.txt");
		if(st.stopwords.containsKey("��"))
			System.out.println("true");
	}

}

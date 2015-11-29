package NER;

public class Segtest 
{
	public static void main(String[] args)
	{
		//Ansj_seg seg = new Ansj_seg();
		jieba_seg seg = new jieba_seg();
		seg.jieba_Seg("科比是湖人球员。");
	}

}

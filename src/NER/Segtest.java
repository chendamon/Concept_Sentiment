package NER;

import java.util.ArrayList;

public class Segtest 
{
	public static void main(String[] args) throws Exception
	{
		Ansj_seg seg = new Ansj_seg();
		seg.Seg("�Ұ������찲�š�");
		//jieba_seg seg = new jieba_seg();
//		ArrayList<String> test_mode = seg.jieba_Seg("�Ʊ��Ǻ�����Ա��");
//		//ͣ�ôʲ���
//		StopWords SW = new StopWords();
//		SW.Init("����ͣ�ôʿ�.txt");
//		ArrayList<String> sw_result = SW.rmStopW(test_mode);
//		System.out.println(sw_result.toString());
//		
	}

}

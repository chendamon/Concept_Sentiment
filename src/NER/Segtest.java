package NER;

import java.util.ArrayList;

public class Segtest 
{
	public static void main(String[] args) throws Exception
	{
//		Ansj_seg seg = new Ansj_seg();
//		seg.Seg("��ϲ���Ʊȣ�����ղķ˹��");
		jieba_seg seg = new jieba_seg();
		ArrayList<String> test_mode = seg.jieba_Seg("��ϲ���Ʊȣ����ᡣ");
		
		System.out.println(test_mode.get(0));
//		//ͣ�ôʲ���
//		StopWords SW = new StopWords();
//		SW.Init("����ͣ�ôʿ�.txt");
//		ArrayList<String> sw_result = SW.rmStopW(test_mode);
//		System.out.println(sw_result.toString());
//		
	}

}

package NER;

import java.util.ArrayList;

public class Segtest 
{
	public static void main(String[] args) throws Exception
	{
//		Ansj_seg seg = new Ansj_seg();
//		seg.Seg("��ϲ���Ʊȣ�����ղķ˹��");
//		jieba_seg seg = new jieba_seg();
//		ArrayList<String> test_mode = seg.jieba_Seg("��ϲ���Ʊȣ����ᡣ");
//		
//		System.out.println(test_mode);
//		//ͣ�ôʲ���
//		StopWords SW = new StopWords();
//		SW.Init("����ͣ�ôʿ�.txt");
//		ArrayList<String> sw_result = SW.rmStopW(test_mode);
//		System.out.println(sw_result.toString());
		Hanlp_seg hseg = new Hanlp_seg();
		String weibo = "����Ϊtfboysӵ����ǿ��ķ�˿Ⱥ��";
		System.out.println("pure: "+hseg.pure_seg(weibo).toString());
		System.out.println("tag filter: "+hseg.filter_seg(weibo).toString());
//		
	}

}

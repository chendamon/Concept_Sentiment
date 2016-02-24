package NER;

import java.util.ArrayList;

public class Segtest 
{
	public static void main(String[] args) throws Exception
	{
		Ansj_seg seg = new Ansj_seg();
		seg.Seg("我爱北京天安门。");
		//jieba_seg seg = new jieba_seg();
//		ArrayList<String> test_mode = seg.jieba_Seg("科比是湖人球员。");
//		//停用词测试
//		StopWords SW = new StopWords();
//		SW.Init("中文停用词库.txt");
//		ArrayList<String> sw_result = SW.rmStopW(test_mode);
//		System.out.println(sw_result.toString());
//		
	}

}

package NER;

import java.util.ArrayList;

public class Segtest 
{
	public static void main(String[] args) throws Exception
	{
//		Ansj_seg seg = new Ansj_seg();
//		seg.Seg("我喜欢科比，讨厌詹姆斯。");
//		jieba_seg seg = new jieba_seg();
//		ArrayList<String> test_mode = seg.jieba_Seg("我喜欢科比，讨厌。");
//		
//		System.out.println(test_mode);
//		//停用词测试
//		StopWords SW = new StopWords();
//		SW.Init("中文停用词库.txt");
//		ArrayList<String> sw_result = SW.rmStopW(test_mode);
//		System.out.println(sw_result.toString());
		Hanlp_seg hseg = new Hanlp_seg();
		String weibo = "你认为tfboys拥有最强大的粉丝群吗？";
		System.out.println("pure: "+hseg.pure_seg(weibo).toString());
		System.out.println("tag filter: "+hseg.filter_seg(weibo).toString());
//		
	}

}

package Sentiment;

import java.util.ArrayList;

public class parse_test 
{
	public static void main(String[] args)
	{
		Parse p = new Parse();
		ArrayList<String> seg = new ArrayList<String>();
		String a = "权力的游戏 简直 太 棒 了 ！";
		///String b = "\n";
		//String c = "我 讨厌 哈登  。";
		seg.add(a);
		//seg.add(b);
		//seg.add(c);
		p.Init();
		ArrayList<String> re = p.Parse(seg);
		System.out.println(re.toString());
	}

}

package WikiConcept;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regextest 
{
	public static void main(String[] args)
	{
        //String l = "href=\"/subview/40566/9763638.htm\">Τ�£����������˶�Ա</a></div></li><li class=\"list-dot list-dot-paddingleft\"><div class=\"para\"><a target=_blank ";
//		String l = "	<div id='catlinks' class='catlinks'><div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\"><a href=\"/wiki/Special:%E9%A1%B5%E9%9D%A2%E5%88%86%E7%B1%BB\" title=\"Special:ҳ�����\">����</a>��<ul><li><a href=\"/wiki/Category:1989%E5%B9%B4%E5%87%BA%E7%94%9F\" title=\"Category:1989�����\">1989�����</a></li><li><a href"
//				+ "=\"/wiki/Category:%E5%9C%A8%E4%B8%96%E4%BA%BA%E7%89%A9\" title=\"Category:��������\">��������</a></li>"
//				+ "<li><a href=\"/wiki/Category:%E6%B4%9B%E6%9D%89%E7%A3%AF%E4%BA%BA\" title=\"Category:��ɼ����\">��ɼ���</a>"
//				+ "</li><li><a href=\"/wiki/Category:%E7%BE%8E%E5%9B%BD%E7%AF%AE%E7%90%83%E8%BF%90%E5%8A%A8%E5%91%98\" title=\"Category:���������˶�Ա\">���������˶�Ա</a></li><li><a href=\"/wiki/Category:%E4%BA%9E%E5%88%A9%E6%A1%91%E9%82%A3%E5%B7%9E%E7%AB%8B%E5%A4%A7%E5%AD%B8%E6%A0%A1%E5%8F%8B\" title=\"Category:����ɣ��������WУ��\">����ɣ��������ѧУ��</a></li>"
//						+ "<li><a href=\"/wiki/Category:%E4%BC%91%E6%96%AF%E6%95%A6%E7%81%AB%E7%AE%AD%E9%98%9F%E7%90%83%E5%91%98\" title=\"Category:��˹�ػ������Ա\">��˹�ػ������Ա</a></li><li><a href=\"/wiki/Category:2012%E5%B9%B4%E5%A4%8F%E5%AD%A3%E5%A5%A7%E6%9E%97%E5%8C%B9%E5%85%8B%E9%81%8B%E5%8B%95%E6%9C%83%E7%8D%8E%E7%89%8C%E5%BE%97%E4%B8%BB\" title=\"Category:2012���ļ��W��ƥ���\�ӕ����Ƶ���\">2012���ļ�����ƥ���˶��ά�Ƶ���</a></li></ul></div><div id=\"mw-hidden-catlinks\" class=\"mw-hidden-catlinks mw-hidden-cats-hidden\">���ط��ࣺ<ul><li><a href=\"/wiki/Category:%E6%9C%AC%E5%9C%B0%E7%9B%B8%E5%85%B3%E5%9B%BE%E7%89%87%E4%B8%8E%E7%BB%B4%E5%9F%BA%E6%95%B0%E6%8D%AE%E4%B8%8D%E5%90%8C\" title=\"Category:�������ͼƬ��ά�����ݲ�ͬ\">�������ͼƬ��ά�����ݲ�ͬ</a></li><li><a href=\"/wiki/Category:%E5%90%AB%E6%9C%89%E8%8B%B1%E8%AA%9E%E7%9A%84%E6%A2%9D%E7%9B%AE\" title=\"Category:����Ӣ�Z�ėlĿ\">����Ӣ�����Ŀ</a></li></ul></div></div>				<div class=\"visualClear\"></div>";
		
	    String regex ="\\pP|\\pS";
	    String t = "\"ddd,.����()";
		Pattern p_u = Pattern.compile(regex);
		Matcher m = p_u.matcher(t);
		while(m.find())
		{
			String temp = m.group(0);
//			String[] items = temp.split("\\s");
//			String category_url = "http://zh.wikipedia.org"+items[0].replace("href=", "").replaceAll("\"", "");
//			String category_dep = items[1].replace("title=\"Category:", "").replaceAll("\">", "");
			//System.out.println(category_dep+"\n"+category_url);
			System.out.println("temp"+temp);
			System.out.println("temp"+temp.replaceAll("[\\s]", ""));
		}
		
		String tt = "<li><a href=\"/wiki/%E6%93%81%E6%9C%89_(%E5%BC%B5%E4%BF%A1%E5%93%B2%E5%B0%88%E8%BC%AF)\" title=\"ӵ�� (������ר��)\">ӵ�� (������ר��)</a>��<a href=\"/wiki/%E5%BC%B5%E4%BF%A1%E5%93%B2\" title=\"������\">������</a>1994��ר����</li>";
		System.out.println(tt.replaceAll("<.*?>", ""));
		
		
		
	}

}

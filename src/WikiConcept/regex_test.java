package WikiConcept;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regex_test 
{
	public static void main(String[] args)
	{
//		String regex ="href=.*?>";
//		String l = "";
//		//String l = "<a href="/w/index.php?title=%E7%A7%91%E6%AF%94_(%E5%8C%97%E5%AE%89%E6%99%AE%E6%95%A6%E9%83%A1)&amp;action=edit&amp;redlink=1" class="new" title="�Ʊ� (�����նؿ�)��ҳ�治���ڣ�">�Ʊ�</a>��Corby����<a href="/wiki/%E8%8B%B1%E6%A0%BC%E8%98%AD" title="Ӣ����" class="mw-redirect">Ӣ����</a><a href="/wiki/%E5%8C%97%E5%AE%89%E6%99%AE%E6%95%A6%E9%83%A1" title="�����նؿ�">�����նؿ�</a>С��</li>";
//		Pattern p_u = Pattern.compile(regex);
//		Matcher m = p_u.matcher(l);
//		while(m.find())
//		{
//			String temp = m.group(0);
//			String[] items = temp.split("\\s");
//			String category_url = "http://zh.wikipedia.org"+items[0].replace("href=", "").replaceAll("\"", "");
//			String category_dep = items[1].replace("title=\"Category:", "").replaceAll("\">", "");
//			
//			//sy.put(category_url, category_dep);
//		}
//		
//		String ll = "";
//		regex = "\\\\x[0-9A-Z]+";
//		ll = ll.replaceAll(regex, "");
//		System.out.println("ll"+ll);
		String p = "ӵ��ϣ��";
		//ArrayList<String> plist = new ArrayList<String>();
		//plist.add(p);
		String src = "ϣ��";
		//String regex = ""+src+"[.*?]";
		System.out.println(p.contains(src));
				
		
	}

}

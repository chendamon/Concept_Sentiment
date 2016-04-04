package WikiConcept;
import java.io.UnsupportedEncodingException;


public class FJ_change 
{
	public static String big5ToChinese(String s){
		  try{
		     if(s==null||s.equals("")) return "";
		     String newstring=null;
		     newstring=new String(s.getBytes("big5"),"gb2312");
		     return newstring;
		    }
		  catch(UnsupportedEncodingException e)
		  {
		  return s;
		  }
		  }

		public static String ChineseTobig5(String s){
		  try{
		  if(s==null||s.equals("")) return "";
		  String newstring=null;
		  newstring=new String(s.getBytes("gb2312"),"big5");
		   return newstring;
		  }
		  catch(UnsupportedEncodingException e)
		  {
		  return s;
		 }
		  }
		public static void main(String[] args)
		{
			System.out.println(big5ToChinese("¸÷‡ø»@Çòß\„Ó†T"));
		}


}

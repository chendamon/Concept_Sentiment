package FanJian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Some handy utility functions for text pre-process, especially for Chinese.
 * @author sixiance
 *
 */
public class LangUtils {
	protected static Pattern allMarksPat;
	protected static Hashtable<Character, Character> t2s;
	protected static Hashtable<Character, Character> s2t;
	
	/**
	 * Prevent this class to be instantiated.
	 */
	private LangUtils () {} 
	
	static {
	  // Initialize the regular expression for FilterMarks().
		String pat = "[" +
			ChineseLanguageConstants.ALL_MARKS[0] + 
			ChineseLanguageConstants.ALL_MARKS[1].
				replace("\\", "\\\\").replace("]","\\]").
				replace("[", "\\[").replace("-", "\\-") +
			"]";
		allMarksPat = Pattern.compile( pat );
		
		// Initialize the map for Traditional/Simplified Chinese conversion.
		t2s = new Hashtable<Character, Character>();
		s2t = new Hashtable<Character, Character>();
		String schars = ChineseLanguageConstants.SIMPLIFIED_CHARS;
		String tchars = ChineseLanguageConstants.TRADITIONAL_CHARS;
		for (int i = 0; i < schars.length(); i++) {
		  t2s.put(tchars.charAt(i), schars.charAt(i));
		  s2t.put(schars.charAt(i), tchars.charAt(i));
		}
	}
	
	/**
	 * Remove extra spaces, which means more than one continuous spaces will be
	 * reduced to one space. Space here is not limited to the %32 white space 
	 * character, it also includes TAB, space in Chinese full width letter 
	 * and other special characters which appear as a white space. Spaces in the 
	 * begin or end of a line are all removed.
	 * @param text
	 * @return
	 */
	public static String removeExtraSpaces( String text ) {
		text = text.replace(
				ChineseLanguageConstants.SPACE[0], 
				ChineseLanguageConstants.SPACE[1]);
		text = text.replaceAll("[ \t\u000B\u000C\u00A0\uE5F1]+", " ");
		text = text.replaceAll("(^ +)|( +$)", "");
		return text;
	}
	
	/**
	 * Remove empty lines. An empty line means a line ends in \n or \r\n and
	 * contains only white space characters, or no characters at all.
	 * @param text
	 * @return
	 */
	public static String removeEmptyLines ( String text ) {
		text = text.replaceAll("^[ " +
		    ChineseLanguageConstants.SPACE[0] +
		    "\t\u000B\u000C\u00A0\uE5F1\r\n]*\n", "");
		text = text.replaceAll("[\r\n][ " +
        ChineseLanguageConstants.SPACE[0] +
        "\t\u000B\u000C\u00A0\uE5F1\r\n]*\n", "\n");
		return text;
	}
	
	/**
	 * Remove all punctuation marks in the text, and replace them with a ' '.
	 * @param text
	 * @return
	 */
	public static String removePunctuationMarks (String text) {
		return removeExtraSpaces(allMarksPat.matcher(text).replaceAll(" "));
	}
	
	public static String removePunctuationMarksExcept(
	    String text, String exception) {
	  String pat = "[" +
    ChineseLanguageConstants.ALL_MARKS[0].replaceAll(exception, "") + 
    ChineseLanguageConstants.ALL_MARKS[1].replaceAll(exception, "").
      replace("\\", "\\\\").replace("]","\\]").
      replace("[", "\\[").replace("-", "\\-") +
    "]";
	  Pattern toRemove = Pattern.compile(pat);
	  return toRemove.matcher(text).replaceAll(" ");
	}
	
	/**
	 * Convert full-width letters in Chinese fonts to normal half-width letters
	 * in ANSI charset. Numbers are not touched.
	 * @param text
	 * @return
	 */
	public static String mapFullWidthLetterToHalfWidth(String text) {
		char [] buf = new char [text.length()];
		text.getChars(0, text.length(), buf, 0);
		
		for ( int i = 0 ; i < buf.length ; i++ ) {
			switch ( buf[i] ) {
			case '��' :
				buf[i] = 'A';
				break;
			case '��' :
				buf[i] = 'B';
			case '��' :
				buf[i]= 'C';
				break;
			case '��':
				buf[i] = 'D';
				break;
			case '��':
				buf[i] = 'E';
				break;
			case '��':
				buf[i] = 'F';
				break;
			case '��':
				buf[i] = 'G';
				break;
			case '��':
				buf[i] = 'H';
				break;
			case '��':
				buf[i] = 'I';
				break;
			case '��':
				buf[i] = 'J';
				break;
			case '��':
				buf[i] = 'K';
				break;
			case '��':
				buf[i] = 'L';
				break;
			case '��':
				buf[i] = 'M';
				break;
			case '��':
				buf[i] = 'N';
				break;
			case '��':
				buf[i] = 'O';
				break;
			case '��':
				buf[i] = 'P';
				break;
			case '��':
				buf[i] = 'Q';
				break;
			case '��':
				buf[i] = 'R';
				break;
			case '��':
				buf[i] = 'S';
				break;
			case '��':
				buf[i] = 'T';
				break;
			case '��':
				buf[i] = 'U';
				break;
			case '��':
				buf[i] = 'V';
				break;
			case '��':
				buf[i] = 'W';
				break;
			case '��' :
				buf[i] = 'X';
				break;
			case '��' :
				buf[i]= 'Y';
				break;
			case '��':
				buf[i] = 'Z';
				break;
			case '��':
				buf[i] = 'a';
				break;
			case '��':
				buf[i] = 'b';
				break;
			case '��':
				buf[i] = 'c';
				break;
			case '��':
				buf[i] = 'd';
				break;
			case '��':
				buf[i] = 'e';
				break;
			case '��':
				buf[i] = 'f';
				break;
			case '��':
				buf[i] = 'g';
				break;
			case '��':
				buf[i] = 'h';
				break;
			case '��':
				buf[i] = 'i';
				break;
			case '��':
				buf[i] = 'j';
				break;
			case '��':
				buf[i] = 'k';
				break;
			case '��':
				buf[i] = 'l';
				break;
			case '��':
				buf[i] = 'm';
				break;
			case '��':
				buf[i] = 'n';
				break;
			case '��':
				buf[i] = 'o';
				break;
			case '��':
				buf[i] = 'p';
				break;
			case '��':
				buf[i] = 'q';
				break;
			case '��':
				buf[i] = 'r';
				break;
			case '��':
				buf[i] = 's';
				break;
			case '��':
				buf[i] = 't';
				break;
			case '��':
				buf[i] = 'u';
				break;
			case '��':
				buf[i] = 'v';
				break;
			case '��':
				buf[i] = 'w';
				break;
			case '��':
				buf[i] = 'x';
				break;
			case '��':
				buf[i] = 'y';
				break;
			case '��':
				buf[i] = 'z';
				break;
			
			default :
				
			}
		}
		text = new String ( buf );
		return text;		
	}
	
	/**
	 * Convert full-width numbers in Chinese fonts to normal half-width numbers
	 * in ANSI charset. 
	 */
	public static String mapFullWidthNumberToHalfWidth(String text){
		char [] buf = new char [text.length()];
		text.getChars(0, text.length(), buf, 0);
		
		for ( int i = 0 ; i < buf.length ; i++ ) {
			switch ( buf[i] ) {
			case '��':
				buf[i] = '0';
				break;
			case '��':
				buf[i] = '1';
				break;
			case '��':
				buf[i] = '2';
				break;
			case '��':
				buf[i] = '3';
				break;
			case '��':
				buf[i] = '4';
				break;
			case '��':
				buf[i] = '5';
				break;
			case '��':
				buf[i] = '6';
				break;
			case '��':
				buf[i] = '7';
				break;
			case '��':
				buf[i] = '8';
				break;
			case '��':
				buf[i] = '9';
				break;
			default :
				
			}
		}
		text = new String ( buf );
		return text;		
	}
	
	/**
	 * Convert Chinese full-width punctuation marks to corresponding ANSI marks.
	 * @param text
	 * @return
	 */
	public static String mapChineseMarksToAnsi( String text ) {
		char [] buf = text.toCharArray();
		
		for ( int i = 0 ; i < buf.length ; i++ ) {
			switch ( buf[i] ) {
			case '��' :
			case '��' :
				buf[i] = '"';
				break;
			case '��' :
			case '��' :
				buf[i]= '\'';
				break;
			case '��':
				buf[i] = '(';
				break;
			case '��':
				buf[i] = ')';
				break;
			case '��':
				buf[i] = '~';
				break;
			case '��':
				buf[i] = '`';
				break;
			case '��':
				buf[i] = '!';
				break;
			case '��':
				buf[i] = '@';
				break;
			case '��':
				buf[i] = '#';
				break;
			case '��':
				buf[i] = '$';
				break;
			case '��':
				buf[i] = '%';
				break;
			case '��':
				buf[i] = '&';
				break;
			case '��':
				buf[i] = '*';
				break;
			case '��':
				buf[i] = '+';
				break;
			case '��':
				buf[i] = '-';
				break;
			case '��':
				buf[i] = '=';
				break;
			case '��':
				buf[i] = ';';
				break;
			case '��':
				buf[i] = ':';
				break;
			case '��':
				buf[i] = ',';
				break;
			case '��':
				buf[i] = '/';
				break;
			case '��':
				buf[i] = '?';
				break;
			case '��':
				buf[i] = '|';
				break;
			case '��':
				buf[i] = ' ';
				break;
			default :
				
			}
		}
		text = new String ( buf );
		return text;
	}
	
	/**
	 * Remove all line-ends like '\r\n' or '\n', make sure the returned text
	 * contains only one line.
	 * @param text
	 * @return
	 */
	public static String removeLineEnds ( String text ) {
		return text.replaceAll("[\r\n]+", " ").trim();
	}
	
  /**
   * Use code point of a character to decide if it is a Chinese character 
   * @param codePoint
   * @return
   */
  public static boolean isChinese( int codePoint ) {
    return codePoint >= ChineseLanguageConstants.CHINESE_START && 
      codePoint <= ChineseLanguageConstants.CHINESE_END;
  }
  
  /**
   * Convert traditional Chinese text to simplified Chinese text.
   * @param text
   * @return
   */
  public static String T2S(String text) {
    char [] chars = text.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      Character replacement = t2s.get(chars[i]);
      if (replacement != null) {
        chars[i] = replacement;
      }
    }
    return new String(chars);
  }
  
  /**
   * Convert simplified Chinese text to traditional Chinese text.
   * @param text
   * @return
   */
  public static String S2T(String text) {
    char [] chars = text.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      Character replacement = s2t.get(chars[i]);
      if (replacement != null) {
        chars[i] = replacement;
      }
    }
    return new String(chars);
  }

  public static boolean isSimplifiedChineseChar(char code){
	  return (code>=19968 && code<=171941);
  }
  public static boolean isLetterAndNumber(char code){
	  return (code>='A'&&code<='Z')||(code>='a'&&code<='z');
  }
public static String replaceAnsiMarkWithSpace(String text) {
//	text=text.replaceAll("\\pP|\\pS|\\pZ|\\pM", " ");
//	text=text.replaceAll(" {1,}", ",");
//	List<Character> characters=new ArrayList<>();
//	for(int i=0;i<text.length()-1;i++){
//		characters.add(text.charAt(i));
//		if((isSimplifiedChineseChar(text.charAt(i))&&isLetterAndNumber(text.charAt(i+1)))||(isLetterAndNumber(text.charAt(i))&&isSimplifiedChineseChar(text.charAt(i+1)))){
//			characters.add(' ');
//		}
//	}
//	characters.add(text.charAt(text.length()-1));
//	StringBuilder builder = new StringBuilder(characters.size());
//    for(Character ch: characters)
//    {
//        builder.append(ch);
//    }
//    return builder.toString();
	
	text=text.replaceAll("\\pP|\\pS|\\pZ|\\pM", " ");
	text=text.trim();
	text=text.replaceAll(" {1,}", ",");
	return text;
}

public static String removeAnsiMarkKeepSpaceAndBreakBetweenEnglishWord(String text) {
	char[] chars=text.toCharArray();
	for(char xx:chars){
		System.out.println(xx);
	}
	// TODO Auto-generated method stub
	return null;
}
}

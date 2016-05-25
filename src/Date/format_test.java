package Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class format_test 
{
	public static void main(String[] args) throws ParseException
	{
		TimeFormatChange format = new TimeFormatChange();
		String start = format.format("1406822400000");
		String end = format.format("1463284208000");
		System.out.println("start: "+start);
		System.out.println("end: "+end);
		
	}

}

package Date;

public class TimeFormatChange 
{
	//将unix时间戳转化成普通的时间格式
	//5.10
	public String format(String time)
	{
		Long timestamp = Long.parseLong(time);
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));  
		//System.out.println(date);
		return date;
	}

}

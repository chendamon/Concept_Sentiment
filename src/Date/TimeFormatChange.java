package Date;

public class TimeFormatChange 
{
	//��unixʱ���ת������ͨ��ʱ���ʽ
	//5.10
	public String format(String time)
	{
		Long timestamp = Long.parseLong(time);
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));  
		//System.out.println(date);
		return date;
	}
	public String format_cal(String time)
	{
		Long timestamp = Long.parseLong(time);
		String date = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date(timestamp));  
		//System.out.println(date);
		return date;
	}

}

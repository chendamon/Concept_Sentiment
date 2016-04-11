package Date;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 4.11
 * 显示时间，记录算法效率
 */
public class showtime 
{
	public void show_time()
	{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String hehe = dateFormat.format( now ); 
		System.out.println("Time now: "+hehe); 
	}
}

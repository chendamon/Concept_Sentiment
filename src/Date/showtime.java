package Date;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 4.11
 * ��ʾʱ�䣬��¼�㷨Ч��
 */
public class showtime 
{
	public void show_time()
	{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//���Է�����޸����ڸ�ʽ
        String hehe = dateFormat.format( now ); 
		System.out.println("Time now: "+hehe); 
	}
}

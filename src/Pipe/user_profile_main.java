package Pipe;

import Date.showtime;

public class user_profile_main 
{
    //构建user profile的主函数
	public static void main(String[] args) throws Exception
	{
		int number = Integer.parseInt(args[0]);
		user_profile_create ufc = new user_profile_create();
		showtime st = new showtime();
		st.show_time();
		ufc.crush_it(number);
		st.show_time();
	}

}

package Pipe;

public class user_profile_main 
{
    //����user profile��������
	public static void main(String[] args) throws Exception
	{
		int number = Integer.parseInt(args[0]);
		user_profile_create ufc = new user_profile_create();
		ufc.crush_it(number);
	}

}

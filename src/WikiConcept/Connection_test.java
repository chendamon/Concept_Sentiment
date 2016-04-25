package WikiConcept;

import java.io.IOException;

public class Connection_test 
{
	public static void main(String[] args) throws IOException
	{
		getConceptsFWT fwt = new getConceptsFWT();
		fwt.getReFB("œ ª®", null, null, false);
	}

}

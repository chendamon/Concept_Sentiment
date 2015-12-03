package WikiConcept;

import java.io.IOException;

public class ConnetTest 
{
	public static void main(String[] args) throws IOException
	{
		getConceptsFWT gc = new getConceptsFWT();
		System.out.println("Processing...");
		gc.getReFB("¿Æ±È",null);
		
	}

}

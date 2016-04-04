package WikiConcept;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class sort_test 
{
	public static void main(String[] args)
	{
		ArrayList<Integer> f = new ArrayList<Integer>();
		f.add(5);
		f.add(6);
		f.add(2);
		f.add(1);
		Collections.sort(f, new Comparator() {

			  @Override
             public int compare(Object o1, Object o2) {

			       if((Integer)o1 >= (Integer)o2)
			    	   return -1;
			       else return 1;

			      }

			    });
		
		System.out.println(f.toString());
	}

}

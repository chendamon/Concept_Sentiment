package WikiConcept;

import java.util.HashMap;

/*
 * ������������ʱ�򣬸�����Ӧ��name�õ���Ӧʵ�����з�ֵ
 * author biront
 * date 2015/12/7
 * eS ��֮ǰNER/Entity_Sent�õ���
 */
public class findSent 
{
	public int getS(HashMap<String,Integer> eS,String name)
	{
		if(eS.containsKey(name))
			return eS.get(name);
		else return 0;
	}
 
}

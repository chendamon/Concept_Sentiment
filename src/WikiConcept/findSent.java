package WikiConcept;

import java.util.HashMap;

/*
 * 构建概念树的时候，根据相应的name得到对应实体的情感分值
 * author biront
 * date 2015/12/7
 * eS 是之前NER/Entity_Sent得到的
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


public class Item 
{
	private boolean isConsumed;
	
	Item()
	{
		isConsumed = false;
	}
	
	void consume()
	{
		isConsumed = true;
	}
	
	boolean isConsumed()
	{
		return isConsumed;
	}
	
	
}

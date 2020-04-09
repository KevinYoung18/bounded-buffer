import java.util.ArrayDeque;

public class AtomicDeque <T>
{
	private ArrayDeque<T> deque = new ArrayDeque<T>();
	
	synchronized void add(T object)
	{
		deque.add(object);
	}
	
	synchronized T poll()
	{
		return deque.poll();
	}
	
	synchronized int size()
	{
		return deque.size();
	}
	
	
}

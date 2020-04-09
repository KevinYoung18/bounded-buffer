import java.util.ArrayDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Locks implements ProducerConsumer
{
	volatile ArrayDeque<Item> buffer= new ArrayDeque<Item>();
	ReentrantLock lock = new ReentrantLock(true);
	final int BOUND = 10;
	volatile boolean isProducing = true;
	
	
	Locks(int numProducers, int numConsumers)
	{
	 
		ExecutorService producerPool = Executors.newFixedThreadPool(numProducers);
		ExecutorService consumerPool = Executors.newFixedThreadPool(numConsumers);
		
		for(int i  = 0; i < numProducers; i++)
		{
			final int id = i;
			Runnable producer = () -> 
			{
				produce(id);
			};
			producerPool.submit(producer);
		}
		
		for(int i  = 0; i < numConsumers; i++)
		{
			final int id = i;
			Runnable consumer = () -> 
			{
				consume(id);
			};
			consumerPool.submit(consumer);
			
		}
		
		try {
			producerPool.shutdown();
			producerPool.awaitTermination(20, TimeUnit.MINUTES);
			isProducing = false;
			consumerPool.shutdown();	
			consumerPool.awaitTermination(20, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void produce(int id)
	{
		for(int i = 0; i < 100; i++)
		{
			
			while(buffer.size() >= BOUND) 
			{
				
				System.out.println("Producer " + id + ": buffer full. Waiting... ");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

					
			}
			lock.lock();
			Item item = new Item();
			buffer.add(item);
			System.out.println("Producer " + id + ": produced Item " + i);
			
			lock.unlock();

		}
	}
	public void consume(int id)
	{
		
		while(isProducing)
		{
			
			while(buffer.size() == 0)
			{
				System.out.println("Consumer " + id + ": no Items to consume. Waiting... ");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(!isProducing)
					break;
			}
			
			if(buffer.size() > 0)
			{
				lock.lock();
				buffer.poll();
				System.out.println("Consumer " + id + ": consumed Item ");
				lock.unlock();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			
			
			
		}
		
	}
}

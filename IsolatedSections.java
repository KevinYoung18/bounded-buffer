import java.util.ArrayDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class IsolatedSections implements ProducerConsumer
{
	volatile ArrayDeque<Item> buffer = new ArrayDeque<Item>();
	final int BOUND = 10;
	volatile boolean isProducing = true;
	
	IsolatedSections(int numProducers, int numConsumers)
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
	
	@Override
	public void produce(int id)
	{
		for(int i = 0; i < 100; i++)
		{
			synchronized(this)
			{
				while(buffer.size() >= BOUND) 
				{
					System.out.println("Producer " + id + ": buffer full. Waiting... ");
					try {
						wait(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

						
				}
				notify();
				Item item = new Item();
				buffer.add(item);
				System.out.println("Producer " + id + ": produced Item " + i);
			}
		}
	}

	@Override
	public void consume(int id)
	{
		
		while(isProducing)
		{
			
				while(buffer.size() == 0)
				{
					System.out.println("Consumer " + id + ": no Items to consume. Waiting... ");
					try {
						wait(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(!isProducing)
						break;
				}
				if(buffer.size() > 0)
				{
					synchronized(this) 
					{
						buffer.poll();
						System.out.println("Consumer " + id + ": consumed Item ");
						notify();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
			}
		}
		//finish consuming rest of buffer
		
		while(buffer.size() > 0)
		{
			synchronized(this) 
			{
				buffer.poll();
				System.out.println("Consumer " + id + ": consumed Item ");
				notify();
			}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
	
}

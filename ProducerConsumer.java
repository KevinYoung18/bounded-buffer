
public interface ProducerConsumer 
{
	//produce an item to a bounded buffer
	void produce(int id);
	
	//consume an item in the bounded buffer
	void consume(int id);
}

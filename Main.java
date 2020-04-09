
public class Main {

	public static void main(String[] args) throws InterruptedException 
	{
		
		long time = System.currentTimeMillis();
		Locks l1 = new Locks(2,5);
		long lock25 = System.currentTimeMillis() - time;
		
		time = System.currentTimeMillis();
		l1 = new Locks(5,2);
		long lock52 = System.currentTimeMillis() - time;
		
		time = System.currentTimeMillis();
		IsolatedSections i1 = new IsolatedSections(2,5);
		long is25 = System.currentTimeMillis() - time;

		time = System.currentTimeMillis();
		i1 = new IsolatedSections(5,2);
		long is52 = System.currentTimeMillis() - time;
		
		time = System.currentTimeMillis();
		Atomics a1 = new Atomics(2,5);
		long atomic25 = System.currentTimeMillis() - time;
		

		time = System.currentTimeMillis();
		a1 = new Atomics(5,2);
		long atomic52 = System.currentTimeMillis() - time;

		System.out.println("Execution time for locks with 2 producers and 5 consumers (seconds): " + (lock25 / 1000));
		System.out.println("Execution time for locks with 5 producers and 2 consumers (seconds): " + (lock52 / 1000));

		System.out.println("Execution time for isolated sections with 2 producers and 5 consumers (seconds): " + (is25 / 1000));
		System.out.println("Execution time for isolated sections with 5 producers and 2 consumers (seconds): " + (is52 / 1000));

		System.out.println("Execution time for atomics with 2 producers and 5 consumers (seconds): " + (atomic25 / 1000));
		System.out.println("Execution time for atomics with 5 producers and 2 consumers (seconds): " + (atomic52 / 1000));

		System.out.println("Execution time for locks with 2 producers and 5 consumers (seconds): " + (lock25 / 1000));
		System.out.println("Execution time for locks with 5 producers and 2 consumers (seconds): " + (lock52 / 1000));

	}


}

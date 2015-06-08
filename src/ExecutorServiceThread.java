import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceThread {

	public static void main(String[] args) {
		//ExecutorService deals with thread management creation and destruction.
		ExecutorService ex = Executors.newFixedThreadPool(2);
		
		for(int i =0 ; i <5 ; i++){
			ex.submit(new ExecutorWorker(i));
		}
		System.out.println("All threads submitted ");
		ex.shutdown();
		//awaitTermination should be called after the shutdown
		try {
			ex.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Completed all threads");
	}

}

class ExecutorWorker implements Runnable {
	private int id;
	
	public ExecutorWorker(int id){
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("Worker Thread started with id: " + id);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Worker Thread completed with id: " + id);
	}
	
}
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class BlockingQueueThread {
	//Using ArrayBlockingQueue which is thread safe and make life simple
	private static BlockingQueue<Integer> bq = new ArrayBlockingQueue<Integer>(10);
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("complete");
	}
	
	private static void producer() throws InterruptedException{
		Random rm = new Random();
		
		while(true){
			Thread.sleep(100);
			bq.put(rm.nextInt(100));
			System.out.println("Size of queue" + bq.size());
		}
	}
	
	private static void consumer() throws InterruptedException{
		Random rm = new Random();
		while(true){
			if(rm.nextInt(10) == 0){
				Thread.sleep(100);
				System.out.println("Value take " + bq.take() + " size of Queue " + bq.size());
			}
		}
	}
}

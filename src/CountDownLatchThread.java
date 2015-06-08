import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Usage
// application should not start processing any thread until all service is up
// and ready to do there job.
// Countdown latch is idle choice here, main thread will start with count 3
// and wait until count reaches zero. each thread once up and read will do
// a count down. this will ensure that main thread is not started processing
// until all services is up.


public class CountDownLatchThread {
	
	public static void main(String[] args) {
		//Synchronized class, runs one thread at a time.
		CountDownLatch latch = new CountDownLatch(3);
		
		String[] service = {"ValidateService", "EmailService", "AlertService", "CacheService"};
		
		ExecutorService ex = Executors.newFixedThreadPool(2);
		for(String srv : service){
			ex.submit(new CountDownLatchWorker(latch, srv));
		}
		
		try {
			latch.await();
			ex.shutdown();
			ex.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("All service up and running");
	}

}

class CountDownLatchWorker implements Runnable{
	
	private CountDownLatch latch;
	private String serviceName;
	
	public CountDownLatchWorker(CountDownLatch latch, String serviceName){
		this.latch = latch;
		this.serviceName = serviceName;
	}

	@Override
	public void run() {
		System.out.println("Starting " + serviceName);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		latch.countDown();
	}
	
}
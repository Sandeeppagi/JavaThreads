import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class CallableThread {

	public static void main(String[] args) {
		ExecutorService ex = Executors.newCachedThreadPool();
		
		//To used void return type for future
		//1. Future<?> 
		//2. new Callable<Void> and public Void call() -- Yes with V capital
		//3. return null in call
		Future<Integer> f = ex.submit(new Callable<Integer>(){

			@Override
			public Integer call() throws Exception {
				Random rm = new Random();
				int number = rm.nextInt(4000);
				System.out.println("Starting.. ");
				if(number > 3000){
					throw new IOException("Too long execution");
				}
				Thread.sleep(1000);
				System.out.println("Finished.. ");
				return number;
			}});
		ex.shutdown();
		
		try {
			ex.awaitTermination(1, TimeUnit.DAYS);
			System.out.println("finished in " + f.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			IOException ex1 = (IOException)e.getCause(); 
			System.out.println(ex1.getMessage());
		}
	}

}

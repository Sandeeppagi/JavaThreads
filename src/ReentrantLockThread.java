import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLockThread {

	public static void main(String[] args) {
		final ReentrantLockWorker wk = new ReentrantLockWorker();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					wk.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					wk.secondThread();
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

		 wk.finished();
	}

}

class ReentrantLockWorker{
	private int count = 0;
	
	//ReentrantLock is same as synchronized only with the difference 
	//unlike synchronized it can be called across  methods
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	
	private void increment(){
		for(int i = 0; i < 10000; i++){
		count++;
		}
	}

	public void firstThread() throws InterruptedException {
		lock.lock();
		
		System.out.println("Waiting...");
		//Same as wait, also needs to be called inside lock() and unloack()
		cond.await();
		
		System.out.println("Running...");
		try{
		increment();
		}finally{
			lock.unlock();
		}
	}

	public void secondThread() throws InterruptedException {
		Thread.sleep(1000);
		lock.lock();
		
		System.out.println("Enter to resume");
		new Scanner(System.in).nextLine();
		System.out.println("Got the key");
		
		//Same as notify, also needs to be called inside lock() and unloack()
		cond.signal();
		
		try{
		increment();
		}finally{
			lock.unlock();
		}
	}
	public void finished(){
		System.out.println("Value of count is " + count);
	}
}

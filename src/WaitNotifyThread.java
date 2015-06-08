import java.util.LinkedList;

public class WaitNotifyThread {
	public static void main(String[] args) {
		final WaitNotifyWorker wk = new WaitNotifyWorker();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					wk.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					wk.consumer();
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

}

//Wait and Notify should be called from synchronized only
class WaitNotifyWorker{
	
	private LinkedList<Integer> lk = new LinkedList<Integer>();
	private final int Limit = 10;
	private Object lock = new Object();
	
	public void producer() throws InterruptedException{
		int value = 0;
		while(true){
			synchronized (lock) {
				while(lk.size() == Limit){
					lock.wait();
				}
				lk.add(value++);
				lock.notify();
			}	
		}
	}
	
	public void consumer() throws InterruptedException{
		while(true){
			synchronized (lock) {
				while(lk.size() == 0){
					lock.wait();
				}
				System.out.println("List size is " + lk.size());
				Integer value = lk.removeFirst();
				System.out.println("Removing value : " +  value + " size is " + lk.size());
				lock.notify();
			}
			Thread.sleep(2000);
		}
	}
	
}
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectLock {
	public static void main(String[] args) {
		Worker w = new Worker();
		w.CreateWorkerForJob();
	}
}

class Worker {
	Random rm = new Random();

	private List<Integer> l1 = new ArrayList<Integer>();
	private List<Integer> l2 = new ArrayList<Integer>();

	// Creating 2 objects locks for 2 different blocks in side the method.
	private Object lock1 = new Object();
	private Object lock2 = new Object();

	public void stageOne() {
		synchronized (lock1) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			l1.add(rm.nextInt(100));
			System.out.println(Thread.currentThread().getName() + " stageOne " + "size of List-1 " + l1.size());
		}
	}

	public void stageTwo() {
		synchronized (lock2) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			l2.add(rm.nextInt(100));
			System.out.println(Thread.currentThread().getName() + " stageTwo " + "size of List-2 " + l2.size());
		}

	}

	public void process() {
		for (int i = 0; i < 10; i++) {
			// We are calling 2 methods one after the another which if marked
			// as synchronized
			// for both the method we have common lock i.e. class lock, so
			// unless one thread doesn't
			// give ups the thread. 2nd thread will be waiting.
			stageOne();
			stageTwo();
		}
	}

	public void CreateWorkerForJob() {
		System.out.println("Stating the work..");
		long start = System.currentTimeMillis();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});

		t1.setName("t1");
		t1.start();
		t2.setName("t2");
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		System.out.println("Total time take : " + (end - start));
		System.out.println("List-1: " + l1.size() + "; List-2: " + l2.size());
	}
}
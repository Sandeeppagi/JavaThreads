class MyThread extends Thread{
	@Override
	public void run(){
		System.out.println("Using extending a Thread Class");
		for(int i=0; i <10 ; i++){
			try {
				Thread.sleep(100);
				System.out.println("Value of i : " + i +  " Running thread name " + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class MyRunnableThread implements Runnable{

	@Override
	public void run() {
		System.out.println("Using Runnable Implementation");
		for(int i=0; i <10 ; i++){
			try {
				Thread.sleep(100);
				System.out.println("Value of i : " + i +  " Running thread name " + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class StartThread {
	public static void main(String[] args) {
		

		//Using Extend
		MyThread t1 = new MyThread();
		t1.setName("t1");
		t1.start();
		MyThread t2 = new MyThread();
		t2.setName("t2");
		t2.start();
		

		//Using Runnable Implementation
		Thread t3 = new Thread(new MyRunnableThread());
		t3.setName("t3");
		t3.start();
		Thread t4 = new Thread(new MyRunnableThread());
		t4.setName("t4");
		t4.start();
		
		//Using anonymous class
		Thread t5 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Using anonymous class");
				for(int i=0; i <10 ; i++){
					try {
						Thread.sleep(100);
						System.out.println("Value of i : " + i +  " Running thread name " + Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		t5.setName("t5");
		t5.start();
		
	}
}

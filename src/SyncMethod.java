public class SyncMethod {
	
	private int count = 0;
	
	public static void main(String[] args) {
		SyncMethod syn = new SyncMethod();
		syn.workers();
	}
	
	public void workers(){
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i < 1000 ; i++){
					increment();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i < 1000 ; i++){
					increment();
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
		
		System.out.println(count);
	}

	public synchronized void increment(){
		count++;
	}
}

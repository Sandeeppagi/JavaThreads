import java.util.Scanner;

class CheckVolatile extends Thread{
	
	private volatile boolean running =  true;
	@Override
	public void run() {
		while(running){
			System.out.println("I am Running");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void stopRunning(){
		this.running =  false;
	}
}
public class SyncThread {
	public static void main(String[] args) {
		CheckVolatile t1 = new CheckVolatile();
		t1.start();
		
		System.out.println("Press enter..");
		Scanner scn = new Scanner(System.in);
		scn.nextLine();
		
		t1.stopRunning();
	}

}

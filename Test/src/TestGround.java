

public class TestGround {
	
	public static void main (String[] args) throws InterruptedException {

		long time = System.currentTimeMillis();

		Thread.sleep(1000);
		System.out.println(time - System.currentTimeMillis());

	}
	
}
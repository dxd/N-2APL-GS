package envJavaSpace;

public class ClockTicker implements Runnable{
	GeoSenseMW env;
	
	public ClockTicker(GeoSenseMW env){
		this.env = env;
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(500);
				env.updateClock(1);
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}
	}
}

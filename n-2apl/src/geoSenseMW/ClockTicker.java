package geoSenseMW;

public class ClockTicker implements Runnable{
	EnvGeoSense env;
	
	public ClockTicker(EnvGeoSense env){
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

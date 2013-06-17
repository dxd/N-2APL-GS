package geoSenseMW;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.j_spaces.core.client.EntryArrivedRemoteEvent;

import tuplespace.Obligation;
import tuplespace.TimeEntry;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;
import net.jini.space.JavaSpace;

public class OrgHandler extends UnicastRemoteObject implements RemoteEventListener {

	EnvGeoSense envGeoSense;


	public OrgHandler(EnvGeoSense envGeoSense) throws RemoteException{ 
		
		this.envGeoSense = envGeoSense;
		
	}

	public void notify(RemoteEvent anEvent) {

        try {
        	EntryArrivedRemoteEvent arrivedRemoteEvent =(EntryArrivedRemoteEvent) anEvent;
        	TimeEntry e = (TimeEntry) arrivedRemoteEvent.getObject();
        	System.out.println("org notification: "+e);
        	envGeoSense.notifyOrg();
        	
            

        } catch (Exception anE) {
            System.out.println("Error while procession organization notification");
            anE.printStackTrace(System.out);
        }
    }
}



package geoSenseMW;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.j_spaces.core.client.EntryArrivedRemoteEvent;

import tuplespace.Obligation;
import tuplespace.Points;
import tuplespace.Prohibition;
import tuplespace.Reading;
import tuplespace.TimeEntry;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;

public class AgentHandler extends UnicastRemoteObject implements RemoteEventListener {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	EnvGeoSense envGeoSense;
	String agent;
	HashMap<String, Timestamp> timestamps;
	
	public AgentHandler(EnvGeoSense envGeoSense, String agent) throws RemoteException {
		
		this.envGeoSense = envGeoSense;
		this.agent = agent;
		timestamps = new HashMap<String,Timestamp>();
    }

	public void notify(RemoteEvent anEvent) {
		//System.out.println("subject notification "+subject+" number "+anEvent.getSequenceNumber());
        try {
        	EntryArrivedRemoteEvent arrivedRemoteEvent =(EntryArrivedRemoteEvent) anEvent;
        	TimeEntry e = (TimeEntry) arrivedRemoteEvent.getObject();
        	System.out.println("subject notification: "+e);
        	envGeoSense.notifyAgent(agent, e);
        	/*
        	String type = anEvent.getRegistrationObject().get().toString();
        	Timestamp newTime = new Timestamp(System.currentTimeMillis());
        	ArrayList<TimeEntry> r = new ArrayList<TimeEntry>();
            //System.out.println("Got event: " + anEvent.getSource() + ", " +
            //                   anEvent.getID() + ", " +
            //                   anEvent.getSequenceNumber() + ", " + 
            //                   anEvent.getRegistrationObject().get());
            
            if (type.equals("reading")) {
            	System.out.println("subject position notification "+subject+" number "+anEvent.getSequenceNumber());
            	Reading temp = new Reading(subject);
            	r = envGeoSense.readTuple(temp,timestamps.get(type) != null?timestamps.get(type):new Timestamp(0),newTime);
            	
            }
            else if (type.equals("obligation")) {
            	System.out.println("subject obligation notification "+subject+" number "+anEvent.getSequenceNumber());
            	Obligation temp = new Obligation(subject);
            	r = envGeoSense.readTuple(temp,timestamps.get(type) != null?timestamps.get(type):new Timestamp(0),newTime);
            	
            }
            else if (type.equals("prohibition")) {
            	System.out.println("subject prohibition notification "+subject+" number "+anEvent.getSequenceNumber());
            	Prohibition temp = new Prohibition(subject);
            	r = envGeoSense.readTuple(temp,timestamps.get(type) != null?timestamps.get(type):new Timestamp(0),newTime);
            	
            }
            else if (type.equals("points")) {
            	System.out.println("subject points notification "+subject+" number "+anEvent.getSequenceNumber());
            	Points temp = new Points(subject);
            	r = envGeoSense.readTuple(temp,timestamps.get(type) != null?timestamps.get(type):new Timestamp(0),newTime);
            	
            }
            timestamps.put(type, newTime);
            
            */
        } catch (Exception anE) {
           System.out.println("Error while processing subject notification");
            anE.printStackTrace(System.out);
        }
    }
}


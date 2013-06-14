package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.rmi.MarshalledObject;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import net.jini.core.lease.Lease;
import net.jini.core.lease.LeaseDeniedException;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.UnusableEntryException;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.transaction.TransactionException;

import com.gigaspaces.events.DataEventSession;
import com.gigaspaces.events.EventSessionConfig;
import com.gigaspaces.events.EventSessionFactory;
import com.gigaspaces.events.NotifyActionType;
import com.j_spaces.core.IJSpace;
import com.javadocmd.simplelatlng.LatLng;

import dataJSon.Status;
import tuplespace.ActionRequest;
import tuplespace.Cargo;
import tuplespace.Cell;
import tuplespace.Coin;
import tuplespace.NotificationHandler;
import tuplespace.Points;
import tuplespace.Position;
import tuplespace.Time;
import tuplespace.TimeEntry;



public class JSpace {
	
	public GigaSpace space = null;
	//ServiceRegistrar sr = null;
	//static ServiceDiscoveryManager sdm;
	//private static TransactionManager transManager;
	//private static LeaseRenewalManager leaseRenewalManager;
	public static String[] agents = {"a1", "a2", "a3", "t1", "c1"};
	public Integer clock = 0;
	private DataEventSession session;
	public Synchronization synchronization;

	public JSpace(){
		init();
	}
	
	public void init() {
		try {
        	File file = new File("./log/"+ new Date(System.currentTimeMillis()) +".log");

            // Create file if it does not exist
            boolean success = file.createNewFile();
            if (success) {
                // File did not exist and was created
            } else {
                // File already exists
            }
            
            PrintStream printStream;
    		try {
    			printStream = new PrintStream(new FileOutputStream(file));
    			System.setOut(printStream);
    		} catch (FileNotFoundException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        } catch (IOException e) {
        	
        }
		
		IJSpace ispace = new UrlSpaceConfigurer("jini://*/*/myGrid").space();
        // use gigaspace wrapper to for simpler API
        this.space = new GigaSpaceConfigurer(ispace).gigaSpace();
       // this.space=DataGridConnectionUtility.getSpace("myGrid");
        EventSessionConfig config = new EventSessionConfig();
        config.setFifo(true);
        config.setBatch(100, 20000);
        EventSessionFactory factory = EventSessionFactory.getFactory(ispace);
        try {
			session = factory.newDataEventSession(config);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
       /* System.setSecurityManager(new RMISecurityManager());
      
        try {
        	File file = new File("./log/"+ new Date(System.currentTimeMillis()) +".log");

            // Create file if it does not exist
            boolean success = file.createNewFile();
            if (success) {
                // File did not exist and was created
            } else {
                // File already exists
            }
            
            PrintStream printStream;
    		try {
    			printStream = new PrintStream(new FileOutputStream(file));
    			System.setOut(printStream);
    		} catch (FileNotFoundException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        } catch (IOException e) {
        	
        }
		
		LookupLocator ll = null;
		try {
			ll = new LookupLocator("jini://kafka.cs.nott.ac.uk:4160");
			//ll = new LookupLocator("jini://10.154.219.251");
			//ll = new LookupLocator("jini://10.154.154.26");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sr = ll.getRegistrar();
		} catch (IOException e) {
			

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println("Service Registrar: "+sr.getServiceID());


		ServiceTemplate template = new ServiceTemplate(null, new Class[] { JavaSpace.class }, null);

		ServiceMatches sms = null;
		try {
			sms = sr.lookup(template, 10);
		} catch (RemoteException e) {

			e.printStackTrace();
		}
		if(0 < sms.items.length) {
		    space = (JavaSpace) sms.items[0].service;
		    System.out.println("Java Space found.");
		   
		   
		} else {
		    System.out.println("No Java Space found.");
		}
		ServiceTemplate trans = new ServiceTemplate(null, new Class[] { TransactionManager.class }, null);

		ServiceMatches sms1 = null;
		try {
			sms1 = sr.lookup(trans, 10);
		} catch (RemoteException e) {

			e.printStackTrace();
		}
		if(0 < sms1.items.length) {
		    transManager = (TransactionManager) sms1.items[0].service;
		    System.out.println("TransactionManager found.");
		   
		   
		} else {
		    System.out.println("No TransactionManager found.");
		}
		try {
			sdm = new ServiceDiscoveryManager(null,null);
			leaseRenewalManager = sdm.getLeaseRenewalManager();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}

	public boolean error() {
		
		return space == null;
	}

	public ArrayList<TimeEntry> readUpdate(TimeEntry te, Timestamp date, Timestamp newTime) {
	
		ArrayList<TimeEntry> t = (ArrayList<TimeEntry>) getAllFromDate(te, date,newTime);
		System.out.println(te.toString() + " "+date.toString()+" - " + newTime.toString());
		//System.out.println("result size is " + t.size());
		return t;
	}
	
	
	
	private ArrayList<TimeEntry> getAllFromDate(TimeEntry te, Timestamp date, Timestamp newTime) {
		TimeEntry entry;
		//Transaction.Created trans = TransactionFactory.create(transManager, Lease.FOREVER);
		//leaseRenewalManager.renewUntil(trans.lease, Lease.FOREVER, null);
		//Transaction txn = trans.transaction;
		try {
			ArrayList<TimeEntry> result = new ArrayList<TimeEntry>();
			while ((entry = (TimeEntry) space.take(te)) != null){
				//System.out.println(entry.toString());
				result.add(entry);
			}
			ArrayList<TimeEntry> e = getFromDate(result,date,newTime);
			//System.out.println(result.toString());
			//txn.abort();
			//leaseRenewalManager.cancel(trans.lease);
			return e;
		} catch (UnusableEntryException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<TimeEntry> getFromDate(ArrayList<TimeEntry> result,
			Timestamp date, Timestamp newTime) {
		ArrayList<TimeEntry> results = new ArrayList<TimeEntry>();
		if (result.size() > 0) {
			for (TimeEntry te : result) {
				if (te.getTime().after(date) && te.getTime().before(newTime))
					results.add(te);
			}
			return results;
		}

		return null;
	}

	void write(TimeEntry e)
	{
		System.out.println(e.toString());
		try {
			if (e.getTime() == null)
				e.setTime();
			if (e.getClock() == null)
				e.setClock(clock);
			space.write(e);

		} catch (TransactionException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	void writeTime(int clock) {
		this.clock = clock;
		Time time = new Time(clock);
		write(time);	
	}

	public void register() {
		try {
			NotificationHandler handler = new NotificationHandler(synchronization);
			for (int i=0; i<agents.length;i++) {
				//session.addListener(new Position(agents[i]), handler,Lease.FOREVER,new MarshalledObject(new String[]{"position",agents[i]}),null, NotifyActionType.NOTIFY_WRITE); 
				session.addListener(new Position(agents[i]), handler); 
				session.addListener(new ActionRequest(agents[i], "reading"), handler); 
				session.addListener(new ActionRequest(agents[i], "investigation"), handler); 
				session.addListener(new Coin(agents[i]), handler); 
				session.addListener(new Points(agents[i]), handler); 
			
			}
			session.addListener(new Cargo(), handler);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (net.jini.core.transaction.TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



/*			theManager.renewFor(myReg.getLease(), Lease.FOREVER,
                30000, new DebugListener());*/
	
	}
	}

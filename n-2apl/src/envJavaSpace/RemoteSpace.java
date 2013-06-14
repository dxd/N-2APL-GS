package envJavaSpace;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import com.j_spaces.core.IJSpace;
 
public class RemoteSpace {
 
    static String url = "/./mySpace";
      
    public static void main(String[] args) throws InterruptedException {
 
    	@SuppressWarnings("unused")
		GigaSpace space=DataGridConnectionUtility.getSpace("myGrid");
    	
        System.out.println("Remote Space : 'mySpace' is running ");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
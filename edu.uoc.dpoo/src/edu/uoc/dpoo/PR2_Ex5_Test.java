package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR2_Ex5_Test {
        
    private final String host1_domain = "h1.dpoo.uoc.edu";
    private final int host1_mem = 1024;
    private final int host1_disk = 256000;  
    
    private final String service1_name = "service1";
    private final int service1_minMem = 500;
    private final int service1_maxMem = 2000;
    private final int service1_minDisk = 1000;
    private final int service1_maxDisk = 5000;
            
    private Manager manager;
            
    public PR2_Ex5_Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {        
        manager = new Manager();              
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testEventGeneration() {  
        class Observer implements EventListener {
            
            public int numMsg_NewHost=0;
            public int numMsg_NewMessage=0;
            public int numMsg_ResourceAlert=0;
            public int numMsg_ServiceStarted=0;

            @Override
            public void onNewHost(Host host) {
                numMsg_NewHost++;
            }

            @Override
            public void onServiceStarted(Service service) {
                numMsg_ServiceStarted++;
            }

            @Override
            public void onNewMessage(Message message) {
                numMsg_NewMessage++;
            }

            @Override
            public void onResourcesAlert() {
                numMsg_ResourceAlert++;
            }
            
        }
        Worker worker = new Worker(manager);
        Observer obs = new Observer();
        manager.addListener(obs);
        
        assertEquals(0, obs.numMsg_NewHost);
        assertEquals(0, obs.numMsg_NewMessage);
        assertEquals(0, obs.numMsg_ResourceAlert);
        assertEquals(0, obs.numMsg_ServiceStarted);
        
        try {
            // Add host
            manager.addHost(host1_domain, host1_mem, host1_disk);         
        } catch (Throwable t) {            
            fail();            
        }
        Host host = manager.getHost(host1_domain);
        
        assertEquals(1, obs.numMsg_NewHost);
        assertEquals(0, obs.numMsg_NewMessage);
        assertEquals(0, obs.numMsg_ResourceAlert);
        assertEquals(0, obs.numMsg_ServiceStarted);
        
        manager.sendMessage("TEST", "Force to create message");

        assertEquals(1, obs.numMsg_NewHost);
        assertEquals(1, obs.numMsg_NewMessage);
        assertEquals(0, obs.numMsg_ResourceAlert);
        assertEquals(0, obs.numMsg_ServiceStarted);
        
        try {
            // Add Service
            manager.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);                            
        } catch (Throwable t) {            
            fail();            
        }
        
        Service srv = manager.getService(service1_name);
        assertNotNull(srv);
        
        try {
            // Start the service
            manager.startService(srv);
            worker.run();
        } catch (Throwable t) {            
            fail();            
        }        
        assertEquals(1, srv.getInstances());
        
        try {
            // Force resources alert
        	System.out.println("Final");
            manager.storeStats(host, srv, host1_mem-1, service1_minDisk);
            
            worker.run();
        } catch (Throwable t) {            
            fail();            
        }    
        
        assertEquals(1, obs.numMsg_NewHost);
        assertEquals(2, obs.numMsg_NewMessage);
        assertEquals(1, obs.numMsg_ResourceAlert);
        assertEquals(0, obs.numMsg_ServiceStarted);
        
    } 
    
    @Test
    public void testWorkerAdaption() {  
        Worker worker = new Worker(manager);
        assertTrue(worker instanceof EventListener);        
    } 
}

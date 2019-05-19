package edu.uoc.dpoo;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR2_Ex2_Test {
        
    private final String host1_domain = "h1.dpoo.uoc.edu";
    private final int host1_mem = 8000;
    private final int host1_disk = 256000;    
    
    private final String service1_name = "service1";
    private final int service1_minMem = 500;
    private final int service1_maxMem = 1000;
    private final int service1_minDisk = 1000;
    private final int service1_maxDisk = 5000;
    
    private final String service2_name = "service2";
    private final int service2_minMem = 1000;
    private final int service2_maxMem = 2000;
    private final int service2_minDisk = 3000;
    private final int service2_maxDisk = 6000;
            
    public PR2_Ex2_Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void startService() {
        Manager m = new Manager();
        
        // Check that initially the list of hosts is empty
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
        
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Check that initially the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
                
        try {
            // Add a new host
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
        
        // Start the new service
        Service s = m.getService(service1_name);
        assertNotNull(s);
        Date dInit = new Date();
        try {            
            m.startService(s);            
        } catch (Throwable t) {                        
            fail();            
        }
        Date dEnd = new Date();
        
        // Check that the task is created
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 1);
        assertEquals(m.getTasks().get(0).getType(), TaskType.DEPLOY);
        assertEquals(m.getTasks().get(0).getStatus(), TaskStatus.PENDING);
        assertNotNull(m.getTasks().get(0).getCreatedAt());
        assertTrue(m.getTasks().get(0).getCreatedAt().compareTo(dInit)>=0);
        assertTrue(m.getTasks().get(0).getCreatedAt().compareTo(dEnd)<=0);
        assertNotNull(m.getTasks().get(0).getUpdatedAt());
        assertTrue(m.getTasks().get(0).getUpdatedAt().compareTo(dInit)>=0);
        assertTrue(m.getTasks().get(0).getUpdatedAt().compareTo(dEnd)<=0);
        assertEquals(m.getTasks().get(0).getArguments(), "serviceName=" + s.getName());        
    }    
    
    @Test
    public void startInvalidService() {
        Manager m = new Manager();
        
        // Check that initially the list of hosts is empty
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
        
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Check that initially the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
                
        try {
            // Add a new host
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
        
        // Get the service
        Service s = m.getService(service1_name);
        assertNotNull(s);
        
        // Remove the service
        try {
            m.removeService(s);
        } catch (Throwable t) {                        
            fail();            
        }
        
        // Start the new service
        try {
            m.startService(s);
            fail();
        } catch (Throwable t) {                        
            if(!(t instanceof TaskException)) {
                fail();
            }                         
        }
        
        // Check that the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
    }    
    
    @Test
    public void startServiceWithoutHosts() {
        Manager m = new Manager();
        
        // Check that initially the list of hosts is empty
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
        
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);       
        
        // Check that initially the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
        
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
        
        // Get the service
        Service s = m.getService(service1_name);
        assertNotNull(s);        
        
        // Start the new service
        try {
            m.startService(s);
            fail();
        } catch (Throwable t) {                        
            if(!(t instanceof TaskException)) {
                fail();
            }                         
        }
        
        // Check that the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
    }

    @Test
    public void stopService() {
        Manager m = new Manager();
                
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Check that initially the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
        
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
        
        // Stop the service
        Service s = m.getService(service1_name);
        assertNotNull(s);
        Date dInit = new Date();
        try {            
            m.stopService(s);            
        } catch (Throwable t) {                        
            fail();            
        }
        Date dEnd = new Date();
        
        // Check that the task is created
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 1);
        assertEquals(m.getTasks().get(0).getType(), TaskType.UNDEPLOY);
        assertEquals(m.getTasks().get(0).getStatus(), TaskStatus.PENDING);
        assertNotNull(m.getTasks().get(0).getCreatedAt());
        assertTrue(m.getTasks().get(0).getCreatedAt().compareTo(dInit)>=0);
        assertTrue(m.getTasks().get(0).getCreatedAt().compareTo(dEnd)<=0);
        assertNotNull(m.getTasks().get(0).getUpdatedAt());
        assertTrue(m.getTasks().get(0).getUpdatedAt().compareTo(dInit)>=0);
        assertTrue(m.getTasks().get(0).getUpdatedAt().compareTo(dEnd)<=0);
        assertEquals(m.getTasks().get(0).getArguments(), "serviceName=" + s.getName());        
    }   
    
    @Test
    public void stopInvalidService() {
        Manager m = new Manager();
        
        // Check that initially the list of hosts is empty
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
        
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Check that initially the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
                
        try {
            // Add a new host
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
        
        // Get the service
        Service s = m.getService(service1_name);
        assertNotNull(s);
        
        // Remove the service
        try {
            m.removeService(s);
        } catch (Throwable t) {                        
            fail();            
        }
        
        // Stop the service
        try {
            m.stopService(s);
            fail();
        } catch (Throwable t) {                        
            if(!(t instanceof TaskException)) {
                fail();
            }                         
        }
        
        // Check that the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
    }    
    
    @Test
    public void scaleService() {
        Manager m = new Manager();
                
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Check that initially the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
        
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
        
        // Scale the service
        Service s = m.getService(service1_name);
        assertNotNull(s);
        Date dInit = new Date();
        try {            
            m.scaleService(s,2);            
        } catch (Throwable t) {                        
            fail();            
        }
        Date dEnd = new Date();
        
        // Check that the task is created
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 1);
        assertEquals(m.getTasks().get(0).getType(), TaskType.SCALE);
        assertEquals(m.getTasks().get(0).getStatus(), TaskStatus.PENDING);
        assertNotNull(m.getTasks().get(0).getCreatedAt());
        assertTrue(m.getTasks().get(0).getCreatedAt().compareTo(dInit)>=0);
        assertTrue(m.getTasks().get(0).getCreatedAt().compareTo(dEnd)<=0);
        assertNotNull(m.getTasks().get(0).getUpdatedAt());
        assertTrue(m.getTasks().get(0).getUpdatedAt().compareTo(dInit)>=0);
        assertTrue(m.getTasks().get(0).getUpdatedAt().compareTo(dEnd)<=0);
        assertEquals("serviceName=" + s.getName() + ";instances=2", m.getTasks().get(0).getArguments());        
    }   
    
    @Test
    public void scaleInvalidService() {
        Manager m = new Manager();
        
        // Check that initially the list of hosts is empty
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
        
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Check that initially the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
                
        try {
            // Add a new host
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
        
        // Get the service
        Service s = m.getService(service1_name);
        assertNotNull(s);
        
        // Remove the service
        try {
            m.removeService(s);
        } catch (Throwable t) {                        
            fail();            
        }
        
        // Scale the service
        try {
            m.scaleService(s, 1);
            fail();
        } catch (Throwable t) {                        
            if(!(t instanceof TaskException)) {
                fail();
            }                         
        }
        
        // Check that the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
    }    
    
    @Test
    public void scaleInvalidInstances() {
        Manager m = new Manager();
        
        // Check that initially the list of hosts is empty
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
        
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Check that initially the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
                
        try {
            // Add a new host
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
        
        // Get the service
        Service s = m.getService(service1_name);
        assertNotNull(s);
        
        // Remove the service
        try {
            m.removeService(s);
        } catch (Throwable t) {                        
            fail();            
        }
        
        // Scale the service
        try {
            m.scaleService(s, 0);
            fail();
        } catch (Throwable t) {                        
            if(!(t instanceof TaskException)) {
                fail();
            }                         
        }
        
        // Check that the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
    }  
    
    @Test
    public void getTask() {
        Manager m = new Manager();
        
        // Check that initially the list of hosts is empty
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
        
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Check that initially the list of tasks is empty
        assertNotNull(m.getTasks());
        assertEquals((int)m.getTasks().size(), 0);
                
        try {
            // Add a new host
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Add two new services
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);
            m.addService(service2_name, service2_minMem, service2_maxMem, service2_minDisk, service2_maxDisk);
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 2);
        
        // Check that if there is no task it returns null
        assertNull(m.getTask());
        
        // Start the new services
        Service s1 = m.getService(service1_name);
        Service s2 = m.getService(service2_name);
        assertNotNull(s1);
        assertNotNull(s2);
        try {            
            m.startService(s1);
            m.startService(s2);
        } catch (Throwable t) {                        
            fail();            
        }
        assertEquals((int)m.getTasks().size(), 2);
             
        // Wait 1 second in order to ensure date management
        try {            
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PR2_Ex2_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Check that first it returns the first created task
        Date dInit = new Date();
        Task task = m.getTask();
        Date dEnd = new Date();
        assertEquals(m.getTasks().get(0), task); 
        assertEquals(task.getStatus(), TaskStatus.RUNNING);
        assertNotNull(task.getUpdatedAt());
        assertTrue(task.getUpdatedAt().compareTo(dInit)>=0);
        assertTrue(task.getUpdatedAt().compareTo(dEnd)<=0);
        
        // Check that the first is skiped and returns the second created task
        dInit = new Date();
        task = m.getTask();
        dEnd = new Date();
        assertEquals(m.getTasks().get(1), task); 
        assertEquals(task.getStatus(), TaskStatus.RUNNING);
        assertNotNull(task.getUpdatedAt());
        assertTrue(task.getUpdatedAt().compareTo(dInit)>=0);
        assertTrue(task.getUpdatedAt().compareTo(dEnd)<=0);
        
        // Check that previous tasks are skiped, and no task is pending
        task = m.getTask();
        assertNull(m.getTask());         
    }
}

package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR2_Ex1_Test {
    
    private final String host1_domain = "h1.dpoo.uoc.edu";
    private final int host1_mem = 8000;
    private final int host1_disk = 256000;    
    
    private final String service1_name = "service1";
    private final int service1_minMem = 500;
    private final int service1_maxMem = 1000;
    private final int service1_minDisk = 1000;
    private final int service1_maxDisk = 5000;
            
    public PR2_Ex1_Test() {
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
    public void createDuplicatedHost() {
        Manager m = new Manager();
        
        // Check that initially the list of hosts is empty
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
                
        try {
            // Add a new host
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        Host newHost = m.getHosts().get(0);                
        assertEquals(newHost.getDomain(), host1_domain);
        assertEquals(newHost.getDisk(), host1_disk);
        assertEquals(newHost.getMemory(), host1_mem); 
        assertNotNull(newHost.getServices());
        assertEquals((int)newHost.getServices().size(), 0);
        
        try {
            // Try to add a host with the same domain, but with different values
            m.addHost(host1_domain, host1_mem * 3, host1_disk * 2);
            fail();
        } catch (Throwable t) {
            if(!(t instanceof TaskException)) {
                fail();
            }            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Check that the data corresponds to the correct host
        newHost = m.getHosts().get(0);
        assertEquals(newHost.getDomain(), host1_domain);
        assertEquals(newHost.getDisk(), host1_disk);
        assertEquals(newHost.getMemory(), host1_mem); 
        assertNotNull(newHost.getServices());
        assertEquals((int)newHost.getServices().size(), 0);
    }    
    
    @Test
    public void removeNotExistingHost() {
        Manager m = new Manager();
                        
        try {
            // Add a new host
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
                
        // Remove the host
        Host newHost = m.getHosts().get(0);
        try {
            m.removeHost(newHost);
        } catch (Throwable t) {
            fail();
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
        
        // Remove it again (nothing should happen)
        try {
            m.removeHost(newHost);
            fail();
        } catch (Throwable t) {
            if(!(t instanceof TaskException)) {
                fail();
            }            
        } 
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0); 
    }
    
    @Test
    public void createDuplicatedService() {
        
        Manager m = new Manager();
        
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
                
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
        
        
        // Try to add a service with the same name
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);
            fail();
        } catch (Throwable t) {            
            if(!(t instanceof TaskException)) {
                fail();
            }           
        }         
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
                
    }    
    
    @Test
    public void createInvalidService() {
        
        Manager m = new Manager();
        
        // Check that initially the list of services is empty
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
                
        // Add a new service with invalid memory parameters
        try {
            m.addService(service1_name, service1_maxMem, service1_minMem, service1_minDisk, service1_maxDisk);            
            fail();
        } catch (Throwable t) {            
            if(!(t instanceof TaskException)) {
                fail();
            }           
        }  
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Add a new service with invalid disk parameters
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_maxDisk, service1_minDisk);            
            fail();
        } catch (Throwable t) {            
            if(!(t instanceof TaskException)) {
                fail();
            }           
        }  
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
    }    
    
    @Test
    public void removeNotExistingService() {
        
        Manager m = new Manager();
                        
        // Add a new service
        try {
            m.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
        } catch (Throwable t) {                        
            fail();            
        }
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 1);
                
        // Remove the service
        Service newService = m.getServices().get(0); 
        try {
            m.removeService(newService);
        } catch (Throwable t) {                        
            fail();            
        }        
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
        // Remove it again (nothing should happen)
        try {
            m.removeService(newService);
            fail();
        } catch (Throwable t) {            
            if(!(t instanceof TaskException)) {
                fail();
            }           
        }           
        assertNotNull(m.getServices());
        assertEquals((int)m.getServices().size(), 0);
        
    }        
}

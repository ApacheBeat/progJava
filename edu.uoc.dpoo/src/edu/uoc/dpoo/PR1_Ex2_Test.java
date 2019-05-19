package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR1_Ex2_Test {
    
    private final String host1_domain = "h1.dpoo.uoc.edu";
    private final int host1_mem = 8000;
    private final int host1_disk = 256000;
    private final String host2_domain = "h2.dpoo.uoc.edu";
    private final int host2_mem = 4000;
    private final int host2_disk = 512000;
        
    public PR1_Ex2_Test() {
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
    public void createHost() throws TaskException {
        
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
    public void removeHost() throws TaskException {
        
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
        } catch (Throwable t) {
            if(!(t instanceof TaskException)) {
                fail();
            }            
        } 
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);        
    }    
    
    @Test
    public void getHost() {
        
        Manager m = new Manager();
        
        // Try to find an unexisting host
        assertNull(m.getHost(host1_domain));
                        
        // Add a new host
        try {
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Try to find an unexisting host        
        assertNull(m.getHost("unexisting.host"));
                
        // Get the host
        Host newHost = m.getHosts().get(0);
        assertEquals(newHost, m.getHost(host1_domain));        
    }    
    
    @Test
    public void multipleHosts() {
        
        Manager m = new Manager();
        
        // Check that initially the list of hosts is empty
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 0);
                
        // Add a new host
        try {
            m.addHost(host1_domain, host1_mem, host1_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Add another host
        try {
            m.addHost(host2_domain, host2_mem, host2_disk);
        } catch (Throwable t) {            
            fail();            
        }        
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 2);
        
        // Remove the first host
        Host h1 = m.getHost(host1_domain);
        try {
            m.removeHost(h1);
        } catch (Throwable t) {            
            fail();            
        } 
        assertNotNull(m.getHosts());
        assertEquals((int)m.getHosts().size(), 1);
        
        // Check that the second host is still in the list
        Host h2 = m.getHosts().get(0);                
        assertEquals(h2.getDomain(), host2_domain);
        assertEquals(h2.getDisk(), host2_disk);
        assertEquals(h2.getMemory(), host2_mem); 
        assertNotNull(h2.getServices());
        assertEquals((int)h2.getServices().size(), 0);
    }    
}

package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR2_Ex4_Test {
        
    private final String host1_domain = "h1.dpoo.uoc.edu";
    private final int host1_mem = 1024;
    private final int host1_disk = 20000;  
    
    private final String host2_domain = "h2.dpoo.uoc.edu";
    private final int host2_mem = 4096;
    private final int host2_disk = 6000;  
    
    private final String service1_name = "service1";
    private final int service1_minMem = 500;
    private final int service1_maxMem = 2000;
    private final int service1_minDisk = 1000;
    private final int service1_maxDisk = 25000;
    
    private final String service2_name = "service2";
    private final int service2_minMem = 1000;
    private final int service2_maxMem = 8000;
    private final int service2_minDisk = 3000;
    private final int service2_maxDisk = 10000;
            
    private Manager manager;
    private Worker worker;
    private Host host1;
    private Host host2;
    private Service service1;
    private Service service2;    
            
    public PR2_Ex4_Test() {
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
        worker = new Worker(manager);
        try {
            // Add hosts
            manager.addHost(host1_domain, host1_mem, host1_disk);
            manager.addHost(host2_domain, host2_mem, host2_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(manager.getHosts());
        assertEquals((int)manager.getHosts().size(), 2);
        
        try {
            // Add services
            manager.addService(service1_name, service1_minMem, service1_maxMem, service1_minDisk, service1_maxDisk);            
            manager.addService(service2_name, service2_minMem, service2_maxMem, service2_minDisk, service2_maxDisk);                        
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(manager.getServices());
        assertEquals((int)manager.getServices().size(), 2);
        
        host1 = manager.getHost(host1_domain);
        host2 = manager.getHost(host2_domain);        
        assertNotNull(host1);
        assertNotNull(host2);
                
        service1 = manager.getService(service1_name);
        service2 = manager.getService(service2_name);        
        assertNotNull(service1);
        assertNotNull(service2);        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void updateServiceStatistics() throws TaskException {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
                
        // Start new services
        try {            
            manager.startService(service1);            
            manager.startService(service2);
            manager.scaleService(service1, 2);            
        } catch (Throwable t) {                        
            fail();            
        }
        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());        
        
        assertEquals(0, host1.getUsedMemory());
        assertEquals(host1_mem, host1.getFreeMemory());
        assertEquals(0, host1.getUsedDisk());
        assertEquals(host1_disk, host1.getFreeDisk());
        
        assertEquals(0, host2.getUsedMemory());
        assertEquals(host2_mem, host2.getFreeMemory());
        assertEquals(0, host2.getUsedDisk());
        assertEquals(host2_disk, host2.getFreeDisk());
        
        worker.run();
        worker.run();
        worker.run();
        
        assertEquals(2, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(2, manager.getHostServices(host2).size());    
        
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service1_minMem + service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-(service1_minMem + service2_minMem), host2.getFreeMemory());
        assertEquals(service1_minDisk+service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-(service1_minDisk+service2_minDisk), host2.getFreeDisk());

        assertEquals(0, manager.getMessages().size());
        try {
            manager.storeStats(host1, service1, service1_minMem+100, service1_minDisk+100);
        } catch (Throwable t) {                        
            fail();            
        }
            
        assertEquals(0, manager.getMessages().size());
        
        assertEquals(service1_minMem+100, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem-100, host1.getFreeMemory());
        assertEquals(service1_minDisk+100, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk-100, host1.getFreeDisk());
        
        assertEquals(service1_minMem + service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-(service1_minMem + service2_minMem), host2.getFreeMemory());
        assertEquals(service1_minDisk+service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-(service1_minDisk+service2_minDisk), host2.getFreeDisk());
    }               
    
    @Test
    public void forceMemAlert() throws TaskException {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
                
        // Start new services
        try {            
            manager.startService(service1);            
            manager.startService(service2);
            manager.scaleService(service1, 2);            
        } catch (Throwable t) {                        
            fail();            
        }
        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());        
        
        assertEquals(0, host1.getUsedMemory());
        assertEquals(host1_mem, host1.getFreeMemory());
        assertEquals(0, host1.getUsedDisk());
        assertEquals(host1_disk, host1.getFreeDisk());
        
        assertEquals(0, host2.getUsedMemory());
        assertEquals(host2_mem, host2.getFreeMemory());
        assertEquals(0, host2.getUsedDisk());
        assertEquals(host2_disk, host2.getFreeDisk());
        
        worker.run();
        worker.run();
        worker.run();
        
        assertEquals(2, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(2, manager.getHostServices(host2).size());    
        
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service1_minMem + service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-(service1_minMem + service2_minMem), host2.getFreeMemory());
        assertEquals(service1_minDisk+service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-(service1_minDisk+service2_minDisk), host2.getFreeDisk());   
        
        assertEquals(0, manager.getMessages().size());
        try {
            manager.storeStats(host1, service1, 1020, service1_minDisk);
        } catch (Throwable t) {                        
            fail();            
        }
        assertEquals(1, manager.getMessages().size());
        assertEquals("MEM ALERT", manager.getMessage().getSubject());
        
        assertEquals(1020, host1.getUsedMemory());
        assertEquals(host1_mem-1020, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service1_minMem + service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-(service1_minMem + service2_minMem), host2.getFreeMemory());
        assertEquals(service1_minDisk+service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-(service1_minDisk+service2_minDisk), host2.getFreeDisk());
    }   
    
    @Test
    public void forceDiskAlert() throws TaskException {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
                
        // Start new services
        try {            
            manager.startService(service1);            
            manager.startService(service2);
            manager.scaleService(service1, 2);            
        } catch (Throwable t) {                        
            fail();            
        }
        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());        
        
        assertEquals(0, host1.getUsedMemory());
        assertEquals(host1_mem, host1.getFreeMemory());
        assertEquals(0, host1.getUsedDisk());
        assertEquals(host1_disk, host1.getFreeDisk());
        
        assertEquals(0, host2.getUsedMemory());
        assertEquals(host2_mem, host2.getFreeMemory());
        assertEquals(0, host2.getUsedDisk());
        assertEquals(host2_disk, host2.getFreeDisk());
        
        worker.run();
        worker.run();
        worker.run();
        
        assertEquals(2, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(2, manager.getHostServices(host2).size());    
        
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service1_minMem + service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-(service1_minMem + service2_minMem), host2.getFreeMemory());
        assertEquals(service1_minDisk+service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-(service1_minDisk+service2_minDisk), host2.getFreeDisk());   
        
        assertEquals(0, manager.getMessages().size());
        try {
            manager.storeStats(host2, service1, service1_minMem, 2990);
        } catch (Throwable t) {                        
            fail();            
        }
        assertEquals(1, manager.getMessages().size());
        assertEquals("DISK ALERT", manager.getMessage().getSubject());
        
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service1_minMem + service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-(service1_minMem + service2_minMem), host2.getFreeMemory());
        assertEquals(2990+service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-(2990+service2_minDisk), host2.getFreeDisk());
        
    }
    
    @Test
    public void invalidStatisticsUpdate() {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
                
        // Start new services
        try {            
            manager.storeStats(host1, service1, service1_minMem, service1_minDisk);                   
        } catch (Throwable t) {                        
            if(!(t instanceof TaskException)) {
                fail();
            }            
        }        
    }
    
}

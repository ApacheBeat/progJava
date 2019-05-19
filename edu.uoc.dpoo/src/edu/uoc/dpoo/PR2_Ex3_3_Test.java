package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR2_Ex3_3_Test {
        
    private final String host1_domain = "h1.dpoo.uoc.edu";
    private final int host1_mem = 1024;
    private final int host1_disk = 256000;  
    
    private final String host2_domain = "h2.dpoo.uoc.edu";
    private final int host2_mem = 4096;
    private final int host2_disk = 256000;  
    
    private final String host3_domain = "h3.dpoo.uoc.edu";
    private final int host3_mem = 4096;
    private final int host3_disk = 256000;  
    
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
            
    private Manager manager;
    private Worker worker;
    private Host host1;
    private Host host2;
    private Host host3;
    private Service service1;
    private Service service2;    
            
    public PR2_Ex3_3_Test() {
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
            manager.addHost(host3_domain, host3_mem, host3_disk);
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(manager.getHosts());
        assertEquals((int)manager.getHosts().size(), 3);
        
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
        host3 = manager.getHost(host3_domain);
        assertNotNull(host1);
        assertNotNull(host2);
        assertNotNull(host3);
        
        service1 = manager.getService(service1_name);
        service2 = manager.getService(service2_name);        
        assertNotNull(service1);
        assertNotNull(service2);        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void taskScaleEqualService() {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        // Start new services
        try {            
            manager.startService(service1);            
            manager.startService(service2);            
        } catch (Throwable t) {                        
            fail();            
        }
        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        assertEquals(0, host1.getUsedMemory());
        assertEquals(host1_mem, host1.getFreeMemory());
        assertEquals(0, host1.getUsedDisk());
        assertEquals(host1_disk, host1.getFreeDisk());
        
        assertEquals(0, host2.getUsedMemory());
        assertEquals(host2_mem, host2.getFreeMemory());
        assertEquals(0, host2.getUsedDisk());
        assertEquals(host2_disk, host2.getFreeDisk());
        
        assertEquals(0, host3.getUsedMemory());
        assertEquals(host3_mem, host3.getFreeMemory());
        assertEquals(0, host3.getUsedDisk());
        assertEquals(host3_disk, host3.getFreeDisk());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(1, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(1, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());

        // Scale up the service
        try {            
            manager.scaleService(service1, 1);
        } catch (Throwable t) {                        
            fail();            
        }
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(1, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(2).getStatus());
    }               
    
    @Test
    public void taskScaleUpService() {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        // Start new services
        try {            
            manager.startService(service1);            
            manager.startService(service2);            
        } catch (Throwable t) {                        
            fail();            
        }
        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        assertEquals(0, host1.getUsedMemory());
        assertEquals(host1_mem, host1.getFreeMemory());
        assertEquals(0, host1.getUsedDisk());
        assertEquals(host1_disk, host1.getFreeDisk());
        
        assertEquals(0, host2.getUsedMemory());
        assertEquals(host2_mem, host2.getFreeMemory());
        assertEquals(0, host2.getUsedDisk());
        assertEquals(host2_disk, host2.getFreeDisk());
        
        assertEquals(0, host3.getUsedMemory());
        assertEquals(host3_mem, host3.getFreeMemory());
        assertEquals(0, host3.getUsedDisk());
        assertEquals(host3_disk, host3.getFreeDisk());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(1, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(1, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());

        // Scale up the service
        try {            
            manager.scaleService(service1, 3);
        } catch (Throwable t) {                        
            fail();            
        }
        
        worker.run();
        
        assertEquals(3, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(2, manager.getHostServices(host2).size());
        assertEquals(1, manager.getHostServices(host3).size());
        
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(2).getStatus());
    }               
    
    @Test
    public void taskScaleDownService() {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        // Start new services
        try {            
            manager.startService(service1);            
            manager.startService(service2);            
        } catch (Throwable t) {                        
            fail();            
        }
        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        assertEquals(0, host1.getUsedMemory());
        assertEquals(host1_mem, host1.getFreeMemory());
        assertEquals(0, host1.getUsedDisk());
        assertEquals(host1_disk, host1.getFreeDisk());
        
        assertEquals(0, host2.getUsedMemory());
        assertEquals(host2_mem, host2.getFreeMemory());
        assertEquals(0, host2.getUsedDisk());
        assertEquals(host2_disk, host2.getFreeDisk());
        
        assertEquals(0, host3.getUsedMemory());
        assertEquals(host3_mem, host3.getFreeMemory());
        assertEquals(0, host3.getUsedDisk());
        assertEquals(host3_disk, host3.getFreeDisk());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(1, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(1, manager.getHostServices(host2).size());
        assertEquals(0, manager.getHostServices(host3).size());

        // Scale up the service
        try {            
            manager.scaleService(service1, 3);
        } catch (Throwable t) {                        
            fail();            
        }
        
        worker.run();
        
        assertEquals(3, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(2, manager.getHostServices(host2).size());
        assertEquals(1, manager.getHostServices(host3).size());
        
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(2).getStatus());
        
        // Scale down the service
        try {            
            manager.scaleService(service1, 1);
        } catch (Throwable t) {                        
            fail();            
        }
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());        
        
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(2).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(3).getStatus());        
    }   
}

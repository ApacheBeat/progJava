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

public class PR2_Ex3_1_Test {
        
    private final String host1_domain = "h1.dpoo.uoc.edu";
    private final int host1_mem = 1024;
    private final int host1_disk = 256000;  
    
    private final String host2_domain = "h2.dpoo.uoc.edu";
    private final int host2_mem = 4096;
    private final int host2_disk = 256000;  
    
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
    
    private final String service3_name = "service3";
    private final int service3_minMem = 4000;
    private final int service3_maxMem = 5000;
    private final int service3_minDisk = 3000;
    private final int service3_maxDisk = 6000;
    
    private final String service4_name = "service4";
    private final int service4_minMem = 500;
    private final int service4_maxMem = 5000;
    private final int service4_minDisk = 255500;
    private final int service4_maxDisk = 600000;
        
    private Manager manager;
    private Worker worker;
    private Host host1;
    private Host host2;
    private Service service1;
    private Service service2;
    private Service service3;
    private Service service4;
            
    public PR2_Ex3_1_Test() {
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
            manager.addService(service3_name, service3_minMem, service3_maxMem, service3_minDisk, service3_maxDisk);            
            manager.addService(service4_name, service4_minMem, service4_maxMem, service4_minDisk, service4_maxDisk);            
        } catch (Throwable t) {            
            fail();            
        }
        assertNotNull(manager.getServices());
        assertEquals((int)manager.getServices().size(), 4);
        
        host1 = manager.getHost(host1_domain);
        host2 = manager.getHost(host2_domain);
        assertNotNull(host1);
        assertNotNull(host2);
        
        service1 = manager.getService(service1_name);
        service2 = manager.getService(service2_name);
        service3 = manager.getService(service3_name);
        service4 = manager.getService(service4_name);
        assertNotNull(service1);
        assertNotNull(service2);
        assertNotNull(service3);
        assertNotNull(service4);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void taskDeployService() {    
    	System.out.println(service1.getInstances());
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        
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
        
        assertEquals(0, host1.getUsedMemory());
        assertEquals(host1_mem, host1.getFreeMemory());
        assertEquals(0, host1.getUsedDisk());
        assertEquals(host1_disk, host1.getFreeDisk());
        
        assertEquals(0, host2.getUsedMemory());
        assertEquals(host2_mem, host2.getFreeMemory());
        assertEquals(0, host2.getUsedDisk());
        assertEquals(host2_disk, host2.getFreeDisk());
        
        worker.run();
        assertEquals(1, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(1, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(1, manager.getHostServices(host2).size());

        assertNull(manager.getTask());
        
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-service2_minMem, host2.getFreeMemory());
        assertEquals(service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-service2_minDisk, host2.getFreeDisk());
        
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
    }         
    
    @Test
    public void taskDeployTooBigService() {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        
        // Start new services
        try {            
            manager.startService(service1);            
            manager.startService(service2);            
            manager.startService(service3);            
            manager.startService(service4);            
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
        
        assertEquals(1, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(1, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(1, manager.getHostServices(host2).size());
               
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-service2_minMem, host2.getFreeMemory());
        assertEquals(service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-service2_minDisk, host2.getFreeDisk());
               
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.PENDING, manager.getTasks().get(2).getStatus());
        assertEquals(TaskStatus.PENDING, manager.getTasks().get(3).getStatus());
        
        worker.run();
        
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.FAILED, manager.getTasks().get(2).getStatus());
        assertEquals(TaskStatus.PENDING, manager.getTasks().get(3).getStatus());
        
        worker.run();
        
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.FAILED, manager.getTasks().get(2).getStatus());
        assertEquals(TaskStatus.FAILED, manager.getTasks().get(3).getStatus());
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(1, manager.getHostServices(host2).size());
               
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-service2_minMem, host2.getFreeMemory());
        assertEquals(service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-service2_minDisk, host2.getFreeDisk());
    }      
    
    @Test
    public void taskDeployMessageService() {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        
        // Start new services
        try {            
            manager.startService(service1);            
            manager.startService(service2);            
            manager.startService(service3);            
            manager.startService(service4);            
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
        
        assertEquals(1, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(1, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(1, manager.getHostServices(host2).size());
               
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-service2_minMem, host2.getFreeMemory());
        assertEquals(service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-service2_minDisk, host2.getFreeDisk());
               
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.PENDING, manager.getTasks().get(2).getStatus());
        assertEquals(TaskStatus.PENDING, manager.getTasks().get(3).getStatus());
        
        assertEquals(0, manager.getMessages().size());
        
        worker.run();
        
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.FAILED, manager.getTasks().get(2).getStatus());
        assertEquals(TaskStatus.PENDING, manager.getTasks().get(3).getStatus());
        
        assertEquals(1, manager.getMessages().size());
        
        worker.run();
        
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.DONE, manager.getTasks().get(1).getStatus());
        assertEquals(TaskStatus.FAILED, manager.getTasks().get(2).getStatus());
        assertEquals(TaskStatus.FAILED, manager.getTasks().get(3).getStatus());
        
        assertEquals(1, service1.getInstances());
        assertEquals(1, service2.getInstances());
        assertEquals(2, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(1, manager.getHostServices(host2).size());
               
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(service2_minMem, host2.getUsedMemory());
        assertEquals(host2_mem-service2_minMem, host2.getFreeMemory());
        assertEquals(service2_minDisk, host2.getUsedDisk());
        assertEquals(host2_disk-service2_minDisk, host2.getFreeDisk());
        
        assertEquals(2, manager.getMessages().size());
    }     
    
    @Test
    public void taskReDeployService() {                        
        assertEquals(0, service1.getInstances());
        assertEquals(0, service2.getInstances());
        assertEquals(0, manager.getRunningServices().size());
        assertEquals(0, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        
        // Start new services
        try {            
            manager.startService(service1);            
            manager.startService(service1);                    
        } catch (Throwable t) {                        
            fail();            
        }
        
        assertEquals(0, service1.getInstances());        
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
        
        assertEquals(1, service1.getInstances());        
        assertEquals(1, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
        assertEquals(0, manager.getMessages().size());
        
        worker.run();
        
        assertEquals(1, service1.getInstances());        
        assertEquals(1, manager.getRunningServices().size());
        assertEquals(1, manager.getHostServices(host1).size());
        assertEquals(0, manager.getHostServices(host2).size());
               
        assertEquals(service1_minMem, host1.getUsedMemory());
        assertEquals(host1_mem-service1_minMem, host1.getFreeMemory());
        assertEquals(service1_minDisk, host1.getUsedDisk());
        assertEquals(host1_disk-service1_minDisk, host1.getFreeDisk());
        
        assertEquals(0, host2.getUsedMemory());
        assertEquals(host2_mem, host2.getFreeMemory());
        assertEquals(0, host2.getUsedDisk());
        assertEquals(host2_disk, host2.getFreeDisk());
               
        assertEquals(TaskStatus.DONE, manager.getTasks().get(0).getStatus());
        assertEquals(TaskStatus.FAILED, manager.getTasks().get(1).getStatus());
        assertEquals(1, manager.getMessages().size());
        
    }          
}

package com.code4reference.jmeter.threads;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.engine.TreeCloner;
import org.apache.jmeter.testelement.property.IntegerProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jmeter.threads.ListenerNotifier;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class HostThreadGroup extends AbstractThreadGroup {
	
	/**
	* 
	*/
	private static final long serialVersionUID = 2406L;
	
	private static final Logger log = LoggingManager.getLoggerForClass();
	
	private static final long WAIT_TO_DIE = JMeterUtils.getPropDefault("jmeterengine.threadstop.wait", 5 * 1000); // 5 seconds
	
	public static final String HOST_NAMES = "HostThreadGroup.hostNames";
	public static final String RAMP_TIME = "HostThreadGroup.ramp_time";
	public static final String DELAYED_START = "HostThreadGroup.delayedStart";
	private volatile boolean hostNamePresent = false;
	private String currentHostName;
	/**
    * Is test (still) running?
    */
    private volatile boolean running = false;
    
	// List of active threads
    private final Map<JMeterThread, Thread> allThreads = new ConcurrentHashMap<JMeterThread, Thread>();
  
    /**
     * No-arg constructor.
     */
    public HostThreadGroup() {
    	currentHostName = JMeterUtils.getLocalHostName();
    	log.debug(String.format("currentHostName : %s", currentHostName));
    }
    /**
     * set the HostName value.
     * 
     * @param hostName  set the hostname value.
     * 				
     */
    public void setHostNames(String hostNames) {
    	
    	if (hostNames == null || hostNames.trim().length() == 0 ) {
    		hostNamePresent = false;
    	} 
    	else {
    		hostNamePresent = true;
    	}
    	setProperty(new StringProperty(HOST_NAMES, hostNames.trim()));
    }
    /**
     * Get the Host names value.
     * 
     * @return returns the comma separated host name. 
     */
    public String getHostNames() {
    	return getPropertyAsString(HostThreadGroup.HOST_NAMES);
    }
    /**
     * Set the ramp-up value.
     *
     * @param rampUp
     *            the ramp-up value.
     */
    public void setRampUp(int rampUp) {
        setProperty(new IntegerProperty(RAMP_TIME, rampUp));
    }
    /**
     * Get the ramp-up value.
     *
     * @return the ramp-up value.
     */
    public int getRampUp() {
        return getPropertyAsInt(HostThreadGroup.RAMP_TIME);
    }
    
    private boolean isDelayedStartup() {
        return getPropertyAsBoolean(DELAYED_START);
    }
    
	@Override
	public void threadFinished(JMeterThread thread) {
		log.debug("Ending thread " + thread.getThreadName());
        allThreads.remove(thread);
	}

	@Override
	public boolean stopThread(String threadName, boolean now) {
		for(Entry<JMeterThread, Thread> entry : allThreads.entrySet()){
            JMeterThread thrd = entry.getKey();
            if (thrd.getThreadName().equals(threadName)){
                thrd.stop();
                thrd.interrupt();
                if (now) {
                    Thread t = entry.getValue();
                    if (t != null) {
                        t.interrupt();
                    }
                }
                return true;
            }
        }
        return false;
	}

	@Override
	public int numberOfActiveThreads() {
		return allThreads.size();
	}

	@Override
	public void start(int groupCount, 
			ListenerNotifier notifier,
			ListedHashTree threadGroupTree, 
			StandardJMeterEngine engine) {

		String[] hostList = getHostNames().split(",");
		boolean hostPresent = false;
		
		log.info(String.format("hostList.length : %d, hostNamePresent : %s", hostList.length, hostNamePresent));

		if (hostNamePresent == true) {  //If hosts' name are present then try to match.
			for(String host : hostList) {

				if(currentHostName.equals(host.trim())){
					log.debug(String.format("Found %s name in the given host list", host));
					hostPresent = true;
					break;
				}
			}
		}
		if (hostPresent || hostNamePresent == false) {
			//If there is no host name provided that means this thread-group should run on all machine.
			
			running = true;
			int numThreads = getNumThreads();       
			float perThreadDelay = 1.0f;

			long now = System.currentTimeMillis(); // needs to be same time for all threads in the group
			final JMeterContext context = JMeterContextService.getContext();
			for (int i = 0; running && i < numThreads; i++) {
				JMeterThread jmThread = makeThread(groupCount, notifier, threadGroupTree, engine, i, context);
				scheduleThread(jmThread, now); // set start and end time
				jmThread.setInitialDelay((int)(i * perThreadDelay));
				Thread newThread = new Thread(jmThread, jmThread.getThreadName());
				registerStartedThread(jmThread, newThread);
				newThread.start();
			}
		}
	}

	/**
     * This will schedule the time for the JMeterThread.
     *
     * @param thread JMeterThread
     */
    private void scheduleThread(JMeterThread thread, long now) {
    	thread.setStartTime(System.currentTimeMillis());
    	thread.setEndTime(System.currentTimeMillis() + 600000); //1 minute delay. 
    	thread.setScheduled(true);
    }

    private JMeterThread makeThread(int groupCount,
            ListenerNotifier notifier, ListedHashTree threadGroupTree,
            StandardJMeterEngine engine, int i, 
            JMeterContext context) { // N.B. Context needs to be fetched in the correct thread
        boolean onErrorStopTest = getOnErrorStopTest();
        boolean onErrorStopTestNow = getOnErrorStopTestNow();
        boolean onErrorStopThread = getOnErrorStopThread();
        boolean onErrorStartNextLoop = getOnErrorStartNextLoop();
        String groupName = getName();
        final JMeterThread jmeterThread = new JMeterThread(cloneTree(threadGroupTree), this, notifier);
        jmeterThread.setThreadNum(i);
        jmeterThread.setThreadGroup(this);
        jmeterThread.setInitialContext(context);
        final String threadName = groupName + " " + (groupCount) + "-" + (i + 1);
        jmeterThread.setThreadName(threadName);
        jmeterThread.setEngine(engine);
        jmeterThread.setOnErrorStopTest(onErrorStopTest);
        jmeterThread.setOnErrorStopTestNow(onErrorStopTestNow);
        jmeterThread.setOnErrorStopThread(onErrorStopThread);
        jmeterThread.setOnErrorStartNextLoop(onErrorStartNextLoop);
        return jmeterThread;
    }

    private ListedHashTree cloneTree(ListedHashTree tree) {
        TreeCloner cloner = new TreeCloner(true);
        tree.traverse(cloner);
        return cloner.getClonedTree();
    }
    /**
     * Register Thread when it starts
     * @param jMeterThread {@link JMeterThread}
     * @param newThread Thread
     */
    private void registerStartedThread(JMeterThread jMeterThread, Thread newThread) {
        allThreads.put(jMeterThread, newThread);
    }
    /**
     * @return boolean true if all threads stopped
     */
    @Override
    public boolean verifyThreadsStopped() {
        boolean stoppedAll = true;
        for (Thread t : allThreads.values()) {
            stoppedAll = stoppedAll && verifyThreadStopped(t);
        }
        return stoppedAll;
    }

    /**
     * Verify thread stopped and return true if stopped successfully
     * @param thread Thread
     * @return boolean
     */
    private boolean verifyThreadStopped(Thread thread) {
        boolean stopped = true;
        if (thread != null) {
            if (thread.isAlive()) {
                try {
                    thread.join(WAIT_TO_DIE);
                } catch (InterruptedException e) {
                }
                if (thread.isAlive()) {
                    stopped = false;
                    log.warn("Thread won't exit: " + thread.getName());
                }
            }
        }
        return stopped;
    }


    @Override
    public void waitThreadsStopped() {
        for (Thread t : allThreads.values()) {
            waitThreadStopped(t);
        }
    }

    private void waitThreadStopped(Thread thread) {
        if (thread != null) {
            while (thread.isAlive()) {
                try {
                    thread.join(WAIT_TO_DIE);
                } catch (InterruptedException e) {
                }
            }
        }
    }

	@Override
	public void tellThreadsToStop() {
        running = false;
        for (Entry<JMeterThread, Thread> entry : allThreads.entrySet()) {
            JMeterThread item = entry.getKey();
            item.stop(); // set stop flag
            item.interrupt(); // interrupt sampler if possible
            Thread t = entry.getValue();
            if (t != null ) { // Bug 49734
                t.interrupt(); // also interrupt JVM thread
            }
        }
	}

	@Override
	public void stop() {
		running = false;
		for (JMeterThread item : allThreads.keySet()) {
			item.stop();
		}

	}

}

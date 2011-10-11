package com.atc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * This class manages the Air-Craft priority list and serves the request. This Class is following 
 * Singleton design, since only one ACManager object is required in the entire system. 
 * @author Rakesh Kumar
 *
 */
public class AcManager {

	private static AcManager 	mACManager;
	private PriorityQueue<Ac> 	mAcPQ;  		/*Air-craft priority queue*/
	private ObjectOutputStream 	mOOS;			/*For serializing the AC priority queue*/
	private ObjectInputStream	mOIS;			/*For object restoration*/

	/**
	 * private constructor so that only this class can make object of its own. Basically to follow 
	 * singleton design.
	 */
	private AcManager(){

		try {
			restoreQueue();
		} catch (Exception e) {
			System.err.println("Some Problem happend during restoration of Aircraft Queue");
			System.out.println("If you are running first time then ignore this message");
			mAcPQ = new PriorityQueue<Ac>(AcConst.INITIAL_PRIORITY_QUEUE_SIZE);
		}		
	}

	/**
	 * This method returns the singleton object of this class. Synchronized has been used on this method 
	 * since the double null checks gives problem because of compiler optimization(http://java.sun.com/developer/technicalArticles/Programming/singletons/). 
	 * This method will be slow since the synchronization has been used on the entire method but will give
	 * surety that the compiler optimization will not affect this design.
	 * @return ACManager singleton object
	 */
	synchronized static public AcManager getInstance(){

		if(null==mACManager){
			mACManager = new AcManager();
		}
		return mACManager;
	}
	/**
	 * This method will put the Air-craft in the priority queue when ever it get the takeoff request
	 * This method takes Air-craft as an object to put in the priority-queue.
	 * @param airCraft
	 */
	public void enqueueAC(Ac airCraft){
		mAcPQ.add(airCraft);
	}
	/**
	 * This method returns the Air-craft object which has highest priority.
	 * @return
	 */
	public Ac dequeueAC(){

		Ac ac=null;

		if(!mAcPQ.isEmpty()){
			ac=mAcPQ.remove();
		}
		return ac;
	}
	/**
	 * returns the number of Air-craft in the priority-Queue.
	 * @return
	 */
	public Integer getPriorityQueueLenght(){
		return mAcPQ.size();
	}
	/**
	 * This method will save the priority queue using Serializable interface. 
	 * @throws IOException Throws IOException if there is problem in IO 
	 * @throws FileNotFoundException 	Throws FileNotFounException of it doesn't find the Serialize file.
	 */
	public void saveQueue() throws FileNotFoundException, IOException{
		/*
		 * This method uses lazi initialization of member variable 
		 */
		mOOS = new ObjectOutputStream(new FileOutputStream(AcConst.SERIALIZED_FILE));
		mOOS.writeObject(mAcPQ);
	}
	/**
	 * This method restore the Aircraft priority queue from serialized file. 
	 * @throws FileNotFoundException Throws FileNotFoundException if it doesn't find the file.
	 * @throws IOException	Throws IOException if there is some problem in IO.
	 * @throws ClassNotFoundException	Throws ClassNotFoundException if there is change in the class version.
	 */
	@SuppressWarnings("unchecked")

	public void restoreQueue() throws FileNotFoundException, IOException, ClassNotFoundException{
		/*this method uses lazi initialization of member variable*/

		mOIS = new ObjectInputStream(new FileInputStream(AcConst.SERIALIZED_FILE));
		mAcPQ = (PriorityQueue<Ac>)mOIS.readObject();
	}

	/**
	 * Displays all the Aircraft currently in the Queue.
	 */
	public void displayPriorityQueue(){
		Integer acNumber=1;

		Object pqArray[] = mAcPQ.toArray();
		Arrays.sort(pqArray);
		System.out.println("Aicrafts in Queue");
		for(Object ac : pqArray){
			System.out.println(acNumber+"."+ac);
			acNumber++;
		}
		System.out.println();
	}
}

import java.util.Queue;
import java.util.LinkedList;

public class SharedParkingState {
	
	private static Queue<Integer> q = new LinkedList<>();
    private static int CarWaitingID = 0;
    private SharedParkingState mySharedObj;
	private String myThreadName;
	private double ParkingSpaces;
	//private double mySharedVariable;
	private boolean accessing=false; // true a thread has a lock, false otherwise
	private int threadsWaiting=0; // number of waiting writers
	private boolean Qactive =false;
	private static int TID=0;
	private CreateLog myLogFile;

// Constructor	
	
	SharedParkingState(double SharedVariable, CreateLog LogObject) {
		ParkingSpaces = SharedVariable;
		myLogFile=LogObject;
	}

//Attempt to aquire a lock
	
	  public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        //System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting;
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0
		      //System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true;
		    //System.out.println(me.getName()+" got a lock!"); 
		  }

		  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      //System.out.println(me.getName()+" released a lock!");
		  }
	
	
    /* The processInput method */

	public synchronized String processInput(String myThreadName, String theInput) {
			myLogFile.WriteFile("\n TID: "+TID+"\n");
			TID++;
			myLogFile.WriteFile("Message Recieved from "+ myThreadName +"\n");
    		//System.out.println(myThreadName + " received "+ theInput);
			String theOutput = null;
			
    		// Check what the client said
    		if (theInput.equalsIgnoreCase("Car has arrived")) {

				myLogFile.WriteFile("A car has arrived at " + myThreadName+"\n");
				//System.out.println("A car has arrived at " + myThreadName);
    			//Correct request
    			
    			if (ParkingSpaces==0){
					q.add(CarWaitingID);
					myLogFile.WriteFile(myThreadName + " requested a space. Car held in queue as there are " + ParkingSpaces + " availible. "+"\n Queue size is "+ q.size()+"\n");
					//System.out.println(myThreadName + " requested a space. Car held in queue as there are " + ParkingSpaces + " availible. "+"\n Queue size is "+ q.size());
					theOutput = "No Space car has been added to queue";
					++CarWaitingID;
				}
				
				else if (ParkingSpaces>0) {
    				myLogFile.WriteFile(myThreadName + " requested a space.\n Car allowed to enter as there were " + ParkingSpaces + " spaces availible"+"\n");
    				//System.out.println(myThreadName + " requested a space.\n Car allowed to enter as there were " + ParkingSpaces + " spaces availible");
					theOutput = "Space availible allow car to enter";
					--ParkingSpaces;
    			}
			}

    		else if(theInput.equalsIgnoreCase("Car has Left")) { 
				
				if(ParkingSpaces<5)
				{
					myLogFile.WriteFile("A car left parking space form " + myThreadName+"\n");
					//System.out.println("A car left parking space form " + myThreadName);
					++ParkingSpaces;
					theOutput = "Parking Space updated";
					Qactive=WaitCheck();
					if(Qactive){
						// crem is Car to be removed thus (crem)oved
						int crem = q.remove();
						myLogFile.WriteFile("Car ID "+ crem+ " has entered the car park from queue"+"\n");
						System.out.println("Car ID "+ crem+ " has entered the car park from queue");
						--ParkingSpaces;
						theOutput= theOutput.concat(". \n A queued car was allowed to enter");
					}
				}
				else {
					myLogFile.WriteFile("Message recieved from " + myThreadName+" A car left parking space form. \n Error Car park already empty"+"\n");
					//System.out.println("Message recieved from " + myThreadName+" A car left parking space form. \n Error Car park empty");
					theOutput = "Car Park is empty ???";
				}
				
				
				
			}
			else {System.out.println("Error - thread call not recognised.");
				theOutput="Error Command not found";
				myLogFile.WriteFile("Error Command not found"+"\n");
			}//incorrect request
			
     		//Return the output message to the PSERVER
			System.out.println(theOutput);
			//myLogFile.WriteFile("Output:"+theOutput+"\n Transaction Successful"+"\n");
    		return theOutput;
		}
		
	private boolean WaitCheck(){
		if(q.size()>0){
			return true;
		}
		return false;
		
	} 

}

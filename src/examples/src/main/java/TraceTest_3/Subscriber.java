package TraceTest_3;

import es.upv.dsic.gti_ia.core.ACLMessage;
import es.upv.dsic.gti_ia.core.AgentID;
import es.upv.dsic.gti_ia.core.BaseAgent;
import es.upv.dsic.gti_ia.core.TraceEvent;
import es.upv.dsic.gti_ia.trace.*;
import es.upv.dsic.gti_ia.trace.exception.TraceServiceNotAllowedException;

/*****************************************************************************************/
/*                                      TraceTest3                                       */
/*****************************************************************************************/
/*                     Author: Luis Burdalo (lburdalo@dsic.upv.es)                       */
/*****************************************************************************************/
/*                                     DESCRIPTION                                       */
/*****************************************************************************************

    Simple battery of requests to make sure the Trace Manager does not let erroneous
    publication/unpublication nor subscription/unsubscription
    
    Initialization:
    
    SUBSCRIBER:
    	- Subscribes to DD_Test_TSSS1 from any entity (FAIL!)
    	- Subscribes to DD_Test_TSSS1 from PUBLISHER entity (FAIL!)
    	- Subscribes to DD_Test_TS1 from any entity (OK)
    	- Subscribes AGAIN to DD_Test_TS1 from any entity (FAIL!)
    	- Subscribes to DD_Test_TS1 from PUBLISHER entity (OK)
    	- Subscribes AGAIN to DD_Test_TS1 from PUBLISHER entity (FAIL!)
    	- Subscribes to DD_Test_TS1 from SUBSCRIBER entity (FAIL!)
    	- Unsubscribes from DD_Test_TS2 from any entity (FAIL!)
    	- Unsubscribes from DD_Test_TS1 from SUBSCRIBER (FAIL!)
    	- Unsubscribes from DD_Test_TS1 from PUBLISHER (OK)
    	- Unsubscribes from DD_Test_TS1 from any entity (OK)
    	- Unpublishes DD_Test_TS1 (FAIL!)
      
    Execution:
    	  
    SUBSCRIBER:
    	- Subscribe to DD_Test_TS1 (OK: Receiving events from DD_Test_TS1)
    	- Subscribe to DD_Test_TS2 (OK: Receiving events from DD_Test_TS1 and DD_Test_TS2)
    	- Subscribe to DD_Test_TS3 (OK: Receiving events from DD_Test_TS1,DD_Test_TS2 and
    		DD_Test_TS3)
    	- Send a message to PUBLISHER requesting the unpublication of DD_Test_TS3
    	   (OK: Receiving events from DD_Test_TS1 and DD_Test_TS2)
    	- Unsubscribe from DD_Test_TS1 and DD_Test_TS2 (OK: No more event receiving)

*****************************************************************************************/
public class Subscriber extends BaseAgent{
	private AgentID publisherAid = new AgentID("qpid://publisher@localhost:8080");
	
	public Subscriber(AgentID aid) throws Exception {
		super(aid);
		/**
		 * Initializing tracing services and stuff
		 */
		System.err.println("[SUBSCRIBER " + this.getName() + "]: Basic test start...");
		
		try {
			System.err.println("[SUBSCRIBER " + this.getName() + "]: First, basic subscriptions and unsubscription operations:");
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Subscribing to DD_Test_TSSS1 from any entity (THIS SHOULD FAIL)...");
			TraceInteract.requestTracingService(this, "DD_Test_TSSS1");
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Subscribing to DD_Test_TSSS1 from 'publisher' entity (THIS SHOULD FAIL)...");
			TraceInteract.requestTracingService(this, "DD_Test_TSSS1", publisherAid);
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Subscribing to DD_Test_TS1 from any entity...");
			TraceInteract.requestTracingService(this, "DD_Test_TS1");
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Subscribing AGAIN to DD_Test_TS1 from any entity (THIS SHOULD FAIL)...");
			TraceInteract.requestTracingService(this, "DD_Test_TS1");
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Subscribing to DD_Test_TS1 from 'publisher' entity...");
			TraceInteract.requestTracingService(this, "DD_Test_TS1", publisherAid);
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Subscribing AGAIN to DD_Test_TS1 from 'publisher' entity (THIS SHOULD FAIL)...");
			TraceInteract.requestTracingService(this, "DD_Test_TS1", publisherAid);
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Subscribing AGAIN to DD_Test_TS1 from a false provider (me myself) (THIS SHOULD FAIL)...");
			TraceInteract.requestTracingService(this, "DD_Test_TS1", this.getAid());
			Thread.sleep(500);
			
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Now unsubscribing from tracing services...");
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Now unsubscribing from DD_Test_TS2 (THIS SHOULD FAIL)...");
			TraceInteract.cancelTracingServiceSubscription(this, "DD_Test_TS2");
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Now unsubscribing from DD_Test_TS1 from a false publisher (me myself) (THIS SHOULD FAIL)...");
			TraceInteract.cancelTracingServiceSubscription(this, "DD_Test_TS1", this.getAid());
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Now unsubscribing from DD_Test_TS1 from the real publisher...");
			TraceInteract.cancelTracingServiceSubscription(this, "DD_Test_TS1", publisherAid);
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Now unsubscribing from DD_Test_TS1 from any publisher...");
			TraceInteract.cancelTracingServiceSubscription(this, "DD_Test_TS1");
			Thread.sleep(500);
			
			System.err.println();
			System.err.println();
			System.err.println();
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Now, we'll try to unpublish an existing tracing service which is not published:");
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Unpublishing DD_Test_TS1 (THIS SHOULD FAIL)...");
			TraceInteract.unpublishTracingService(this, "DD_Test_TS1");
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.err.println("[SUBSCRIBER " + this.getName() + "]: OK! Ready to execute...");
	}

	public void execute() {
		ACLMessage msg;
		System.err.println("[SUBSCRIBER " + this.getName() + "]: Executing...");
		
		try {
			System.err.println("\n[SUBSCRIBER " + this.getName() + "]: Subscribing to DD_Test_TS1...\n\tReceiving [ DD_Test_TS1 ]\n");
			TraceInteract.requestTracingService(this, "DD_Test_TS1");
			Thread.sleep(3000);
			
			System.err.println("\n[SUBSCRIBER " + this.getName() + "]: Subscribing to DD_Test_TS2...\n\tReceiving [ DD_Test_TS1 DD_Test_TS2 ]\n");
			TraceInteract.requestTracingService(this, "DD_Test_TS2");
			Thread.sleep(3000);
			
			System.err.println("\n[SUBSCRIBER " + this.getName() + "]: Subscribing to DD_Test_TS3...\n\tReceiving [ DD_Test_TS1 DD_Test_TS2 DD_Test_TS3 ]\n");
			TraceInteract.requestTracingService(this, "DD_Test_TS3");
			Thread.sleep(3000);
			
			System.err.println("\n[SUBSCRIBER " + this.getName() + "]: Sending message to PUBLISHER to request unpublication of DD_Test_TS3\n");
			msg = new ACLMessage(ACLMessage.REQUEST);
	    	msg.setSender(this.getAid());
	    	msg.setReceiver(publisherAid);
			msg.setContent("UNPUBLISH#DD_Test_TS3");
			send(msg);
			System.err.println("\n[SUBSCRIBER " + this.getName() + "]: Message sent...\n\tReceiving [ DD_Test_TS1 DD_Test_TS2 ]\n");
			Thread.sleep(3000);
		
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Done!");
		
			System.err.println("[SUBSCRIBER " + this.getName() + "]: Now unsubscribing from tracing services...");
			TraceInteract.cancelTracingServiceSubscription(this, "DD_Test_TS1");
			TraceInteract.cancelTracingServiceSubscription(this, "DD_Test_TS2");

			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TraceServiceNotAllowedException e) {
			e.printStackTrace();
		}
		
    	System.err.println("[SUBSCRIBER " + this.getName() + "]: Done!");
		System.err.println("[SUBSCRIBER " + this.getName() + "]: Bye!");
		
	}

	public void onTraceEvent(TraceEvent tEvent) {
		/**
		 * When a trace event arrives, it prints it on the screen
		 */
//		System.err.println("[SUBSCRIBER " + this.getName() + "]: Event from " + tEvent.getOriginEntity().getAid().toString() + ": " + tEvent.getTracingService() + ": " + tEvent.getContent());
	}
	
	public void onMessage(ACLMessage msg){
//		System.err.println("[SUBSCRIBER " + this.getName() + "]: Msg from " + msg.getSender().toString() + ": " + msg.getPerformative() + ":" + msg.getContent());
	}
}

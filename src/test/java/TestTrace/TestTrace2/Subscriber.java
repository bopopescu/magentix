package TestTrace.TestTrace2;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import es.upv.dsic.gti_ia.core.ACLMessage;
import es.upv.dsic.gti_ia.core.AgentID;
import es.upv.dsic.gti_ia.core.BaseAgent;
import es.upv.dsic.gti_ia.core.TraceEvent;
import es.upv.dsic.gti_ia.trace.*;
import es.upv.dsic.gti_ia.trace.exception.TraceServiceNotAllowedException;

/*****************************************************************************************
/*                                      TraceTest_2                                      *
/*****************************************************************************************
/*                     Author: Luis Burdalo (lburdalo@dsic.upv.es)                       *
/*****************************************************************************************
/*                                     DESCRIPTION                                       *
/*****************************************************************************************

    Simple test with two types of agents: 10 PUBLISHER agents and 5 SUBSCRIBER agents.
    
    SUBSCRIBER agents subscribe randomly to two of the services offered by the PUBLISHER
    agents and wait during 12 seconds for events to arrive. Each time a trace event is
    received, the SUBSCRIBER agent updates the corresponding counter so that it is
    possible to verify after the execution that the number of received events of each
    tracing service is 10. Before finishing, each SUBSCRIBER agent displays the number
    of trace events of each tracing service which have been received.
    
    Messages to be displayed on the screen during the execution have been commented in
    order to make the execution more easily readable.

*****************************************************************************************/

/** 
 * @author Luis Burdalo - lburdalo@dsic.upv.es
 * @author Jose Alemany Bordera - jalemany1@dsic.upv.es
 * 
 */

public class Subscriber extends BaseAgent{
	
	static Semaphore contExec;
	private ArrayList<ACLMessage> messages;
	private ArrayList<TraceEvent> events;
	private Random generator;
	private final int N_PUBLISHERS = 10, N_SUBSCRIBERS = 5, N_EVENTS = 10;
	private int publisher_number1=0, publisher_number2=0;
	private int service1=0, service2=0;
	private int n_received1=0, n_received2=0;
	
	public Subscriber(AgentID aid) throws Exception {
		
		super(aid);
		contExec = new Semaphore(0);
		messages = new ArrayList<ACLMessage>();
		events = new ArrayList<TraceEvent>();
		updateTraceMask();
		
		/**
		 * Initializing tracing services and stuff
		 */
		generator = new Random(System.currentTimeMillis());
		//System.out.println("[SUBSCRIBER "+ this.getName() + "]: Subscribing to tracing services...");
		while ((publisher_number1 == publisher_number2) && (service1 == service2)){
			publisher_number1=generator.nextInt(N_PUBLISHERS)+1;
			publisher_number2=generator.nextInt(N_PUBLISHERS)+1;
			service1=generator.nextInt(2)+1;
			service2=generator.nextInt(2)+1;
		}
		
		System.out.println("[SUBSCRIBER " + this.getName() + "]: Subscribing to publisher"+publisher_number1+"<DD_Test_TS_"+service1+">");
		TraceInteract.requestTracingService(this, "publisher"+publisher_number1+"<DD_Test_TS_"+service1+">");
		contExec.acquire();
		
		System.out.println("[SUBSCRIBER " + this.getName() + "]: Subscribing to publisher"+publisher_number2+"<DD_Test_TS_"+service2+">");
		TraceInteract.requestTracingService(this, "publisher"+publisher_number2+"<DD_Test_TS_"+service2+">");
		contExec.acquire();
		
		TestTrace2.end.release();
	}

	public void execute() {
		/**
		 * This agent has no definite work. Wait infinitely the arrival of new
		 * messages.
		 */
		try {
			
			contExec.acquire();
			
			//System.out.println("[SUBSCRIBER " + this.getName() + "]: Now unsubscribing from tracing services publisher"+publisher_number1+"<DD_Test_TS_"+service1+"> and publisher"+publisher_number2+"<DD_Test_TS_"+service2+">...");
			TraceInteract.cancelTracingServiceSubscription(this, "publisher"+publisher_number1+"<DD_Test_TS_"+service1+">");
			TraceInteract.cancelTracingServiceSubscription(this, "publisher"+publisher_number2+"<DD_Test_TS_"+service2+">");
			
			contExec.acquire(2);
			
		} catch (TraceServiceNotAllowedException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if ((n_received1 == N_EVENTS) && (n_received2 == N_EVENTS))
			System.out.println("[SUBSCRIBER " + this.getName() + "]: OK! Received " + n_received1 + " of " + N_EVENTS);
		else
			System.out.println("[SUBSCRIBER " + this.getName() + "]: FAIL! Missed events. Received " + n_received1 + " of " + N_EVENTS + " and " + n_received2 + " of " + N_EVENTS);
		
		System.out.println("[SUBSCRIBER " + this.getName() + "]: Bye!");
		
	}

	public void onTraceEvent(TraceEvent tEvent) {
		events.add(tEvent);
		
		/**
		 * When a trace event arrives, it updates counters and prints the content on the screen
		 */
		System.out.println("[SUBSCRIBER " + this.getName() + "]: Received from " + tEvent.getOriginEntity().getAid().toString() + ": " + tEvent.getTracingService() + " " + tEvent.getContent());

		if (tEvent.getTracingService().contentEquals("publisher"+publisher_number1+"<DD_Test_TS_"+service1+">"))
			n_received1++;
		else if (tEvent.getTracingService().contentEquals("publisher"+publisher_number2+"<DD_Test_TS_"+service2+">"))
			n_received2++;
			
	}
	
	public void onMessage(ACLMessage msg) {
		messages.add(msg);
		
		//System.out.println("[SUBSCRIBER " + this.getName() + "]: Received from " + msg.getSender().toString() + ": " + msg.getPerformative() + " "+ msg.getContent());
		contExec.release();
	}
	
	public ArrayList<ACLMessage> getMessages() {
		return this.messages;
	}
	
	public ArrayList<TraceEvent> getEvents() {
		return this.events;
	}
}

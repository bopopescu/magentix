package TraceDaddy;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import es.upv.dsic.gti_ia.core.AgentID;
import es.upv.dsic.gti_ia.core.AgentsConnection;
import es.upv.dsic.gti_ia.trace.TraceManager;

/*****************************************************************************************
/*                                       TraceDaddy                                      *
/*****************************************************************************************
/*                     Author: Luis Burdalo (lburdalo@dsic.upv.es)                       *
/*****************************************************************************************
/*                                     DESCRIPTION                                       *
/*****************************************************************************************
    Simple example of how to use domain independent tracing services to follow other
    agents' activities and to make decisions according to this activity.
    
    In this case, a Daddy agent listens to his sons (Boy agents) while they are playing
    and when one of them starts crying, he proposes them to take them to the park. When
    both children agree, daddy and his sons leave the building and the application
    finishes.
    
    Initialization:
    
    DADDY:
       - Requests to the NEW_AGENT tracing service in order to know when
         children arrive.
       - Prints on screen that he intends to read the newspaper.
       
    BOYS (Bobby and Timmy):
       - Print on screen their name and age.
       
    Execution:
    
    DADDY:
       - Each time a NEW_AGENT event is received, Daddy requests the tracing
         service MESSAGE_SENT_DETAIL in order to 'listen' to what that agent says.
       - Each time a MESSAGE_SENT_DETAIL trace event is received, Daddy prints its
         content on screen and checks if the content of the message is equal
         to 'GUAAAAAA!'. If so, Daddy cancels the subscription to MESSAGE_SENT_DETAIL
         tracing services and sends ACL request messages to both children to propose
         the go to the park.
       - When both childre have replied with an AGREE message, Daddy agent prints it on
         screen and ends its execution.
         
    BOYS (Bobby and Timmy):
       - Bobby, which is only 5, sends each second an ACL request message to Timmy (which
         is 7) to request him his toy (Give me your toy). After 5 denials, Bobby starts
         requesting it by crying (sending an ACL message with a loud GUAAAAAA!).
       - Both Boy agents reply NO! to any request which does not come from their father
         and only AGREE when their dad requestes them to GO TO THE PARK.
       - When dad requests them (via an ACL message) to go to the park, both sons agree
         and end their execution.
         
*****************************************************************************************/

public class Run {
	public static void main(String[] args) {
		Boy olderSon, youngerSon;
		Daddy dad;
		/**
		 * Setting the Logger
		 */
		DOMConfigurator.configure("configuration/loggin.xml");
		Logger logger = Logger.getLogger(Run.class);

		/**
		 * Connecting to Qpid Broker
		 */
		AgentsConnection.connect();

		try {
			/**
			 * Instantiating the Trace Manager
			 */
			TraceManager tm = new TraceManager(new AgentID("tm"));

			System.out.println("INITIALIZING...");
			
			/**
			 * Instantiating Dad
			 */
			dad = new Daddy(new AgentID("qpid://MrSmith@localhost:8080"));
			
			/**
			 * Instantiating sons
			 */
			olderSon = new Boy(new AgentID("qpid://Timmy@localhost:8080"), 7, dad.getAid());
			youngerSon = new Boy(new AgentID("qpid://Bobby@localhost:8080"), 5, dad.getAid());
			
			/**
			 * Execute the agents
			 */
			dad.start();
			olderSon.start();
			youngerSon.start();
			
		} catch (Exception e) {
			logger.error("Error  " + e.getMessage());
		}
	}
}

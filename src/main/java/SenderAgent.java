import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class SenderAgent extends Agent {
    protected void setup(){
        System.out.println("Sender agent: " + getAID().getName() + " has started");

        addBehaviour(new WakerBehaviour(this, 10000) {
            @Override
            protected void onWake() {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.addReceiver(new AID("receiver", AID.ISLOCALNAME));
                message.setLanguage("English");
                message.setOntology("message-test-ontology");
                message.setContent("Hello this is a message from sender");
                send(message);
                System.out.println("***Message sent to receiver***");
            }
        });
    }
}

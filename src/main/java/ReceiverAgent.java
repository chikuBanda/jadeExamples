import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiverAgent extends Agent {
    protected void setup(){
        System.out.println("Receiver agent: " + getAID().getName() + " has started");
        addBehaviour(new TickerBehaviour(this, 5000) {
            @Override
            protected void onTick() {
                ACLMessage message = receive();
                if(message != null){
                    String messageContent = message.getContent();
                    String sender = message.getSender().getLocalName();

                    System.out.println("\n\n***Received message from " + sender + "***");
                    System.out.println("Message: " + messageContent);
                }
            }
        });
    }
}

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Hashtable;
import java.util.Set;

public class BookSellerAgent1 extends Agent {
    private Hashtable<String, Integer> catalogue;

    protected void setup(){
        catalogue = new Hashtable<>();

        catalogue.put("book1", 30);
        catalogue.put("book2", 30);
        catalogue.put("book3", 40);

        printCatalogue(catalogue);

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.CFP);
                ACLMessage message = receive(template);

                if(message != null){
                    String title = message.getContent();
                    ACLMessage reply = message.createReply();

                    Integer price = (Integer) catalogue.get(title);
                    if(price != null){
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContent(String.valueOf(price.intValue()));
                    }
                    else {
                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("not-available");
                    }
                    send(reply);
                }
                else {
                    block();
                }
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
                ACLMessage message = receive(template);

                if(message != null){
                    String title = message.getContent();
                    ACLMessage reply = message.createReply();

                    reply.setPerformative(ACLMessage.INFORM);
                    send(reply);

                    System.out.println("***SOLD BOOK: " + title + " at $" + catalogue.get(title));
                    catalogue.remove(title);
                    printCatalogue(catalogue);
                    if(catalogue.size() == 0){
                        System.out.println("Catalogue is empty! Terminating agent!!");
                        doDelete();
                    }
                }
                else {
                    block();
                }
            }
        });
    }

    public void printCatalogue(Hashtable<String, Integer> catalogue){
        System.out.println("\n\n");
        System.out.println("***Catalogue***");
        Set<String> books = catalogue.keySet();
        for(String book: books){
            System.out.println(book + "....$" + catalogue.get(book));
        }
        System.out.println("\n\n");
    }
}

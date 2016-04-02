package sample.tcp.client;

import akka.actor.*;
import akka.japi.Procedure;
import model.Ping;
import scala.concurrent.duration.Duration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * @author renatomoitinhodias@gmail.com
 */
public class PingLookupActor extends UntypedActor {

    private final String path;
    private ActorRef calculator = null;

    public PingLookupActor(String path) {
        this.path = path;
        sendIdentifyRequest();
    }

    private void sendIdentifyRequest() {
        getContext().actorSelection(path).tell(new Identify(path), getSelf());
        getContext()
                .system()
                .scheduler()
                .scheduleOnce(Duration.create(3, TimeUnit.SECONDS), getSelf(),
                        ReceiveTimeout.getInstance(), getContext().dispatcher(), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ActorIdentity) {
            calculator = ((ActorIdentity) message).getRef();
            if (calculator == null) {
                System.out.println("Remote actor not available: " + path);
            } else {
                getContext().watch(calculator);
                getContext().become(active, true);
            }

        } else if (message instanceof ReceiveTimeout) {
            sendIdentifyRequest();

        } else {
            System.out.println("Not ready yet");

        }
    }

    Procedure<Object> active = message -> {
        if (message instanceof Ping.Request) {
            Ping.PingRequest request = (Ping.PingRequest) message;
            System.out.printf("Ping Send request ID: %s\tnow: %s \t", request.getId(), LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getNow()), ZoneId.systemDefault()));
            calculator.tell(message, getSelf());
        } else if (message instanceof Ping.PingResponse) {
            Ping.PingResponse result = (Ping.PingResponse) message;
            System.out.printf("Ping Response ID: %s\tlatency: %d mls\n", result.getId(), result.latencyInMillis());
        } else if (message instanceof Terminated) {
            System.out.println("Calculator terminated");
            sendIdentifyRequest();
            getContext().unbecome();

        } else if (message instanceof ReceiveTimeout) {
            // ignore

        } else {
            unhandled(message);
        }

    };
}
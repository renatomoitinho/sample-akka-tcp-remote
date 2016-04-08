package sample.tcp.server;


import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.typesafe.config.ConfigFactory;
import model.proto.Ping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author renatomoitinhodias@gmail.com
 */
public class PingServerSystemMain {

    public static final Logger log = LoggerFactory.getLogger(PingServerSystemMain.class);

    public static void main(String[] args) {
        ActorSystem.create("PingSystem", ConfigFactory.load("pingConfiguration"))
                .actorOf(Props.create(PingProcessActor.class), "pingServer");
        log.info("Started Ping Server :)");

    }

    public static class PingProcessActor extends UntypedActor {
        @Override
        public void onReceive(Object message) {
            if (message instanceof Ping.PingRequest) {
                Ping.PingRequest request = (Ping.PingRequest) message;
                //System.out.printf("Received Ping time: %s\tMessage Request:%s \n", LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()), request);

                getSender().tell(Ping.PingResponse.newBuilder().setPingRequest(request).setLatency(
                        Duration.between(Instant.ofEpochMilli(request.getNow()), Instant.now()).toMillis()
                ).build(), getSelf());
            } else {
                unhandled(message);
            }
        }
    }
}

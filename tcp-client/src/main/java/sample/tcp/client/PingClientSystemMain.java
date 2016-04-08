package sample.tcp.client;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import model.proto.Ping;
import scala.concurrent.duration.Duration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
public class PingClientSystemMain {
    public static void main(String[] args) throws InterruptedException {

        final ActorSystem system = ActorSystem.create("PingLookupSystem", ConfigFactory.load("pingRemoteLookup"));
        final ActorRef actor = system.actorOf(Props.create(PingLookupActor.class, "akka.tcp://PingSystem@127.0.0.1:2552/user/pingServer"), "PingLookupActor");

//        system.scheduler().schedule(Duration.create(1, TimeUnit.SECONDS),
//                Duration.create(1, TimeUnit.SECONDS), () -> {
//                    actor.tell(Ping.PingRequest.newBuilder()
//                            .setId(UUID.randomUUID().toString())
//                            .setNow(System.currentTimeMillis())
//                            .build(), ActorRef.noSender());
//                }, system.dispatcher());

        for(int i=0; i < 10000;i++) {
            actor.tell(Ping.PingRequest.newBuilder()
                    .setId(UUID.randomUUID().toString())
                    .setNow(System.currentTimeMillis())
                    .build(), ActorRef.noSender());
            TimeUnit.MILLISECONDS.sleep(1);
        }

    }

}

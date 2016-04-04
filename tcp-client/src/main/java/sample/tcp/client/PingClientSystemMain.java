package sample.tcp.client;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import model.Ping;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
public class PingClientSystemMain {
    public static void main(String[] args) throws InterruptedException {

        final ActorSystem system = ActorSystem.create("PingLookupSystem", ConfigFactory.load("pingRemoteLookup"));
        final ActorRef actor = system.actorOf(Props.create(PingLookupActor.class, "akka.tcp://PingSystem@127.0.0.1:2552/user/pingServer"), "PingLookupActor");

        system.scheduler().schedule(Duration.create(1, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.SECONDS), () -> {
                    actor.tell(new Ping.PingRequest(System.currentTimeMillis()), ActorRef.noSender());
                }, system.dispatcher());

    }

}

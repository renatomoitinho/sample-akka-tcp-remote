#sample-akka-tcp-remote (one million messages per minute)
Example AKKA/IO(2.4.3) TCP remote object 

## run maven parent and started server and client :]

> Simple config in sever resource:pingConfiguration.conf
```javascript
akka {
  actor {
    provider = "akka.remote.RemoteActorRefProvider"
  }
  remote {
    netty.tcp {
      hostname = "127.0.0.1"
      port = 2552
    }
  }
}
```

> Simple config Client resource:pingRemoteLookup.conf
```javascript
akka {
  actor {
    provider = "akka.remote.RemoteActorRefProvider"
  }
}
```


### Terminal outuput Client

```sh
Ping Send request ID: b84d916c-fe8f-4f1e-8dab-cdfd4d743fa6	now: 2016-04-02T13:36:51.138 	Ping Response ID: b84d916c-fe8f-4f1e-8dab-cdfd4d743fa6	latency: 4 mls
Ping Send request ID: dc7dd78a-d620-44c9-86f9-f1c80b99abd6	now: 2016-04-02T13:36:52.149 	Ping Response ID: dc7dd78a-d620-44c9-86f9-f1c80b99abd6	latency: 5 mls
Ping Send request ID: 7c77a9fa-35ca-41ea-adea-b477bf340cbf	now: 2016-04-02T13:36:53.138 	Ping Response ID: 7c77a9fa-35ca-41ea-adea-b477bf340cbf	latency: 5 mls
```

### Terminal output Server

```sh
Received Ping time: 2016-04-02T13:36:54.155	Message Request:PingRequest{now=1459615014150, id='4f0a50d6-c553-4e8a-838f-4c0011153659'} 
Received Ping time: 2016-04-02T13:36:55.142	Message Request:PingRequest{now=1459615015139, id='01f789d5-8723-4f95-952c-1f75efbdf3d4'} 
```

### Tech
* [akka] - Build powerful concurrent & distributed applications
more easily

[akka]: <http://akka.io>

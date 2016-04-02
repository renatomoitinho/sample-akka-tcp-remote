package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * @author renatomoitinhodias@gmail.com
 */
public class Ping {

    public interface Request extends Serializable {}

    public interface Response extends Serializable {}

    public static class PingRequest implements Request {
        private static final long serialVersionUID = 1L;

        final long now;
        final String id;

        public PingRequest(long now) {
            this.now = now;
            this.id = UUID.randomUUID().toString();
        }

        public long getNow() {
            return now;
        }

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "PingRequest{" +
                    "now=" + now +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    public static class PingResponse implements Response {
        private static final long serialVersionUID = 1L;
        private final PingRequest request;
        private final long now;

        public PingResponse(PingRequest request, long now) {
            this.request = request;
            this.now = now;
        }

        public String getId() {
            return request.getId();
        }

        public long latencyInMillis() {
            return Duration.between(Instant.ofEpochMilli(request.getNow()), Instant.ofEpochMilli(now)).toMillis();
        }
    }

}

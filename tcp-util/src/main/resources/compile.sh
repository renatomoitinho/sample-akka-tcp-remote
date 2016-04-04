SRC_DIR=~/Desktop/oi/tcp/sample-akka-tcp-remote/tcp-util/src/main/resources
DST_DIR=~/Desktop/oi/tcp/sample-akka-tcp-remote/tcp-util/src/main/java/model

protoc -I=${SRC_DIR} --java_out=${DST_DIR} ${SRC_DIR}/sample.proto
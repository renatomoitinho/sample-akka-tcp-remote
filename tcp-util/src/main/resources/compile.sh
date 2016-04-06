SRC_DIR=/Data/java/supra/tcp-sample/tcp-sample/tcp-util/src/main/resources
#~/Desktop/oi/tcp/sample-akka-tcp-remote/tcp-util/src/main/resources
DST_DIR=/Data/java/supra/tcp-sample/tcp-sample/tcp-util/src/main/java/model

protoc -I=${SRC_DIR} --java_out=${DST_DIR} ${SRC_DIR}/ping.proto
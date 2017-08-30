# The Hitchhiker's Guide

Android client app under src/main/.
`gradle -b build.gradle build` for local gradle compiling, 
`gradle wrapper && ./gradlew build` for remote (dumps in `build`), or 
`mvn package` for maven compiling by `pom.xml` (dumps in `target`).


Standard-java TCP host and client with javafx in src/protocol, 
`javac -d bin src/protocol/*.java` will comple them in `bin/`, than 
`java -cp bin protocol/Host` to host, or `.../Client` to peer. 


# The Hitchhiker's Guide


Standard-java TCP host and client with javafx in src/protocol, \n
`javac -d bin src/protocol/*.java` will comple them in `bin/`, then \n
`java -cp bin protocol/Host` to host, or `.../Client` to peer. 


Android client app under src/main/. \n
`gradle -b build.gradle build` for local gradle compiling, \n
`gradle wrapper && ./gradlew build` for remote (dumps in `build/`), or \n
`mvn package` for maven compiling by `pom.xml` (dumps in `target/`).


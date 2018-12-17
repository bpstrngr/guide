# The Hitchhiker's Guide


Standard-java TCP host and client with javafx in src/protocol, 
`javac -d bin src/protocol/*.java` will compile them in `bin/`, so 
`java -cp bin protocol.Host` will host,  ~`.Client` will peer. 

Android client is under src/main/. 

   `wget https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip  
   && unzip sdk-tools-linux-3859397.zip && export ANDROID_HOME=$(pwd)/tools` for android sdk,  
   then install modules with sh tools/bin/sdkmanager "build-tools;26.0.1" "platform-tools"
`gradle -b build.gradle build` for local gradle compiling, 
`gradle wrapper && sh ./gradlew build` for remote (dumps in `build/`), or 
`mvn package` for maven compiling with `pom.xml` (dumps in `target/`).


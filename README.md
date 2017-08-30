# The Hitchhiker's Guide

Android client app under src/main/.
`gradle.build` for local gradle compiling, `gradlew` wrapper for remote. 

Standard java tcp host and client with javafx in src/protocol, run 
`javac -d bin src/protocol/*.java && java -cp bin protocol/Host` to host, or `.../Client` 
to 
peer. 


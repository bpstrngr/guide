# Hitchhiker's Guide

Android client  app under src/app/.
`gradle.build` for local gradle compiling, `gradlew` wrapper for remote. 

the rest is standard java, run 
javac -d bin src/protocol/*.java && java -cp bin protocol/Host.java

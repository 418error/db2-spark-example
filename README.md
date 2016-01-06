# spark-db2
This started off as a work related thing. Showing how simple it could be talking back to our legacy DB2 instance running on a mainframe using a lite weight framework like [Java spark][Java Spark].

As I was proving this off our network I spun up an environment with DB2 Express-C, a free to develop, free to deploy and free to distribute version of DB2.

## Prerequisites
I'm on a Mac using Homebrew goodness...

virtualbox
```
• brew cask install virtualbox
```
• vagrant
```
brew cask install vagrant
```
• Java 1.8
```
brew cask install java
```
• Gradle
```
brew install gradle
```
• vagrant trigger plugin
```
vagrant plugin install vagrant-triggers
```

## Setting up and compiling
1.
  Download DB2 Express-C from IBM at the time of writing the version was 10.5 (v10.5_linuxx64_expc.tar.gz) and place it here with the vagrant file.

  ```
  vagrant up
  ```
  ignore the following error message...

  ```
  ==> default: Requirement not matched for DB2 database "Server" . Version: "10.5.0.5".
  ==> default:
  ==> default: Summary of prerequisites that are not met on the current system:
  ==> default:
  ==> default:    DBT3514W  The db2prereqcheck utility failed to find the following 32-bit library file: "/lib/libpam.so*".
  ==> default:
  ==> default:
  ==> default: DBT3514W  The db2prereqcheck utility failed to find the following 32-bit library file: "libstdc++.so.6".
  ```

  You now have a running instance of DB2 with the out of the box sample database, which we make use of in the API.

2.
  Create a libs folder and download the IBM JDBC driver from [IBM db2jcc4.jar][IBM JDBC download page] and place it in the lib folder.

  ```
  gradle build
  ```
  ```
  gradle runJar
  ```

3.
  As this is using log4j you can watch the print out in the console of the generated logs folder.





[Java Spark]:http://sparkjava.com/
[IBM DB2 Lite download page]:http://www-01.ibm.com/software/data/db2/express-c/download.html
[IBM JDBC download page]:http://www-01.ibm.com/support/docview.wss?uid=swg21363866

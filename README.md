# db2-spark-example
This is the supporting example application for the [db2-dev-env][db2-dev-env page] project. Showing how simple it could be talking back to our legacy DB2 instance running on a mainframe using a lite weight framework like [Java spark][Java Spark].

## Prerequisites
I'm on a Mac using Homebrew goodness...

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
• A running DB2 instance

Follow the instructions here...
[db2-dev-env][db2-dev-env page]

or

This can be run with your own instance of DB2, just update the params in the build.gradle file or run the jar with manual arguments


## Setting up and compiling
1.
  Create a libs folder and download the IBM JDBC driver from [IBM db2jcc4.jar][IBM JDBC download page] and place it in the lib folder.

  If outside the [db2-dev-env][db2-dev-env page] on your OS
  ```
  gradle runJar
  ```

  or

  If inside the [db2-dev-env][db2-dev-env page]
  ```
  gradle runDevJar
  ```

  or

  Update the runJar task in the build.gradle file to suit your environment.

2.
  If running the API outside the [db2-dev-env][db2-dev-env page]

  <http://localhost:7654/healh>

  <http://localhost:7654/staff?name=Sanders>

  Look here for example data in the [IBM sample database][IBM sample database]


3.
  As this is using log4j you can watch the print out in the console.




[db2-dev-env page]:https://github.com/lendmeapound/db2-dev-env
[Java Spark]:http://sparkjava.com/
[IBM JDBC download page]:http://www-01.ibm.com/support/docview.wss?uid=swg21363866
[IBM sample database]:https://www-01.ibm.com/support/knowledgecenter/SSEPGG_10.5.0/com.ibm.db2.luw.apdv.samptop.doc/doc/r0001094.html?cp=SSEPGG_10.5.0&lang=en

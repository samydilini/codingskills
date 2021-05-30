
Dependency injection can be seen at Services
classes are doing there own responsibilities

Assumptions
only one company will get acquired at one Sometimes
file naming will be respected
Cons

Should have introduced a validator class to validate the headers
Given answer has 7 out puts however this program produces 9
result_output.csv is getting saved in target classes

Technologies used
1. java 11
2. junit tests
3. Mockito
4. maven
5. Git
6. exec-maven-plugin

How to run (steps 2 and 3 are already done)

1.	cd to com.bunnings.catelog.merger
2.  set the file naming in company.properties file in resurce folder
3.  Add the files to resources folder
4.	`mvn clean install` to build and run the unit tests
5.	`mvn clean compile exec:java` to run the app
6.  Find the out put file "result_output.csv" in target/classes

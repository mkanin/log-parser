# log-parser
This is a tool for parsing log files from a web server

# 1. Where you can find the files

1.1 Jar file with dependencies you can find in the folder /target after the project building using Apache Maven 

1.2 Source code in the folder /src 

1.3 MySQL schema in the file /db/scripts/LogDDL.sql 

1.4 SQL queries in the file /db/queries/queries.txt 


# 2. The important steps before running the application

2.1 Before you run the application you need to run the next two commands (without any changes) via MySQL Command Line Client:

CREATE DATABASE dbparser;

GRANT ALL PRIVILEGES ON dbparser.* TO 'uparser'@'localhost' IDENTIFIED BY 'Pa$$w0rd';

2.2 From the command line run the next command:

mysql -u uparser -p dbparser < LogDDL.sql

2.3 You can change the parameters of database connection in the file src/main/resources/application.properties and after that create new .jar file using Apache Maven


# 3. How to run the application

3.1 The first start

java -cp "parser-jar-with-dependencies.jar" com.mkanin.logparser.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=5

You can not specify the other parameters except of --accesslog. In this case input log file will be parsed and load to database, but the report will not be created.

3.2 The second start

The first option for creating "hourly" report (please, see the details below):
java -cp "parser-jar-with-dependencies.jar" com.mkanin.logparser.Parser --startDate=2018-07-01.01:00:00 --duration=hourly --threshold=5


The second option for creating "daily" report (please, see the details below):
java -cp "parser-jar-with-dependencies.jar" com.mkanin.logparser.Parser --startDate=2018-07-01.01:00:00 --duration=daily --threshold=20


Parameters:

--startDate - the date which the report created from

--duration - the period of time for creating the end date (except border). It might be "hourly" by default or "daily". You do not have to specify this parameter.
             In this case duration will be "hourly".
--threshold - the number of time which an ip address required to server during particular period of time that is defined by --startDate and --duration. If the number 
              of requests is more than or equals this parameter, the ip address will be include to the report and saved to database.  

# MasterCardCodingChallenge
MasterCardCodingChallenge

Project Name : City connector

Introduction :
 . The aim of the project is to find if a given pair of cities are connected or not
 
Prerequisites:
 . The system has the capability to run  1.8 or higher version of Java.
 
Set Up:
 . Clone the code locally using the command "git clone"
 . The file containing information on cities and their connections should be named "city.txt" an be place at "src\main\resources"
 . In order to run the application , rum the command "gradlew bootrun"
 . If it run unit tests , that can be done by running the command "gradlew test"
 
How to Use the application
 . Once the application is up and running , send in the source and destination cities following the signature of the below given URL
   http://localhost:8080/connected?origin=Boston&destination=Philadelphia
 . There is no need to restart the server after changing the content of "city.text". The content is dynamically read into the system following every request

Contact Information
 . In case of queries, please email me to "nihar.1882@gmail.com"
# githubRepository
Application to search github repository

> This is sample project for job interview technical test. Below guide is for examiner

This project has been uploaded to AWS: http://ec2-54-179-161-149.ap-southeast-1.compute.amazonaws.com:8080/. Due to limitation for free-tier EC2, I will stop the server after 19th May 2019. Since I'm using bootstrap, this web also support mobile view.

<b>How to start?</b>
1. Make sure your system has java install. run 'java -version' using command prompt to check
2. Clone this project
3. Run 'mvn install' using command prompt to run this project
4. Go to target folder that contain petronas-0.0.1-SNAPSHOT.war file
5. Run 'java -jar petronas-0.0.1-SNAPSHOT.war' from this folder
6. Open http://localhost:8080/

<b>How to test?</b>

Spring boot will run test when building the .war file. If you want to run test manually, follow below steps.
1. Clone this project
2. Open Eclipse and import project as maven project
3. Press CTRL+SHIFT+R to search java resource PetronasApplicationTests.java
4. Open PetronasApplicationTests.java
5. Right click PetronasApplicationTests.java then run as JUnit Test

<b>Admin credential:</b>

```
Username: admin
Password: 123
```

<b>Use Case:</b>
1. As user I want search git repository using topic
2. As user I want search git repository using language
3. As user I want view search result in table with pagination
4. As admin I want search git repository using topic
5. As admin I want search git repository using language
6. As admin I want view search result in table with pagination
7. As admin I want to login to view admin page
8. As admin I want view report of search result
9. As admin I want download search result

<b>Technologies:</b>
1. Java 8
2. Spring framework
3. Bootstrap 3 (HTML, CSS, JS)
4. AWS (EC2)

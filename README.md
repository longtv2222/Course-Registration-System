# ENSF409 Term Project

## Created by [Viet Long Ta](long.ta1@ucalgary.ca) and [Cloud Chagnon](cloud.chagnon@ucalgary.ca).


## Running the program:

To run this this program, there are two parts:

### To run the server:

In your terminal, run the jar in bin called `Server.jar`, eg

```bash
java -jar Server.jar
```

Then, it will ask you for information on the port to run the server on, the jdbc mysql address, the database to use, as well as the username and password for the server.

It will then begin running, only exiting on the server input `exit`

### To run the client:

Execute the jar file in bin called `Client.jar`.

This will startup the client gui. To begin, enter the address and the port of the server.

The client will not run without a server, so for demonstration purposes, the server is deployed on ip `198.53.181.161` with the port `9098`.

Once the client is connected, there is a dialogue to select between an ADMIN and a STUDENT.

The admin is able to register courses in their cart as a student would, but they are also able to create new courses lectures, and students. The admin can also view and search through all students.

The student is able register or deregister for lectures, if a selected course has them.

Both the student and the admin are able to view all courses and their lectures.

To add courses, lectures, or register, you need to enter in the course NAME and the IDS seperately, but the client guides you through this.
## Bonus Features Implimented:

### The admin gui: 

It has seperate functionality from the Student gui, being able to add courses and lectures.

### The project deployment: 

Connect to the ip address above to view an active deployment.

### Maintaining a list of users, log in/out features:

The server will not allow multiple clients to connect under the same user.

The server will also log on connections and disconnections.

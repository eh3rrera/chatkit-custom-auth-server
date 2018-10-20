# Chatkit Custom Authorization Server

Custom authorization server made in Java for chats build with [Chatkit](https://pusher.com/chatkit). The server bootstrap an embedded LDAP server where the user information is stored. Follow the tutorial [here](https://pusher.com/tutorials/java-authentication-server-chatkit).

## Getting Started

1. Clone this repository.
2. Create a [Chatkit instance](https://dash.pusher.com/chatkit).
3. In the Chatkit dashboard of your instance, in the *Instance Inspector* section, create one or two users with the same `sn` of the users registered in the file `src/main/resources/schema.ldif`, (`eh` and `eh2` by default) and one chat room.
4. Enter your *Instance Locator ID* and your complete *Secret Key* (find them in the *Credentials* section of your Chatkit instance dashboard) at the top of the file `src/main/java/com/example/authserver/AuthenticationController.java`.
5. In a terminal window, execute `mvnw spring-boot:run` to run the server or in an IDE, execute the main class of the application (`com.example.authserver.AuthserverApplication.java`).
6. You can use a REST client (like Postman) to test the authentication endpoint using the following information:
```
URL: http://localhost:8080/authenticate?user_id=eh&passw=12345 (specify your own user credentials if you changed them)
HTPP method: POST
```

Or test the server using the sample chat from https://github.com/eh3rrera/chatkit-custom-auth-test-chat.

### Prerequisites
- [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html) (8 or superior)
- [Chatkit account](https://dash.pusher.com)

## Built With

* [Spring Initializr](https://start.spring.io/)
* [Pusher Chatkit](https://pusher.com/chatkit) - Developer-driven chat done simply

## Acknowledgments
* Thanks to [Pusher](https://pusher.com/) for sponsoring this tutorial.

## LICENSE
MIT
package com.example.authserver;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {
    private UserRepository userRepository;

    private String key = "<YOUR_WHOLE_SECRET_KEY>"; // something like 2b06ce7d-5c60-41ad:fRvtU+JoxdSlUqWezh2jojiiQcH+lFs032MzeIcAw8g=
    private String chatkitInstanceID = "<YOUR_INSTANCE_ID>"; // something like 3635013a-2594-4640

    private String keyId;
    private String keySecret;

    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;

        String[] keyParts = key.split(":");
        this.keyId = keyParts[0];
        this.keySecret = keyParts[1];
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestParam(value="user_id") String userId,
                                       @RequestParam(value="passw") String passw) {
        Object body = null;
        HttpStatus status = null;

        User user = userRepository.findByUsernameAndPassword(userId, passw);
        System.out.println(user);

        if(user != null) {
            Map<String, String> map = new HashMap<>();
            Instant now = Instant.now();
            Instant expireTime = now.plusSeconds(3600);
            String token = generateToken(userId, now, expireTime);

            map.put("access_token", token);
            map.put("expires_in", Long.toString(expireTime.toEpochMilli()));
            map.put("user_id", userId);

            status = HttpStatus.OK;
            body = map;
        } else {
            status = HttpStatus.BAD_REQUEST;
            body = "User not found";
        }

        return ResponseEntity
                .status(status)
                .body(body);
    }

    private String generateToken(String userId, Instant time, Instant expireTime) {
        Algorithm algorithm = Algorithm.HMAC256(this.keySecret);
        String jws = JWT.create()
                .withClaim("instance", this.chatkitInstanceID)
                .withIssuer("api_keys/" + this.keyId)
                .withIssuedAt(Date.from(time))
                .withExpiresAt(Date.from(expireTime))
                .withSubject(userId)
                .sign(algorithm);
        System.out.println(jws);

        return jws;
    }
}

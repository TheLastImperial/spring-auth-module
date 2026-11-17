# OAuth Client

Module to reuse the OAuth Client.

## Create a new RegisteredClient

To create a new Registered Client you have to use the module `OAuthServer` and the next data
example:

Redirect URI:
http://localhost:8080/login/oauth2/code/thelastimperial

Post Logout Redirect Uris:
http://localhost:8080

This will give you the ClientId and ClientSecret, and then set the next properties:

```bash
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_THELASTIMPERIAL_CLIENTID=DHnnMtgiBrlnOnRe
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_THELASTIMPERIAL_CLIENTSECRET='<_yPZ[)~Rq/Ec"zWD<TC'
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_THELASTIMPERIAL_REDIRECTURI=http://localhost:8080/login/oauth2/code/thelastimperial
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_THELASTIMPERIAL_SCOPE=USER,profile,openid
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_THELASTIMPERIAL_AUTHORIZATIONGRANTTYPE=authorization_code
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_THELASTIMPERIAL_CLIENTAUTHENTICATIONMETHOD=client_secret_post
SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_THELASTIMPERIAL_ISSUERURI=http://127.0.0.1:8081
```

> Note: Assuming that OAuthServer is running on `http://127.0.0.1:8081`.

## Security Filter configuration.

```java
http
    .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.GET, "/").hasAnyAuthority("SCOPE_USER")
        .anyRequest().authenticated()
    )
    .formLogin(Customizer.withDefaults()) // Optional if you have a login page.
    .oauth2Login(Customizer.withDefaults());
```

If `.formLogin` if not configured it, the Client going to redirect to OAuthServer by default
to login.

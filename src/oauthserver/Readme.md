# OAuth Server

OAuth server module to be reused.

## Properties

The properties the have to create to run the system.

| Properties                                                    | Descripcion                   |
| ------------------------------------------------------------- | ----------------------------- |
| com.thelastimperial.auth.oauthserver.jwk.id                   | JWK Id to create tokens       |
| com.thelastimperial.auth.oauthserver.jwk.publicKey            | Public Key String             |
| com.thelastimperial.auth.oauthserver.jwk.privateKey           | Private Key String            |
| com.thelastimperial.auth.oauthserver.newclient.expiresDays    | Expiration of new Clients     |
| com.thelastimperial.auth.oauthserver.newclient.secretSize     | Size of the secret generated  |
| com.thelastimperial.auth.oauthserver.newclient.clientSize     | Size of the client generated  |
| com.thelastimperial.auth.oauthserver.newclient.scopes         | Scopes to the client          |

## Endpoints

The endpoints for this module.

### /clients

Show all the clients created.

### /clients/new

Create a new OAuth2 Client

## Configuration to Security filter chain

```java
@Autowired
private OAuthServerAuthorizeCustomizer oAuthServerAuthorizeCustomizer;
@Autowired
private OAuthServerExceptionHandlerCustomizer oAuthServerExceptionHandlerCustomizer;
.exceptionHandling(
    oAuthServerExceptionHandlerCustomizer
)
.authorizeHttpRequests(
    oAuthServerAuthorizeCustomizer
)
```

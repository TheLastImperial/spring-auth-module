# WebAuthn

Allow associate PassKey and use it to authenticate.

## Endpoints

The endpoints created by this module.

### /webauthn/register

Custom view to register a new keypass.

### /webauthn/webauthn.js

Get the default webauthn.js

## Configuration

The SecurityFilterChain to use the WebAuthn

### Properties

| Property                                      | Description     |
| --------------------------------------------- | --------------- |
| com.thelastimperial.auth.authn.rpName         | RP Name         |
| com.thelastimperial.auth.authn.rpId           | RP Id           |
| com.thelastimperial.auth.authn.allowedOrigins | Allowed Origins |

### WebAuthn configuration

```java
.webAuthn(authn -> authn
    .rpName(webAuthnProperty.getRpName())
    .rpId(webAuthnProperty.getRpId())
    .allowedOrigins(webAuthnProperty.getAllowedOrigins())
    .disableDefaultRegistrationPage(true)
)
```

### Requests Matcher configuration:

```java
.requestMatchers("/webauthn/webauthn.js").permitAll()
.requestMatchers("/webauthn/**").authenticated()
```

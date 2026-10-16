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

The default configuration is in the object `WebAuthnCustomizer` you can just use it.

```java
@Autowired
private WebAuthnCustomizer webAuthnCustomizer;

.webAuthn(
    webAuthnCustomizer
)
```

### Requests Matcher configuration:

The default configuration is in the object `WebAuthnAuthorizeCustomizer` you can just use it.

```java
@Autowired
private WebAuthnAuthorizeCustomizer webAuthnAuthorizeCustomizer;

.authorizeHttpRequests(
    webAuthnAuthorizeCustomizer
)

```

# Authentication process.

The project register, recovery account, set new password and login users. To be reused.

## Parameters

The parameters are used to be fit to your needs.

| Param                                           | Descripcion                       |
| ----------------------------------------------- | --------------------------------- |
| `com.thelastimperial.auth.patterns.password`    | A pattern to validate a password. |
| `com.thelastimperial.auth.patterns.email`       | A pattern to validate a email.    |
| `com.thelastimperial.auth.rememberme.key`       | Key to generate rememeberme       |
| `com.thelastimperial.auth.authn.rpName`         | RP Name                           |
| `com.thelastimperial.auth.authn.rpId`           | RP Id                             |
| `com.thelastimperial.auth.authn.allowedOrigins` | Allowed Origins                   |

## Notifications

To send notifications you have to create a service that implements `NotificationService`.

### Register notification

For register notification you have to create a new service with the name
`registerNotificationService` to be injected and used.

### Recovery notification
For recovery notification you have to create a new service with the name
`recoveryNotificationService` to be injected and used.

### New Password Notification

For new password notification you have to create a new service with the name
`newPasswordNotificationService` to be injected and used.

## Default user roles.

To overwrite the default User roles you can create a new Bean that implements
the interface `DefaultUserRoleService`.

## Security filter chain

Use the default security filter chain to activate.

```java
http
.authorizeHttpRequests(auth -> {
    webAuthnAuthorizeCustomizer.customize(auth);
    authAuthorizeCustomizer.customize(auth);
    auth.requestMatchers("/").hasRole("USER");

})
.formLogin(
    formLoginCustomizer
)
.rememberMe(
    rememberMeCustomizer
)
.logout(
    logoutCustomizer
)
.webAuthn(
    webAuthnCustomizer
);
```

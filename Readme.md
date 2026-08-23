# Authentication process.

The project register, recovery account, set new password and login users. To be reused.

## Parameters

The parameters are used to be fit to your needs.

| Param                                           | Descripcion                       |
| ----------------------------------------------- | --------------------------------- |
| `com.thelastimperial.auth.patterns.password`    | A pattern to validate a password. |
| `com.thelastimperial.auth.patterns.email`       | A pattern to validate a email.    |
| `com.thelastimperial.auth.rememberme.key`       | Key to generate rememeberme       |

## Notifications

To send notifications you have to create a service that implements `NotificationService`.

### Register notification

For register notification you have to create a new service with the name
`registerNotificationService` to be injected and used.

### Recovery notification
For recovery notification you have to create a new service with the name
`recoveryNotificationService` to be injected and used.

## Security filter chain

Use the default security filter chain to activate.

```java
http
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/").hasRole("USER")
    .requestMatchers("/css/auth/**","/js/auth/**","/auth/**").permitAll()
)
.formLogin( login -> login
    .loginPage("/auth/login")
    .failureUrl("/auth/login?error=true")
    .defaultSuccessUrl("/", true)
    .permitAll()
)
.rememberMe(rememberme -> rememberme
    .rememberMeServices(rememberMeServices)
    .rememberMeParameter("remember-me")
)
.logout(logout -> logout
    .logoutUrl("/auth/logout")
    .logoutSuccessUrl("/auth/login")
    .permitAll()
);
```

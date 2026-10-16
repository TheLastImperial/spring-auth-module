# Admin
Admin module to manage users.

## Endpoints

### /admin/users
Show all the users.

### /admin/users/lock-unlock/{username}
Lock or unlock user

### /admin/users/show/{username}

Show user info.

## Configuration to Security filter chain

```java
@Autowired
private AdminAuthorizeCustomizer adminAuthorizeCustomizer;
.authorizeHttpRequests(
    adminAuthorizeCustomizer
);
```

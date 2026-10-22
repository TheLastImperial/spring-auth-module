# Auth Batch

Batch to lock user if the credentials or the account is expired.

## Jobs

The list of jobs on this module.

- CredentialExpiredJob: Job to lock user if the credential is expired.
- AccountExpiredJob: Job to lock user if the account is expired.

## Params

All the params needed to run the batch.

- SPRING_BATCH_JOB_NAME: The job to run.
- COM_THELASTIMPERIAL_AUTH_BATCH_USERID: The user id to lock the users.

## DataSource

The batch need two datasource one to the user tables and the other to the batch tables.

- `spring.datasource` is the prefix to the user tables.
- `spring.datasource.batch` is the prefix to the batch tables.

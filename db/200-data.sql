INSERT INTO user_roles(id, name, created_at, updated_at)
VALUES
('019dffbe-d07e-7bfd-ab68-f566727ec57a', 'ROLE_ROOT', NOW(), NOW()),
('019dffbe-d07e-7bfd-ab68-f566727ec57b', 'ROLE_ADMIN', NOW(), NOW()),
('019dffbe-d07e-7bfd-ab68-f566727ec57c', 'ROLE_USER', NOW(), NOW()),
('019dffbe-d07e-7bfd-ab68-f566727ec57d', 'ROLE_MONITOR', NOW(), NOW()),
('019dffbe-d07e-7bfd-ab68-f566727ec57e', 'ROLE_SYSTEM_BATCH', NOW(), NOW());

INSERT INTO user_actions (id, description, created_at, updated_at)
VALUES
('ACCOUNT_EXPIRED', 'Account with expiration date before actual date.', NOW(), NOW() ),
('ACCOUNT_EXPIRATION_UPDATED', 'Updated account expiration date.', NOW(), NOW()),
('CREDENTIALS_EXPIRED', 'Account credentials expired.', NOW(), NOW()),
('CREDENTIALS_UPDATED', 'Updated account credentials and set new expiration.', NOW(), NOW()),
('ACCOUNT_LOCKED', 'Account locked by system or admin.', NOW(), NOW()),
('ACCOUNT_UNLOCKED', 'Account unlocked by system or admin.', NOW(), NOW()),
('RESTART_CREDENTIALS', 'Account restart credentials.', NOW(), NOW()),
('ACCOUNT_ACTIVATION', 'Activated account by user or admin.', NOW(), NOW());

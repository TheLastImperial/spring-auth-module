
-- Password: 1234
INSERT INTO users (
    id,
    username,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled,
    created_at,
    updated_at
)
VALUES('019dffbe-d07e-7bfd-ab68-f566727ec57d', 'user',
    '$2a$10$eO6sAdt3WNQscoIA1f.RmuTNTF.ovZseDmr1jRBteIOMmDRSgknlG',
    true, true, true, true, now(), now()
),
('019dffbe-d07e-7bfd-ab68-f566727ec570', 'user@user.com',
    '$2a$10$eO6sAdt3WNQscoIA1f.RmuTNTF.ovZseDmr1jRBteIOMmDRSgknlG',
    true, true, true, true, now(), now()
);


INSERT INTO user_role(user_id, role_id)
VALUES
('019dffbe-d07e-7bfd-ab68-f566727ec57d', '019dffbe-d07e-7bfd-ab68-f566727ec57c'),
('019dffbe-d07e-7bfd-ab68-f566727ec570', '019dffbe-d07e-7bfd-ab68-f566727ec57c');

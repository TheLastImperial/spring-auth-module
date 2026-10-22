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
VALUES('119dffbe-d07e-7bfd-ab68-f566727ec57d', 'account_expired',
    '$2a$10$eO6sAdt3WNQscoIA1f.RmuTNTF.ovZseDmr1jRBteIOMmDRSgknlG',
    true, true, true, true, now(), now()
),
('119dffbe-d07e-7bfd-ab68-f566727ec570', 'credential_expired',
    '$2a$10$eO6sAdt3WNQscoIA1f.RmuTNTF.ovZseDmr1jRBteIOMmDRSgknlG',
    true, true, true, true, now(), now()
),
('219dffbe-d07e-7bfd-ab68-f566727ec561', 'BATCHUSER',
    '$2a$10$eO6sAdt3WNQscoIA1f.RmuTNTF.ovZseDmr1jRBteIOMmDRSgknlG',
    false, false, false, false, now(), now()
);

INSERT INTO user_expiries (
    id, user_id, account_expired_at, credentials_expired_at,
    created_at, updated_at
)
VALUES(
    gen_random_uuid(), '119dffbe-d07e-7bfd-ab68-f566727ec57d', NOW() - interval '1 day',
    NULL, NOW(), NOW()
),
(
    gen_random_uuid(), '119dffbe-d07e-7bfd-ab68-f566727ec570', NULL, NOW() - interval '1 day',
    NOW(), NOW()
);

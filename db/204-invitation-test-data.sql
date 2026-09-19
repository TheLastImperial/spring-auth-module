INSERT INTO user_invitations(id, host_id, guest_id, username, created_at, updated_at, expired_at)
VALUES(
    'dd42630f-0b7f-473d-8c8d-8b2dabb8c0d4', '019dffbe-d07e-7bfd-ab68-f566727ec57d', NULL,
    'anotheremail@mail.com', NOW(), NOW(), NOW() + interval '5 days'
);

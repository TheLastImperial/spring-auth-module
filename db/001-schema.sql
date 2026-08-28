
CREATE TABLE public.user_actions (
    id character varying(255) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    description character varying(255),
    updated_at timestamp(6) without time zone NOT NULL
);

CREATE TABLE public.user_activations (
    id uuid NOT NULL,
    activated_at timestamp(6) without time zone,
    created_at timestamp(6) without time zone NOT NULL,
    is_used boolean NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    user_id uuid
);

CREATE TABLE public.user_audits (
    id uuid NOT NULL,
    comment character varying(255),
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    updated_by uuid,
    user_id uuid,
    action_id character varying(255)
);

CREATE TABLE public.user_expiries (
    id uuid NOT NULL,
    account_expired_at timestamp(6) without time zone,
    created_at timestamp(6) without time zone NOT NULL,
    credentials_expired_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone NOT NULL,
    user_id uuid
);

CREATE TABLE public.user_recoveries (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    is_used boolean DEFAULT false NOT NULL,
    last_password character varying(255),
    updated_at timestamp(6) without time zone NOT NULL,
    username character varying(255),
    valid_until_at timestamp(6) without time zone,
    user_id uuid
);

CREATE TABLE public.user_role (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL
);

CREATE TABLE public.user_roles (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    name character varying(255),
    updated_at timestamp(6) without time zone NOT NULL,
    is_default boolean NOT NULL DEFAULT false
);

CREATE TABLE public.users (
    id uuid NOT NULL,
    account_non_expired boolean NOT NULL DEFAULT true,
    account_non_locked boolean NOT NULL DEFAULT true,
    created_at timestamp(6) without time zone NOT NULL,
    credentials_non_expired boolean NOT NULL DEFAULT true,
    enabled boolean NOT NULL DEFAULT true,
    password character varying(255),
    updated_at timestamp(6) without time zone NOT NULL,
    username character varying(255) NOT NULL
);

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT uk872xec3woupu3gw59b04pj3sa UNIQUE (user_id, role_id);

ALTER TABLE ONLY public.user_expiries
    ADD CONSTRAINT ukau7coo78lw6biyjrf7dy14mjs UNIQUE (user_id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);

ALTER TABLE ONLY public.user_actions
    ADD CONSTRAINT user_actions_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_activations
    ADD CONSTRAINT user_activations_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_audits
    ADD CONSTRAINT user_audits_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_expiries
    ADD CONSTRAINT user_expiries_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_recoveries
    ADD CONSTRAINT user_recoveries_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_expiries
    ADD CONSTRAINT fk6w5wraqtinh6cfa7qy10jv35 FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.user_audits
    ADD CONSTRAINT fkbvy1j6qvvc5vg8lqx310k9ttl FOREIGN KEY (action_id) REFERENCES public.user_actions(id);

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkeesok3qinlnaaqdkx1lcbscdt FOREIGN KEY (role_id) REFERENCES public.user_roles(id);

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.user_recoveries
    ADD CONSTRAINT fkmtcgsbytmpx96h7mhbdfm74mb FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.user_activations
    ADD CONSTRAINT fkrv0fp1x05wk0mko77qvgxswvq FOREIGN KEY (user_id) REFERENCES public.users(id);

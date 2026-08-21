--
-- PostgreSQL database dump
--

\restrict OjaeMLfPemjDTdjOAk9CpYdvukggyNVZJ0qLc9L8UOJfTwlN3289977IBrRT7Rk

-- Dumped from database version 18.4
-- Dumped by pg_dump version 18.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: user_actions; Type: TABLE; Schema: public; Owner: auth
--

CREATE TABLE public.user_actions (
    id character varying(255) NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    description character varying(255),
    updated_at timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.user_actions OWNER TO auth;

--
-- Name: user_activations; Type: TABLE; Schema: public; Owner: auth
--

CREATE TABLE public.user_activations (
    id uuid NOT NULL,
    activated_at timestamp(6) without time zone,
    created_at timestamp(6) without time zone NOT NULL,
    is_used boolean NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    user_id uuid
);


ALTER TABLE public.user_activations OWNER TO auth;

--
-- Name: user_audits; Type: TABLE; Schema: public; Owner: auth
--

CREATE TABLE public.user_audits (
    id uuid NOT NULL,
    comment character varying(255),
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    updated_by uuid,
    user_id uuid,
    action_id character varying(255)
);


ALTER TABLE public.user_audits OWNER TO auth;

--
-- Name: user_expiries; Type: TABLE; Schema: public; Owner: auth
--

CREATE TABLE public.user_expiries (
    id uuid NOT NULL,
    account_expired_at timestamp(6) without time zone,
    created_at timestamp(6) without time zone NOT NULL,
    credentials_expired_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone NOT NULL,
    user_id uuid
);


ALTER TABLE public.user_expiries OWNER TO auth;

--
-- Name: user_recoveries; Type: TABLE; Schema: public; Owner: auth
--

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


ALTER TABLE public.user_recoveries OWNER TO auth;

--
-- Name: user_role; Type: TABLE; Schema: public; Owner: auth
--

CREATE TABLE public.user_role (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL
);


ALTER TABLE public.user_role OWNER TO auth;

--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: auth
--

CREATE TABLE public.user_roles (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    name character varying(255),
    updated_at timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.user_roles OWNER TO auth;

--
-- Name: users; Type: TABLE; Schema: public; Owner: auth
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    account_non_expired boolean NOT NULL,
    account_non_locked boolean NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    credentials_non_expired boolean NOT NULL,
    enabled boolean NOT NULL,
    password character varying(255),
    updated_at timestamp(6) without time zone NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO auth;

--
-- Name: user_role uk872xec3woupu3gw59b04pj3sa; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT uk872xec3woupu3gw59b04pj3sa UNIQUE (user_id, role_id);


--
-- Name: user_expiries ukau7coo78lw6biyjrf7dy14mjs; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_expiries
    ADD CONSTRAINT ukau7coo78lw6biyjrf7dy14mjs UNIQUE (user_id);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: user_actions user_actions_pkey; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_actions
    ADD CONSTRAINT user_actions_pkey PRIMARY KEY (id);


--
-- Name: user_activations user_activations_pkey; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_activations
    ADD CONSTRAINT user_activations_pkey PRIMARY KEY (id);


--
-- Name: user_audits user_audits_pkey; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_audits
    ADD CONSTRAINT user_audits_pkey PRIMARY KEY (id);


--
-- Name: user_expiries user_expiries_pkey; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_expiries
    ADD CONSTRAINT user_expiries_pkey PRIMARY KEY (id);


--
-- Name: user_recoveries user_recoveries_pkey; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_recoveries
    ADD CONSTRAINT user_recoveries_pkey PRIMARY KEY (id);


--
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: user_expiries fk6w5wraqtinh6cfa7qy10jv35; Type: FK CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_expiries
    ADD CONSTRAINT fk6w5wraqtinh6cfa7qy10jv35 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_audits fkbvy1j6qvvc5vg8lqx310k9ttl; Type: FK CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_audits
    ADD CONSTRAINT fkbvy1j6qvvc5vg8lqx310k9ttl FOREIGN KEY (action_id) REFERENCES public.user_actions(id);


--
-- Name: user_role fkeesok3qinlnaaqdkx1lcbscdt; Type: FK CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkeesok3qinlnaaqdkx1lcbscdt FOREIGN KEY (role_id) REFERENCES public.user_roles(id);


--
-- Name: user_role fkj345gk1bovqvfame88rcx7yyx; Type: FK CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_recoveries fkmtcgsbytmpx96h7mhbdfm74mb; Type: FK CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_recoveries
    ADD CONSTRAINT fkmtcgsbytmpx96h7mhbdfm74mb FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_activations fkrv0fp1x05wk0mko77qvgxswvq; Type: FK CONSTRAINT; Schema: public; Owner: auth
--

ALTER TABLE ONLY public.user_activations
    ADD CONSTRAINT fkrv0fp1x05wk0mko77qvgxswvq FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

\unrestrict OjaeMLfPemjDTdjOAk9CpYdvukggyNVZJ0qLc9L8UOJfTwlN3289977IBrRT7Rk


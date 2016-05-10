--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: albums; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE albums (
    id integer NOT NULL,
    title character varying,
    artist character varying,
    date_released character varying
);


ALTER TABLE albums OWNER TO "Guest";

--
-- Name: album_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE album_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE album_id_seq OWNER TO "Guest";

--
-- Name: album_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE album_id_seq OWNED BY albums.id;


--
-- Name: albums_genres; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE albums_genres (
    id integer NOT NULL,
    album_id integer,
    genre_id integer
);


ALTER TABLE albums_genres OWNER TO "Guest";

--
-- Name: albums_genres_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE albums_genres_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE albums_genres_id_seq OWNER TO "Guest";

--
-- Name: albums_genres_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE albums_genres_id_seq OWNED BY albums_genres.id;


--
-- Name: genres; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE genres (
    id integer NOT NULL,
    genre_type character varying
);


ALTER TABLE genres OWNER TO "Guest";

--
-- Name: genre_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE genre_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE genre_id_seq OWNER TO "Guest";

--
-- Name: genre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE genre_id_seq OWNED BY genres.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY albums ALTER COLUMN id SET DEFAULT nextval('album_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY albums_genres ALTER COLUMN id SET DEFAULT nextval('albums_genres_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY genres ALTER COLUMN id SET DEFAULT nextval('genre_id_seq'::regclass);


--
-- Name: album_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('album_id_seq', 4, true);


--
-- Data for Name: albums; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY albums (id, title, artist, date_released) FROM stdin;
4	LEMONADE	RIHANNA	2-16
\.


--
-- Data for Name: albums_genres; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY albums_genres (id, album_id, genre_id) FROM stdin;
1	3	1
2	3	1
3	3	1
4	3	1
5	3	1
6	3	1
7	3	1
8	3	1
9	3	1
10	3	1
11	3	1
12	3	1
13	3	1
14	3	1
15	3	1
16	3	1
17	3	1
18	3	1
19	3	1
20	3	1
21	3	1
22	3	1
23	3	1
24	3	1
25	3	1
26	3	1
27	3	1
28	3	1
29	3	1
30	3	1
31	3	1
32	3	1
33	3	1
34	3	1
35	3	1
36	3	1
37	3	1
38	3	1
39	3	1
40	4	2
\.


--
-- Name: albums_genres_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('albums_genres_id_seq', 40, true);


--
-- Name: genre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('genre_id_seq', 2, true);


--
-- Data for Name: genres; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY genres (id, genre_type) FROM stdin;
2	PINK
\.


--
-- Name: album_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY albums
    ADD CONSTRAINT album_pkey PRIMARY KEY (id);


--
-- Name: albums_genres_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY albums_genres
    ADD CONSTRAINT albums_genres_pkey PRIMARY KEY (id);


--
-- Name: genre_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY genres
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


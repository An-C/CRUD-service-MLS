-- Table: userinfo

-- DROP TABLE userinfo;

CREATE TABLE userinfo
(
  id uuid NOT NULL,
  username character varying(100) NOT NULL, -- user name
  pwd character(32), -- user password MD5 hash
  address character varying(1000), -- user address
  email character varying(100), -- user e-mail
  phone character(12), -- user phone
  hide boolean, -- flag if the user is disabled
  CONSTRAINT userinfo_pkey PRIMARY KEY (id),
  CONSTRAINT userinfo_username_key UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userinfo
  OWNER TO postgres;
COMMENT ON COLUMN userinfo.username IS 'user name';
COMMENT ON COLUMN userinfo.pwd IS 'user password MD5 hash';
COMMENT ON COLUMN userinfo.address IS 'user address';
COMMENT ON COLUMN userinfo.email IS 'user e-mail';
COMMENT ON COLUMN userinfo.phone IS 'user phone';
COMMENT ON COLUMN userinfo.hide IS 'flag if the user is disabled';
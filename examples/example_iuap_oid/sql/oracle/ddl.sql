-- DROP TABLE pub_oid;
CREATE TABLE pub_oid (
  schemacode          VARCHAR2(20)      NOT NULL,
  oidbase             VARCHAR2(20)      NOT NULL,
  id                  VARCHAR2(50)      NOT NULL,
  ts                  DATE,
  PRIMARY KEY (id)
);
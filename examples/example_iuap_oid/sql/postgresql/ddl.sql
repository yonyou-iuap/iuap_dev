--DROP TABLE pub_oid;
CREATE TABLE
    pub_oid
    (
        schemacode CHARACTER VARYING(50) NOT NULL,
        oidbase CHARACTER VARYING(50) NOT NULL,
        id CHARACTER VARYING(50) NOT NULL,
        ts TIMESTAMP(6) WITHOUT TIME ZONE,
        PRIMARY KEY (id)
    );
    

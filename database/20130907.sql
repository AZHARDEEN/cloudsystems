
DROP TABLE IF EXISTS audit.program_exception CASCADE ;



CREATE TABLE audit.program_exception
(
	pex_id_in            SERIAL NOT NULL ,
	pex_insert_dt        TIMESTAMP DEFAULT  now()  NOT NULL ,
	pex_stack_ch         TEXT NULL ,
 PRIMARY KEY (pex_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX 
);



GRANT ALL ON audit.program_exception TO postgres;
GRANT ALL ON audit.program_exception TO r_system;
ALTER TABLE audit.program_exception OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON audit.program_exception TO r_system_user;
GRANT SELECT ON audit.program_exception TO r_system_report;

grant USAGE ON SCHEMA AUDIT TO r_system_user;
GRANT usage ON SEQUENCE AUDIT.program_exception_pex_id_in_seq to R_SYSTEM_USER;


ALTER TABLE product DROP COLUMN prd_code_ch;



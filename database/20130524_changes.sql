                                   -- object: inep.revisor_type | type: TABLE --
CREATE TABLE inep.revisor_type(
	irt_id_in integer NOT NULL,
	irt_description_ch varchar(128) NOT NULL,
	CONSTRAINT pk_revisor_type PRIMARY KEY (irt_id_in)
	WITH (FILLFACTOR = 10)
	USING INDEX TABLESPACE tb_cloud_index
)
WITH (OIDS=TRUE)
TABLESPACE tb_cloud_data;
-- ddl-end --

COMMENT ON COLUMN inep.revisor_type.irt_id_in IS 'revisror type table id';
ALTER TABLE inep.revisor_type OWNER TO r_system;
-- ddl-end --

-- object: grant_b0bfd0e8e3 | type: PERMISSION --
GRANT SELECT,INSERT,UPDATE,DELETE
   ON TABLE inep.revisor_type
   TO r_system_user;
;


INSERT INTO INEP.REVISOR_TYPE ( irt_id_in, irt_description_ch ) VALUES ( 1, 'Corretor da prova escrita' );
INSERT INTO INEP.REVISOR_TYPE ( irt_id_in, irt_description_ch ) VALUES ( 2, 'Corretor da prova oral' );

ALTER TABLE inep.inep_revisor
   ADD COLUMN irt_id_in integer NOT NULL DEFAULT 1;

  ALTER TABLE inep.inep_revisor
  ADD CONSTRAINT fk_revisor_type FOREIGN KEY (irt_id_in) REFERENCES inep.revisor_type (irt_id_in) MATCH FULL
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_revisor_type
  ON inep.inep_revisor(irt_id_in);
 
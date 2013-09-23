-- Table: audit.program_exception

DROP TABLE IF EXISTS audit.program_exception;

CREATE TABLE audit.program_exception
(
  pex_id_in serial NOT NULL,
  pex_insert_dt timestamp without time zone NOT NULL DEFAULT now(),
  pex_stack_ch character varying(64),
  CONSTRAINT xpkprogram_exceptions PRIMARY KEY (pex_id_in )
  USING INDEX TABLESPACE tb_cloud_index
)
WITH (
  OIDS=FALSE
);
ALTER TABLE audit.program_exception
  OWNER TO postgres;
GRANT ALL ON TABLE audit.program_exception TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE audit.program_exception TO cloud;
GRANT SELECT ON TABLE audit.program_exception TO jreport;


-- Table: audit.program_exception_trace

DROP TABLE IF EXISTS audit.program_exception_trace;

CREATE TABLE audit.program_exception_trace
(
  pex_id_in serial NOT NULL,
  pet_id_in integer NOT NULL,
  pet_class_ch character varying(1024),
  pet_filename_ch character varying(1024),
  pet_line_in integer,
  ped_method_ch character varying(64),
  CONSTRAINT xpkprogram_exception_trace PRIMARY KEY (pex_id_in , pet_id_in )
  USING INDEX TABLESPACE tb_cloud_index,
  CONSTRAINT fk_pex_pet FOREIGN KEY (pex_id_in)
      REFERENCES audit.program_exception (pex_id_in) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE audit.program_exception_trace
  OWNER TO postgres;
GRANT ALL ON TABLE audit.program_exception_trace TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE audit.program_exception_trace TO cloud;
GRANT SELECT ON TABLE audit.program_exception_trace TO jreport;


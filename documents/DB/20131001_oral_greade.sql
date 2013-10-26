drop table if exists inep.inep_observer_grade;
CREATE TABLE inep.inep_observer_grade
(
  isc_id_ch character varying(32) NOT NULL,
  pct_id_in integer NOT NULL,
  usr_id_in integer NOT NULL, -- Usar sequence: seq_usuario
  iog_id_in integer NOT NULL,
  iot_grade_in integer,
  CONSTRAINT inep_observer_grade_pkey PRIMARY KEY (pct_id_in, usr_id_in, isc_id_ch, iog_id_in)
  USING INDEX TABLESPACE tb_cloud_index,
  CONSTRAINT inep_obs_grade_subs_fkey FOREIGN KEY (pct_id_in, usr_id_in, isc_id_ch)
      REFERENCES inep.inep_subscription (pct_id_in, usr_id_in, isc_id_ch) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE inep.inep_observer_grade
  OWNER TO r_system;
GRANT ALL ON TABLE inep.inep_observer_grade TO r_system;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE inep.inep_observer_grade TO r_system_user;
GRANT SELECT ON TABLE inep.inep_observer_grade TO r_system_report;



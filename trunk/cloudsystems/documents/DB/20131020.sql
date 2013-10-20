DROP TABLE IF EXISTS inep.inep_station_reponsable;

CREATE TABLE inep.inep_station_reponsable
(
  usr_id_in integer NOT NULL,
  pct_id_in integer NOT NULL,
  cli_seq_in integer NOT NULL,
  col_seq_in integer NOT NULL,
  CONSTRAINT inep_station_resp_pkey PRIMARY KEY (usr_id_in, pct_id_in, cli_seq_in, col_seq_in) USING INDEX TABLESPACE tb_cloud_index,
  CONSTRAINT inep_station_r_cli_fkey FOREIGN KEY (usr_id_in, cli_seq_in) REFERENCES client (usr_id_in, cli_seq_in) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT inep_station_r_event_fkey FOREIGN KEY (usr_id_in, pct_id_in) REFERENCES inep.inep_package (usr_id_in, pct_id_in) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT inep_station_r_cli2_fkey FOREIGN KEY (usr_id_in, col_seq_in) REFERENCES collaborator (usr_id_in, col_seq_in) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE inep.inep_station_reponsable OWNER TO r_system;
GRANT ALL ON TABLE inep.inep_station_reponsable TO r_system;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE inep.inep_station_reponsable TO r_system_user;
GRANT SELECT ON TABLE inep.inep_station_reponsable TO r_system_report;

-- Index: inep.xif1inep_station

-- DROP INDEX inep.xif1inep_station;

CREATE INDEX xif1inep_station_r ON inep.inep_station_reponsable USING btree (usr_id_in, cli_seq_in) TABLESPACE tb_cloud_index;
CREATE INDEX xif1inep2_station_r ON inep.inep_station_reponsable USING btree (usr_id_in, col_seq_in) TABLESPACE tb_cloud_index;
CREATE INDEX xifinep3_station_r ON inep.inep_station_reponsable USING btree (pct_id_in, usr_id_in) TABLESPACE tb_cloud_index;
-- Index: inep.xif2inep_station

-- DROP INDEX inep.xif2inep_station;


-- Function: insert_or_update_users()

DROP FUNCTION IF EXISTS insert_or_update_users() CASCADE;

CREATE OR REPLACE FUNCTION insert_or_update_users()
  RETURNS trigger AS
$BODY$
begin
	NEW.usr_name_ch = unaccent ( upper ( trim ( NEW.usr_name_ch ) ) );
RETURN NEW;
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION insert_or_update_users()
  OWNER TO dba_cloud;



-- Trigger: insert_or_update_trigger on person

 DROP TRIGGER if exists insert_or_update_trigger ON person;

CREATE TRIGGER insert_or_update_trigger
  BEFORE INSERT OR UPDATE
  ON USERS
  FOR EACH ROW
  EXECUTE PROCEDURE insert_or_update_users();

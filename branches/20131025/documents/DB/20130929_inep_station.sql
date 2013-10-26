
DROP TABLE IF EXISTS inep.inep_station CASCADE ;

CREATE TABLE inep.inep_station
(
	usr_id_in            INTEGER NOT NULL ,
	pct_id_in            INTEGER NOT NULL ,
	cli_seq_in           INTEGER NOT NULL ,
 PRIMARY KEY (usr_id_in,pct_id_in,cli_seq_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (usr_id_in, cli_seq_in) REFERENCES public.client (usr_id_in, cli_seq_in),
FOREIGN KEY (usr_id_in, pct_id_in) REFERENCES inep.inep_package (usr_id_in, pct_id_in)
)
	TABLESPACE tb_cloud_data;

CREATE  INDEX XIF1inep_station ON inep.inep_station
(usr_id_in   ASC,cli_seq_in   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF2inep_station ON inep.inep_station
(pct_id_in   ASC,usr_id_in   ASC)
	TABLESPACE tb_cloud_index;



GRANT ALL ON inep.inep_station TO postgres;
GRANT ALL ON inep.inep_station TO r_system;
ALTER TABLE inep.inep_station OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON inep.inep_station TO r_system_user;
GRANT SELECT ON inep.inep_station TO r_system_report;





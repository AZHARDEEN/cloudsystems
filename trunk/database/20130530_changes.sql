
DROP TABLE IF EXISTS inep.inep_oral_distribution CASCADE ;



CREATE TABLE inep.inep_oral_distribution
(
	usr_id_in            INTEGER NOT NULL ,
	col_seq_in           INTEGER NOT NULL ,
	pct_id_in            INTEGER NOT NULL ,
	isc_id_ch            VARCHAR(32) NOT NULL ,
	ids_id_in            INTEGER NOT NULL ,
	iod_grade_in         INTEGER NULL ,
 PRIMARY KEY (usr_id_in,col_seq_in,pct_id_in,isc_id_ch) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (usr_id_in, col_seq_in, pct_id_in) REFERENCES inep.inep_revisor (usr_id_in, col_seq_in, pct_id_in),
FOREIGN KEY (pct_id_in, usr_id_in, isc_id_ch) REFERENCES inep.inep_oral_test (pct_id_in, usr_id_in, isc_id_ch),
FOREIGN KEY (ids_id_in) REFERENCES inep.inep_distribution_status (ids_id_in)
)
	TABLESPACE tb_cloud_data;




COMMENT ON COLUMN inep.inep_oral_distribution.usr_id_in IS 'Usar sequence: seq_usuario';



CREATE  INDEX XIF1inep_oral_distribution ON inep.inep_oral_distribution
(usr_id_in   ASC,col_seq_in   ASC,pct_id_in   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF2inep_oral_distribution ON inep.inep_oral_distribution
(pct_id_in   ASC,usr_id_in   ASC,isc_id_ch   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF3inep_oral_distribution ON inep.inep_oral_distribution
(ids_id_in   ASC);



GRANT ALL ON inep.inep_oral_distribution TO postgres;
GRANT ALL ON inep.inep_oral_distribution TO r_system;
ALTER TABLE inep.inep_oral_distribution OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON inep.inep_oral_distribution TO r_system_user;
GRANT SELECT ON inep.inep_oral_distribution TO r_system_report;






ALTER TABLE inep.inep_oral_test
   ADD COLUMN iot_agreement_grade_in integer;
   
ALTER TABLE inep.inep_oral_test ADD COLUMN iot_written_grade_nm numeric(6,3);

ALTER TABLE inep.inep_oral_test
   ADD COLUMN iot_agreement2_grade_nm numeric(6,3);

ALTER TABLE inep.inep_oral_test
   ADD COLUMN iot_grade_nm numeric(6,3);   


ALTER TABLE inep.inep_oral_test ADD COLUMN iot_status_ch character varying(64);

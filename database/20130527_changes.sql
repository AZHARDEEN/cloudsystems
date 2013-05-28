DROP TABLE IF EXISTS inep.inep_media CASCADE  PURGE;



CREATE TABLE inep.inep_media
(
	isc_id_ch            VARCHAR(32) NOT NULL ,
	pct_id_in            INTEGER NOT NULL ,
	usr_id_in            INTEGER NOT NULL ,
	med_id_in            INTEGER NOT NULL ,
	tsk_id_in            INTEGER NULL ,
CONSTRAINT  XPKinep_media PRIMARY KEY (pct_id_in,usr_id_in,isc_id_ch,med_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
CONSTRAINT FK_InepSubscription_InepMedia FOREIGN KEY (pct_id_in, usr_id_in, isc_id_ch) REFERENCES inep.inep_subscription (pct_id_in, usr_id_in, isc_id_ch),
CONSTRAINT FK_Media_InepMedia FOREIGN KEY (med_id_in) REFERENCES public.media (med_id_in)
)
	TABLESPACE tb_cloud_data;




COMMENT ON COLUMN inep.inep_media.usr_id_in IS 'Usar sequence: seq_usuario';




COMMENT ON COLUMN inep.inep_media.med_id_in IS 'Código sequencial da media. Este código é gerado por sequence (seq_media).';



CREATE UNIQUE INDEX XPKinep_media ON inep.inep_media
(pct_id_in   ASC,usr_id_in   ASC,isc_id_ch   ASC,med_id_in   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF1inep_media ON inep.inep_media
(pct_id_in   ASC,usr_id_in   ASC,isc_id_ch   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF2inep_media ON inep.inep_media
(med_id_in   ASC)
	TABLESPACE tb_cloud_index;



GRANT ALL ON inep.inep_media TO postgres;
GRANT ALL ON inep.inep_media TO r_system;
ALTER TABLE inep.inep_media OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON inep.inep_media TO r_system_user;
GRANT SELECT ON inep.inep_media TO r_system_report;






DROP TRIGGER IF EXISTS trgDeleteMediaFrominep_media ON inep.inep_media;
CREATE TRIGGER trgDeleteMediaFrominep_media after delete ON inep.inep_media FOR EACH ROW EXECUTE PROCEDURE deleteMedia();



DROP TABLE IF EXISTS inep.inep_oral_test CASCADE;



CREATE TABLE inep.inep_oral_test
(
	isc_id_ch            VARCHAR(32) NOT NULL ,
	pct_id_in            INTEGER NOT NULL ,
	usr_id_in            INTEGER NOT NULL ,
	iot_station_ch       VARCHAR(128) NULL ,
	iot_interviewer_grade_nm NUMERIC(6,3) NULL ,
	iot_observer_grade_nm NUMERIC(6,3) NULL ,
	iot_final_grade_nm   NUMERIC(6,3) NULL ,
	ids_id_in            INTEGER NOT NULL ,
 PRIMARY KEY (pct_id_in,usr_id_in,isc_id_ch) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (pct_id_in, usr_id_in, isc_id_ch) REFERENCES inep.inep_subscription (pct_id_in, usr_id_in, isc_id_ch),
FOREIGN KEY (ids_id_in) REFERENCES inep.inep_distribution_status (ids_id_in)
)
	TABLESPACE tb_cloud_data;




COMMENT ON COLUMN inep.inep_oral_test.usr_id_in IS 'Usar sequence: seq_usuario';



CREATE UNIQUE INDEX XPKinep_oral_test ON inep.inep_oral_test
(pct_id_in   ASC,usr_id_in   ASC,isc_id_ch   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF2inep_oral_test ON inep.inep_oral_test
(ids_id_in   ASC);



GRANT ALL ON inep.inep_oral_test TO postgres;
GRANT ALL ON inep.inep_oral_test TO r_system;
ALTER TABLE inep.inep_oral_test OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON inep.inep_oral_test TO r_system_user;
GRANT SELECT ON inep.inep_oral_test TO r_system_report;





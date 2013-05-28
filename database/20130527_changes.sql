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







DROP TABLE IF EXISTS inep.inep_distribution CASCADE ;



DROP TABLE IF EXISTS inep.inep_oral_distribution CASCADE ;



DROP TABLE IF EXISTS inep.inep_revisor CASCADE ;



CREATE TABLE inep.inep_revisor
(
	tsk_id_in            INTEGER NULL ,
	rvs_coordinator_bt   BOOLEAN DEFAULT  FALSE  NOT NULL ,
	usr_id_in            INTEGER NOT NULL ,
	col_seq_in           INTEGER NOT NULL ,
	pct_id_in            INTEGER NOT NULL ,
	irt_id_in            INTEGER NOT NULL ,
 PRIMARY KEY (usr_id_in,col_seq_in,pct_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (usr_id_in, pct_id_in, tsk_id_in) REFERENCES inep.inep_task (usr_id_in, pct_id_in, tsk_id_in) ON DELETE SET NULL,
FOREIGN KEY (usr_id_in, col_seq_in) REFERENCES public.collaborator (usr_id_in, col_seq_in),
FOREIGN KEY (irt_id_in) REFERENCES inep.revisor_type (irt_id_in)
);




COMMENT ON COLUMN inep.inep_revisor.usr_id_in IS 'Usar sequence: seq_usuario';



CREATE UNIQUE INDEX XPKinep_revisor ON inep.inep_revisor
(usr_id_in   ASC,col_seq_in   ASC,pct_id_in   ASC);



CREATE  INDEX XIF2inep_revisor ON inep.inep_revisor
(tsk_id_in   ASC,pct_id_in   ASC,usr_id_in   ASC);



CREATE  INDEX XIF3inep_revisor ON inep.inep_revisor
(usr_id_in   ASC,col_seq_in   ASC);



CREATE  INDEX XIF4inep_revisor ON inep.inep_revisor
(irt_id_in   ASC);



GRANT ALL ON inep.inep_revisor TO postgres;
GRANT ALL ON inep.inep_revisor TO r_system;
ALTER TABLE inep.inep_revisor OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON inep.inep_revisor TO r_system_user;
GRANT SELECT ON inep.inep_revisor TO r_system_report;





CREATE TABLE inep.inep_oral_distribution
(
	usr_id_in            INTEGER NOT NULL ,
	col_seq_in           INTEGER NOT NULL ,
	pct_id_in            INTEGER NOT NULL ,
	isc_id_ch            VARCHAR(32) NOT NULL ,
 PRIMARY KEY (usr_id_in,col_seq_in,pct_id_in,isc_id_ch) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (usr_id_in, col_seq_in, pct_id_in) REFERENCES inep.inep_revisor (usr_id_in, col_seq_in, pct_id_in),
FOREIGN KEY (pct_id_in, usr_id_in, isc_id_ch) REFERENCES inep.inep_oral_test (pct_id_in, usr_id_in, isc_id_ch)
)
	TABLESPACE tb_cloud_data;




COMMENT ON COLUMN inep.inep_oral_distribution.usr_id_in IS 'Usar sequence: seq_usuario';



CREATE UNIQUE INDEX XPKinep_oral_distribution ON inep.inep_oral_distribution
(usr_id_in   ASC,col_seq_in   ASC,pct_id_in   ASC,isc_id_ch   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF1inep_oral_distribution ON inep.inep_oral_distribution
(usr_id_in   ASC,col_seq_in   ASC,pct_id_in   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF2inep_oral_distribution ON inep.inep_oral_distribution
(pct_id_in   ASC,usr_id_in   ASC,isc_id_ch   ASC)
	TABLESPACE tb_cloud_index;



GRANT ALL ON inep.inep_oral_distribution TO postgres;
GRANT ALL ON inep.inep_oral_distribution TO r_system;
ALTER TABLE inep.inep_oral_distribution OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON inep.inep_oral_distribution TO r_system_user;
GRANT SELECT ON inep.inep_oral_distribution TO r_system_report;





CREATE TABLE inep.inep_distribution
(
	dis_grade_in         INTEGER NULL ,
	usr_id_in            INTEGER NOT NULL ,
	col_seq_in           INTEGER NOT NULL ,
	dis_obs_tx           TEXT NULL ,
	dis_insert_dt        TIMESTAMP DEFAULT  now()  NOT NULL ,
	tsk_id_in            INTEGER NOT NULL ,
	pct_id_in            INTEGER NOT NULL ,
	isc_id_ch            VARCHAR(32) NOT NULL ,
	ids_id_in            INTEGER NOT NULL ,
	dis_update_dt        TIMESTAMP NULL ,
	dis_priority_in      INTEGER DEFAULT  9  NULL ,
 PRIMARY KEY (usr_id_in,col_seq_in,pct_id_in,isc_id_ch,tsk_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (usr_id_in, col_seq_in, pct_id_in) REFERENCES inep.inep_revisor (usr_id_in, col_seq_in, pct_id_in),
FOREIGN KEY (pct_id_in, usr_id_in, isc_id_ch, tsk_id_in) REFERENCES inep.inep_test (pct_id_in, usr_id_in, isc_id_ch, tsk_id_in),
FOREIGN KEY (ids_id_in) REFERENCES inep.inep_distribution_status (ids_id_in)
);



COMMENT ON TABLE inep.inep_distribution IS 'Tabela para a distribuição das tarefas entre os corretores/coordenadores';




COMMENT ON COLUMN inep.inep_distribution.usr_id_in IS 'Usar sequence: seq_usuario';



CREATE UNIQUE INDEX XPKinep_distribution ON inep.inep_distribution
(usr_id_in   ASC,col_seq_in   ASC,pct_id_in   ASC,isc_id_ch   ASC,tsk_id_in   ASC);



CREATE  INDEX XIF3inep_distribution ON inep.inep_distribution
(usr_id_in   ASC,col_seq_in   ASC,pct_id_in   ASC);



CREATE  INDEX XIF4inep_distribution ON inep.inep_distribution
(pct_id_in   ASC,usr_id_in   ASC,isc_id_ch   ASC,tsk_id_in   ASC);



CREATE  INDEX XIF5inep_distribution ON inep.inep_distribution
(ids_id_in   ASC);



GRANT ALL ON inep.inep_distribution TO postgres;
GRANT ALL ON inep.inep_distribution TO r_system;
ALTER TABLE inep.inep_distribution OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON inep.inep_distribution TO r_system_user;
GRANT SELECT ON inep.inep_distribution TO r_system_report;





DROP FUNCTION if exists inep.InsertUpdateInepDistribution() cascade;

CREATE OR REPLACE FUNCTION inep.InsertUpdateInepDistribution() RETURNS trigger AS
$BODY$
begin
	IF(TG_OP = 'INSERT') THEN
		NEW.dis_insert_dt = now ();
		NEW.dis_update_dt := null;
	ELSEIF ( TG_OP = 'UPDATE' ) THEN
		IF ( new.dis_insert_dt <> old.dis_insert_dt ) then
			new.dis_insert_dt := old.dis_insert_dt;
		END IF;
		NEW.dis_update_dt = now ();
	END IF;
	
RETURN NEW;
end
$BODY$
  LANGUAGE plpgsql VOLATILE COST 100;
  
ALTER FUNCTION inep.InsertUpdateInepDistribution() OWNER TO dba_cloud;

DROP TRIGGER IF EXISTS InsertUpdateInepDistributionTrg ON inep.inep_distribution;

CREATE TRIGGER InsertUpdateInepDistributionTrg
  BEFORE INSERT OR UPDATE
  ON inep.inep_distribution
  FOR EACH ROW
  EXECUTE PROCEDURE inep.InsertUpdateInepDistribution();




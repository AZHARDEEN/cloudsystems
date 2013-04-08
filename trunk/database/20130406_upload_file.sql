
DROP TABLE IF EXISTS file_upload CASCADE ;



DROP TABLE IF EXISTS upload_status CASCADE ;



CREATE TABLE upload_status
(
	ups_id_in            INTEGER NOT NULL ,
	ups_description_ch   VARCHAR(64) NOT NULL ,
CONSTRAINT  XPKUpload_Status PRIMARY KEY (ups_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX 
);



GRANT SELECT, INSERT, UPDATE, DELETE ON public.upload_status TO r_system_user;





GRANT SELECT ON public.upload_status TO r_system_report;



insert into upload_status (ups_id_in, ups_description_ch ) values ( 1, 'Sucesso' );
insert into upload_status (ups_id_in, ups_description_ch ) values ( 2, 'Registro Duplicado' );
insert into upload_status (ups_id_in, ups_description_ch ) values ( 3, 'Falha ao processar o arquivo' );
insert into upload_status (ups_id_in, ups_description_ch ) values ( 99, 'Falha não Registrada' );



CREATE TABLE file_upload
(
	usr_id_in            INTEGER NOT NULL ,
	col_seq_in           INTEGER NOT NULL ,
	med_id_in            INTEGER NOT NULL ,
	ups_id_in            INTEGER NULL ,
	fup_records_in       INTEGER NULL ,
	fup_rejected_in      INTEGER NULL ,
CONSTRAINT  XPKFile_Upload PRIMARY KEY (usr_id_in,col_seq_in,med_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
CONSTRAINT R_279 FOREIGN KEY (usr_id_in, col_seq_in) REFERENCES public.collaborator (usr_id_in, col_seq_in),
CONSTRAINT FK_Media_FileUpload FOREIGN KEY (med_id_in) REFERENCES public.media (med_id_in),
CONSTRAINT FK_UploadStatus_FileUpload FOREIGN KEY (ups_id_in) REFERENCES upload_status (ups_id_in) ON DELETE SET NULL
);


DROP TABLE IF EXISTS rejected_records CASCADE ;



CREATE TABLE rejected_records
(
	usr_id_in            INTEGER NOT NULL ,
	col_seq_in           INTEGER NOT NULL ,
	med_id_in            INTEGER NOT NULL ,
	rrd_id_in            INTEGER NOT NULL ,
	rrd_value_ch         VARCHAR(1024) NOT NULL ,
	rrd_cause_ch         VARCHAR(256) NOT NULL ,
CONSTRAINT  XPKRejected_Records PRIMARY KEY (usr_id_in,col_seq_in,med_id_in,rrd_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
CONSTRAINT R_282 FOREIGN KEY (usr_id_in, col_seq_in, med_id_in) REFERENCES file_upload (usr_id_in, col_seq_in, med_id_in) ON DELETE CASCADE
);



GRANT SELECT, INSERT, UPDATE, DELETE ON public.Rejected_Records TO r_system_user;



COMMENT ON COLUMN file_upload.usr_id_in IS 'Usar sequence: seq_usuario';




COMMENT ON COLUMN file_upload.med_id_in IS 'Código sequencial da media. Este código é gerado por sequence (seq_media).';



GRANT SELECT, INSERT, UPDATE, DELETE ON public.file_upload TO r_system_user;





GRANT SELECT ON public.file_upload TO r_system_report;


CREATE  INDEX AK_MediaName ON public.media
(med_name_ch   ASC)
	TABLESPACE tb_cloud_index;


ALTER TABLE media
   ALTER COLUMN med_mime_ch TYPE character varying(128);


drop trigger if exists "trgFileUpload_Delete" on public.file_upload;
CREATE TRIGGER "trgFileUpload_Delete" AFTER DELETE
   ON file_upload FOR EACH ROW
   EXECUTE PROCEDURE public.deletemedia();   
   
   
   
   

DROP TABLE IF EXISTS client_entry CASCADE ;



CREATE TABLE client_entry
(
	etr_id_in            SERIAL NOT NULL ,
	usr_id_in            INTEGER NOT NULL ,
	cli_seq_in           INTEGER NOT NULL ,
	col_seq_in           INTEGER NULL ,
	med_id_in            INTEGER NULL ,
	etr_insert_dt        TIMESTAMP NULL ,
	etr_value_mn         NUMERIC(12,4) NOT NULL ,
	etr_cycle_in         INTEGER NOT NULL ,
	etr_line_number_in   INTEGER NULL ,
CONSTRAINT  XPKclient_entry PRIMARY KEY (etr_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
CONSTRAINT FK_Client_Entry FOREIGN KEY (usr_id_in, cli_seq_in) REFERENCES public.client (usr_id_in, cli_seq_in) ON DELETE CASCADE,
CONSTRAINT FK_Entry_FileUpload FOREIGN KEY (usr_id_in, col_seq_in, med_id_in) REFERENCES file_upload (usr_id_in, col_seq_in, med_id_in)
);

COMMENT ON COLUMN client_entry.usr_id_in IS 'Usar sequence: seq_usuario';
COMMENT ON COLUMN client_entry.med_id_in IS 'Código sequencial da media. Este código é gerado por sequence (seq_media).';

GRANT SELECT, INSERT, UPDATE, DELETE ON client_entry TO r_system_user;
GRANT SELECT ON client_entry TO r_system_report;
grant usage on client_entry_etr_id_in_seq to r_system_user;


CREATE  INDEX public.AKUserClient ON public.client
(usr_id_in   ASC,cli_id_in   ASC)
	TABLESPACE tb_cloud_index;


   
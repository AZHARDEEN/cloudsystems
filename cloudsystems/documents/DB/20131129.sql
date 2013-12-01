ALTER TABLE file_upload DROP CONSTRAINT r_279;
ALTER TABLE client_entry DROP CONSTRAINT fk_entry_fileupload;
ALTER TABLE rejected_records DROP CONSTRAINT r_282;
ALTER TABLE file_upload DROP CONSTRAINT xpkfile_upload;
ALTER TABLE file_upload ADD CONSTRAINT xpkfile_upload PRIMARY KEY(usr_id_in, med_id_in) USING INDEX TABLESPACE tb_cloud_index;
ALTER TABLE file_upload ALTER COLUMN col_seq_in DROP NOT NULL;
ALTER TABLE client_entry ADD CONSTRAINT fk_entry_fileupload FOREIGN KEY (usr_id_in, med_id_in) REFERENCES file_upload (usr_id_in, med_id_in) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE rejected_records ADD CONSTRAINT FK_REJREC_FILEUPLOAD FOREIGN KEY (usr_id_in, med_id_in) REFERENCES file_upload (usr_id_in, med_id_in) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE;
insert into upload_status values ( 4, 'Processado Com Sucesso' );
ALTER TABLE file_upload ADD COLUMN med_dup_id_in integer;
ALTER TABLE file_upload ADD CONSTRAINT fk_media_dup_fpload FOREIGN KEY (med_dup_id_in) REFERENCES media (med_id_in) ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX fki_media_dup_fpload ON file_upload(med_dup_id_in);

ALTER TABLE file_upload
   ADD COLUMN fup_error_ch character varying(512);

CREATE OR REPLACE FUNCTION inep.certificationlevel(grade numeric)
  RETURNS integer AS
$BODY$
DECLARE
  level integer;

  
BEGIN
	level := 0;
	if ( grade < 2 ) then 
		level := 0;
	elseif ( grade < 2.76 ) then 
		level := 1;
	elseif ( grade < 3.51 ) then 
		level := 2;
	elseif ( grade < 4.26 ) then 
		level := 3;
	else 
		level := 4;
	end if;

RETURN level;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION inep.certificationlevel(numeric)
  OWNER TO postgres;


ALTER TABLE inep.inep_oral_test DROP CONSTRAINT inep_oral_test_ids_id_in_fkey;

ALTER TABLE inep.inep_oral_test DROP COLUMN iot_written_grade_nm;








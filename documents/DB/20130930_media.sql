ALTER TABLE media
   ADD COLUMN med_url_ch character varying(1024);
COMMENT ON COLUMN media.med_url_ch
  IS 'Caminho (path) onde está localizado o arquivo. Este recurso é usado para quando não se deseja armazenar a media no banco. Um exemplo é um arquivo de audio de 200mb. Nesta situação é melhor deixá-lo em disco';


ALTER TABLE inep.inep_media
   ADD COLUMN imt_id_in integer;
COMMENT ON COLUMN inep.inep_media.imt_id_in
  IS 'Define tipo de media. Porém o contexto do tipo de média aqui no inep quer dizer audio, prova, e outros';



DROP TABLE IF EXISTS inep.inep_element CASCADE ;



CREATE TABLE inep.inep_element
(
	pct_id_in            INTEGER NOT NULL ,
	usr_id_in            INTEGER NOT NULL ,
	isc_id_ch            VARCHAR(32) NOT NULL ,
	iel_id_in            INTEGER NOT NULL ,
 PRIMARY KEY (pct_id_in,usr_id_in,isc_id_ch,iel_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (pct_id_in, usr_id_in, isc_id_ch) REFERENCES inep.inep_subscription (pct_id_in, usr_id_in, isc_id_ch)
);




COMMENT ON COLUMN inep.inep_element.usr_id_in IS 'Usar sequence: seq_usuario';



CREATE  INDEX AK_InepElementElementUsed ON inep.inep_element
(iel_id_in   ASC)
	TABLESPACE tb_cloud_index;



GRANT ALL ON inep.inep_element TO postgres;
GRANT ALL ON inep.inep_element TO r_system;
ALTER TABLE inep.inep_element OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON inep.inep_element TO r_system_user;
GRANT SELECT ON inep.inep_element TO r_system_report;





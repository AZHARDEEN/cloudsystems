ALTER TABLE client
   ADD COLUMN cli_internal_code_ch character varying(16);


CREATE UNIQUE INDEX "AK_ClientInternalCode"
   ON client USING btree (usr_id_in ASC NULLS LAST, cli_internal_code_ch ASC NULLS LAST)
  TABLESPACE tb_cloud_index;


DROP INDEX IF EXISTS "AK_UserDocumentCode";

CREATE INDEX "AK_UserDocumentCode"
  ON user_document
  USING btree
  (doc_id_in, udc_code_ch COLLATE pg_catalog."default")
TABLESPACE tb_cloud_index;


ALTER TABLE inep.inep_subscription
   ADD COLUMN cli_seq_in integer;

ALTER TABLE inep.inep_subscription
  ADD CONSTRAINT "FK_Inep_Station_Subscription" FOREIGN KEY (usr_id_in, pct_id_in, cli_seq_in) REFERENCES inep.inep_station (usr_id_in, pct_id_in, cli_seq_in)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE INDEX "FKI_Inep_Station_Subscription"
  ON inep.inep_subscription(usr_id_in, pct_id_in, cli_seq_in);
 
insert into document_type values ( 14, 'Passaporte', null, true );

insert into document_user_type values ( 1, 14 );

ALTER TABLE inep.inep_subscription 
    ADD COLUMN isc_candidate_in integer,
    ADD COLUMN isc_special_needs_ch character varying(128),
    ADD CONSTRAINT "FK_Inep_Subscription_Candidate" FOREIGN KEY (isc_candidate_in) REFERENCES users (usr_id_in)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
   
UPDATE COUNTRY_NAME SET CTR_NAME_CH = UPPER ( CTR_NAME_CH );

CREATE INDEX "AK_Country_Name"
   ON country_name (ctr_name_ch COLLATE pg_catalog."C" ASC NULLS LAST)
  TABLESPACE tb_cloud_index;
  
ALTER TABLE inep.inep_subscription
   ADD COLUMN isc_citizenship_ch character varying(64);
COMMENT ON COLUMN inep.inep_subscription.isc_citizenship_ch
  IS 'Este campo só existe por que na planilha de importação do INEP consta a nacionalidade por extenso - chinesa, por exemplo - sem vínculo algum com o pais. Neste caso, infelizmente, não há como realizar um vínculo automático com a tabela de países, sem fazer um ''DE-PARA'' primeiro.';
     
ALTER TABLE person
   ALTER COLUMN usr_middle_name_ch TYPE character varying(64);     
 
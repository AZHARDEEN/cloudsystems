ALTER TABLE collaborator DROP CONSTRAINT fk_type_collaborator;

update collaborator set clt_id_in = 1;


delete from collaborator_type;


--ALTER TABLE collaborator_type DROP COLUMN usr_id_in;


ALTER TABLE collaborator_type ADD COLUMN usr_id_in integer not null;


ALTER TABLE collaborator_type DROP CONSTRAINT xpkcollaborator_type;


ALTER TABLE collaborator_type
  ADD CONSTRAINT xpkcollaborator_type PRIMARY KEY(usr_id_in, clt_id_in)
  USING INDEX TABLESPACE tb_cloud_index;
  
insert into collaborator_type 
select distinct clt_id_in, 'Administrator Geral', true, usr_id_in from collaborator
  
ALTER TABLE collaborator
  ADD CONSTRAINT "FK_CollaboratorType_Collaborator" FOREIGN KEY (usr_id_in, clt_id_in) REFERENCES collaborator_type (usr_id_in, clt_id_in)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
   
   
CREATE INDEX "FKI_CollaboratorType_Collaborator"
  ON collaborator(usr_id_in, clt_id_in);


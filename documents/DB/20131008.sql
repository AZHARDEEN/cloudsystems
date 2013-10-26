CREATE TABLE inep.inep_subscription_status
(
	iss_id_in            INTEGER NOT NULL ,
	iss_descriptin_ch    VARCHAR(64) NOT NULL ,
 PRIMARY KEY (iss_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX 
)
	TABLESPACE tb_cloud_data;



GRANT ALL ON inep.inep_subscription_status TO postgres;
GRANT ALL ON inep.inep_subscription_status TO r_system;
ALTER TABLE inep.inep_subscription_status OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON inep.inep_subscription_status TO r_system_user;
GRANT SELECT ON inep.inep_subscription_status TO r_system_report;

insert into inep.inep_subscription_status values ( 1, 'Inscrito' );
insert into inep.inep_subscription_status values ( 2, 'Presente' );
insert into inep.inep_subscription_status values ( 3, 'Ausente' );


ALTER TABLE inep.inep_subscription 
	ADD COLUMN iss_id_in integer NOT NULL DEFAULT 1,
	ADD FOREIGN KEY (iss_id_in) REFERENCES inep.inep_subscription_status (iss_id_in);


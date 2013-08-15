
DROP TABLE IF EXISTS garage.client_car CASCADE ;



DROP TABLE IF EXISTS garage.car CASCADE ;



DROP TABLE IF EXISTS garage.fuel_type CASCADE ;



DROP TABLE IF EXISTS garage.car_model CASCADE ;



DROP TABLE IF EXISTS garage.car_manufacturer CASCADE ;



CREATE TABLE garage.car_manufacturer
(
	crm_id_in            INTEGER NOT NULL ,
	crm_description_ch   VARCHAR(64) NOT NULL ,
 PRIMARY KEY (crm_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX 
)
	TABLESPACE tb_cloud_data;



GRANT ALL ON garage.car_manufacturer TO postgres;
GRANT ALL ON garage.car_manufacturer TO r_system;
ALTER TABLE garage.car_manufacturer OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON garage.car_manufacturer TO r_system_user;
GRANT SELECT ON garage.car_manufacturer TO r_system_report;





INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 1,'Acura');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 2,'Agrale');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 3,'Alfa Romeo');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 4,'AM Gen');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 5,'Asia Motors');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 189,'ASTON MARTIN');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 6,'Audi');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 7,'BMW');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 8,'BRM');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 9,'Buggy');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 123,'Bugre');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 10,'Cadillac');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 11,'CBT Jipe');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 136,'CHANA');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 182,'CHANGAN');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 161,'CHERY');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 12,'Chrysler');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 13,'Citro&#235;n');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 14,'Cross Lander');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 15,'Daewoo');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 16,'Daihatsu');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 17,'Dodge');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 147,'EFFA');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 18,'Engesa');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 19,'Envemo');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 20,'Ferrari');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 21,'Fiat');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 149,'Fibravan');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 22,'Ford');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 190,'FOTON');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 170,'Fyber');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 23,'GM - Chevrolet');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 153,'GREAT WALL');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 24,'Gurgel');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 152,'HAFEI');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 25,'Honda');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 26,'Hyundai');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 27,'Isuzu');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 177,'JAC');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 28,'Jaguar');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 29,'Jeep');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 154,'JINBEI');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 30,'JPX');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 31,'Kia Motors');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 32,'Lada');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 171,'LAMBORGHINI');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 33,'Land Rover');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 34,'Lexus');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 168,'LIFAN');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 127,'LOBINI');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 35,'Lotus');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 140,'Mahindra');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 36,'Maserati');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 37,'Matra');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 38,'Mazda');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 39,'Mercedes-Benz');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 40,'Mercury');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 167,'MG');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 156,'MINI');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 41,'Mitsubishi');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 42,'Miura');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 43,'Nissan');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 44,'Peugeot');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 45,'Plymouth');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 46,'Pontiac');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 47,'Porsche');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 185,'RAM');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 186,'RELY');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 48,'Renault');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 49,'Rover');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 50,'Saab');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 51,'Saturn');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 52,'Seat');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 183,'SHINERAY');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 157,'smart');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 125,'SSANGYONG');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 54,'Subaru');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 55,'Suzuki');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 165,'TAC');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 56,'Toyota');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 57,'Troller');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 58,'Volvo');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 59,'VW - VolksWagen');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 163,'Wake');
INSERT INTO garage.car_manufacturer ( crm_id_in, crm_description_ch) values ( 120,'Walk');




CREATE TABLE garage.car_model
(
	crm_id_in            INTEGER NOT NULL ,
	cmd_id_in            INTEGER NOT NULL ,
	cmd_description_ch   VARCHAR(64) NOT NULL ,
 PRIMARY KEY (crm_id_in,cmd_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (crm_id_in) REFERENCES garage.car_manufacturer (crm_id_in)
)
	TABLESPACE tb_cloud_data;



CREATE  INDEX XIF1Car_Model ON garage.car_model
(crm_id_in   ASC)
	TABLESPACE tb_cloud_index;



GRANT ALL ON garage.car_model TO postgres;
GRANT ALL ON garage.car_model TO r_system;
ALTER TABLE garage.car_model OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON garage.car_model TO r_system_user;
GRANT SELECT ON garage.car_model TO r_system_report;





CREATE TABLE garage.fuel_type
(
	ftp_id_in            INTEGER NOT NULL ,
	ftp_description_ch    VARCHAR(64) NOT NULL ,
 PRIMARY KEY (ftp_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX 
)
	TABLESPACE tb_cloud_data;



GRANT ALL ON garage.fuel_type TO postgres;
GRANT ALL ON garage.fuel_type TO r_system;
ALTER TABLE garage.fuel_type OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON garage.fuel_type TO r_system_user;
GRANT SELECT ON garage.fuel_type TO r_system_report;





INSERT INTO garage.fuel_type ( ftp_id_in, ftp_description_ch) values ( 1, 'Gasolina' );
INSERT INTO garage.fuel_type ( ftp_id_in, ftp_description_ch) values ( 2, 'Alcool' );
INSERT INTO garage.fuel_type ( ftp_id_in, ftp_description_ch) values ( 3, 'Flex' );




CREATE TABLE garage.car
(
	car_id_in            INTEGER NOT NULL ,
	ftp_id_in            INTEGER NOT NULL ,
	car_plate_ch         VARCHAR(12) NOT NULL ,
	crm_id_in            INTEGER NULL ,
	cmd_id_in            INTEGER NULL ,
	car_year_in          INTEGER NOT NULL ,
	car_model_in         INTEGER NOT NULL ,
 PRIMARY KEY (car_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (crm_id_in, cmd_id_in) REFERENCES garage.car_model (crm_id_in, cmd_id_in) ON DELETE SET NULL,
FOREIGN KEY (ftp_id_in) REFERENCES garage.fuel_type (ftp_id_in)
)
	TABLESPACE tb_cloud_data;



COMMENT ON TABLE garage.car IS 'Tabela de Carros';




COMMENT ON COLUMN garage.car.car_year_in IS 'Ano de Fabricação, confome documento.';



CREATE  INDEX XIF1Cars ON garage.car
(crm_id_in   ASC,cmd_id_in   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF2Cars ON garage.car
(ftp_id_in   ASC);



GRANT ALL ON garage.car TO postgres;
GRANT ALL ON garage.car TO r_system;
ALTER TABLE garage.car OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON garage.car TO r_system_user;
GRANT SELECT ON garage.car TO r_system_report;





CREATE TABLE garage.client_car
(
	usr_id_in            INTEGER NOT NULL ,
	cli_seq_in           INTEGER NOT NULL ,
	car_id_in            INTEGER NOT NULL ,
	ccr_insert_dt        TIMESTAMP NULL ,
 PRIMARY KEY (usr_id_in,cli_seq_in,car_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
FOREIGN KEY (usr_id_in, cli_seq_in) REFERENCES public.client (usr_id_in, cli_seq_in),
FOREIGN KEY (car_id_in) REFERENCES garage.car (car_id_in)
)
	TABLESPACE tb_cloud_data;




COMMENT ON COLUMN garage.client_car.usr_id_in IS 'Usar sequence: seq_usuario';



CREATE  INDEX XIF1Client_Cars ON garage.client_car
(usr_id_in   ASC,cli_seq_in   ASC)
	TABLESPACE tb_cloud_index;



CREATE  INDEX XIF2Client_Cars ON garage.client_car
(car_id_in   ASC)
	TABLESPACE tb_cloud_index;



GRANT ALL ON garage.client_car TO postgres;
GRANT ALL ON garage.client_car TO r_system;
ALTER TABLE garage.client_car OWNER TO r_system;
GRANT SELECT, INSERT, UPDATE, DELETE ON garage.client_car TO r_system_user;
GRANT SELECT ON garage.client_car TO r_system_report;





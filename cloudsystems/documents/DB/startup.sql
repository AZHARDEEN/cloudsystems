

CREATE TABLESPACE tb_cloud_data
  OWNER dba_cloud
  LOCATION 'D:\\conf\\cloud\\data';


CREATE TABLESPACE tb_cloud_index
  OWNER dba_cloud
  LOCATION 'E:\\postgres\\cloud\\index';




CREATE ROLE r_system
  NOSUPERUSER INHERIT CREATEDB NOCREATEROLE NOREPLICATION;

  CREATE ROLE r_system_report
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

  CREATE ROLE r_system_user
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

  CREATE ROLE cloud LOGIN
  ENCRYPTED PASSWORD 'md5313e20fe4ca8bf6751ffd3c5b963a9ad'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
GRANT r_system_user TO cloud;

CREATE ROLE dba_cloud LOGIN
  ENCRYPTED PASSWORD 'md5ce2bb513595f406d2462887b50619a57'
  NOSUPERUSER INHERIT CREATEDB NOCREATEROLE NOREPLICATION;
GRANT r_system TO dba_cloud;

CREATE ROLE jreport LOGIN
  ENCRYPTED PASSWORD 'md586efe798840adcd728556c9db5e73e08'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
GRANT r_system_report TO jreport;



drop view if exists inep.v_inep_station;

CREATE OR REPLACE VIEW inep.v_inep_station AS
select 
	st.usr_id_in,
	st.pct_id_in,
	st.cli_seq_in,
	e.pct_code_ch,
	c.cli_id_in,
	c.cli_from_dt,
	c.cli_to_dt,
	c.cli_internal_code_ch,
	u.usr_name_ch,
	u.ust_id_in,
	u.usr_nick_name_ch,
	u.usr_insert_dt,
	u.usr_update_dt,
	u.usr_observation_tx,
	u.usr_birth_dt
from 
	inep.inep_station st inner join client c on (st.usr_id_in = c.usr_id_in and st.cli_seq_in = c.cli_seq_in )
	inner join inep.inep_package e on ( st.usr_id_in = e.usr_id_in and st.pct_id_in = e.pct_id_in )
	inner join company cp on ( c.cli_id_in = cp.usr_id_in ) 
	inner join users u on ( cp.usr_id_in = u.usr_id_in );
ALTER TABLE inep.v_inep_station
  OWNER TO r_system;
GRANT SELECT, UPDATE, INSERT ON TABLE inep.v_inep_station TO GROUP r_system_user;
GRANT SELECT ON TABLE inep.v_inep_station TO GROUP r_system_report;

GRANT ALL ON TABLE client TO r_system;
GRANT SELECT ON TABLE client TO r_system_report;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE client TO r_system_user;

GRANT ALL ON TABLE company TO r_system;
GRANT SELECT ON TABLE company TO r_system_report;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE company TO r_system_user;

GRANT ALL ON TABLE users TO r_system;
GRANT SELECT ON TABLE users TO r_system_report;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE users TO r_system_user;

GRANT ALL ON TABLE inep.inep_package TO r_system;
GRANT SELECT ON TABLE inep.inep_package TO r_system_report;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE inep.inep_package TO r_system_user;


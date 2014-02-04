﻿select 
	coalesce ( iot_station_ch, usr_name_ch ) as posto,
	count(*) total,
	count ( case when ( inep.certificationlevel ( iot_final_grade_nm ) <> inep.certificationlevel ( iot_agreement_grade_in ) ) or ( inep.certificationlevel ( iot_final_grade_nm ) <> inep.certificationlevel ( iot_agreement2_grade_nm ) )
	then 1 else null end ) as notas_alteradas
from 
	inep.inep_oral_test o left outer join inep.inep_subscription s on ( o.usr_id_in = s.usr_id_in and o.pct_id_in = s.pct_id_in and o.isc_id_ch = s.isc_id_ch )
	left outer join client c on ( s.usr_id_in = c.usr_id_in and s.cli_seq_in = c.cli_seq_in )
	left outer join users u on ( c.cli_id_in = u.usr_id_in )
where 
	s.usr_id_in = 13623 and s.pct_id_in = 2
and 	abs ( iot_interviewer_grade_nm - iot_observer_grade_nm ) >= 1.5	
group by 
	coalesce ( iot_station_ch, usr_name_ch )
order by 
	coalesce ( iot_station_ch, usr_name_ch )
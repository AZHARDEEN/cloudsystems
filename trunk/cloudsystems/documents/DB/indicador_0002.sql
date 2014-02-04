select posto, 
	count(nivel_0 ) as total_nivel_0,
	count(nivel_1 ) as total_nivel_1,
	count(nivel_2 ) as total_nivel_2,
	count(nivel_3 ) as total_nivel_3,
	count(nivel_4 ) as total_nivel_4
from ( 
	select posto, 
		case when nivel_certificacao = 0 then 1 else null end nivel_0,
		case when nivel_certificacao = 1 then 1 else null end nivel_1,
		case when nivel_certificacao = 2 then 1 else null end nivel_2,
		case when nivel_certificacao = 3 then 1 else null end nivel_3,
		case when nivel_certificacao = 4 then 1 else null end nivel_4,
		case when nivel_certificacao = 5 then 1 else null end nivel_5
	from (
		select 
			coalesce ( iot_station_ch, usr_name_ch ) as posto,
			inep.certificationlevel ( least( isc_written_grade_nm, isc_oral_grade_nm ) ) nivel_certificacao
		from 
			inep.inep_oral_test o left outer join inep.inep_subscription s on ( o.usr_id_in = s.usr_id_in and o.pct_id_in = s.pct_id_in and o.isc_id_ch = s.isc_id_ch )
			left outer join client c on ( s.usr_id_in = c.usr_id_in and s.cli_seq_in = c.cli_seq_in )
			left outer join users u on ( c.cli_id_in = u.usr_id_in )
		where 
			s.usr_id_in = 13623 and s.pct_id_in = 1
	) as t1
) as t2
group by posto
order by posto
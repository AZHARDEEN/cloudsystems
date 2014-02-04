select 
	posto,
	max(case when row_num = 1 then indicador end) as "1st-most-occurance",
	max(case when row_num = 2 then indicador end) as "2st-most-occurance",
	max(case when row_num = 3 then indicador end) as "3rd-most-occurance"
from 
(
	select 
		usr_name_ch as posto,
		iel_id_in as indicador,
		row_number() over ( partition by usr_name_ch order by count(*) desc ) as row_num
	from 
		inep.inep_element o inner join inep.inep_subscription s on ( o.usr_id_in = s.usr_id_in and o.pct_id_in = s.pct_id_in and o.isc_id_ch = s.isc_id_ch )
		inner join client c on ( s.usr_id_in = c.usr_id_in and s.cli_seq_in = c.cli_seq_in )
		inner join users u on ( c.cli_id_in = u.usr_id_in )
	where 
		s.usr_id_in = 13623 and s.pct_id_in = 2
	group by 
		usr_name_ch, iel_id_in
) as t1
group by posto
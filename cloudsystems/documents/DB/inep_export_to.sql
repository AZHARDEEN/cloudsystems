select 
	'201302' ID_EXAME,
	c.cli_internal_code_ch ID_POSTO_APLICADOR,
	s.isc_id_ch ID_INSCRICAO,
	(select iot_grade_in from inep.inep_observer_grade g where g.usr_id_in = s.usr_id_in and g.pct_id_in = s.pct_id_in and g.isc_id_ch = s.isc_id_ch and iog_id_in = 1) NT_COMPREENSAO,
	(select iot_grade_in from inep.inep_observer_grade g where g.usr_id_in = s.usr_id_in and g.pct_id_in = s.pct_id_in and g.isc_id_ch = s.isc_id_ch and iog_id_in = 2) NT_COMPETENCIA,
	(select iot_grade_in from inep.inep_observer_grade g where g.usr_id_in = s.usr_id_in and g.pct_id_in = s.pct_id_in and g.isc_id_ch = s.isc_id_ch and iog_id_in = 3) NT_FLUENCIA,
	(select iot_grade_in from inep.inep_observer_grade g where g.usr_id_in = s.usr_id_in and g.pct_id_in = s.pct_id_in and g.isc_id_ch = s.isc_id_ch and iog_id_in = 4) NT_AD_LEXICAL,
	(select iot_grade_in from inep.inep_observer_grade g where g.usr_id_in = s.usr_id_in and g.pct_id_in = s.pct_id_in and g.isc_id_ch = s.isc_id_ch and iog_id_in = 5) NT_AD_GRAMATICAL,
	(select iot_grade_in from inep.inep_observer_grade g where g.usr_id_in = s.usr_id_in and g.pct_id_in = s.pct_id_in and g.isc_id_ch = s.isc_id_ch and iog_id_in = 6) NT_PRONUNCIA,
	round ( o.iot_observer_grade_nm, 2 ) NT_OBSERVADOR,
	round ( o.iot_interviewer_grade_nm, 2 ) NT_ENTREVISTADOR,
	round ( abs( o.iot_observer_grade_nm - o.iot_interviewer_grade_nm ), 2 ) DISCREPANCIA,
	round ( o.iot_final_grade_nm, 2 ) NT_PROVA_ORAL
from 
	inep.inep_subscription s inner join client c on ( s.usr_id_in = c.usr_id_in and s.cli_seq_in = c.cli_seq_in )
	inner join users p on ( c.cli_id_in = p.usr_id_in )
	left outer join inep.inep_oral_test o on ( o.usr_id_in = s.usr_id_in and o.pct_id_in = s.pct_id_in and o.isc_id_ch = s.isc_id_ch )
where 
	s.usr_id_in = 13623
and 	s.pct_id_in = 2
order by c.cli_internal_code_ch, s.isc_id_ch




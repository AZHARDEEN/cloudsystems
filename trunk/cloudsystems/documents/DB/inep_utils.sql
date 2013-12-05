select * from inep.inep_oral_test where isc_id_ch in ( 
select isc_id_ch from inep.inep_subscription where isc_id_ch in ( 
select distinct isc_id_ch from ( 
select 
	isc_id_ch, count(*) 
from 
	inep.inep_test 
where 
	usr_id_in = 13623 and pct_id_in = 2 and itt_grade_nm is not null 
group by 
	isc_id_ch
having count(*) = 4 ) as t1 )
and usr_id_in = 13623 and pct_id_in = 2
and isc_written_grade_nm > isc_oral_grade_nm
and inep.certificationlevel( isc_written_grade_nm ) <>  inep.certificationlevel ( isc_oral_grade_nm ) 
) and usr_id_in = 13623 and pct_id_in = 2
and ids_id_in < 12



update inep.inep_oral_test 
set ids_id_in = 12
where isc_id_ch in ( 
select DISTINCT T.ISC_ID_CH from inep.inep_oral_test t inner join inep.inep_subscription s on ( t.usr_id_in = s.usr_id_in and t.pct_id_in = s.pct_id_in and t.isc_id_ch = s.isc_id_ch ) where t.isc_id_ch in ( 
select isc_id_ch from inep.inep_subscription where isc_id_ch in ( 
select distinct isc_id_ch from ( 
select 
	isc_id_ch, count(*) 
from 
	inep.inep_test 
where 
	usr_id_in = 13623 and pct_id_in = 2 and itt_grade_nm is not null 
group by 
	isc_id_ch
having count(*) = 4 ) as t1 )
and usr_id_in = 13623 and pct_id_in = 2
and isc_written_grade_nm > isc_oral_grade_nm
and inep.certificationlevel( isc_written_grade_nm ) <>  inep.certificationlevel ( isc_oral_grade_nm ) 
) and t.usr_id_in = 13623 and t.pct_id_in = 2
and t.isc_id_ch not in ( '201301004583', '201301004624', '201301005117' )
and t.ids_id_in < 12
)





select * from inep.inep_oral_test t inner join inep.inep_subscription s on ( t.usr_id_in = s.usr_id_in and t.pct_id_in = s.pct_id_in and t.isc_id_ch = s.isc_id_ch ) where t.isc_id_ch in ( 
select isc_id_ch from inep.inep_subscription where isc_id_ch in ( 
select distinct isc_id_ch from ( 
select 
	isc_id_ch, count(*) 
from 
	inep.inep_test 
where 
	usr_id_in = 13623 and pct_id_in = 2 and itt_grade_nm is not null 
group by 
	isc_id_ch
having count(*) = 4 ) as t1 )
and usr_id_in = 13623 and pct_id_in = 2
and isc_written_grade_nm > isc_oral_grade_nm
and inep.certificationlevel( isc_written_grade_nm ) <>  inep.certificationlevel ( isc_oral_grade_nm ) 
) and t.usr_id_in = 13623 and t.pct_id_in = 2
and t.ids_id_in < 12
and t.isc_id_ch not in ( '201301004583', '201301004624', '201301005117' )
order by t.isc_id_ch


select count(*) from inep.inep_oral_distribution where usr_id_in = 13623 and pct_id_in = 2


select * from inep.inep_oral_test where isc_id_ch like '%6568'

select * from inep.inep_oral_distribution where isc_id_ch like '%6568' 




delete from inep.inep_oral_distribution where (usr_id_in, pct_id_in, isc_id_ch, col_seq_in) in (
SELECT d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.col_seq_in 
FROM INEP.INEP_ORAL_DISTRIBUTION D inner join inep.inep_oral_test t on ( d.usr_id_in = t.usr_id_in and d.pct_id_in = t.pct_id_in and d.isc_id_ch = t.isc_id_ch )
WHERE D.USR_ID_IN = 13623 AND D.PCT_ID_IN = 2
AND D.COL_SEQ_IN = 72
and d.ids_id_in = 1
and t.ids_id_in in ( 4, 14 )
)







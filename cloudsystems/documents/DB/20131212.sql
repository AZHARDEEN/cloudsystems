DROP VIEW inep.subscriptiongradeview;

DROP FUNCTION inep.nota_final_tarefa(integer, integer, text, integer);

CREATE OR REPLACE FUNCTION inep.nota_final_tarefa(companyid integer, eventid integer, subscription text, taskid integer)
  RETURNS numeric AS
$BODY$
DECLARE
	revisor numeric;
	corretor1 numeric;
	corretor2 numeric;
	nota numeric;
BEGIN
	select dis_grade_in into nota
	from inep.inep_distribution, inep.inep_revisor 
	where 
			inep_distribution.usr_id_in = companyId 
		and inep_distribution.pct_id_in = eventId
		and inep_distribution.isc_id_ch = subscription 
		and inep_distribution.tsk_id_in = taskId
		
		and inep_distribution.usr_id_in = inep_revisor.usr_id_in 
		and inep_distribution.pct_id_in = inep_revisor.pct_id_in
		and inep_distribution.tsk_id_in = inep_revisor.tsk_id_in
		and inep_distribution.col_seq_in = inep_revisor.col_seq_in
		and coalesce ( inep_distribution.dis_golden_bt, false ) = false 
		and inep_revisor.rvs_coordinator_bt = true
		and inep_distribution.ids_id_in = 4 limit 1 offset 0;
	raise notice 'Nota Coordenador %. Parametros: %-%-%-%', nota, companyid , eventid , subscription , taskid ;
	if ( nota is null ) then
		select dis_grade_in into corretor1
		from inep.inep_distribution 
		where inep_distribution.usr_id_in = companyId 
			and inep_distribution.pct_id_in = eventId
			and inep_distribution.isc_id_ch = subscription 
			and inep_distribution.tsk_id_in = taskId
			and inep_distribution.ids_id_in = 2 limit 1 offset 0;
		select dis_grade_in into corretor2
		from inep.inep_distribution 
		where inep_distribution.usr_id_in = companyId 
			and inep_distribution.pct_id_in = eventId
			and inep_distribution.isc_id_ch = subscription 
			and inep_distribution.tsk_id_in = taskId
			and coalesce ( inep_distribution.dis_golden_bt, false ) = false 
			and inep_distribution.ids_id_in = 2 limit 1 offset 1;
		nota := ( ( corretor1 + corretor2 ) / 2 );
	end if;
	return nota;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION inep.nota_final_tarefa(integer, integer, text, integer)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION inep.nota_final_tarefa(integer, integer, text, integer) TO postgres;
GRANT EXECUTE ON FUNCTION inep.nota_final_tarefa(integer, integer, text, integer) TO public;
GRANT EXECUTE ON FUNCTION inep.nota_final_tarefa(integer, integer, text, integer) TO dba_cloud WITH GRANT OPTION;
GRANT EXECUTE ON FUNCTION inep.nota_final_tarefa(integer, integer, text, integer) TO jreport;
GRANT EXECUTE ON FUNCTION inep.nota_final_tarefa(integer, integer, text, integer) TO cloud;





CREATE OR REPLACE VIEW inep.subscriptiongradeview AS 
 SELECT DISTINCT s.usr_id_in, s.pct_id_in, s.isc_id_ch, 
    inep.nota_final_tarefa(s.usr_id_in, s.pct_id_in, s.isc_id_ch::text, 1) AS nf1, 
    inep.nota_final_tarefa(s.usr_id_in, s.pct_id_in, s.isc_id_ch::text, 2) AS nf2, 
    inep.nota_final_tarefa(s.usr_id_in, s.pct_id_in, s.isc_id_ch::text, 3) AS nf3, 
    inep.nota_final_tarefa(s.usr_id_in, s.pct_id_in, s.isc_id_ch::text, 4) AS nf4
   FROM inep.inep_subscription s
  ORDER BY s.usr_id_in, s.pct_id_in, s.isc_id_ch;

ALTER TABLE inep.subscriptiongradeview
  OWNER TO postgres;
GRANT ALL ON TABLE inep.subscriptiongradeview TO postgres;
GRANT ALL ON TABLE inep.subscriptiongradeview TO dba_cloud;
GRANT SELECT ON TABLE inep.subscriptiongradeview TO cloud;
GRANT SELECT ON TABLE inep.subscriptiongradeview TO jreport;

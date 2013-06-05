package br.com.mcampos.web.inep.controller.report;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import br.com.mcampos.web.core.report.ReportItem;

public class InepReportController extends BaseReportController
{
	private static final long serialVersionUID = -8511479189709820364L;

	@Override
	protected void setReports( )
	{
		List<ReportItem> list = new ArrayList<ReportItem>( );
		ReportItem item;

		if ( getRevisor( ) == null ) {
			/*Relatorio 1*/
			item = new ReportItem( "Extrato de Correção - Corretor" );
			item.setReportUrl( "/reports/inep/revisor_1" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 3*/
			item = new ReportItem( "Extrato de Correção Geral" );
			item.setReportUrl( "/reports/inep/revisor_3" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 6*/
			item = new ReportItem( "Notas Finais (Escrita + Oral)" );
			item.setReportUrl( "/reports/inep/inep_6" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 7*/
			item = new ReportItem( "Concentração de Notas por Corretor" );
			item.setReportUrl( "/reports/inep/inep_7" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 7*/
			item = new ReportItem( "Concentração de Notas por Corretor - Percentual" );
			item.setReportUrl( "/reports/inep/inep_7_1" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 8*/
			item = new ReportItem( "Media de Notas por Tarefa/Corretor" );
			item.setReportUrl( "/reports/inep/inep_8" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 9*/
			item = new ReportItem( "Discrepâncias por Corretor - Notas Individuais" );
			item.setReportUrl( "/reports/inep/inep_9_1" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 9*/
			item = new ReportItem( "Discrepâncias por Corretor - Comparação com o Coordenador" );
			item.setReportUrl( "/reports/inep/inep_9" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 4*/
			item = new ReportItem( "Situação da Correção - Corretores" );
			item.setReportUrl( "/reports/inep/inep_1" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 5*/
			item = new ReportItem( "Situação da Correção - Coordenadores" );
			item.setReportUrl( "/reports/inep/inep_1_2" );
			item.setParams( configReportParams( ) );
			list.add( item );

			item = new ReportItem( "Tarefas Não Corrigidas" );
			item.setReportUrl( "/reports/inep/inep_4_1" );
			item.setParams( configReportParams( ) );
			list.add( item );

			item = new ReportItem( "Tarefas Corrigidas" );
			item.setReportUrl( "/reports/inep/inep_4" );
			item.setParams( configReportParams( ) );
			list.add( item );

		}
		else {

			if ( getRevisor( ).isCoordenador( ) ) {
				/*Relatorio 1*/
				item = new ReportItem( "Extrato de Correção - Corretor" );
				item.setReportUrl( "/reports/inep/revisor_1" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 3*/
				item = new ReportItem( "Extrato de Correção Geral" );
				item.setReportUrl( "/reports/inep/revisor_3" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 3*/
				item = new ReportItem( "Extrato de Correção da Parte Oral" );
				item.setReportUrl( "/reports/inep/revisor_3_1" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 6*/
				item = new ReportItem( "Notas Finais (Escrita + Oral)" );
				item.setReportUrl( "/reports/inep/inep_6" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 7*/
				item = new ReportItem( "Concentração de Notas por Corretor" );
				item.setReportUrl( "/reports/inep/inep_7_2" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 7*/
				item = new ReportItem( "Concentração de Notas por Corretor - Percentual" );
				item.setReportUrl( "/reports/inep/inep_7_3" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 4*/
				item = new ReportItem( "Situação da Correção - Corretores" );
				item.setReportUrl( "/reports/inep/inep_2" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 9*/
				item = new ReportItem( "Discrepâncias por Corretor" );
				item.setReportUrl( "/reports/inep/inep_9_2" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 5*/
				item = new ReportItem( "Extrato de Correção" );
				item.setReportUrl( "/reports/inep/inep_1_1" );
				item.setParams( configReportParams( ) );
				list.add( item );

				item = new ReportItem( "Tarefas Não Corrigidas" );
				item.setReportUrl( "/reports/inep/inep_4_1" );
				item.setParams( configReportParams( ) );
				list.add( item );

				item = new ReportItem( "Tarefas Corrigidas" );
				item.setReportUrl( "/reports/inep/inep_4" );
				item.setParams( configReportParams( ) );
				list.add( item );
			}
			else {
				/*Relatorio 4*/
				item = new ReportItem( "Meu Extrato de Correção" );
				item.setReportUrl( "/reports/inep/inep_3" );
				item.setParams( configReportParams( ) );
				list.add( item );

				item = new ReportItem( "Minhas Tarefas Não Corrigidas" );
				item.setReportUrl( "/reports/inep/inep_4_1" );
				item.setParams( configReportParams( ) );
				list.add( item );

				item = new ReportItem( "Minhas Tarefas Corrigidas" );
				item.setReportUrl( "/reports/inep/inep_4" );
				item.setParams( configReportParams( ) );
				list.add( item );
			}
		}

		if ( getListbox( ) != null ) {
			getListbox( ).setModel( new ListModelList<ReportItem>( list ) );
		}
	}

}

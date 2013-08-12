package br.com.mcampos.web.inep.controller.report;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import br.com.mcampos.web.core.report.ReportItem;

public class InepOralReportController extends BaseReportController
{
	private static final long serialVersionUID = 8974239030243129532L;

	@Override
	protected void setReports( )
	{
		List<ReportItem> list = new ArrayList<ReportItem>( );
		ReportItem item;

		if ( getRevisor( ) == null ) {

			/*Relatorio 3*/
			item = new ReportItem( "Extrato de Correção da Parte Oral" );
			item.setReportUrl( "/reports/inep/revisor_3_1" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 3*/
			item = new ReportItem( "Discrepâncias por Posto Aplicador" );
			item.setReportUrl( "/reports/inep/revisor_3_2" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 3*/
			item = new ReportItem( "Audios por Corretor" );
			item.setReportUrl( "/reports/inep/revisor_3_3" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 5*/
			item = new ReportItem( "Notas da Parte Oral" );
			item.setReportUrl( "/reports/inep/inep_5" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 6*/
			item = new ReportItem( "Notas Finais (Escrita + Oral)" );
			item.setReportUrl( "/reports/inep/inep_6" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 10*/
			item = new ReportItem( "Discrepância Oral/Escrita" );
			item.setReportUrl( "/reports/inep/inep_10" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 10*/
			item = new ReportItem( "Prova Oral/Escrita - Certificados" );
			item.setReportUrl( "/reports/inep/inep_10_1" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 10*/
			item = new ReportItem( "Prova Oral/Escrita - Não Certificados" );
			item.setReportUrl( "/reports/inep/inep_10_2" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 10*/
			item = new ReportItem( "Prova Oral/Escrita - Reavaliadas" );
			item.setReportUrl( "/reports/inep/inep_10_3" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 10*/
			item = new ReportItem( "Prova Oral/Escrita - Pendentes Reavaliação" );
			item.setReportUrl( "/reports/inep/inep_10_4" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 11*/
			item = new ReportItem( "Reavaliações da Prova Oral por Posto Aplicador" );
			item.setReportUrl( "/reports/inep/inep_11" );
			item.setParams( configReportParams( ) );
			list.add( item );

		}
		else {

			if ( getRevisor( ).isCoordenador( ) ) {

				/*Relatorio 3*/
				item = new ReportItem( "Extrato de Correção da Parte Oral" );
				item.setReportUrl( "/reports/inep/revisor_3_1" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 3*/
				item = new ReportItem( "Discrepâncias por Posto Aplicador" );
				item.setReportUrl( "/reports/inep/revisor_3_2" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 3*/
				item = new ReportItem( "Audios por Corretor" );
				item.setReportUrl( "/reports/inep/revisor_3_3" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 5*/
				item = new ReportItem( "Notas da Parte Oral" );
				item.setReportUrl( "/reports/inep/inep_5" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 6*/
				item = new ReportItem( "Notas Finais (Escrita + Oral)" );
				item.setReportUrl( "/reports/inep/inep_6" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 10*/
				item = new ReportItem( "Discrepância Oral/Escrita" );
				item.setReportUrl( "/reports/inep/inep_10" );
				item.setParams( configReportParams( ) );
				list.add( item );
			}
			else {
			}
		}

		if ( getListbox( ) != null ) {
			getListbox( ).setModel( new ListModelList<ReportItem>( list ) );
		}
	}
}

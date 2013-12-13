package br.com.mcampos.web.inep.controller.report;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import br.com.mcampos.web.core.report.ReportItem;

public class InepManagementReportController extends BaseReportController
{
	private static final long serialVersionUID = -7490425044342405528L;

	@Override
	protected void setReports( )
	{
		List<ReportItem> list = new ArrayList<ReportItem>( );
		ReportItem item;

		item = new ReportItem( "Listagem Geral da Situação dos Postos" );
		item.setReportUrl( "/reports/inep/management_001" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Listagem Geral de Inscrições do Posto" );
		item.setReportUrl( "/reports/inep/management_002" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Listagem Geral de Ausentes do Posto" );
		item.setReportUrl( "/reports/inep/management_003" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Listagem Geral de Inscrições Sem Nota e Não Ausentes" );
		item.setReportUrl( "/reports/inep/management_004" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Listagem Geral de Audios Carregados" );
		item.setReportUrl( "/reports/inep/management_006" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Listagem Geral de Inscrições Com Nota e Sem Audio" );
		item.setReportUrl( "/reports/inep/management_005" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Elementos Provocadores por Posto" );
		item.setReportUrl( "/reports/inep/management_007" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Gráfico de Uso dos Elementos Provocadores" );
		item.setReportUrl( "/reports/inep/management_008" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Certificação por Posto Aplicador" );
		item.setReportUrl( "/reports/inep/management_009" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		if ( this.getListbox( ) != null ) {
			this.getListbox( ).setModel( new ListModelList<ReportItem>( list ) );
		}
	}

}

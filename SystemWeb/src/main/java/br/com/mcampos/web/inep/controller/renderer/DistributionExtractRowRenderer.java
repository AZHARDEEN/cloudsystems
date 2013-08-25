package br.com.mcampos.web.inep.controller.renderer;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.dto.inep.TaskGrade;
import br.com.mcampos.sysutils.SysUtils;

public class DistributionExtractRowRenderer implements RowRenderer<InepAnaliticoCorrecao>
{

	@Override
	public void render( Row row, InepAnaliticoCorrecao data, int index ) throws Exception
	{
		if ( SysUtils.isEmpty( row.getChildren( ) ) == false )
		{
			row.getChildren( ).clear( );
		}
		new Label( data.getSubscritpion( ) ).setParent( row );
		Integer finalGrade = 0;
		for ( TaskGrade grade : data.getGrades( ) )
		{
			addGrade( row, grade != null ? grade.getFirstGrade( ) : null );
			addGrade( row, grade != null ? grade.getSecondGrade( ) : null );
			addGrade( row, grade != null ? grade.getThirdGrade( ) : null );
			Integer taskGrade = computeFinalTaskGrade( row, grade );
			if ( taskGrade.intValue( ) >= 0 && taskGrade.intValue( ) <= 5 ) {
				finalGrade += taskGrade;
			}
			addGrade( row, taskGrade );
		}
		addGrade( row, finalGrade.intValue( ) / 4 );
	}

	private void addGrade( Row row, Integer grade )
	{
		if ( grade == null ) {
			new Label( "" ).setParent( row );
		}
		else {
			switch ( grade )
			{
			case 6:
				new Label( "An" ).setParent( row );
				break;
			case 7:
				new Label( "Br" ).setParent( row );
				break;
			default:
				new Label( grade.toString( ) ).setParent( row );
				break;
			}
		}
	}

	private Integer computeFinalTaskGrade( Row row, TaskGrade grade )
	{
		if ( grade == null ) {
			return 0;
		}
		if ( grade.getThirdGrade( ) != null ) {
			return grade.getThirdGrade( );
		}
		else
		{
			Integer n1, n2;
			n1 = grade.getFirstGrade( );
			n2 = grade.getSecondGrade( );

			if ( n1 != null && n2 != null ) {
				return n1.intValue( ) >= n2.intValue( ) ? n1 : n2;
			}
			else if ( n2 != null ) {
				return n2;
			}
			else {
				return n1 == null ? 0 : n1;
			}
		}
	}
}

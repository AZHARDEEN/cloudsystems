package br.com.mcampos.web.inep.controller;

import br.com.mcampos.entity.inep.InepDistribution;

public class FirstGradeController extends CoordinatorGradeController
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void showGrade( int nIndex, InepDistribution grade )
	{
		if ( nIndex != 0 ) {
			return;
		}
		showGrade( grade );
		getMainWindow( ).setTitle( "Primeira Correção: [" + grade.getRevisor( ).getCollaborator( ).getPerson( ).getName( ) + "]" );
	}

}

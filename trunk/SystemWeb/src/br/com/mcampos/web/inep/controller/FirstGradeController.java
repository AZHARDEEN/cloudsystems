package br.com.mcampos.web.inep.controller;

import br.com.mcampos.ejb.inep.entity.InepDistribution;

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
	}

}

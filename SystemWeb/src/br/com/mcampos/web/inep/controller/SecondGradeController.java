package br.com.mcampos.web.inep.controller;

import br.com.mcampos.ejb.inep.entity.InepDistribution;

public class SecondGradeController extends CoordinatorGradeController
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void showGrade( int nIndex, InepDistribution grade )
	{
		if ( nIndex != 1 ) {
			return;
		}
		showGrade( grade );

	}

}

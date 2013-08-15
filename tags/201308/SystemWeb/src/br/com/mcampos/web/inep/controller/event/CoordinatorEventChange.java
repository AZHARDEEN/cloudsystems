package br.com.mcampos.web.inep.controller.event;

import java.util.List;

import org.zkoss.zk.ui.event.Event;

import br.com.mcampos.ejb.inep.entity.InepDistribution;

public class CoordinatorEventChange extends Event
{
	List<InepDistribution> grades;

	public CoordinatorEventChange( List<InepDistribution> grades )
	{
		super( "onChangeCoordinator" );
		setGrades( grades );
	}

	private static final long serialVersionUID = 4104730036451151942L;

	public List<InepDistribution> getGrades( )
	{
		return this.grades;
	}

	private void setGrades( List<InepDistribution> grades )
	{
		this.grades = grades;
	}

}

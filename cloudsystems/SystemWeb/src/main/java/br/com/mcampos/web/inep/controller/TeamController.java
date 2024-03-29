package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.renderer.inep.RevisorListRenderer;

public class TeamController extends BaseController<Window>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1481224347756300890L;

	@Wire( "#tabTasks" )
	Tabs tabBox;

	@Wire( "#panels" )
	Tabpanels panels;

	Listbox[ ] listboxes;

	private TeamSession session;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.createTabs( );
	}

	private void createTabs( )
	{
		List<InepTask> tasks = this.getSession( ).getTasks( this.getPrincipal( ) );
		Tabpanel newPanel;
		int nIndex = 0;

		this.listboxes = new Listbox[ tasks.size( ) ];
		for ( InepTask task : tasks )
		{
			this.tabBox.appendChild( new Tab( task.getDescription( ) ) );
			newPanel = new Tabpanel( );
			this.listboxes[ nIndex ] = new Listbox( );
			newPanel.appendChild( this.loadTeam( this.listboxes[ nIndex++ ], task ) );
			this.panels.appendChild( newPanel );
		}
	}

	private Listbox loadTeam( Listbox list, InepTask task )
	{
		List<InepRevisor> team = this.getSession( ).getTeam( task );
		ListModelList<InepRevisor> model = new ListModelList<InepRevisor>( team );
		list.setItemRenderer( new RevisorListRenderer( ) );
		list.setModel( model );

		return list;
	}

	private TeamSession getSession( )
	{
		if ( this.session == null ) {
			this.session = (TeamSession) this.getSession( TeamSession.class );
		}
		return this.session;
	}

	@Listen( "onClick = #btnDistribution" )
	public void onClickDistribution( )
	{
		Messagebox.show( "Os pacotes foram distribuidos/redistribuidos com sucesso" );
	}
}

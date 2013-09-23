package br.com.mcampos.controller.user.collaborator;


import br.com.mcampos.controller.commom.user.PersonController;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.facade.CollaboratorFacade;
import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Label;

public class AddCollaboratorController extends PersonController
{
    private CollaboratorFacade session;
    private Label labelCollaboratorTitle;
    private Integer companyId;


    public AddCollaboratorController()
    {
        super();
    }

    public AddCollaboratorController( char c )
    {
        super( c );
    }


    public CollaboratorFacade getSession()
    {
        if ( session == null )
            session = ( CollaboratorFacade )getRemoteSession( CollaboratorFacade.class );
        return session;
    }

    @Override
    protected Boolean persist() throws ApplicationException
    {
        if ( super.persist() == false )
            return false;
        getSession().add( getLoggedInUser(), companyId, getCurrentDTO() );
        return true;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelCollaboratorTitle );
    }

    @Override
    public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
    {
        companyId = ( Integer )getParameter( "company" );
        if ( SysUtils.isZero( companyId ) )
            return null;
        return super.doBeforeCompose( page, parent, compInfo );
    }
}

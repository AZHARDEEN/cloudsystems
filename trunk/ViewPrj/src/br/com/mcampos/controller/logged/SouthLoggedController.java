package br.com.mcampos.controller.logged;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.facade.CollaboratorFacade;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;


public class SouthLoggedController extends LoggedBaseController
{
    private Label labelCopyright;
    private Label labelVersion;
    private Label labelCurrentCompany;
    private Combobox companies;

    private CollaboratorFacade session;


    public SouthLoggedController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabels();
        List<CompanyDTO> list = getSession().getCompanies( getLoggedInUser() );
        if ( list.size() > 1 ) {
            companies.setVisible( true );
        }
        else {
            CompanyDTO dto = list.get( 0 );
            labelCurrentCompany.setValue( SysUtils.isEmpty( dto.getNickName() ) ? dto.getName() : dto.getNickName() );
        }
        labelVersion.setValue( desktop.getWebApp().getVersion() );
    }


    private void setLabels()
    {
        setLabel( labelCopyright );
    }

    public CollaboratorFacade getSession()
    {
        if ( session == null )
            session = ( CollaboratorFacade )getRemoteSession( CollaboratorFacade.class );
        return session;
    }
}

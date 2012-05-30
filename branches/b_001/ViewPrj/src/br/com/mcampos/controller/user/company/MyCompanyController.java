package br.com.mcampos.controller.user.company;


import br.com.mcampos.controller.commom.user.CompanyController;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.user.company.facade.MyCompanyFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.system.UploadMedia;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;


public class MyCompanyController extends CompanyController
{
    private MyCompanyFacade session;
    private Button cmdUploadLogo;
    private Label labelMyRecordTitle;

    public MyCompanyController( char c )
    {
        super( c );
    }

    public MyCompanyController()
    {
        super();
    }

    protected CompanyDTO searchByDocument( String document, Integer type ) throws Exception
    {
        return null;
    }

    private MyCompanyFacade getSession()
    {
        if ( session == null )
            session = ( MyCompanyFacade )getRemoteSession( MyCompanyFacade.class );
        return session;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        showInfo( getSession().get( getLoggedInUser() ) );
        setLabel( cmdUploadLogo );
        setLabel( labelMyRecordTitle );
        if ( cnpj != null )
            cnpj.setDisabled( true );
    }

    @Override
    protected Boolean persist() throws ApplicationException
    {
        if ( super.persist() == false )
            return false;
        CompanyDTO dto = getCurrentDTO();
        getSession().update( getLoggedInUser(), dto );
        return true;
    }

    public void onUpload$cmdUploadLogo( UploadEvent evt )
    {
        MediaDTO dto = null;
        try {
            dto = UploadMedia.getMedia( evt.getMedia() );
            getSession().setLogo( getLoggedInUser(), dto );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "UploadMedia" );
        }
    }
}

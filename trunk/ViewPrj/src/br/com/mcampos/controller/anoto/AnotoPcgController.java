package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.PgcListRendered;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import java.io.File;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;


public class AnotoPcgController extends LoggedBaseController
{
    private AnodeFacade session;
    protected Button cmdCreate;
    protected org.zkoss.zul.Image imgTest;
    protected Listbox listboxRecord;

    public AnotoPcgController()
    {
        super();
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        List<PGCDTO> medias = getSession().getAllPgc( getLoggedInUser() );

        listboxRecord.setItemRenderer( new PgcListRendered() );
        listboxRecord.setModel( new ListModelList( medias, true ) );
    }

    private File checkAndCreateDir( File directory ) throws Exception
    {
        if ( !directory.exists() ) {
            directory.mkdirs();
        }
        return directory;
    }

}

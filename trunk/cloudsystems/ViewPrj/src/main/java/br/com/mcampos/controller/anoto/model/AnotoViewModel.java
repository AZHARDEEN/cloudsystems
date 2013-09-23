package br.com.mcampos.controller.anoto.model;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.facade.AnodeFacade;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import org.zkoss.zul.AbstractTreeModel;


public class AnotoViewModel extends AbstractTreeModel
{
    private AnodeFacade session;
    private AuthenticationDTO currentUser;

    public AnotoViewModel( AnodeFacade session, AuthenticationDTO user, List<FormDTO> dto )
    {
        super( dto );
        this.session = session;
        this.currentUser = user;
    }

    public boolean isLeaf( Object node )
    {
        return getChildCount( node ) == 0;
    }

    public Object getChild( Object parent, int index )
    {
        if ( parent instanceof List )
            return ( ( List<Object> )parent ).get( index );
        else if ( parent instanceof FormDTO ) {
            return ( ( FormDTO )parent ).getPads().get( index );
        }
        else if ( parent instanceof PadDTO ) {
            return ( ( PadDTO )parent ).getPages().get( index );
        }
        else if ( parent instanceof AnotoPageDTO ) {
            return ( ( AnotoPageDTO )parent ).getPenPages().get( index );
        }
        else if ( parent instanceof AnotoPenPageDTO )
            return ( ( AnotoPenPageDTO )parent ).getPgcPenPages().get( index );
        else if ( parent instanceof PgcPenPageDTO )
            return ( ( PgcPenPageDTO )parent ).getBackgroundImages().get( index );
        else
            return null;
    }

    public int getChildCount( Object parent )
    {
        if ( parent == null )
            return 0;

        if ( parent instanceof List ) {
            return ( ( List )parent ).size();
        }
        else if ( parent instanceof FormDTO ) {
            return getPads( ( FormDTO )parent );
        }
        else if ( parent instanceof PadDTO ) {
            return getPages( ( PadDTO )parent );
        }
        else if ( parent instanceof AnotoPageDTO )
            return getPenPages( ( ( AnotoPageDTO )parent ) );
        else if ( parent instanceof AnotoPenPageDTO )
            return getPgcs( ( ( AnotoPenPageDTO )parent ) );
        else if ( parent instanceof PgcPenPageDTO )
            return getBackgroundImages( ( PgcPenPageDTO )parent );
        else {
            return 0;
        }
    }

    protected int getBackgroundImages( PgcPenPageDTO pgc )
    {
        if ( SysUtils.isEmpty( pgc.getBackgroundImages() ) ) {
            try {
                pgc.setBackgroundImages( getSession().getImages( pgc.getPenPage().getPage() ) );
            }
            catch ( ApplicationException e ) {
                e = null;
            }
        }
        return pgc.getBackgroundImages().size();
    }

    protected int getPads( FormDTO form )
    {
        if ( SysUtils.isEmpty( form.getPads() ) ) {
            try {
                form.setPads( getSession().getPads( form ) );
            }
            catch ( ApplicationException e ) {
                e = null;
            }
        }
        return form.getPads().size();
    }

    protected int getPages( PadDTO pad )
    {
        if ( SysUtils.isEmpty( pad.getPages() ) ) {
            try {
                pad.setPages( getSession().getPages( getCurrentUser(), pad ) );
            }
            catch ( ApplicationException e ) {
                e = null;
            }
        }
        return pad.getPages().size();
    }


    protected int getPenPages( AnotoPageDTO page )
    {
        if ( SysUtils.isEmpty( page.getPenPages() ) ) {
            try {
                page.setPenPages( getSession().getPenPages( getCurrentUser(), page ) );
            }
            catch ( ApplicationException e ) {
                e = null;
            }
        }
        return page.getPenPages().size();
    }


    protected int getPgcs( AnotoPenPageDTO penPage )
    {
        if ( SysUtils.isEmpty( penPage.getPgcPenPages() ) ) {
            try {
                penPage.setPgcPenPages( getSession().get( getCurrentUser(), penPage ) );
            }
            catch ( ApplicationException e ) {
                e = null;
            }
        }
        return penPage.getPgcPenPages().size();
    }


    protected AnodeFacade getSession()
    {
        return session;
    }

    protected AuthenticationDTO getCurrentUser()
    {
        return currentUser;
    }
}

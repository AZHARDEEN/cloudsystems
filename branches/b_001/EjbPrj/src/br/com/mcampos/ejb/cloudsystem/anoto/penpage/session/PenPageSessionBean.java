package br.com.mcampos.ejb.cloudsystem.anoto.penpage.session;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPagePK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.exception.ApplicationRuntimeException;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PenPageSession", mappedName = "CloudSystems-EjbPrj-PenPageSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PenPageSessionBean extends Crud<AnotoPenPagePK, AnotoPenPage> implements PenPageSessionLocal
{
    public AnotoPenPage add( AnotoPage page, AnotoPen pen ) throws ApplicationException
    {
        AnotoPenPage e = get( page, pen );
        if ( e != null )
            return e;
        e = get( pen, page.getPageAddress() );
        if ( e != null )
            throw new ApplicationRuntimeException( "Esta caneta já encontra-se associada a uma malha com o mesmo IP" );
        e = new AnotoPenPage();
        e.setPage( page );
        e.setPen( pen );
        e.setInsertDate( new Date() );
        e.setSequence( nextId( page ) );
        return add( e );
    }

    public void delete( AnotoPage page, AnotoPen pen ) throws ApplicationException
    {
        AnotoPenPage e = get( page, pen );
        if ( e == null )
            return;
        e.setToDate( new Date() );
    }

    public AnotoPenPage get( AnotoPage page, AnotoPen pen ) throws ApplicationException
    {
        AnotoPenPage e;

        e = ( AnotoPenPage )getSingleResult( AnotoPenPage.pagePenQueryName, page, pen );
        return e;
    }

    private Integer nextId( AnotoPage page ) throws ApplicationException
    {
        return nextIntegerId( AnotoPenPage.nextId, page );
    }


    public AnotoPenPage get( AnotoPen pen, String pageAddress ) throws ApplicationException
    {
        AnotoPenPage e;

        e = ( AnotoPenPage )getSingleResult( AnotoPenPage.penPageAddressQueryName, pen, pageAddress );
        return e;
    }

    public List<AnotoPenPage> get( AnotoForm form ) throws ApplicationException
    {
        return ( List<AnotoPenPage> )getResultList( AnotoPenPage.formPensQueryName, form );
    }
}

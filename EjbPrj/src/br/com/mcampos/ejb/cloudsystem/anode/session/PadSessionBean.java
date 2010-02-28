package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PadPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PadSession", mappedName = "CloudSystems-EjbPrj-PadSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PadSessionBean extends Crud<PadPK, Pad> implements PadSessionLocal
{
    public PadSessionBean()
    {
    }

    public List<AnotoPage> getPages( Pad pad ) throws ApplicationException
    {
        List<Object> parameter = new ArrayList<Object>( 1 );
        parameter.add( pad );
        List<AnotoPage> pageList = ( List<AnotoPage> )getResultList( AnotoPage.padPagesGetAllNamedQuery, parameter );
        return pageList;
    }

    public Pad get( PadPK key )
    {
        return get( Pad.class, key );
    }


}

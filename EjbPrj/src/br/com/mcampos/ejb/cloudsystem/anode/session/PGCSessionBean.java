package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.Pgc;
import br.com.mcampos.ejb.session.core.Crud;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PGCSession", mappedName = "CloudSystems-EjbPrj-PGCSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PGCSessionBean extends Crud<Integer, Pgc> implements PGCSessionLocal
{
    public PGCSessionBean()
    {
    }

    public void delete( Integer key )
    {
        delete( Pgc.class, key );
    }

    public Pgc get( Integer key )
    {
        return get( Pgc.class, key );
    }

    public List<Pgc> getAll()
    {
        return getAll( Pgc.findAllQueryName );
    }
}

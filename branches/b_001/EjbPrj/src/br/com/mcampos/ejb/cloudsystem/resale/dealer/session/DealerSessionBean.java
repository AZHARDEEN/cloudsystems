package br.com.mcampos.ejb.cloudsystem.resale.dealer.session;


import br.com.mcampos.ejb.cloudsystem.resale.dealer.entity.Dealer;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.entity.DealerPK;
import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "DealerSession", mappedName = "CloudSystems-EjbPrj-DealerSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class DealerSessionBean extends Crud<DealerPK, Dealer> implements DealerSessionLocal
{
    public void delete( DealerPK key ) throws ApplicationException
    {
        Dealer d = get( key );
        if ( d == null )
            return;
        d.setToDate( new Date() );
    }

    public void delete( Resale resale, Integer sequence ) throws ApplicationException
    {
        DealerPK key = new DealerPK( resale, sequence );
        delete( key );
    }

    public Dealer get( DealerPK key ) throws ApplicationException
    {
        return get( Dealer.class, key );
    }

    public Dealer get( Resale resale, Integer sequence ) throws ApplicationException
    {
        return get( new DealerPK( resale, sequence ) );
    }

    public Dealer get( Person dealer ) throws ApplicationException
    {
        return ( Dealer )getSingleResult( Dealer.hasResale, dealer );
    }


    public List<Dealer> getAll( Resale resale ) throws ApplicationException
    {
        return ( List<Dealer> )getResultList( Dealer.getAll, resale );
    }

    public List<Dealer> getAll( Company owner ) throws ApplicationException
    {
        return ( List<Dealer> )getResultList( Dealer.getAllResaleDealers, owner );
    }

    public Dealer get( Resale resale, Person dealer ) throws ApplicationException
    {
        return ( Dealer )getSingleResult( Dealer.findDealer, resale, dealer );
    }

    private Integer getNextSequence( Resale resale ) throws ApplicationException
    {
        return nextIntegerId( Dealer.nextSequence, resale );
    }

    @Override
    public Dealer add( Dealer entity ) throws ApplicationException
    {
        Dealer e = get( entity.getDealer() );
        if ( e == null ) {
            entity.setFromDate( new Date() );
            entity.setSequence( getNextSequence( entity.getResale() ) );
            return super.add( entity );
        }
        else
            return null;
    }
}

package br.com.mcampos.ejb.cloudsystem.resale.session;


import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;
import br.com.mcampos.ejb.cloudsystem.resale.entity.ResalePK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "ResaleSession", mappedName = "CloudSystems-EjbPrj-ResaleSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class ResaleSessionBean extends Crud<ResalePK, Resale> implements ResaleSessionLocal
{
    public void delete( ResalePK key ) throws ApplicationException
    {
        Resale e = get( key );
        if ( e == null )
            return;
        e.setToDate( new Date() );
    }

    public Resale get( ResalePK key ) throws ApplicationException
    {
        return get( Resale.class, key );
    }

    public Resale get( Company owner, Client resale ) throws ApplicationException
    {
        return ( Resale )getSingleResult( Resale.findResale, owner, resale );
    }


    public List<Resale> getAll( Company company ) throws ApplicationException
    {
        return ( List<Resale> )getResultList( Resale.getAll, company );
    }

    @Override
    public Resale add( Resale entity ) throws ApplicationException
    {
        entity.setFromDate( new Date() );
        entity.setSequence( nextSequence( entity.getCompany() ) );
        return super.add( entity );
    }


    public void delete( Company company, Integer sequence ) throws ApplicationException
    {
        delete( new ResalePK( company, sequence ) );
    }

    public Resale get( Company company, Integer sequence ) throws ApplicationException
    {
        return get( new ResalePK( company, sequence ) );
    }

    private Integer nextSequence( Company company ) throws ApplicationException
    {
        return nextIntegerId( Resale.nextSequence, company );
    }
}

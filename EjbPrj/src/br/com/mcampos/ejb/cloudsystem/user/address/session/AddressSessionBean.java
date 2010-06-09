package br.com.mcampos.ejb.cloudsystem.user.address.session;


import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.cloudsystem.locality.city.session.CitySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.entity.AddressType;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.session.AddressTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.Address;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.AddressPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.Query;


@Stateless( name = "AddressSession", mappedName = "CloudSystems-EjbPrj-AddressSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AddressSessionBean extends Crud<AddressPK, Address> implements AddressSessionLocal
{
    @EJB
    private AddressTypeSessionLocal addressTypeSession;

    @EJB
    private CitySessionLocal citySession;

    public AddressSessionBean()
    {
    }

    @Override
    public void delete( AddressPK key ) throws ApplicationException
    {
        Address address = get( key );
        if ( address != null ) {
            unlinkToUser( address );
            getEntityManager().remove( address );
        }
    }

    @Override
    public Address get( AddressPK key ) throws ApplicationException
    {
        return get( Address.class, key );
    }

    @Override
    public List<Address> getAll( Users user ) throws ApplicationException
    {
        return ( List<Address> )getResultList( Address.getAll, user );
    }

    @Override
    public Address add( Address entity ) throws ApplicationException
    {
        setAddressType( entity );
        setCity( entity );
        Address address = super.add( entity );
        address.getUser().addAddress( address );
        return address;
    }

    private void setAddressType( Address entity ) throws ApplicationException
    {
        if ( entity.getAddressType() != null ) {
            AddressType addrType = addressTypeSession.get( entity.getAddressType().getId() );
            entity.setAddressType( addrType );
        }
    }

    private void setCity( Address entity ) throws ApplicationException
    {
        if ( entity.getCity() != null ) {
            City c = citySession.get( entity.getCity().getId() );
            entity.setCity( c );
        }
    }

    private void linkToUser( Address entity )
    {
        if ( entity != null ) {
            Users user = entity.getUser();
            if ( user != null )
                user.addAddress( entity );
        }
    }

    private void unlinkToUser( Address entity )
    {
        if ( entity != null ) {
            Users user = entity.getUser();
            if ( user != null )
                user.removeAddress( entity );
        }
    }

    public void delete( Users user ) throws ApplicationException
    {
        if ( user == null )
            return;
        Query query = getEntityManager().createNamedQuery( Address.deleteFromUser );
        query.setParameter( 1, user ).executeUpdate();
        user.getAddresses().clear();
    }

    public void refresh( Users user, List<Address> addresses ) throws ApplicationException
    {
        if ( user == null )
            return;
        delete( user );
        for ( Address d : addresses ) {
            add( d );
        }
    }
}

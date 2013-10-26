package br.com.mcampos.ejb.cloudsystem.user.address.addresstype.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.AddressTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.entity.AddressType;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.session.AddressTypeSessionLocal;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AddressTypeFacade", mappedName = "AddressTypeFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AddressTypeFacadeBean extends AbstractSecurity implements AddressTypeFacade
{
    public static final Integer messageId = 18;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private AddressTypeSessionLocal session;

    public AddressTypeFacadeBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return session.nextIntegerId();
    }

    public void delete( AuthenticationDTO auth, AddressTypeDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        session.delete( entity.getId() );
    }

    public AddressTypeDTO get( AuthenticationDTO auth, AddressTypeDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AddressType addressType = session.get( entity.getId() );
        if ( addressType == null )
            return null;
        return AddressTypeUtil.copy( addressType );
    }

    public AddressTypeDTO add( AuthenticationDTO auth, AddressTypeDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AddressType addressType = session.get( entity.getId() );
        if ( addressType != null ) {
            throwException( 1 );
        }
        addressType = AddressTypeUtil.createEntity( entity );
        return AddressTypeUtil.copy( session.add( addressType ) );
    }

    public AddressTypeDTO update( AuthenticationDTO auth, AddressTypeDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        AddressType addressType = session.get( entity.getId() );
        if ( addressType == null ) {
            throwException( 2 );
        }
        addressType = AddressTypeUtil.copy( addressType, entity );
        addressType = session.update( addressType );
        return AddressTypeUtil.copy( addressType );
    }

    public List<AddressTypeDTO> getAll() throws ApplicationException
    {
        return AddressTypeUtil.toDTOList( session.getAll() );
    }
}

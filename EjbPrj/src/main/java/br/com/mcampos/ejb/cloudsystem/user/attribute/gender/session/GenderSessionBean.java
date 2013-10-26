package br.com.mcampos.ejb.cloudsystem.user.attribute.gender.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "GenderSession", mappedName = "GenderSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class GenderSessionBean extends Crud<Integer, Gender> implements GenderSessionLocal
{
    public GenderSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( Gender.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Gender get( Integer key ) throws ApplicationException
    {
        return get( Gender.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Gender> getAll() throws ApplicationException
    {
        return getAll( Gender.getAll );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer getNextId() throws ApplicationException
    {
        return nextIntegerId( Gender.getNextId );
    }
}

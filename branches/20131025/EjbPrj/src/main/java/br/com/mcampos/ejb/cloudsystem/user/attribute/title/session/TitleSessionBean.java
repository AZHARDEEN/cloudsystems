package br.com.mcampos.ejb.cloudsystem.user.attribute.title.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.entity.Title;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "TitleSession", mappedName = "TitleSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class TitleSessionBean extends Crud<Integer, Title> implements TitleSessionLocal
{
    public TitleSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( Title.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Title get( Integer key ) throws ApplicationException
    {
        return get( Title.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Title> getAll() throws ApplicationException
    {
        return ( List<Title> )getResultList( Title.getAll );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Title> getAll( Gender gender ) throws ApplicationException
    {
        return ( List<Title> )getResultList( Title.getAll );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer getNextId() throws ApplicationException
    {
        return nextIntegerId( Title.getNextId );
    }
}

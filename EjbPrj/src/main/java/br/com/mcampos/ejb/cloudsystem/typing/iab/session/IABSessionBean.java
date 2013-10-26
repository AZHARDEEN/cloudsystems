package br.com.mcampos.ejb.cloudsystem.typing.iab.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.typing.iab.entity.DadosIab;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "IABSession", mappedName = "IABSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class IABSessionBean extends Crud<Integer, DadosIab> implements IABSessionLocal
{
    public void delete( Integer key ) throws ApplicationException
    {
        delete( DadosIab.class, key );
    }

    public DadosIab get( Integer key ) throws ApplicationException
    {
        return get( DadosIab.class, key );
    }

    public DadosIab getPrimeiraDigitacao( String id ) throws ApplicationException
    {
        return ( DadosIab )getSingleResult( DadosIab.getById, id );
    }


    public DadosIab getSegundaDigitacao( String id ) throws ApplicationException
    {
        return ( DadosIab )getSingleResult( DadosIab.getById2, id );
    }


    public List<DadosIab> getAll() throws ApplicationException
    {
        return Collections.emptyList();
    }

    public List<DadosIab> getProblems() throws ApplicationException
    {
        return ( List<DadosIab> )getResultList( DadosIab.getProblems );
    }

    public DadosIab getNextId() throws ApplicationException
    {
        return null;
    }
}

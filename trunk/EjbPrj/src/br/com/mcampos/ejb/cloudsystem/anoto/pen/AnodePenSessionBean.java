package br.com.mcampos.ejb.cloudsystem.anoto.pen;


import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AnodePenSession", mappedName = "CloudSystems-EjbPrj-AnodePenSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AnodePenSessionBean extends Crud<String, AnotoPen> implements AnodePenSessionLocal
{
    public AnodePenSessionBean()
    {
    }

    public void delete( String key ) throws ApplicationException
    {
        super.delete( AnotoPen.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public AnotoPen get( String key ) throws ApplicationException
    {
        return super.get( AnotoPen.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPen> getAll() throws ApplicationException
    {
        return super.getAll( "Pen.findAll" );
    }

    @Override
    public AnotoPen add( AnotoPen entity ) throws ApplicationException
    {
        entity.setInsertDate( new Date() );
        return super.add( entity );
    }
}

package br.com.mcampos.ejb.cloudsystem.anoto.pen.user.session;


import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUserPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AnotoPenUserSession", mappedName = "CloudSystems-EjbPrj-AnotoPenUserSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class AnotoPenUserSessionBean extends Crud<AnotoPenUserPK, AnotoPenUser> implements AnotoPenUserSessionLocal
{
    public AnotoPenUserSessionBean()
    {
    }

    public void delete( AnotoPenUserPK key ) throws ApplicationException
    {
        AnotoPenUser entity = get( AnotoPenUser.class, key );
        if ( entity != null )
            entity.setToDate( new Date() );
    }

    @Override
    public AnotoPenUser add( AnotoPenUser entity ) throws ApplicationException
    {
        entity.setFromDate( new Date() );
        entity = super.add( entity );
        return entity;
    }
}

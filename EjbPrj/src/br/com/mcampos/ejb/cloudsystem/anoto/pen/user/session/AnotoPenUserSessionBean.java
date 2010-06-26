package br.com.mcampos.ejb.cloudsystem.anoto.pen.user.session;


import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUserPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

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
        delete( entity );
    }

    public void delete( AnotoPenUser entity ) throws ApplicationException
    {
        AnotoPenUser merged = get( AnotoPenUser.class, new AnotoPenUserPK( entity ) );
        _delete( merged );
    }

    private void _delete( AnotoPenUser entity )
    {
        if ( entity != null )
            entity.setToDate( new Date() );
    }

    @Override
    public AnotoPenUser add( AnotoPenUser entity ) throws ApplicationException
    {
        entity.setFromDate( new Date() );
        entity.setSequence( getSequence( entity.getPen() ) );
        entity = super.add( entity );
        return entity;
    }

    public AnotoPenUser getUser( String penId ) throws ApplicationException
    {
        return ( AnotoPenUser )getSingleResult( AnotoPenUser.getPenUser, penId );
    }

    private Integer getSequence( AnotoPen pen ) throws ApplicationException
    {
        Integer id = nextIntegerId( AnotoPenUser.nextSequence, pen );
        if ( SysUtils.isZero( id ) )
            id = 1;
        return id;
    }
}
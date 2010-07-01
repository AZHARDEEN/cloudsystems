package br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.session;


import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.entity.PgcProperty;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.entity.PgcPropertyPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PgcPropertySession", mappedName = "CloudSystems-EjbPrj-PgcPropertySession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PgcPropertySessionBean extends Crud<PgcPropertyPK, PgcProperty> implements PgcPropertySessionLocal
{
    public List<PgcProperty> get( Integer pgcId, Integer propertyId ) throws ApplicationException
    {
        return ( List<PgcProperty> )getResultList( PgcProperty.getProperty, pgcId, propertyId );
    }

    public List<PgcProperty> getGPS( Integer pgcId ) throws ApplicationException
    {
        return ( List<PgcProperty> )getResultList( PgcProperty.getAllGPS, pgcId );
    }
}

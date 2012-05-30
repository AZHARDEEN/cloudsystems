package br.com.mcampos.ejb.cloudsystem.system.fieldtype.session;


import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "FieldTypeSession", mappedName = "CloudSystems-EjbPrj-FieldTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class FieldTypeSessionBean extends Crud<Integer, FieldType> implements FieldTypeSessionLocal
{
    public FieldTypeSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( FieldType.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public FieldType get( Integer key ) throws ApplicationException
    {
        return get( FieldType.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<FieldType> getAll() throws ApplicationException
    {
        return ( List<FieldType> )getResultList( FieldType.findAll );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer nextIntegerId() throws ApplicationException
    {
        return nextIntegerId( FieldType.nextId );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<FieldTypeDTO> toDTOList( List<FieldType> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<FieldTypeDTO> dtoList = new ArrayList<FieldTypeDTO>( list.size() );
        for ( FieldType f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }
}

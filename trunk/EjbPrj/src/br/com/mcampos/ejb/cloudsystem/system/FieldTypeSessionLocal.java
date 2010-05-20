package br.com.mcampos.ejb.cloudsystem.system;


import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.ejb.cloudsystem.system.entity.FieldType;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface FieldTypeSessionLocal
{
    FieldType add( FieldType entity ) throws ApplicationException;

    FieldType update( FieldType entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    FieldType get( Integer key ) throws ApplicationException;

    List<FieldType> getAll() throws ApplicationException;

    Integer nextIntegerId() throws ApplicationException;

    List<FieldTypeDTO> toDTOList( List<FieldType> list );
}
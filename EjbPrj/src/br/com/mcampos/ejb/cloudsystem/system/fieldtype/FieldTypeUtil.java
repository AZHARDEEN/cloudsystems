package br.com.mcampos.ejb.cloudsystem.system.fieldtype;

import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;

public class FieldTypeUtil
{
    public FieldTypeUtil()
    {
        super();
    }

    public static FieldType createEntity( FieldTypeDTO dto )
    {
        FieldType target = new FieldType( dto.getId() );
        return update( target, dto );
    }


    public static FieldType update( FieldType target, FieldTypeDTO dto )
    {
        target.setDescription( dto.getDescription() );
        return target;
    }
}

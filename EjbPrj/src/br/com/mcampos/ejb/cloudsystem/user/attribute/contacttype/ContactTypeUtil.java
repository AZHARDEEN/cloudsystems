package br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype;


import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.entity.ContactType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactTypeUtil
{
    public ContactTypeUtil()
    {
        super();
    }

    public static ContactType createEntity( ContactTypeDTO dto )
    {
        if ( dto == null )
            return null;

        ContactType entity = new ContactType( dto.getId() );
        return update( entity, dto );
    }

    public static ContactType update( ContactType entity, ContactTypeDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static ContactTypeDTO copy( ContactType entity )
    {
        if ( entity == null )
            return null;

        ContactTypeDTO dto = new ContactTypeDTO( entity.getId() );
        dto.setAllowDuplicate( entity.getAllowDuplicate() );
        dto.setDescription( entity.getDescription() );
        dto.setMask( entity.getMask() );
        return dto;
    }

    public static List<ContactTypeDTO> toDTOList( List<ContactType> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<ContactTypeDTO> listDTO = new ArrayList<ContactTypeDTO>( list.size() );
        for ( ContactType m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}

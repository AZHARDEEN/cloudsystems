package br.com.mcampos.ejb.cloudsystem.user.contact;


import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.ContactTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContact;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserContactUtil
{
    public UserContactUtil()
    {
        super();
    }

    public static UserContactDTO copy( UserContact entity )
    {
        UserContactDTO dto = new UserContactDTO();
        dto.setContactType( ContactTypeUtil.copy( entity.getContactType() ) );
        dto.setDescription( entity.getDescription() );
        dto.setComment( entity.getComment() );
        return dto;
    }


    public static UserContact createEntity( UserContactDTO dto, Users user )
    {
        UserContact entity = new UserContact();
        entity.setUser( user );
        return update( entity, dto );
    }


    public static UserContact update( UserContact entity, UserContactDTO dto )
    {
        entity.setContactType( ContactTypeUtil.createEntity( dto.getContactType() ) );
        entity.setDescription( dto.getDescription() );
        entity.setComment( dto.getComment() );
        return entity;
    }

    public static List<UserContact> toEntityList( Users user, List<UserContactDTO> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<UserContact> listDTO = new ArrayList<UserContact>( list.size() );
        for ( UserContactDTO m : list ) {
            listDTO.add( createEntity( m, user ) );
        }
        return listDTO;
    }
}

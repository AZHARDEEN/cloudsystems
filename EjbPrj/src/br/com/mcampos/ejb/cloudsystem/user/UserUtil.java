package br.com.mcampos.ejb.cloudsystem.user;


import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.cloudsystem.user.address.AddressUtil;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.Address;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.UserTypeUtil;
import br.com.mcampos.ejb.entity.user.Users;

import java.util.List;

public abstract class UserUtil
{
    public UserUtil()
    {
        super();
    }

    protected static void update( Users entity, UserDTO dto )
    {
        entity.setName( dto.getName() );
        entity.setNickName( dto.getNickName() );
        entity.setUserType( UserTypeUtil.createEntity( dto.getUserType() ) );
        entity.setId( dto.getId() );
        addAddresses( entity, dto );
    }

    protected static void update( UserDTO dto, Users entity )
    {
        dto.setName( entity.getName() );
        dto.setNickName( entity.getNickName() );
        dto.setUserType( UserTypeUtil.copy( entity.getUserType() ) );
        dto.setId( entity.getId() );
        addAddresses( dto, entity );
    }

    public static void addAddresses( Users user, UserDTO dto )
    {
        List<AddressDTO> list = dto.getAddressList();

        user.getAddresses().clear();
        for ( AddressDTO item : list ) {
            user.addAddress( AddressUtil.createEntity( item, user ) );
        }
    }

    public static void addAddresses( UserDTO dto, Users user )
    {
        List<Address> list = user.getAddresses();

        dto.getAddressList().clear();
        for ( Address item : list ) {
            dto.add( AddressUtil.copy( item ) );
        }
    }

}

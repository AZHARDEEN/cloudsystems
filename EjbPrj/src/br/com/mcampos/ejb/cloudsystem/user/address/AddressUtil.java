package br.com.mcampos.ejb.cloudsystem.user.address;


import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.AddressTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.Address;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddressUtil
{
    public AddressUtil()
    {
        super();
    }

    public static Address createEntity( AddressDTO dto, Users user )
    {
        if ( dto == null )
            return null;

        Address entity = new Address( user, AddressTypeUtil.createEntity( dto.getAddressType() ) );
        return update( entity, dto );
    }

    public static Address update( Address entity, AddressDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setAddress( dto.getAddress() );
        entity.setDistrict( dto.getDistrict() );
        entity.setComment( dto.getComment() );
        entity.setZip( dto.getZip() );
        entity.setAddressType( AddressTypeUtil.createEntity( dto.getAddressType() ) );
        entity.setCity( DTOFactory.copy( dto.getCity() ) );
        return entity;
    }

    public static AddressDTO copy( Address entity )
    {
        if ( entity == null )
            return null;
        AddressDTO dto = new AddressDTO();
        dto.setAddress( entity.getAddress() );
        dto.setAddressType( AddressTypeUtil.copy( entity.getAddressType() ) );
        dto.setCity( DTOFactory.copy( entity.getCity() ) );
        dto.setComment( entity.getComment() );
        dto.setDistrict( entity.getDistrict() );
        dto.setZip( entity.getZip() );
        return dto;
    }

    public static List<AddressDTO> toDTOList( List<Address> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<AddressDTO> listDTO = new ArrayList<AddressDTO>( list.size() );
        for ( Address m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}

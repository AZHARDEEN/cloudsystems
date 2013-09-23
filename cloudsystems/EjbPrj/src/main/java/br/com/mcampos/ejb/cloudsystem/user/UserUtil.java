package br.com.mcampos.ejb.cloudsystem.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.cloudsystem.user.address.AddressUtil;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.Address;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.UserTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.contact.UserContactUtil;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContact;
import br.com.mcampos.ejb.cloudsystem.user.document.UserDocumentUtil;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.sysutils.SysUtils;

public abstract class UserUtil
{
	public UserUtil( )
	{
		super( );
	}

	protected static Users update( Users entity, UserDTO dto )
	{
		entity.setName( dto.getName( ) );
		entity.setNickName( dto.getNickName( ) );
		entity.setUserType( UserTypeUtil.createEntity( dto.getUserType( ) ) );
		entity.setId( dto.getId( ) );
		// addAddresses( entity, dto );
		// addContacts( entity, dto );
		// addDocuments( entity, dto );
		return entity;
	}

	protected static UserDTO update( UserDTO dto, Users entity )
	{
		if ( dto == null || entity == null )
			return null;
		dto.setName( entity.getName( ) );
		dto.setNickName( entity.getNickName( ) );
		dto.setUserType( UserTypeUtil.copy( entity.getUserType( ) ) );
		dto.setId( entity.getId( ) );
		addAddresses( dto, entity );
		addContacts( dto, entity );
		addDocuments( dto, entity );
		return dto;
	}

	public static Users addAddresses( Users user, UserDTO dto )
	{
		List<AddressDTO> list = dto.getAddressList( );

		user.getAddresses( ).clear( );
		if ( SysUtils.isEmpty( list ) == false )
			for ( AddressDTO item : list )
				user.addAddress( AddressUtil.createEntity( item, user ) );
		return user;
	}

	public static UserDTO addAddresses( UserDTO dto, Users user )
	{
		List<Address> list = user.getAddresses( );

		dto.getAddressList( ).clear( );
		if ( SysUtils.isEmpty( list ) == false )
			for ( Address item : list )
				dto.add( AddressUtil.copy( item ) );
		return dto;
	}

	public static UserDTO addDocuments( UserDTO dto, Users user )
	{
		List<UserDocument> list = null;// user.getDocuments();

		dto.getDocumentList( ).clear( );
		if ( SysUtils.isEmpty( list ) == false )
			for ( UserDocument item : list )
				dto.add( UserDocumentUtil.copy( item ) );
		return dto;
	}

	public static Users addDocuments( Users user, UserDTO dto )
	{
		List<UserDocumentDTO> list = dto.getDocumentList( );

		user.getDocuments( ).clear( );
		if ( SysUtils.isEmpty( list ) == false )
			for ( UserDocumentDTO item : list )
				user.addDocument( UserDocumentUtil.createEntity( item, user ) );
		return user;
	}

	public static Users addDocuments( Users user, RegisterDTO dto )
	{
		return user;
	}

	protected static Users addDocuments( Users user, List<UserDocumentDTO> list )
	{
		user.getDocuments( ).clear( );
		if ( SysUtils.isEmpty( list ) == false )
			for ( UserDocumentDTO item : list )
				user.addDocument( UserDocumentUtil.createEntity( item, user ) );
		return user;
	}

	public static UserDTO addContacts( UserDTO dto, Users user )
	{
		List<UserContact> list = user.getContacts( );

		dto.getContactList( ).clear( );
		if ( SysUtils.isEmpty( list ) == false )
			for ( UserContact item : list )
				dto.add( UserContactUtil.copy( item ) );
		return dto;
	}

	public static Users addContacts( Users user, UserDTO dto )
	{
		List<UserContactDTO> list = dto.getContactList( );

		user.getContacts( ).clear( );
		if ( SysUtils.isEmpty( list ) == false )
			for ( UserContactDTO item : list )
				user.addContact( UserContactUtil.createEntity( item, user ) );
		return user;
	}

	public static ListUserDTO copy( Users entity )
	{
		if ( entity == null )
			return null;

		ListUserDTO dto = new ListUserDTO( );

		dto.setName( entity.getName( ) );
		dto.setNickName( entity.getNickName( ) );
		dto.setId( entity.getId( ) );
		dto.setUserType( UserTypeUtil.copy( entity.getUserType( ) ) );
		dto.setLastUpdate( entity.getUpdateDate( ) == null ? entity.getInsertDate( ) : entity.getUpdateDate( ) );
		return dto;
	}

	public static List<ListUserDTO> copy( List<Users> list )
	{
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList( );
		List<ListUserDTO> dtos = new ArrayList<ListUserDTO>( list.size( ) );
		for ( Users item : list )
			dtos.add( copy( item ) );
		return dtos;

	}

}

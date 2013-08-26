package br.com.mcampos.ejb.user;

import java.util.Date;

import javax.ejb.EJB;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.user.address.Address;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseUserSession<BEAN extends Users> extends SimpleSessionBean<BEAN>
{
	@EJB
	UserDocumentSessionLocal documentSession;

	protected void configAttributes( Users u )
	{
		for ( UserDocument item : u.getDocuments( ) ) {
			switch ( item.getType( ).getId( ) )
			{
			case UserDocument.typeEmail:
				item.setCode( item.getCode( ).toLowerCase( ).trim( ) );
				break;
			default:
				item.setCode( item.getCode( ).replaceAll( "\\.", "" ) );
				item.setCode( item.getCode( ).replaceAll( "-", "" ).trim( ) );
				break;
			}
			if ( item.getUser( ) == null || item.getUser( ).equals( u ) == false ) {
				item.setUser( u );
			}
		}
		for ( UserContact item : u.getContacts( ) ) {
			item.setDescription( item.getDescription( ).trim( ) );
			if ( item.getUser( ) == null || item.getUser( ).equals( u ) == false ) {
				item.setUser( u );
			}
		}
		for ( Address item : u.getAddresses( ) ) {
			if ( item.getFromDate( ) == null ) {
				item.setFromDate( new Date( ) );
			}
			if ( SysUtils.isEmpty( item.getAddress( ) ) == false ) {
				item.setAddress( SysUtils.unaccent( item.getAddress( ).trim( ).toUpperCase( ) ) );
			}
			if ( SysUtils.isEmpty( item.getDistrict( ) ) == false ) {
				item.setDistrict( SysUtils.unaccent( item.getDistrict( ).trim( ).toUpperCase( ) ) );
			}
			if ( item.getUser( ) == null || item.getUser( ).equals( u ) == false ) {
				item.setUser( u );
			}
		}
		u.setName( SysUtils.unaccent( u.getName( ).toUpperCase( ) ) );
		if ( SysUtils.isEmpty( u.getNickName( ) ) == false ) {
			u.setNickName( SysUtils.unaccent( u.getNickName( ).toUpperCase( ) ) );
		}
		if ( u.getInsertDate( ) == null ) {
			u.setInsertDate( new Date( ) );
		}
	}

	@Override
	public BEAN merge( BEAN newEntity )
	{
		/*
		List<UserDocument> documents = new ArrayList<UserDocument>( );
		List<Address> addresses = new ArrayList<Address>( );
		List<UserContact> contacts = new ArrayList<UserContact>( );

		configAttributes( newEntity );
		for ( UserContact i : newEntity.getContacts( ) ) {
			contacts.add( i );
		}
		newEntity.getContacts( ).clear( );
		for ( UserDocument i : newEntity.getDocuments( ) ) {
			documents.add( i );
		}
		newEntity.getDocuments( ).clear( );
		for ( Address i : newEntity.getAddresses( ) ) {
			addresses.add( i );
		}
		newEntity.getAddresses( ).clear( );
		*/
		newEntity = super.merge( newEntity );
		/*
		for ( UserContact i : contacts ) {
			newEntity.add( i );
		}
		for ( UserDocument i : documents ) {
			newEntity.add( i );
		}
		for ( Address i : addresses ) {
			newEntity.add( i );
		}
		*/
		return newEntity;
	}

	protected void lazyLoad( Users item )
	{
		if ( item == null ) {
			return;
		}
		item.getAddresses( ).size( );
		item.getDocuments( ).size( );
		item.getContacts( ).size( );
	}

}

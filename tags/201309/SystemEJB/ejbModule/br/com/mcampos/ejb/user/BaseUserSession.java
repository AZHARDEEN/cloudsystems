package br.com.mcampos.ejb.user;

import java.util.Date;

import javax.ejb.EJB;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.user.document.UserDocumentSessionLocal;
import br.com.mcampos.jpa.user.Address;
import br.com.mcampos.jpa.user.UserContact;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.jpa.user.Users;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseUserSession<BEAN extends Users> extends
		SimpleSessionBean<BEAN>
{
	@EJB
	UserDocumentSessionLocal documentSession;

	protected void configAttributes( Users u )
	{
		for( UserDocument item : u.getDocuments( ) ) {
			switch( item.getType( ).getId( ) ) {
			case UserDocument.typeEmail:
				item.setCode( item.getCode( ).toLowerCase( ).trim( ) );
				break;
			default:
				item.setCode( item.getCode( ).replaceAll( "-", "" ).trim( ) );
				item.setCode( item.getCode( ).replaceAll( "\\.", "" ) );
				item.setCode( item.getCode( ).replaceAll( "\\(", "" ) );
				item.setCode( item.getCode( ).replaceAll( "\\)", "" ) );
				item.setCode( item.getCode( ).replaceAll( "/", "" ) );
				break;
			}
			if( item.getUser( ) == null || item.getUser( ).equals( u ) == false ) {
				item.setUser( u );
			}
		}
		for( UserContact item : u.getContacts( ) ) {
			switch( item.getType( ).getId( ) ) {
			case 1:
			case 2:
			case 3:
				item.setDescription( item.getDescription( ).replaceAll( "\\.", "" ).trim( ) );
				item.setDescription( item.getDescription( ).replaceAll( "-", "" ) );
				item.setDescription( item.getDescription( ).replaceAll( "\\(", "" ) );
				item.setDescription( item.getDescription( ).replaceAll( "\\)", "" ) );
				item.setDescription( item.getDescription( ).replaceAll( "/", "" ) );
				item.setDescription( item.getDescription( ).replaceAll( " ", "" ) );
				break;
			default:
				item.setDescription( item.getDescription( ).trim( ) );
				break;
			}
			if( item.getUser( ) == null || item.getUser( ).equals( u ) == false ) {
				item.setUser( u );
			}
		}
		for( Address item : u.getAddresses( ) ) {
			if( item.getFromDate( ) == null ) {
				item.setFromDate( new Date( ) );
			}
			if( SysUtils.isEmpty( item.getAddress( ) ) == false ) {
				item.setAddress( SysUtils.unaccent( item.getAddress( ).trim( ).toUpperCase( ) ) );
			}
			if( SysUtils.isEmpty( item.getDistrict( ) ) == false ) {
				item.setDistrict( SysUtils.unaccent( item.getDistrict( ).trim( ).toUpperCase( ) ) );
			}
			if( item.getUser( ) == null || item.getUser( ).equals( u ) == false ) {
				item.setUser( u );
			}
			if( SysUtils.isEmpty( item.getZip( ) ) == false ) {
				item.setZip( item.getZip( ).replaceAll( "\\.", "" ) );
				item.setZip( item.getZip( ).replaceAll( "-", "" ).trim( ) );
			}
		}
		u.setName( SysUtils.unaccent( u.getName( ).toUpperCase( ) ) );
		if( SysUtils.isEmpty( u.getNickName( ) ) == false ) {
			u.setNickName( SysUtils.unaccent( u.getNickName( ).toUpperCase( ) ) );
		}
		if( u.getInsertDate( ) == null ) {
			u.setInsertDate( new Date( ) );
		}
	}

	@Override
	public BEAN merge( BEAN newEntity )
	{
		configAttributes( newEntity );
		newEntity = super.merge( newEntity );
		return newEntity;
	}

	protected void lazyLoad( Users item )
	{
		if( item == null ) {
			return;
		}
		item.getAddresses( ).size( );
		item.getDocuments( ).size( );
		item.getContacts( ).size( );
	}

}

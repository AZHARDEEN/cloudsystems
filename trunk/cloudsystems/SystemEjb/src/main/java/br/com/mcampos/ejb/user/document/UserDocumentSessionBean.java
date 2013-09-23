package br.com.mcampos.ejb.user.document;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.jpa.user.Users;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class UserDocumentSessionBean
 */
@Stateless( name = "UserDocumentSession", mappedName = "UserDocumentSession" )
@LocalBean
public class UserDocumentSessionBean extends SimpleSessionBean<UserDocument> implements UserDocumentSessionLocal
{

	@Override
	protected Class<UserDocument> getEntityClass( )
	{
		return UserDocument.class;
	}

	@Override
	public Users getUserByDocument( String document )
	{
		UserDocument doc = null;

		try {
			doc = getByNamedQuery( UserDocument.findDocument, document );
		}
		catch ( Exception e ) {
			e = null;
		}
		return doc != null ? doc.getUser( ) : null;
	}

	@Override
	public List<UserDocument> searchByDocument( Integer documentType, String lookFor )
	{
		if ( documentType == null || SysUtils.isEmpty( lookFor ) ) {
			return null;
		}
		String sql = "select distinct o from UserDocument o where o.type.id = ?1 and o.code ";

		if ( lookFor.contains( "*" ) || lookFor.contains( "%" ) || lookFor.contains( "_" ) || lookFor.contains( "?" ) ) {
			sql = sql + "like ";
			if ( lookFor.contains( "*" ) ) {
				lookFor = lookFor.replaceAll( "\\*", "\\%" );
			}
			if ( lookFor.contains( "?" ) ) {
				lookFor = lookFor.replaceAll( "\\?", "_" );
			}
		}
		else {
			sql = sql + " = ";
		}
		sql = sql + " ( ?2 )";
		return findByQuery( sql, new DBPaging( 0, 40 ), documentType, lookFor );
	}

	@Override
	public List<UserDocument> searchByEmail( String lookFor )
	{
		if ( SysUtils.isEmpty( lookFor ) ) {
			return null;
		}
		lookFor = lookFor.toLowerCase( );
		return searchByDocument( UserDocument.typeEmail, lookFor );
	}

	@Override
	public List<UserDocument> searchByCPF( String lookFor )
	{
		if ( SysUtils.isEmpty( lookFor ) ) {
			return null;
		}
		lookFor = lookFor.replaceAll( "\\.", "" );
		lookFor = lookFor.replaceAll( "-", "" );
		return searchByDocument( UserDocument.typeCPF, lookFor );
	}

}

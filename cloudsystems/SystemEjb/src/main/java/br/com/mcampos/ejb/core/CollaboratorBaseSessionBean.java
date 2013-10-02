package br.com.mcampos.ejb.core;

import javax.ejb.EJB;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.system.fileupload.FileUploadSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginPropertySessionLocal;
import br.com.mcampos.jpa.BaseCompanyEntity;
import br.com.mcampos.jpa.security.LoginProperty;
import br.com.mcampos.jpa.system.FileUpload;

public abstract class CollaboratorBaseSessionBean<Y extends BaseCompanyEntity> extends BaseCompanySessionBean<Y> implements CollaboratorBaseSessionInterface<Y>
{
	@EJB
	private LoginPropertySessionLocal propertySession;

	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@EJB
	private FileUploadSessionLocal fileUploadSession;

	@Override
	public String getProperty( PrincipalDTO auth, String name )
	{
		if ( auth == null || name == null ) {
			return null;
		}
		LoginProperty p = this.getPropertySession( ).getProperty( auth, name );
		return p != null ? p.getValue( ) : "";
	}

	@Override
	public void setProperty( PrincipalDTO auth, String name, String value )
	{
		this.getPropertySession( ).setProperty( auth, name, value );
	}

	@Override
	public void remove( PrincipalDTO auth, String name )
	{
		this.getPropertySession( ).remove( auth, name );
	}

	protected LoginPropertySessionLocal getPropertySession( )
	{
		return this.propertySession;
	}

	protected CollaboratorSessionLocal getCollaboratorSession( )
	{
		return this.collaboratorSession;
	}

	@Override
	public FileUpload storeUploadInformation( PrincipalDTO auth, MediaDTO media )
	{
		return this.fileUploadSession.addNewFile( auth, media );
	}

}

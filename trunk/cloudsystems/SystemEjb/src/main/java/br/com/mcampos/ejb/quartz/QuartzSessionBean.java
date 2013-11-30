package br.com.mcampos.ejb.quartz;

import java.security.InvalidParameterException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.BaseSessionBean;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.ejb.params.SystemParameterSessionLocal;
import br.com.mcampos.ejb.system.fileupload.FileUploadSessionLocal;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.jpa.user.Company;

/**
 * Session Bean implementation class QuartzSessionBean
 */
@Stateless( name = "QuartzSession", mappedName = "QuartzSession" )
public class QuartzSessionBean extends BaseSessionBean implements QuartzSession, QuartzSessionLocal
{
	private static final long serialVersionUID = 4481663756603460251L;

	@EJB
	private CompanySessionLocal companySession;

	@EJB
	private MediaSessionBeanLocal mediaSession;

	@EJB
	private SystemParameterSessionLocal parameterSession;

	@EJB
	private FileUploadSessionLocal fileUploadSession;

	@Override
	public List<Company> getCompanies( )
	{
		return this.companySession.getWithUploadJobsEnabled( );
	}

	@Override
	public String get( String id )
	{
		return this.parameterSession.get( id );
	}

	@Override
	public void set( Company c, MediaDTO mediaDto )
	{
		if ( c == null || mediaDto == null ) {
			throw new InvalidParameterException( "QuartzSession.set ( Company, MediaDTO ) parameters cannot be null" );
		}
		this.fileUploadSession.addNewFile( c, mediaDto );
	}

}

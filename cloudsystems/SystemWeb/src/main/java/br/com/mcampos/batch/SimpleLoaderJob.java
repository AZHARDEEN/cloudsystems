package br.com.mcampos.batch;

import java.io.File;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.sysutils.SysUtils;

public class SimpleLoaderJob extends BaseQuartzJob
{
	private static final long serialVersionUID = -2723048064942513434L;
	private static final Logger LOGGER = LoggerFactory.getLogger( SimpleLoaderJob.class );

	@Override
	public void execute( JobExecutionContext context ) throws JobExecutionException
	{
		if ( this.amIRunning( context ) ) {
			return;
		}
		List<Company> companies = this.getSession( ).getCompanies( );
		if ( SysUtils.isEmpty( companies ) ) {
			return;
		}
		for ( Company company : companies ) {
			this.processCompanyUpload( company );
		}
	}

	private void processCompanyUpload( Company c )
	{
		String basePath = this.getBasePath( c );
		if ( SysUtils.isEmpty( basePath ) ) {
			LOGGER.error( "Error getting base path for company ", c.getName( ) );
			return;
		}
		List<String> items = SysUtils.searchDirectory( new File( basePath ) );
		if ( SysUtils.isEmpty( items ) ) {
			return;
		}
	}
}

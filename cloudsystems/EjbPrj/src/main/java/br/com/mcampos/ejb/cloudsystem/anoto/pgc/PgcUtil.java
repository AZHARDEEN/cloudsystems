package br.com.mcampos.ejb.cloudsystem.anoto.pgc;

import java.security.InvalidParameterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.facade.AnodeFacadeBean;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcstatus.PgcStatusUtil;
import br.com.mcampos.ejb.cloudsystem.media.MediaUtil;

public class PgcUtil
{
	private static final Logger LOGGER = LoggerFactory.getLogger( AnodeFacadeBean.class.getSimpleName( ) );

	public PgcUtil( )
	{
		super( );
	}

	public static Pgc createEntity( PGCDTO source )
	{
		if ( source == null || source.getMedia( ) == null ) {
			throw new InvalidParameterException( "PGCDTO source or source.Media cannot be null" );
		}
		Pgc pgc = new Pgc( );
		LOGGER.info( "Creating Entity from PGCDTO: Id: " + source.getId( ) + " " + source.getMedia( ) == null ? "Media is not null" : "Media is null" );
		pgc.setMedia( MediaUtil.createEntity( source.getMedia( ) ) );
		String penId = source.getPenId( );
		if ( penId != null ) {
			if ( penId.length( ) > 16 ) {
				penId = penId.substring( 0, 15 );
			}
		}
		pgc.setPenId( penId );
		pgc.setTimediff( source.getTimeDiff( ) );
		return pgc;
	}

	public static PGCDTO copy( Pgc entity )
	{
		PGCDTO dto = new PGCDTO( );
		dto.setId( entity.getId( ) );
		dto.setInsertDate( entity.getInsertDate( ) );
		dto.setPenId( entity.getPenId( ) );
		dto.setPgcStatus( PgcStatusUtil.copy( entity.getPgcStatus( ) ) );
		dto.setTimeDiff( entity.getTimediff( ) );
		return dto;
	}

}

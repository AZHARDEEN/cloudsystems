package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment;


import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PgcPageAttachmentSessionBean", mappedName = "CloudSystems-EjbPrj-PgcPageAttachmentSessionBean" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PgcPageAttachmentSessionBean extends Crud<PgcPageAttachmentPK, PgcPageAttachment> implements PgcPageAttachmentSessionLocal
{
	public PgcPageAttachmentSessionBean()
	{
	}

	public void delete( PgcPageAttachmentPK key ) throws ApplicationException
	{
		delete( PgcPageAttachment.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public PgcPageAttachment get( PgcPageAttachmentPK key ) throws ApplicationException
	{
		return get( PgcPageAttachment.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<PgcPageAttachment> getAll( PgcPage page ) throws ApplicationException
	{
		return ( List<PgcPageAttachment> )getResultList( PgcPageAttachment.findByPage, page );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<PgcPageAttachment> getAll( String barCodeValue, String pageAddress, Integer padId,
										   Integer currentPGC ) throws ApplicationException
	{
		List<Object> parameter = new ArrayList<Object>( 1 );
		parameter.add( barCodeValue );
		parameter.add( pageAddress );
		parameter.add( padId );
		parameter.add( currentPGC );
		return ( List<PgcPageAttachment> )getResultList( PgcPageAttachment.findOtherBarCode, parameter );
	}
}

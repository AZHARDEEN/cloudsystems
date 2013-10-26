package br.com.mcampos.ejb.cloudsystem.anoto.form.media;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

@Stateless( name = "FormMediaSession", mappedName = "FormMediaSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class FormMediaSessionBean extends Crud<FormMediaPK, FormMedia> implements FormMediaSessionLocal
{
	public FormMediaSessionBean( )
	{
	}

	@Override
	public FormMedia add( AnotoForm form, Media media ) throws ApplicationException
	{
		FormMedia fm = new FormMedia( form, media );
		return add( fm );
	}

	private void delete( FormMediaPK key ) throws ApplicationException
	{
		delete( FormMedia.class, key );
	}

	@Override
	public void delete( AnotoForm form, Media media ) throws ApplicationException
	{
		delete( new FormMediaPK( form, media ) );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public FormMedia get( FormMediaPK key ) throws ApplicationException
	{
		return get( FormMedia.class, key );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<FormMedia> get( AnotoForm form ) throws ApplicationException
	{
		return (List<FormMedia>) getResultList( FormMedia.formGetFiles, form );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public FormMedia get( AnotoForm form, Media media ) throws ApplicationException
	{
		return get( new FormMediaPK( form, media ) );
	}

	@Override
	public FormMedia getPDFTemplate( AnotoForm form ) throws ApplicationException
	{
		return (FormMedia) getSingleResult( FormMedia.formPDFTemplate, form );
	}
}

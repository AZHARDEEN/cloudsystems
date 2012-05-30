package br.com.mcampos.ejb.cloudsystem.anoto.form.media;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "FormMediaSession", mappedName = "CloudSystems-EjbPrj-FormMediaSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class FormMediaSessionBean extends Crud<FormMediaPK, FormMedia> implements FormMediaSessionLocal
{
    public FormMediaSessionBean()
    {
    }

    public FormMedia add( AnotoForm form, Media media ) throws ApplicationException
    {
        FormMedia fm = new FormMedia( form, media );
        return add( fm );
    }

    private void delete( FormMediaPK key ) throws ApplicationException
    {
        delete( FormMedia.class, key );
    }

    public void delete( AnotoForm form, Media media ) throws ApplicationException
    {
        delete( new FormMediaPK( form, media ) );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public FormMedia get( FormMediaPK key ) throws ApplicationException
    {
        return get( FormMedia.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<FormMedia> get( AnotoForm form ) throws ApplicationException
    {
        return ( List<FormMedia> )getResultList( FormMedia.formGetFiles, form );
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public FormMedia get( AnotoForm form, Media media ) throws ApplicationException
    {
        return get( new FormMediaPK( form, media ) );
    }

    public FormMedia getPDFTemplate( AnotoForm form ) throws ApplicationException
    {
        return ( FormMedia )getSingleResult( FormMedia.formPDFTemplate, form );
    }
}

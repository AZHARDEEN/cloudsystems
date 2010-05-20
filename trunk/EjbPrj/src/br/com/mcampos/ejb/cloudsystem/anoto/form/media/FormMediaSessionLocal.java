package br.com.mcampos.ejb.cloudsystem.anoto.form.media;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface FormMediaSessionLocal
{
    FormMedia add( AnotoForm form, Media media ) throws ApplicationException;

    void delete( AnotoForm form, Media media ) throws ApplicationException;

    FormMedia get( FormMediaPK key ) throws ApplicationException;

    List<FormMedia> get( AnotoForm form ) throws ApplicationException;

    FormMedia get( AnotoForm form, Media media ) throws ApplicationException;

}

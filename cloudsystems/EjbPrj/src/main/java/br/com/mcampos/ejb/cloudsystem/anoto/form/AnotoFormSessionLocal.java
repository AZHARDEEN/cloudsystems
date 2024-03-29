package br.com.mcampos.ejb.cloudsystem.anoto.form;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.anoto.form.media.FormMedia;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.Pad;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface AnotoFormSessionLocal extends Serializable
{
    AnotoForm add( AnotoForm entity, Company company ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    AnotoForm get( Integer key ) throws ApplicationException;

    List<AnotoForm> getAll( Company company ) throws ApplicationException;

    AnotoForm update( AnotoForm entity ) throws ApplicationException;

    Integer nextId() throws ApplicationException;

    Pad addPadFile( AnotoForm form, Media pad, List<String> pages, Boolean bUnique ) throws ApplicationException;

    Media removePadFile( AnotoForm form, Media pad ) throws ApplicationException;

    List<Pad> getPads( AnotoForm form ) throws ApplicationException;

    List<AnotoPen> getAvailablePens( AnotoForm form ) throws ApplicationException;

    List<AnotoPen> getPens( AnotoForm form ) throws ApplicationException;

    void add( AnotoForm form, List<AnotoPen> pens ) throws ApplicationException;

    void remove( AnotoForm form, List<AnotoPen> pens ) throws ApplicationException;


    FormMedia addFile( AnotoForm form, Media media ) throws ApplicationException;

    void removeFile( AnotoForm form, Media media ) throws ApplicationException;

    List<FormMedia> getFiles( AnotoForm form ) throws ApplicationException;

}

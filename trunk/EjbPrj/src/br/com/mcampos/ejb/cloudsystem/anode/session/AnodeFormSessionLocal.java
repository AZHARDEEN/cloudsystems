package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.FormMedia;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AnodeFormSessionLocal
{
    AnotoForm add( AnotoForm entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    AnotoForm get( Integer key ) throws ApplicationException;

    List<AnotoForm> getAll() throws ApplicationException;

    AnotoForm update( AnotoForm entity ) throws ApplicationException;

    Integer nextId() throws ApplicationException;

    Pad addPadFile( AnotoForm form, Media pad ) throws ApplicationException;

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

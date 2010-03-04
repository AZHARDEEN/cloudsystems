package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
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

    Media addPadFile( AnotoForm form, Media pad ) throws ApplicationException;

    Media removePadFile( AnotoForm form, Media pad ) throws ApplicationException;

    List<Pad> getPads( AnotoForm form ) throws ApplicationException;

}

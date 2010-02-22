package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.Form;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pen;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;


public interface AnodeFormSessionLocal
{
    Form add( Form entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    Form get( Integer key ) throws ApplicationException;

    List<Form> getAll() throws ApplicationException;

    Form update( Form entity ) throws ApplicationException;

    Integer nextId() throws ApplicationException;

    Media addMedia( Integer key, Media media ) throws ApplicationException;

    Media removeMedia( Integer key, Media media ) throws ApplicationException;

    List<Media> getMedias( Integer key );

    List<Pen> getAvailablePens( Integer key ) throws ApplicationException;

    Pen addPen( Integer key, Pen toInsert ) throws ApplicationException;

    Pen removePen( Integer key, Pen toRemove ) throws ApplicationException;

    List<Pen> getPens( Integer key ) throws ApplicationException;
}

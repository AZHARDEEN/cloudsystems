package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.Form;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pen;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.session.core.Crud;

import br.com.mcampos.exception.ApplicationException;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless( name = "AnodeFormSession", mappedName = "CloudSystems-EjbPrj-AnodeFormSession" )
@Local( AnodeFormSessionLocal.class )
public class AnodeFormSessionBean extends Crud<Integer, Form> implements AnodeFormSessionLocal
{
    public AnodeFormSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( Form.class, key );
    }

    public Form get( Integer key ) throws ApplicationException
    {
        return get( Form.class, key );
    }

    public List<Form> getAll()
    {
        return getAll( "Form.findAll" );
    }

    public Integer nextId() throws ApplicationException
    {
        return nextIntegerId( "Form.nextId" );
    }

    public Media addMedia( Integer key, Media newMedia ) throws ApplicationException
    {
        getEntityManager().merge( newMedia );
        Form form = get( key );
        form.addFormMedia( newMedia );
        return newMedia;
    }

    public Media removeMedia( Integer key, Media media ) throws ApplicationException
    {
        getEntityManager().merge( media );
        Form form = get( key );
        form.removeFormMedia( media );
        return media;
    }

    public List<Media> getMedias( Integer key )
    {
        Form form = get( Form.class, key );
        if ( form != null )
            return form.getMedias();
        else
            return Collections.emptyList();
    }

    public List<Pen> getAvailablePens( Integer key ) throws ApplicationException
    {

        Form form = get( key );
        if ( form != null ) {
            ArrayList<Object> params = new ArrayList<Object>( 1 );
            params.add( form );
            return ( List<Pen> )getResultList( "Pen.findAvailablePensForForm", params );
        }
        else
            return Collections.emptyList();
    }

    public Pen addPen( Integer key, Pen toInsert ) throws ApplicationException
    {
        Form entity = get( key );
        getEntityManager().merge( toInsert ); /*Make it menageable*/
        toInsert = entity.addFormPen( toInsert );
        getEntityManager().refresh( toInsert );
        return toInsert;
    }


    public Pen removePen( Integer key, Pen toRemove ) throws ApplicationException
    {
        Form entity = get( key );
        getEntityManager().merge( toRemove ); /*Make it menageable*/
        toRemove = entity.removeFormPen( toRemove );
        getEntityManager().refresh( toRemove );
        return toRemove;
    }

    public List<Pen> getPens( Integer key ) throws ApplicationException
    {
        Form entity = get( key );
        if ( entity != null ) {
            return entity.getPens();
        }
        else
            return Collections.emptyList();
    }


}

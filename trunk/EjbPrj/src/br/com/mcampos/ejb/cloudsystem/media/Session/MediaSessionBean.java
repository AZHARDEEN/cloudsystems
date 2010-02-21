package br.com.mcampos.ejb.cloudsystem.media.Session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.Form;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.session.core.Crud;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "MediaSession", mappedName = "CloudSystems-EjbPrj-MediaSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class MediaSessionBean extends Crud<Integer, Media> implements MediaSessionLocal
{
    public MediaSessionBean()
    {
    }

    @Override
    public Media add( Media entity )
    {
        return super.add( entity );
    }

    public void delete( Integer key )
    {
        delete( Media.class, key );
    }
}

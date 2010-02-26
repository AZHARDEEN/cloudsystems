package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.session.core.Crud;

import br.com.mcampos.exception.ApplicationException;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

@Stateless( name = "AnodeFormSession", mappedName = "CloudSystems-EjbPrj-AnodeFormSession" )
public class AnodeFormSessionBean extends Crud<Integer, AnotoForm> implements AnodeFormSessionLocal
{
    public AnodeFormSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( AnotoForm.class, key );
    }

    public AnotoForm get( Integer key ) throws ApplicationException
    {
        return get( AnotoForm.class, key );
    }

    public List<AnotoForm> getAll()
    {
        return getAll( "Form.findAll" );
    }

    public Integer nextId() throws ApplicationException
    {
        return nextIntegerId( "Form.nextId" );
    }

    @Override
    public AnotoForm add( AnotoForm entity )
    {
        entity.setInsertDate( new Date() );
        return super.add( entity );
    }
}

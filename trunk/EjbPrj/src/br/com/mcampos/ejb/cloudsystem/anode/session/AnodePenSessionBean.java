package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.session.core.Crud;

import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;


@Stateless( name = "AnodePenSession", mappedName = "CloudSystems-EjbPrj-AnodePenSession" )
public class AnodePenSessionBean extends Crud<String, AnotoPen> implements AnodePenSessionLocal
{
    public AnodePenSessionBean()
    {
    }

    public void delete( String key ) throws ApplicationException
    {
        super.delete( AnotoPen.class, key );
    }

    public AnotoPen get( String key ) throws ApplicationException
    {
        return super.get( AnotoPen.class, key );
    }

    public List<AnotoPen> getAll() throws ApplicationException
    {
        return super.getAll( "Pen.findAll" );
    }
}

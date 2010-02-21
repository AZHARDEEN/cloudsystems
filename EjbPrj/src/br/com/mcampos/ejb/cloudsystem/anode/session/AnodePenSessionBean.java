package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.Form;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pen;
import br.com.mcampos.ejb.session.core.Crud;

import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless( name = "AnodePenSession", mappedName = "CloudSystems-EjbPrj-AnodePenSession" )
@Local
public class AnodePenSessionBean extends Crud<String, Pen> implements AnodePenSessionLocal
{
    public AnodePenSessionBean()
    {
    }

    public void delete( String key )
    {
        super.delete( Pen.class, key );
    }

    public Pen get( String key )
    {
        return super.get( Pen.class, key );
    }

    public List<Pen> getAll()
    {
        return super.getAll( "Pen.findAll" );
    }
}

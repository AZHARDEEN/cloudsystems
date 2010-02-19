package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.Form;
import br.com.mcampos.ejb.session.core.Crud;

import br.com.mcampos.exception.ApplicationException;

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
}

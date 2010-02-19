package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.Form;
import br.com.mcampos.ejb.session.core.CrudInterface;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


public interface AnodeFormSessionLocal
{

    public Form add( Form entity ) throws ApplicationException;

    public void delete( Integer key ) throws ApplicationException;

    public Form get( Integer key ) throws ApplicationException;

    public List<Form> getAll() throws ApplicationException;

    public Form update( Form entity ) throws ApplicationException;
}

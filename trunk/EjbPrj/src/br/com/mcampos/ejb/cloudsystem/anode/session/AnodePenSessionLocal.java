package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.Pen;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;

@Local
public interface AnodePenSessionLocal
{
    public Pen add( Pen entity ) throws ApplicationException;

    public void delete( String key ) throws ApplicationException;

    public Pen get( String key ) throws ApplicationException;

    public List<Pen> getAll() throws ApplicationException;

    public Pen update( Pen entity ) throws ApplicationException;

}

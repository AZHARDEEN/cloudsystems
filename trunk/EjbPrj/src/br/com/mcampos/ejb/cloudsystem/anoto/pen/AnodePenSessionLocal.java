package br.com.mcampos.ejb.cloudsystem.anoto.pen;

import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;

@Local
public interface AnodePenSessionLocal
{
    public AnotoPen add( AnotoPen entity ) throws ApplicationException;

    public void delete( String key ) throws ApplicationException;

    public AnotoPen get( String key ) throws ApplicationException;

    public List<AnotoPen> getAll() throws ApplicationException;

    public AnotoPen update( AnotoPen entity ) throws ApplicationException;
}

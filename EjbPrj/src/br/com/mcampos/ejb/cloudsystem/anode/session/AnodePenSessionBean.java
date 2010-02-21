package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.Form;
import br.com.mcampos.ejb.cloudsystem.anode.entity.FormPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pen;
import br.com.mcampos.ejb.session.core.Crud;

import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Collections;
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

    public void delete( String key ) throws ApplicationException
    {
        super.delete( Pen.class, key );
    }

    public Pen get( String key ) throws ApplicationException
    {
        return super.get( Pen.class, key );
    }

    public List<Pen> getAll() throws ApplicationException
    {
        return super.getAll( "Pen.findAll" );
    }

    public List<Form> getAvailableForms( String key ) throws ApplicationException
    {

        Pen pen = get( key );
        if ( pen != null ) {
            ArrayList<Object> params = new ArrayList<Object>( 1 );
            FormPen fp = new FormPen();
            fp.setPen( pen );
            params.add( pen );
            return ( List<Form> )getResultList( "Form.findAvailableFormsForPen", params );
        }
        else
            return Collections.emptyList();
    }
}

package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anode.session.AnodeFormSessionLocal;


import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless( name = "AnodeFacade", mappedName = "CloudSystems-EjbPrj-AnodeFacade" )
public class AnodeFacadeBean extends AbstractSecurity implements AnodeFacade
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;


    @EJB
    private AnodeFormSessionLocal form;


    public AnodeFacadeBean()
    {
    }

    public FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return form.add( DTOFactory.copy( entity ) ).toDTO();
    }

    public void delete( AuthenticationDTO auth, Integer key ) throws ApplicationException
    {
        authenticate( auth );
        form.delete( key );
    }

    public FormDTO get( AuthenticationDTO auth, Integer key ) throws ApplicationException
    {
        authenticate( auth );
        return form.get( key ).toDTO();
    }

    public List<FormDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return Collections.emptyList();
    }

    public FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return form.update( DTOFactory.copy( entity ) ).toDTO();
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return 7;
    }
}

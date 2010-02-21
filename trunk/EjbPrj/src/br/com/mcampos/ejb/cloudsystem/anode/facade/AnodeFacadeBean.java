package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Form;
import br.com.mcampos.ejb.cloudsystem.anode.session.AnodeFormSessionLocal;


import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
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
        List<Form> list = form.getAll();
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<FormDTO> dtoList = new ArrayList<FormDTO>( list.size() );
        for ( Form f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
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

    public Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException
    {
        return form.nextId();
    }
}

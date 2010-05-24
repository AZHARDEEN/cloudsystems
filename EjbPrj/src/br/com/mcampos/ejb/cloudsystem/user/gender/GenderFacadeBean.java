package br.com.mcampos.ejb.cloudsystem.user.gender;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "GenderFacade", mappedName = "CloudSystems-EjbPrj-GenderFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class GenderFacadeBean extends AbstractSecurity implements GenderFacade
{
    public static final Integer messageId = 10;

    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    GenderSessionLocal genderSession;

    public GenderFacadeBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }


    @TransactionAttribute( TransactionAttributeType.NEVER )
    public List<GenderDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );

        List<Gender> genders = genderSession.getAll();
        return toDTO( genders );
    }

    public static List<GenderDTO> toDTO( List<Gender> genders )
    {
        if ( SysUtils.isEmpty( genders ) )
            return Collections.EMPTY_LIST;
        List<GenderDTO> dtos = new ArrayList<GenderDTO>( genders.size() );
        for ( Gender gender : genders )
            dtos.add( gender.toDTO() );
        return dtos;
    }
}



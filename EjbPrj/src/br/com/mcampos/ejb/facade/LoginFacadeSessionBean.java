package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.RegisterDTO;

import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;

import br.com.mcampos.ejb.session.user.LoginSessionBean;

import br.com.mcampos.ejb.session.user.LoginSessionLocal;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless( name = "LoginFacadeSession", mappedName = "CloudSystems-EjbPrj-LoginFacadeSession" )
@Remote
@Local
public class LoginFacadeSessionBean implements LoginFacadeSession, LoginFacadeSessionLocal
{
    @EJB SystemMessagesSessionLocal systemMessage;
    @EJB LoginSessionLocal login;
    
    public LoginFacadeSessionBean()
    {
    }


    public Boolean add(RegisterDTO dto)
    {
        if ( dto == null )
            systemMessage.throwMessage( 26 );
        login.add( dto );
        return true;
    }
}

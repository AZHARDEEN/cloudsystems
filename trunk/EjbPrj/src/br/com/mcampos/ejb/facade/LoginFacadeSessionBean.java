package br.com.mcampos.ejb.facade;

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
    public LoginFacadeSessionBean()
    {
    }


    public Boolean add()
    {
        return true;
    }
}

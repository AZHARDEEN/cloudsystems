package br.com.mcampos.ejb.inep;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class InepOralFacadeBean
 */
@Stateless( name = "InepOralFacade", mappedName = "InepOralFacade" )
@LocalBean
public class InepOralFacadeBean implements InepOralFacade
{

}

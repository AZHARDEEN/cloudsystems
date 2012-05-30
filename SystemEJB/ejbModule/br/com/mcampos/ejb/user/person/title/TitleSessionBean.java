package br.com.mcampos.ejb.user.person.title;


import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;


@Stateless( name = "TitleSession", mappedName = "TitleSession" )
public class TitleSessionBean extends SimpleSessionBean<Title> implements TitleSession, TitleSessionLocal
{
	@Resource
	SessionContext sessionContext;


	@Override
	protected Class<Title> getEntityClass()
	{
		return Title.class;
	}
}

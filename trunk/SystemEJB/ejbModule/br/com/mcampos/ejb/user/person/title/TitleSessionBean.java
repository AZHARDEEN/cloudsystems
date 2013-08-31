package br.com.mcampos.ejb.user.person.title;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.user.Gender;
import br.com.mcampos.entity.user.Title;

@Stateless( name = "TitleSession", mappedName = "TitleSession" )
public class TitleSessionBean extends SimpleSessionBean<Title> implements TitleSession, TitleSessionLocal
{
	@Resource
	SessionContext sessionContext;

	@Override
	protected Class<Title> getEntityClass( )
	{
		return Title.class;
	}

	@Override
	public List<Title> getAll( Gender gender )
	{
		return findByNamedQuery( Title.getAllFromGender, gender );
	}
}

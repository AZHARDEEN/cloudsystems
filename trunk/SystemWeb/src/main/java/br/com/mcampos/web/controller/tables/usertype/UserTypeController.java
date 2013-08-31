package br.com.mcampos.web.controller.tables.usertype;


import br.com.mcampos.ejb.user.usertype.UserTypeSession;
import br.com.mcampos.entity.user.UserType;
import br.com.mcampos.web.core.SimpleTableController;


public class UserTypeController extends SimpleTableController<UserTypeSession, UserType>
{
	private static final long serialVersionUID = 5370605795536233949L;


	public UserTypeController()
	{
		super();
	}


	@Override
	protected Class<UserTypeSession> getSessionClass( )
	{
		return UserTypeSession.class;
	}
}

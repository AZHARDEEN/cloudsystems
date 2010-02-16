package br.com.mcampos.ejb.session.system;


import br.com.mcampos.ejb.core.MenuInterface;

import br.com.mcampos.ejb.core.TaskInterface;


import javax.ejb.Local;

@Local
public interface SystemSessionLocal extends MenuInterface, TaskInterface
{

}

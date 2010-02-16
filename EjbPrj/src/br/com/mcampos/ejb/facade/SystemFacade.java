package br.com.mcampos.ejb.facade;


import br.com.mcampos.ejb.core.MenuInterface;

import br.com.mcampos.ejb.core.TaskInterface;

import javax.ejb.Remote;

@Remote
public interface SystemFacade extends MenuInterface, TaskInterface
{
}

package br.com.mcampos.ejb.security.core;

import java.io.Serializable;
import java.util.List;

import br.com.mcampos.ejb.security.task.Task;

public interface TaskRelationInterface extends Serializable
{
	public List<Task> getTasks( );

	public void setTasks( List<Task> tasks );

	public Task add( Task task );

	public Task remove( Task task );

}

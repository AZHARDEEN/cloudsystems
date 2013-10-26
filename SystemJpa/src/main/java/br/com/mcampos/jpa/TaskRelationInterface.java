package br.com.mcampos.jpa;

import java.io.Serializable;
import java.util.List;

import br.com.mcampos.jpa.security.Task;

public interface TaskRelationInterface extends Serializable
{
	public List<Task> getTasks( );

	public void setTasks( List<Task> tasks );

	public Task add( Task task );

	public Task remove( Task task );

}

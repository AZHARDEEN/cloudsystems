package br.com.mcampos.ejb.cloudsystem.security.task;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.security.task.subtask.Subtask;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;


@Stateless( name = "TaskSession", mappedName = "TaskSession" )
@TransactionAttribute( TransactionAttributeType.SUPPORTS )
public class TaskSessionBean extends Crud<Integer, Task> implements TaskSessionLocal
{
	public TaskSessionBean()
	{
	}

	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public void delete( Integer key ) throws ApplicationException
	{
		if ( SysUtils.isZero( key ) )
			return;
		super.delete( Task.class, key );
	}

	public Task get( Integer key ) throws ApplicationException
	{
		if ( SysUtils.isZero( key ) )
			return null;
		return super.get( Task.class, key );
	}


	public List<Task> getAll() throws ApplicationException
	{
		return ( List<Task> )getResultList( Task.findAll );
	}


	public List<Task> getRoots() throws ApplicationException
	{
		return ( List<Task> )getResultList( Task.rootTasks );
	}

	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public void add( Task master, Task entity ) throws ApplicationException
	{
		Subtask st = new Subtask( master, entity );
		getEntityManager().persist( st );
		getEntityManager().refresh( master );
		getEntityManager().refresh( entity );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public Task update( Task entity ) throws ApplicationException
	{
		Task updated = super.update( entity );
		return updated;
	}

	public Integer getNextTaskId() throws ApplicationException
	{
		String sql;

		sql = "SELECT COALESCE ( MAX ( TSK_ID_IN ), 0 ) + 1 AS ID FROM TASK";
		Query query = getEntityManager().createNativeQuery( sql );
		try {
			return ( Integer )query.getSingleResult();
		}
		catch ( Exception e ) {
			return 1;
		}
	}
}

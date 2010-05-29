package br.com.mcampos.ejb.cloudsystem.security.task;


import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TaskUtil
{
	public TaskUtil()
	{
		super();
	}

	public static List<TaskDTO> toTaskDTOList( List<Task> list )
	{
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList();
		ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>( list.size() );
		for ( Task m : list ) {
			listDTO.add( m.toDTO() );
		}
		return listDTO;
	}

	public static TaskDTO copy( Task source )
	{
		if ( source == null )
			return null;

		TaskDTO target = new TaskDTO();

		target.setId( source.getId() );
		target.setDescription( source.getDescription() );
		return target;
	}

	public static Task createEntity( TaskDTO source )
	{
		if ( source == null )
			return null;
		Task target = new Task();
		target.setId( source.getId() );
		return update( target, source );
	}

	public static Task update( Task target, TaskDTO source )
	{
		if ( source == null )
			return null;
		target.setDescription( source.getDescription() );
		return target;
	}
}

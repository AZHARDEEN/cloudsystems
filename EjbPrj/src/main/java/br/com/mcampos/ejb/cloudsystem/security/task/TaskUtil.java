package br.com.mcampos.ejb.cloudsystem.security.task;


import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.cloudsystem.security.task.subtask.Subtask;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenu;
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


	public static List<Task> getTasks( List<Task> tasks )
	{
		List<Task> allTasks = new ArrayList<Task>();
		for ( Task Task : tasks ) {
			getTasks( Task, allTasks );
		}
		return allTasks;
	}

	public static List<Task> getTasks( Task task, List<Task> allTasks )
	{
		if ( task == null )
			return allTasks;
		if ( SysUtils.isEmpty( task.getSubtasks() ) == false ) {
			for ( Subtask subTask : task.getSubtasks() )
				getTasks( subTask.getSubTask(), allTasks );
		}
		if ( allTasks.contains( task ) == false )
			allTasks.add( task );
		return allTasks;
	}

	public static List<Menu> toMenuList( List<Task> tasks )
	{
		if ( SysUtils.isEmpty( tasks ) )
			return Collections.emptyList();
		ArrayList<Menu> menus = new ArrayList<Menu>();
		for ( Task task : tasks ) {
			if ( SysUtils.isEmpty( task.getTaskMenuList() ) )
				continue;
			for ( TaskMenu m : task.getTaskMenuList() ) {
				if ( menus.contains( m.getMenu() ) )
					continue;
				menus.add( m.getMenu() );
			}
		}
		return menus;
	}
}

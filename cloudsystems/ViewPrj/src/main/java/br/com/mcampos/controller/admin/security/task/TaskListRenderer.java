package br.com.mcampos.controller.admin.security.task;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.security.TaskDTO;

public class TaskListRenderer implements ListitemRenderer
{
	public TaskListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		item.setValue( data );
		TaskDTO dto = (TaskDTO) data;
		if ( dto != null )
			item.getChildren( ).add( new Listcell( dto.toString( ) ) );
	}
}

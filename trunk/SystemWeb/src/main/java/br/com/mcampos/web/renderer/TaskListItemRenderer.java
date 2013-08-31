package br.com.mcampos.web.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.entity.security.Task;

public class TaskListItemRenderer extends PopupItemRenderer<Task>
{
	public TaskListItemRenderer( String menuPopup )
	{
		super( menuPopup );
	}

	@Override
	public void render( Listitem item, Task data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getDescription( ) );
	}
}

package br.com.mcampos.controller.admin.system.config.task;


import br.com.mcampos.dto.security.TaskDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class TaskListRenderer implements ListitemRenderer
{
    public TaskListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        TaskDTO dto = ( TaskDTO ) data;
        if ( dto != null ) {
            item.getChildren().add( new Listcell( dto.toString() ) );
        }
    }
}

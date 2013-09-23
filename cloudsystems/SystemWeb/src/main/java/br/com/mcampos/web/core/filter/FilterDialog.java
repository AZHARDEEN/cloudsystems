package br.com.mcampos.web.core.filter;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.FieldFilterDTO;
import br.com.mcampos.web.core.BaseController;

public class FilterDialog extends BaseController<Window>
{
	private static final long serialVersionUID = 7258448014229950655L;
	public static final String fieldsParamName = "fields";

	@Wire
	private Combobox comboField;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );

		try {
			Object value;

			value = getParameter( fieldsParamName );
			if ( value instanceof List ) {
				@SuppressWarnings( "unchecked" )
				List<FieldFilterDTO> fields = (List<FieldFilterDTO>) value;
				this.comboField.setModel( new ListModelList<FieldFilterDTO>( fields ) );
			}
		}
		catch ( Exception e ) {
			e.printStackTrace( );
		}
	}

	@Listen( "onAfterRender=combobox,listbox" )
	public void onAfterRender( Event evt )
	{
		if ( evt != null ) {
			Component c = evt.getTarget( );
			if ( c instanceof Combobox ) {
				Combobox cb = (Combobox) c;
				cb.setSelectedIndex( 0 );
			}
			evt.stopPropagation( );
		}
	}

	@Listen( "onClick = addFilter" )
	public void onAddFilter( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}
}

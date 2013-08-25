package br.com.mcampos.web.controller.test;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class TestController extends SelectorComposer<Window>
{
	private static final long serialVersionUID = 4271727248673678455L;

	@Wire
	Combobox gender;

	@Wire
	MyCombo title;

	@Wire
	Combobox maritalStatus;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );

		if ( this.gender == null ) {
			Messagebox.show( "gender is not Not wired!!!" );
		}
		if ( this.title == null ) {
			Messagebox.show( "Title is not Not wired!!!" );
		}
		if ( this.maritalStatus == null ) {
			Messagebox.show( "maritalStatus is not Not wired!!!" );
		}

	}
}

package br.com.mcampos.web.controller.product;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import br.com.mcampos.ejb.product.CategorySession;
import br.com.mcampos.jpa.product.Category;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.dbwidgets.DBIntbox;
import br.com.mcampos.web.core.dbwidgets.DBTextbox;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public class CategoryController extends BaseDBListController<CategorySession, Category>
{
	private static final long serialVersionUID = 982452702734832618L;

	@Wire
	private Label infoId;

	@Wire
	private Label infoName;

	@Wire
	private Label infoDescription;

	@Wire
	private DBIntbox id;

	@Wire
	private DBTextbox name;

	@Wire
	private DBTextbox description;

	@Override
	protected Class<CategorySession> getSessionClass( )
	{
		return CategorySession.class;
	}

	@Override
	protected void showFields( Category targetEntity )
	{
		if ( targetEntity != null ) {
			id.setValue( targetEntity.getCategoryId( ) );
			if ( targetEntity.getCategoryId( ) != null )
				infoId.setValue( targetEntity.getCategoryId( ).toString( ) );
			else
				infoId.setValue( "" );
			name.setValue( targetEntity.getName( ) );
			infoName.setValue( targetEntity.getName( ) );
			description.setValue( targetEntity.getDescription( ) );
			infoDescription.setValue( targetEntity.getDescription( ) );
		}
		else {
			id.setValue( 0 );
			infoId.setValue( "" );
			name.setValue( "" );
			infoName.setValue( "" );
			description.setValue( "" );
			infoDescription.setValue( "" );
		}
	}

	@Override
	protected void updateTargetEntity( Category entity )
	{
		if ( entity != null ) {
			entity.getId( ).setCompanyId( getPrincipal( ).getCompanyID( ) );
			entity.getId( ).setId( id.getValue( ) );
			entity.setName( name.getValue( ) );
			entity.setDescription( description.getValue( ) );
		}
	}

	@Override
	protected boolean validateEntity( Category entity, int operation )
	{
		if ( entity == null )
			return false;
		if ( SysUtils.isEmpty( entity.getName( ) ) ) {
			Messagebox.show( "O nome da categoria não pode estar em branco", "Categoria", Messagebox.OK, Messagebox.INFORMATION );
			return false;
		}
		return true;
	}

}

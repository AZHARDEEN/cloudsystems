package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.PageFieldRowRenderer;
import br.com.mcampos.controller.anoto.util.IAnotoPageFieldEvent;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.facade.AnotoPageFieldFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;


public class AnotoFieldController extends LoggedBaseController implements IAnotoPageFieldEvent
{
    private Combobox cmbForms;

    private AnotoPageFieldFacade session;

    private Label labelAnotoFormTitle;

    private Grid gridFields;

    public AnotoFieldController( char c )
    {
        super( c );
    }

    public AnotoFieldController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        List<FormDTO> forms = getSession().getForms( getLoggedInUser() );
        loadCombobox( cmbForms, forms );
        setLabel( labelAnotoFormTitle );
        gridFields.setRowRenderer( new PageFieldRowRenderer( this, getSession().getFieldTypes() ) );
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "labelAnotoFieldTitle" );
    }

    public AnotoPageFieldFacade getSession()
    {
        if ( session == null )
            session = ( AnotoPageFieldFacade )getRemoteSession( AnotoPageFieldFacade.class );
        return session;
    }

    public void onSelect$cmbForms()
    {
        Comboitem item = cmbForms.getSelectedItem();
        if ( item != null && item.getValue() != null ) {
            FormDTO formDto = ( FormDTO )item.getValue();
            loadFields( formDto );
        }
    }

    private void loadFields( FormDTO formDto )
    {
        getModel().clear();
        if ( formDto == null )
            return;
        try {
            getModel().addAll( getSession().getFields( getLoggedInUser(), formDto.getId() ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    private List getList()
    {
        return Collections.emptyList();
    }


    private ListModelList getModel()
    {
        ListModelList model = ( ListModelList )gridFields.getModel();
        if ( model == null ) {
            model = new ListModelList( new ArrayList<AnotoPageFieldDTO>(), true );
            gridFields.setModel( model );
        }
        return model;
    }


    public void onCheck( org.zkoss.zk.ui.event.CheckEvent evt )
    {
        if ( evt.getTarget() != null && evt.getTarget() instanceof Checkbox ) {
            Checkbox target = ( Checkbox )evt.getTarget();
            if ( target.getParent() instanceof Row ) {
                Row row = ( ( Row )target.getParent() );
                Object value = row.getValue();
                if ( value instanceof AnotoPageFieldDTO ) {
                    AnotoPageFieldDTO dto = ( AnotoPageFieldDTO )value;
                    String attr = ( String )target.getAttribute( "field" );
                    if ( attr != null && attr.equalsIgnoreCase( "icr" ) )
                        dto.setIcr( evt.isChecked() );
                    if ( attr != null && attr.equalsIgnoreCase( "export" ) )
                        dto.setExport( evt.isChecked() );
                    tryUpdate( dto );
                }
            }
        }
    }

    public void onSelect( org.zkoss.zk.ui.event.SelectEvent evt )
    {
        if ( evt.getTarget() != null && evt.getTarget() instanceof Combobox ) {
            Combobox target = ( Combobox )evt.getTarget();
            if ( target.getParent() instanceof Row ) {
                Row row = ( ( Row )target.getParent() );
                Object value = row.getValue();
                if ( value instanceof AnotoPageFieldDTO ) {
                    Comboitem item = ( Comboitem )evt.getReference();
                    FieldTypeDTO type = ( FieldTypeDTO )item.getValue();
                    AnotoPageFieldDTO dto = ( AnotoPageFieldDTO )value;
                    dto.setType( type );
                    tryUpdate( dto );
                }
            }
        }
    }


    protected void tryUpdate( AnotoPageFieldDTO dto )
    {
        try {
            getSession().update( getLoggedInUser(), dto );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Atualizar Campo" );
        }
    }
}

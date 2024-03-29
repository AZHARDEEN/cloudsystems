package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.PenUserRowRenderer;
import br.com.mcampos.controller.anoto.util.PenUserSearchBox;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PenUserDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.facade.PenPageUserFacade;
import br.com.mcampos.util.system.IClickEvent;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.Toolbarbutton;


public class AnotoPenUserController extends LoggedBaseController implements IClickEvent
{
    private PenPageUserFacade session;
    private Column columnPen;
    private Column columnUser;
    private Column columnEmail;
    private Column columnFromDate;

    private Combobox cmbForms;
    private Label labelAnotoFormTitle;
    private Grid gridFields;
    private Checkbox chkActivePens;

    public AnotoPenUserController( char c )
    {
        super( c );
    }

    public AnotoPenUserController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( columnPen );
        setLabel( labelAnotoFormTitle );
        setLabel( chkActivePens );
        setLabel( columnUser );
        setLabel( columnEmail );
        setLabel( columnFromDate );
        List<FormDTO> forms = getSession().getForms( getLoggedInUser() );
        loadCombobox( cmbForms, forms );
        if ( cmbForms.getItemCount() > 0 ) {
            cmbForms.setSelectedIndex( 0 );
            getPens();
        }
        gridFields.setRowRenderer( new PenUserRowRenderer( this ) );
        chkActivePens.setVisible( false );
    }

    public void onSelect$cmbForms()
    {
        getPens();
    }

    public void onCheck$chkActivePens()
    {
        getPens();
    }


    private void getPens()
    {
        if ( cmbForms.getSelectedItem() == null )
            return;
        FormDTO form = ( FormDTO )cmbForms.getSelectedItem().getValue();
        Boolean bActive = ( chkActivePens != null ) ? chkActivePens.isChecked() : false;
        if ( form == null )
            return;
        try {
            List<PenUserDTO> list = getSession().getAll( getLoggedInUser(), form.getId(), bActive );
            ListModelList model = getModel();
            model.clear();
            model.addAll( list );
            gridFields.invalidate();
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "anotoPenUserTitle" );
    }


    private PenPageUserFacade getSession()
    {
        if ( session == null )
            session = ( PenPageUserFacade )getRemoteSession( PenPageUserFacade.class );
        return session;
    }

    private ListModelList getModel()
    {
        ListModelList model = ( ListModelList )gridFields.getModel();
        if ( model == null ) {
            model = new ListModelList( new ArrayList<PenUserDTO>(), true );
            gridFields.setModel( model );
        }
        return model;
    }

    public void onClick( MouseEvent evt )
    {
        PenUserDTO dto = null;
        try {
            Toolbarbutton button = ( Toolbarbutton )evt.getTarget();
            if ( button != null ) {
                Row row = ( Row )button.getParent();
                if ( row != null )
                    dto = ( PenUserDTO )row.getValue();
            }
            if ( dto == null )
                return;
            FormDTO form = ( FormDTO )cmbForms.getSelectedItem().getValue();
            if ( form != null && dto != null ) {
                ListUserDTO newUser = ( ListUserDTO )PenUserSearchBox.show( getLoggedInUser(), getRootParent(), form );
                getSession().changeUser( getLoggedInUser(), dto.getPenId(), newUser.getId() );
                getPens();
            }
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
            return;
        }
    }
}

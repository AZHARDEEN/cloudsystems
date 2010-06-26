package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.controller.anoto.model.PenIdComparator;
import br.com.mcampos.controller.anoto.renderer.AnotoPenListRenderer;
import br.com.mcampos.dto.anoto.LinkedUserDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.facade.AnotoPenFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;


public class AnotoPenController extends BasicListController<PenDTO>
{
    private Textbox editId;
    private Label recordId;


    private Textbox editDescription;
    private Label recordDescription;
    private Label recordPenUser;
    private Intbox editUserId;
    private Textbox editUserDocumentCode;
    private Textbox editUserName;

    private AnotoPenFacade session;

    private Listheader headerPenId;
    private Label labelPenTitle;
    private Label labelCode;
    private Label labelDescription;
    private Label labelEditCode;
    private Label labelEditDescription;
    private Label labelPenUser;
    private Label labelEditPenUser;
    private Label labelUserDocument;
    private Label labelUserCode;

    private ListUserDTO penUser;


    public AnotoPenController()
    {
        super();
    }

    protected AnotoPenFacade getSession()
    {
        if ( session == null )
            session = ( AnotoPenFacade )getRemoteSession( AnotoPenFacade.class );
        return session;
    }

    @Override
    protected void showRecord( PenDTO record )
    {
        if ( record != null ) {
            recordId.setValue( record.getId() );
            recordDescription.setValue( record.getDescription() );
            if ( record.getUser() != null && record.getUser().getUser() != null )
                recordPenUser.setValue( record.getUser().getUser().getName() );
            else
                recordPenUser.setValue( "" );
        }
        else {
            recordId.setValue( "" );
            recordDescription.setValue( "" );
            recordPenUser.setValue( "" );
        }
    }

    @Override
    protected Object createNewRecord()
    {
        return new PenDTO();
    }

    protected PenDTO copyTo( PenDTO dto )
    {
        LinkedUserDTO user = null;
        dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue().trim() );
        if ( penUser != null ) {
            user = new LinkedUserDTO();
            user.setUser( penUser );
        }
        dto.setUser( user );
        return dto;
    }

    @Override
    public ListitemRenderer getRenderer()
    {
        return new AnotoPenListRenderer();
    }

    @Override
    protected List getRecordList() throws ApplicationException
    {
        showRecord( null );
        return getSession().getPens( getLoggedInUser() );
    }

    @Override
    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( PenDTO )currentRecord );
    }

    @Override
    protected Object saveRecord( Object record )
    {
        PenDTO dto = ( PenDTO )record;
        copyTo( dto );
        return dto;
    }

    @Override
    protected void clearRecordInfo()
    {
        editId.setValue( "" );
        editDescription.setValue( "" );
        editUserId.setValue( 0 );
        editUserDocumentCode.setValue( "" );
        editUserId.setValue( 0 );
        editUserName.setValue( "" );
        editUserDocumentCode.setValue( "" );

    }

    @Override
    protected void prepareToInsert()
    {
        clearRecordInfo();
        editId.setFocus( true );
        editId.setDisabled( false );
    }

    @Override
    protected Object prepareToUpdate( Object currentRecord )
    {
        PenDTO dto = ( PenDTO )currentRecord;

        editId.setValue( dto.getId() );
        editDescription.setValue( dto.getDescription() );
        ListUserDTO user = ( dto.getUser() != null ) ? dto.getUser().getUser() : null;
        editUserId.setValue( user != null ? user.getId() : 0 );
        editUserDocumentCode.setValue( "" );
        editUserName.setValue( user != null ? user.getName() : "" );
        editDescription.setFocus( true );
        editId.setDisabled( true );
        return dto;
    }

    @Override
    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( PenDTO )e );
        else
            getSession().update( getLoggedInUser(), ( PenDTO )e );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        headerPenId.setSortAscending( new PenIdComparator( true ) );
        headerPenId.setSortDescending( new PenIdComparator( false ) );

        setLabel( headerPenId );
        setLabel( labelPenTitle );
        setLabel( labelCode );
        setLabel( labelDescription );
        setLabel( labelEditCode );
        setLabel( labelEditDescription );
        setLabel( labelPenUser );
        setLabel( labelEditPenUser );
        setLabel( labelUserCode );
        setLabel( labelUserDocument );

    }

    public void onOK$editUserId() throws ApplicationException
    {
        Integer id = editUserId.getValue();

        penUser = getSession().findUser( getLoggedInUser(), id );
        showUser( penUser );
    }

    public void onOK$editUserDocumentCode() throws ApplicationException
    {
        String email = editUserDocumentCode.getValue();
        penUser = getSession().findUserByEmail( getLoggedInUser(), email );
        showUser( penUser );
    }

    private ListUserDTO showUser( ListUserDTO dto )
    {
        editUserName.setValue( dto != null ? dto.getName() : "" );
        editUserId.setValue( dto != null ? dto.getId() : 0 );
        return dto;
    }


}

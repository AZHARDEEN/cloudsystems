package br.com.mcampos.controller.admin.users;

import br.com.mcampos.controller.admin.users.model.BaseListModel;
import br.com.mcampos.controller.core.LoggedBaseController;

import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.user.CompanyDTO;

import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.business.UsersLocator;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.event.PagingEvent;

public abstract class BaseUserListController extends LoggedBaseController
{
    private Paging userUpperPaging;
    private Paging userLowerPaging;
    protected Listbox dataList;


    private Integer _startPageNumber = 0;
    private final Integer _pageSize = 50;
    private Integer _totalSize = 0;
    private Boolean _needsTotalSizeUpdate = true;

    private UsersLocator locator;


    /*Non editable fields*/
    Label userId;
    Label name;
    Label firstName;
    Label lastName;
    Label userType;
    Label userStatus;
    Label birthDate;
    Label fatherName;
    Label motherName;
    Label civilState;
    Label gender;
    Label title;
    Label bornCity;
    Label nickName;


    Label addressType;
    Label address;
    Label hood;
    Label city;
    Label state;
    Label zip;
    Label labelNickName;
    Label labelName;


    Row rowId;
    Row rowName;
    Row rowFirstName;
    Row rowLastName;
    Row rowUserType;
    Row rowBirthDate;
    Row rowFatherName;
    Row rowMotherName;
    Row rowCivilState;
    Row rowGender;
    Row rowTitle;
    Row rowBornCity;
    Row rowNickName;


    Grid documentGrid;
    Grid contactGrid;


    public BaseUserListController( char c )
    {
        super( c );
    }

    public BaseUserListController()
    {
        super();
    }

    protected abstract BaseListModel getModel( int activePage, int pageSize );

    protected abstract ListitemRenderer getRenderer();

    protected abstract void showInformation( Object obj ) throws ApplicationException;


    protected void setPageSize( Integer pageSize )
    {
        if ( getUserUpperPaging() != null )
            getUserUpperPaging().setPageSize( pageSize );
        if ( getUserLowerPaging() != null )
            getUserLowerPaging().setPageSize( pageSize );
    }

    protected void setTotalSize( Integer size )
    {
        if ( getUserUpperPaging() != null )
            getUserUpperPaging().setTotalSize( size );
        if ( getUserLowerPaging() != null )
            getUserLowerPaging().setTotalSize( size );
    }

    public void setUserUpperPaging( Paging userUpperPaging )
    {
        this.userUpperPaging = userUpperPaging;
    }

    public Paging getUserUpperPaging()
    {
        return userUpperPaging;
    }

    public void setUserLowerPaging( Paging userLowerPaging )
    {
        this.userLowerPaging = userLowerPaging;
    }

    public Paging getUserLowerPaging()
    {
        return userLowerPaging;
    }


    protected void setPageInfo( ForwardEvent event )
    {
        final PagingEvent pe = ( PagingEvent )event.getOrigin();


        setStartPageNumber( pe.getActivePage() );
        refreshModel( getStartPageNumber() );
    }

    public Integer getStartPageNumber()
    {
        return _startPageNumber;
    }

    public void setStartPageNumber( Integer pageNumber )
    {
        _startPageNumber = pageNumber;
    }

    public Integer getPageSize()
    {
        return _pageSize;
    }


    public void setDataList( Listbox dataGrid )
    {
        this.dataList = dataGrid;
    }

    public Listbox getDataList()
    {
        return dataList;
    }


    public void onPaging$userUpperPaging( ForwardEvent event )
    {
        setPageInfo( event );
    }

    public void onPaging$userLowerPaging( ForwardEvent event )
    {
        setPageInfo( event );
    }

    protected void refreshModel( int activePage )
    {
        BaseListModel model;


        setPageSize( getPageSize() );
        model = getModel( activePage, getPageSize() );
        if ( _needsTotalSizeUpdate ) {
            _totalSize = model.getTotalSize();
            _needsTotalSizeUpdate = false;
        }
        setTotalSize( _totalSize );
        dataList.setModel( model );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        showInfo( false );
        getDataList().setItemRenderer( getRenderer() );
        refreshModel( getStartPageNumber() );
    }


    public void onSelect$dataList()
    {
        Listitem item = getDataList().getSelectedItem();

        if ( item != null ) {
            Object obj = item.getValue();
            if ( obj != null )
                try {
                    showInformation( obj );
                }
                catch ( ApplicationException e ) {
                    e = null;
                }
        }
    }

    public void setTotalSize1( Integer _totalSize )
    {
        this._totalSize = _totalSize;
    }

    public Integer getTotalSize()
    {
        return _totalSize;
    }

    public void setNeedsTotalSizeUpdate( Boolean _needsTotalSizeUpdate )
    {
        this._needsTotalSizeUpdate = _needsTotalSizeUpdate;
    }

    public Boolean getNeedsTotalSizeUpdate()
    {
        return _needsTotalSizeUpdate;
    }

    public void setUserId( Integer id )
    {
        getUserId().setValue( id.toString() );
    }

    public void setUserId( Label userId )
    {
        this.userId = userId;
    }

    public Label getUserId()
    {
        return userId;
    }

    public void setName( Label name )
    {
        this.name = name;
    }

    public void setName( String name )
    {
        getName().setValue( name );
    }

    public Label getName()
    {
        return name;
    }

    public void setFirstName( Label firstName )
    {
        this.firstName = firstName;
    }

    public Label getFirstName()
    {
        return firstName;
    }

    public void setLastName( Label lastName )
    {
        this.lastName = lastName;
    }

    public Label getLastName()
    {
        return lastName;
    }

    public void setUserType( Label userType )
    {
        this.userType = userType;
    }

    public Label getUserType()
    {
        return userType;
    }

    public void setUserStatus( Label userStatus )
    {
        this.userStatus = userStatus;
    }

    public void setUserStatus( String userStatus )
    {
        getUserStatus().setValue( userStatus );
    }

    public Label getUserStatus()
    {
        return userStatus;
    }

    public void setBirthDate( Label birthDate )
    {
        this.birthDate = birthDate;
    }

    public Label getBirthDate()
    {
        return birthDate;
    }

    public void setFatherName( Label fatherName )
    {
        this.fatherName = fatherName;
    }

    public Label getFatherName()
    {
        return fatherName;
    }

    public void setMotherName( Label motherName )
    {
        this.motherName = motherName;
    }

    public Label getMotherName()
    {
        return motherName;
    }

    public void setCivilState( Label civilState )
    {
        this.civilState = civilState;
    }

    public Label getCivilState()
    {
        return civilState;
    }

    public void setGender( Label gender )
    {
        this.gender = gender;
    }

    public Label getGender()
    {
        return gender;
    }

    public void setTitle( Label title )
    {
        this.title = title;
    }

    public Label getTitle()
    {
        return title;
    }

    public void setBornCity( Label bornCity )
    {
        this.bornCity = bornCity;
    }

    public Label getBornCity()
    {
        return bornCity;
    }

    public void setLocator( UsersLocator locator )
    {
        this.locator = locator;
    }

    public UsersLocator getUserLocator()
    {
        if ( locator == null )
            locator = new UsersLocator();
        return locator;
    }

    protected void showUserInfo( UserDTO dto )
    {
        setName( dto.getName() );
        setUserId( dto.getId() );
        if ( dto.getUserType() != null )
            userType.setValue( dto.getUserType().getDisplayName() );
        else
            userType.setValue( "" );

        if ( labelNickName != null )
            labelNickName.setValue( ( dto instanceof PersonDTO ) ? "Apelido" : "Nome Fantasia" );
        if ( labelName != null )
            labelNickName.setValue( ( dto instanceof PersonDTO ) ? "Nome" : "Razão Social" );


        if ( SysUtils.isNull( rowNickName ) == false ) {
            if ( dto.getNickName() != null && dto.getNickName().equals( dto.getName() ) == false ) {
                rowNickName.setVisible( true );
                nickName.setValue( dto.getNickName() );
            }
            else {
                rowNickName.setVisible( false );
            }
        }

        if ( dto.getAddressList().size() == 0 ) {
            addressType.setValue( "" );
            address.setValue( "" );
            hood.setValue( "" );
            city.setValue( "" );
            state.setValue( "" );
            zip.setValue( "" );
        }
        for ( AddressDTO address : dto.getAddressList() ) {
            showAddressInfo( address );
            /*
             * TODO verificar como fazer para mostrar mais de um endereço
             */
            break;
        }

        Row newRow;

        if ( contactGrid != null ) {
            if ( contactGrid.getRows() == null )
                contactGrid.appendChild( new Rows() );
            contactGrid.getRows().getChildren().clear();
            for ( UserContactDTO item : dto.getContactList() ) {
                newRow = new Row();
                showContactInfo( newRow, item );
                contactGrid.getRows().appendChild( newRow );
            }
        }
        if ( documentGrid != null ) {
            if ( documentGrid.getRows() == null )
                documentGrid.appendChild( new Rows() );
            documentGrid.getRows().getChildren().clear();
            for ( UserDocumentDTO item : dto.getDocumentList() ) {
                newRow = new Row();
                showDocumentInfo( newRow, item );
                documentGrid.getRows().appendChild( newRow );
            }
        }
    }

    protected void showInfo( boolean bPerson )
    {
        rowFirstName.setVisible( bPerson );
        rowLastName.setVisible( bPerson );
        rowBirthDate.setVisible( bPerson );
        rowFatherName.setVisible( bPerson );
        rowMotherName.setVisible( bPerson );
        rowCivilState.setVisible( bPerson );
        rowGender.setVisible( bPerson );
        rowTitle.setVisible( bPerson );
        rowBornCity.setVisible( bPerson );
    }

    protected void showCompanyInfo( CompanyDTO dto )
    {
        showUserInfo( dto );
        showInfo( false );
    }


    protected void showPersonInfo( PersonDTO dto )
    {
        showUserInfo( dto );
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd/MM/yyyy" );

        firstName.setValue( dto.getFirstName() );
        lastName.setValue( dto.getLastName() );
        if ( dto.getBirthDate() != null )
            birthDate.setValue( simpleDateFormat.format( new Date( dto.getBirthDate().getTime() ) ) );
        else
            birthDate.setValue( "" );
        fatherName.setValue( dto.getFatherName() );
        motherName.setValue( dto.getMotherName() );
        civilState.setValue( ( dto.getCivilState() != null ) ? dto.getCivilState().getDisplayName() : "" );
        gender.setValue( ( dto.getGender() != null ) ? dto.getGender().getDisplayName() : "" );
        title.setValue( ( dto.getTitle() != null ) ? dto.getTitle().getDisplayName() : "" );
        if ( dto.getBornCity() != null )
            bornCity.setValue( dto.getBornCity().getDisplayName() );
        else
            bornCity.setValue( "" );
        showInfo( true );
    }

    protected void showAddressInfo( AddressDTO dto )
    {
        addressType.setValue( ( dto.getAddressType() != null ) ? dto.getAddressType().getDescription() : "" );
        address.setValue( dto.getAddress() );
        hood.setValue( dto.getDistrict() );
        city.setValue( dto.getCity() != null ? dto.getCity().getDisplayName() : "" );
        state.setValue( dto.getCity().getState() != null ? dto.getCity().getState().getDisplayName() : "" );
        zip.setValue( dto.getZip() );
    }


    protected void showContactInfo( Row row, UserContactDTO dto )
    {
        if ( row == null )
            return;

        row.appendChild( new Label( ( dto.getContactType() != null ) ? dto.getContactType().getDisplayName() : "" ) );
        row.appendChild( new Label( dto.getDescription() ) );
    }


    protected void showDocumentInfo( Row row, UserDocumentDTO dto )
    {
        if ( row == null )
            return;

        row.appendChild( new Label( ( dto.getDocumentType() != null ) ? dto.getDocumentType().getDisplayName() : "" ) );
        row.appendChild( new Label( dto.getCode() ) );
    }
}

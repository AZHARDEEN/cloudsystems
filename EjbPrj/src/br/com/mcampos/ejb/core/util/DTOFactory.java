package br.com.mcampos.ejb.core.util;


import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.system.SystemParametersDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pgc;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcField;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPage;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.security.entity.Menu;
import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.ejb.cloudsystem.system.entity.FieldType;
import br.com.mcampos.ejb.entity.address.AddressType;
import br.com.mcampos.ejb.entity.address.City;
import br.com.mcampos.ejb.entity.address.Country;
import br.com.mcampos.ejb.entity.address.Region;
import br.com.mcampos.ejb.entity.address.State;
import br.com.mcampos.ejb.entity.login.AccessLogType;
import br.com.mcampos.ejb.entity.login.Login;
import br.com.mcampos.ejb.entity.system.SystemParameters;
import br.com.mcampos.ejb.entity.user.Address;
import br.com.mcampos.ejb.entity.user.Company;
import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.UserContact;
import br.com.mcampos.ejb.entity.user.UserDocument;
import br.com.mcampos.ejb.entity.user.Users;
import br.com.mcampos.ejb.entity.user.attributes.CivilState;
import br.com.mcampos.ejb.entity.user.attributes.CompanyPosition;
import br.com.mcampos.ejb.entity.user.attributes.CompanyType;
import br.com.mcampos.ejb.entity.user.attributes.ContactType;
import br.com.mcampos.ejb.entity.user.attributes.DocumentType;
import br.com.mcampos.ejb.entity.user.attributes.Gender;
import br.com.mcampos.ejb.entity.user.attributes.Title;
import br.com.mcampos.ejb.entity.user.attributes.UserStatus;
import br.com.mcampos.ejb.entity.user.attributes.UserType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


public final class DTOFactory implements Serializable
{
    public DTOFactory()
    {
        super();
    }


    public static Person copy( PersonDTO dto )
    {
        Person person;

        person = new Person();

        /*Copy users base class*/
        copy( person, dto, true );
        person.setCivilState( copy( dto.getCivilState() ) );
        person.setGender( copy( dto.getGender() ) );
        person.setBirthDate( dto.getBirthDate() );
        person.setTitle( copy( dto.getTitle() ) );
        person.setFirstName( dto.getFirstName() );
        person.setMiddleName( dto.getMiddleName() );
        person.setLastName( dto.getLastName() );
        person.setFatherName( dto.getFatherName() );
        person.setMotherName( dto.getMotherName() );
        person.setBornCity( copy( dto.getBornCity() ) );


        return person;
    }

    public static Person copy( Person person, PersonDTO dto )
    {
        copy( person, dto, false );

        person.setBirthDate( dto.getBirthDate() );
        person.setBornCity( copy( dto.getBornCity() ) );
        person.setCivilState( copy( dto.getCivilState() ) );
        person.setFatherName( dto.getFatherName() );
        person.setGender( copy( dto.getGender() ) );
        person.setInsertDate( null );
        person.setMotherName( dto.getMotherName() );
        person.setTitle( copy( dto.getTitle() ) );
        return person;
    }


    public static Person copy( RegisterDTO dto )
    {
        Person person;

        person = new Person( dto.getName() );
        copyDocuments( person, dto );
        return person;
    }

    protected static UserDTO copy( UserDTO dto, Users entity )
    {
        dto.setName( entity.getName() );
        dto.setNickName( entity.getNickName() );
        dto.setUserType( copy( entity.getUserType() ) );
        dto.setId( entity.getId() );

        for ( Address item : entity.getAddresses() )
            dto.add( copy( item ) );
        for ( UserDocument item : entity.getDocuments() )
            dto.add( copy( item ) );
        for ( UserContact item : entity.getContacts() )
            dto.add( copy( item ) );


        return dto;
    }


    public static PersonDTO copy( Person entity, Boolean mustCopyLogin )
    {
        PersonDTO person = new PersonDTO();

        copy( person, entity );
        person.setBirthDate( entity.getBirthDate() );
        person.setBornCity( copy( entity.getBornCity() ) );
        person.setFirstName( entity.getFirstName() );
        person.setMiddleName( entity.getMiddleName() );
        person.setLastName( entity.getLastName() );
        person.setFatherName( entity.getFatherName() );
        person.setMotherName( entity.getMotherName() );
        person.setCivilState( copy( entity.getCivilState() ) );
        person.setGender( copy( entity.getGender() ) );
        person.setTitle( copy( entity.getTitle() ) );
        if ( mustCopyLogin )
            person.setLogin( copy( entity.getLogin(), false ) );
        return person;
    }


    public static AddressDTO copy( Address entity )
    {

        AddressDTO addr = new AddressDTO();

        addr.setAddress( entity.getAddress() );
        addr.setAddressType( copy( entity.getAddressType() ) );
        addr.setCity( copy( entity.getCity() ) );
        addr.setComment( entity.getComment() );
        addr.setDistrict( entity.getDistrict() );
        addr.setZip( entity.getZip() );

        return addr;
    }


    public static UserDocumentDTO copy( UserDocument entity )
    {
        UserDocumentDTO dto = new UserDocumentDTO();
        dto.setDocumentType( copy( entity.getDocumentType() ) );
        dto.setCode( entity.getCode() );
        dto.setAdditionalInfo( entity.getAdditionalInfo() );
        return dto;
    }


    public static UserContactDTO copy( UserContact entity )
    {
        UserContactDTO dto = new UserContactDTO();
        dto.setContactType( copy( entity.getContactType() ) );
        dto.setDescription( entity.getDescription() );
        dto.setComment( entity.getComment() );
        return dto;
    }


    public static Address copy( AddressDTO dto )
    {

        Address entity = new Address();
        entity.setAddress( dto.getAddress() );
        entity.setDistrict( dto.getDistrict() );
        entity.setComment( dto.getComment() );
        entity.setZip( dto.getZip() );
        entity.setAddressType( copy( dto.getAddressType() ) );
        entity.setCity( copy( dto.getCity() ) );
        return entity;
    }


    public static Address copy( Address address, AddressDTO dto )
    {
        address.setAddress( dto.getAddress() );
        address.setDistrict( dto.getDistrict() );
        address.setComment( dto.getComment() );
        address.setZip( dto.getZip() );
        address.setCity( copy( dto.getCity() ) );
        return address;
    }


    protected static String applyDocumentRules( String document, int type )
    {
        String code;


        code = document.trim();
        switch ( type ) {
        case UserDocument.typeCPF: code = code.replaceAll( "[\\-.\\/]", "" );
            break;
        case UserDocument.typeEmail: code = code.toLowerCase();
            break;
        }
        return code;
    }

    public static UserDocument copy( UserDocumentDTO dto )
    {
        UserDocument entity = new UserDocument();
        entity.setCode( applyDocumentRules( dto.getCode(), dto.getDocumentType().getId() ) );
        entity.setAdditionalInfo( dto.getAdditionalInfo() );
        entity.setDocumentType( copy( dto.getDocumentType() ) );
        return entity;
    }


    public static UserDocument copy( UserDocument entity, UserDocumentDTO dto )
    {
        String code = applyDocumentRules( dto.getCode(), dto.getDocumentType().getId() );
        entity.setCode( code );
        entity.setAdditionalInfo( dto.getAdditionalInfo() );
        return entity;
    }


    public static UserContact copy( UserContactDTO dto )
    {
        UserContact entity = new UserContact();
        entity.setContactType( copy( dto.getContactType() ) );
        entity.setDescription( dto.getDescription() );
        entity.setComment( dto.getComment() );
        return entity;
    }


    public static UserContact copy( UserContact entity, UserContactDTO dto )
    {
        entity.setContactType( copy( dto.getContactType() ) );
        entity.setDescription( dto.getDescription() );
        entity.setComment( dto.getComment() );
        return entity;
    }


    public static AddressTypeDTO copy( AddressType entity )
    {
        return new AddressTypeDTO( entity.getId(), entity.getDescription() );
    }

    public static AddressType copy( AddressTypeDTO dto )
    {
        return new AddressType( dto.getId(), dto.getDescription() );
    }


    public static CityDTO copy( City entity )
    {
        CityDTO city;


        if ( entity == null )
            return null;
        city = new CityDTO();
        city.setId( entity.getId() );
        city.setName( entity.getName() );
        city.setCountryCapital( entity.isCountryCapital() );
        city.setStateCapital( entity.isStateCapital() );
        city.setState( copy( entity.getState() ) );
        return city;
    }

    public static City copy( CityDTO dto )
    {
        City city = null;

        if ( dto != null ) {
            city = new City();
            city.setId( dto.getId() );
            if ( dto.getState() != null )
                city.setState( copy( dto.getState() ) );
        }
        return city;
    }


    public static Country copy( CountryDTO dto )
    {
        return new Country( dto.getId(), dto.getCode3(), dto.getCode(), dto.getNumericCode() );
    }

    public static CountryDTO copy( Country entity )
    {
        CountryDTO dto = new CountryDTO();
        dto.setId( entity.getId() );
        dto.setCode3( entity.getCode3() );
        dto.setCode( entity.getCode() );
        dto.setNumericCode( entity.getNumericCode() );
        return dto;
    }


    public static State copy( StateDTO dto )
    {
        State entity;
        Region region;
        Country country;

        country = new Country( dto.getCountryId() );
        region = new Region( country, dto.getRegionId() );
        entity = new State( region, dto.getId(), dto.getAbbreviation(), dto.getName() );
        return entity;
    }

    public static StateDTO copy( State entity )
    {
        StateDTO dto;

        if ( entity == null )
            return null;

        dto = new StateDTO();
        dto.setCountryId( entity.getCountryId() );
        dto.setRegionId( entity.getRegionId() );
        dto.setId( entity.getId() );
        dto.setName( entity.getName() );
        dto.setAbbreviation( entity.getAbbreviation() );
        return dto;
    }


    public static CivilState copy( CivilStateDTO dto )
    {
        return new CivilState( dto.getId(), dto.getDescription() );
    }

    public static CivilStateDTO copy( CivilState entity )
    {
        if ( entity != null )
            return new CivilStateDTO( entity.getId(), entity.getDescription() );
        else
            return null;
    }


    public static ContactType copy( ContactTypeDTO dto )
    {
        return new ContactType( dto.getId(), dto.getDescription(), dto.getMask(), dto.getAllowDuplicate() );
    }

    public static ContactTypeDTO copy( ContactType entity )
    {
        return new ContactTypeDTO( entity.getId(), entity.getDescription(), entity.getMask(), entity.getAllowDuplicate() );
    }

    public static DocumentType copy( DocumentTypeDTO dto )
    {
        return new DocumentType( dto.getId(), dto.getName(), dto.getMask() );
    }


    public static DocumentTypeDTO copy( DocumentType entity )
    {
        return new DocumentTypeDTO( entity.getId(), entity.getName(), entity.getMask() );
    }


    public static Gender copy( GenderDTO dto )
    {
        Gender gender;
        ArrayList<Title> titles;

        if ( dto == null )
            return null;
        gender = new Gender( dto.getId(), dto.getDescription() );
        if ( dto.getTitles() != null ) {
            titles = new ArrayList<Title>( dto.getTitles().size() );
            for ( TitleDTO title : dto.getTitles() ) {
                titles.add( copy( title ) );
            }
            gender.setTitles( titles );
        }
        return gender;
    }

    public static GenderDTO copy( Gender entity )
    {
        if ( entity == null )
            return null;

        ArrayList<TitleDTO> titles = new ArrayList<TitleDTO>( entity.getTitles().size() );

        for ( Title title : entity.getTitles() ) {
            titles.add( copy( title ) );
        }
        return new GenderDTO( entity.getId(), entity.getDescription(), titles );
    }


    public static Title copy( TitleDTO dto )
    {
        return new Title( dto.getId(), dto.getDescription(), dto.getAbbreviation() );
    }

    public static TitleDTO copy( Title entity )
    {
        if ( entity != null )
            return new TitleDTO( entity.getId(), entity.getDescription(), entity.getAbbreviation() );
        else
            return null;
    }

    public static UserStatus copy( UserStatusDTO dto )
    {
        UserStatus entity = new UserStatus();

        entity.setId( dto.getId() );
        entity.setDescription( dto.getDescription() );
        entity.setAllowLogin( dto.getAllowLogin() );
        return entity;
    }


    public static UserStatusDTO copy( UserStatus entity )
    {
        UserStatusDTO dto = new UserStatusDTO();

        dto.setId( entity.getId() );
        dto.setDescription( entity.getDescription() );
        dto.setAllowLogin( entity.getAllowLogin() );
        return dto;
    }


    public static UserTypeDTO copy( UserType entity )
    {
        if ( entity != null )
            return new UserTypeDTO( Integer.parseInt( entity.getId() ), entity.getDescription() );
        else
            return null;
    }

    public static UserType copy( UserTypeDTO dto )
    {
        return new UserType( dto.getId(), dto.getDescription() );
    }

    public static Login copy( LoginDTO dto )
    {
        Login login = new Login();


        login.setPassword( dto.getPassword() );
        login.setPasswordExpirationDate( dto.getPasswordExpirationDate() );
        login.setUserStatus( copy( dto.getUserStatus() ) );
        login.setPerson( copy( dto.getPerson() ) );
        return login;
    }

    public static LoginDTO copy( Login entity, Boolean mustCopyPerson )
    {
        /*
         * We shall never return password! Never!
         */

        if ( entity == null )
            return null;

        LoginDTO login = new LoginDTO();
        login.setUserStatus( copy( entity.getUserStatus() ) );
        login.setUserId( entity.getUserId() );
        if ( mustCopyPerson )
            login.setPerson( copy( entity.getPerson(), false ) );
        return login;
    }


    public static SystemParameters copy( SystemParametersDTO dto )
    {
        return new SystemParameters( dto.getId(), dto.getDescription(), dto.getValue() );
    }

    public static SystemParametersDTO copy( SystemParameters entity )
    {
        return new SystemParametersDTO( entity.getId(), entity.getDescription(), entity.getValue() );
    }

    public static ListUserDTO copy( Users entity )
    {
        ListUserDTO dto = new ListUserDTO();


        dto.setName( entity.getName() );
        dto.setNickName( entity.getNickName() );
        dto.setId( entity.getId() );
        dto.setUserType( copy( entity.getUserType() ) );
        dto.setLastUpdate( entity.getUpdateDate() == null ? entity.getInsertDate() : entity.getUpdateDate() );
        return dto;
    }

    public static ListLoginDTO copy( Login entity )
    {
        ListLoginDTO dto;

        dto = new ListLoginDTO( entity.getPerson().getId(), entity.getPerson().getName(), copy( entity.getUserStatus() ) );
        return dto;
    }

    public static AccessLogType copy( AccessLogTypeDTO dto )
    {
        return new AccessLogType( dto.getId(), dto.getDescription() );
    }


    public static AccessLogTypeDTO copy( AccessLogType entity )
    {
        return new AccessLogTypeDTO( entity.getId(), entity.getDescription() );
    }


    public static CompanyTypeDTO copy( CompanyType entity )
    {
        return new CompanyTypeDTO( entity.getId(), entity.getDescription() );
    }


    public static CompanyType copy( CompanyTypeDTO dto )
    {
        return new CompanyType( dto.getId(), dto.getDescription() );
    }


    public static CompanyPositionDTO copy( CompanyPosition entity )
    {
        return new CompanyPositionDTO( entity.getId(), entity.getDescription() );
    }


    public static CompanyPosition copy( CompanyPositionDTO dto )
    {
        return new CompanyPosition( dto.getId(), dto.getDescription() );
    }

    public static Company copy( CompanyDTO dto )
    {
        Company company = new Company();

        copy( company, dto, true );

        company.setId( dto.getId() );
        company.setCompanyType( copy( dto.getCompanyType() ) );


        return company;
    }


    public static CompanyDTO copy( Company entity )
    {
        CompanyDTO company = new CompanyDTO();

        copy( company, entity );
        company.setCompanyType( copy( entity.getCompanyType() ) );

        return company;
    }


    protected static Users copy( Users user, UserDTO dto, Boolean copyLists )
    {
        user.setName( dto.getName() );
        user.setComment( dto.getComment() );
        user.setId( dto.getId() );
        user.setNickName( dto.getNickName() );

        if ( copyLists ) {
            copyAddresses( user, dto );
            copyDocuments( user, dto );
            copyContacts( user, dto );
        }

        return user;
    }

    protected static void copyDocuments( Users user, List<UserDocumentDTO> list )
    {
        user.getDocuments().clear();
        for ( UserDocumentDTO item : list ) {
            user.addDocument( copy( item ) );
        }
    }

    protected static void copyDocuments( Users user, UserDTO dto )
    {
        List<UserDocumentDTO> list = dto.getDocumentList();
        copyDocuments( user, list );
    }

    protected static void copyDocuments( Users user, RegisterDTO dto )
    {
        List<UserDocumentDTO> list = dto.getDocuments();
        copyDocuments( user, list );
    }


    protected static void copyContacts( Users user, UserDTO dto )
    {
        List<UserContactDTO> list = dto.getContactList();


        user.getContacts().clear();
        for ( UserContactDTO item : list ) {
            user.addContact( copy( item ) );
        }
    }


    protected static void copyAddresses( Users user, UserDTO dto )
    {
        List<AddressDTO> list = dto.getAddressList();

        user.getAddresses().clear();
        for ( AddressDTO item : list ) {
            user.addAddress( copy( item ) );
        }
    }

    public static MenuDTO copy( Menu source, Boolean copySubMenu )
    {
        if ( source == null )
            return null;

        MenuDTO target = new MenuDTO();

        target.setId( source.getId() );
        target.setDescription( source.getDescription() );
        target.setSequence( source.getSequence() );
        target.setTargetURL( source.getTargetURL() );
        target.setAutocheck( source.getAutocheck() );
        target.setChecked( source.getChecked() );
        target.setCheckmark( source.getCheckmark() );
        target.setDisabled( source.getDisabled() );
        target.setSeparatorBefore( source.getSeparatorBefore() );
        if ( ( source.getSubMenus().size() > 0 ) && copySubMenu ) {
            for ( Menu sm : source.getSubMenus() ) {
                target.addSubMenu( copy( sm, true ) );
            }
        }
        if ( source.getParentMenu() != null ) {
            target.setParent( copy( source.getParentMenu(), false ) );
            target.setParentId( source.getParentMenu().getId() );
        }
        return target;
    }


    public static Menu copy( Menu target, MenuDTO source )
    {
        if ( source == null || target == null )
            return null;

        target.setId( source.getId() );
        target.setDescription( source.getDescription() );
        target.setSequence( source.getSequence() );
        target.setTargetURL( source.getTargetURL() );
        target.setAutocheck( source.getAutocheck() );
        target.setChecked( source.getChecked() );
        target.setCheckmark( source.getCheckmark() );
        target.setDisabled( source.getDisabled() );
        target.setSeparatorBefore( source.getSeparatorBefore() );
        return target;
    }

    public static Menu copy( MenuDTO source )
    {
        if ( source == null )
            return null;
        Menu target = new Menu();

        target.setId( source.getId() );
        target.setDescription( source.getDescription() );
        target.setSequence( source.getSequence() );
        target.setTargetURL( source.getTargetURL() );
        target.setAutocheck( source.getAutocheck() );
        target.setChecked( source.getChecked() );
        target.setCheckmark( source.getCheckmark() );
        target.setDisabled( source.getDisabled() );
        target.setSeparatorBefore( source.getSeparatorBefore() );
        return target;
    }


    public static TaskDTO copy( Task source )
    {
        if ( source == null )
            return null;

        TaskDTO target = new TaskDTO();

        target.setId( source.getId() );
        target.setDescription( source.getDescription() );
        return target;
    }

    public static Task copy( TaskDTO source )
    {
        if ( source == null )
            return null;
        Task target = new Task( source.getDescription(), source.getId() );
        return target;
    }


    public static FormDTO copy( AnotoForm source )
    {
        if ( source == null )
            return null;

        FormDTO target = new FormDTO( source.getId(), source.getDescription() );

        target.setIp( source.getApplication() );
        return target;
    }


    public static AnotoForm copy( FormDTO source )
    {
        if ( source == null )
            return null;

        AnotoForm target = new AnotoForm( source.getId(), source.getApplication(), source.getDescription() );

        return target;
    }

    public static Media copy( MediaDTO source )
    {
        Media target = new Media();

        /*
         * Não será retornado o objeto nesta cópia.
         */
        target.setId( source.getId() );
        target.setMimeType( source.getMimeType() );
        target.setName( source.getName() );
        target.setObject( source.getObject() );
        target.setFormat( source.getFormat() );
        return target;

    }

    public static AnotoPen copy( PenDTO entity )
    {
        if ( entity == null )
            return null;
        return new AnotoPen( entity.getId(), entity.getDescription() );
    }

    public static Pgc copy( PGCDTO source )
    {
        Pgc pgc = new Pgc();
        pgc.setMedia( copy( source.getMedia() ) );
        pgc.setPenId( source.getPenId() );
        pgc.setTimediff( source.getTimeDiff() );
        return pgc;
    }

    public static Role copy( RoleDTO source )
    {
        Role role = new Role();
        role.setId( source.getId() );
        role.setDescription( source.getDescription() );
        if ( source.getParent() != null )
            role.setParentRole( copy( source.getParent() ) );
        return role;
    }

    public static PgcPage copy( PgcPageDTO dto )
    {
        PgcPage entity = new PgcPage( copy( dto.getPgc() ), dto.getBookId(), dto.getPageId() );

        return entity;
    }

    public static PgcField copy( PgcFieldDTO dto )
    {
        PgcField entity = new PgcField();

        entity.setIcrText( dto.getIrcText() );
        entity.setName( dto.getName() );
        entity.setRevisedText( dto.getRevisedText() );
        entity.setPgcPage( copy( dto.getPgcPage() ) );
        entity.setHasPenstrokes( dto.getHasPenstrokes() );
        entity.setStartTime( dto.getStartTime() );
        entity.setEndTime( dto.getEndTime() );
        entity.setType( dto.getType() );
        return entity;
    }

    public static PgcAttachment copy( PgcAttachmentDTO dto )
    {
        PgcAttachment entity = new PgcAttachment();
        entity.setPgcPage( copy( dto.getPgcPage() ) );
        entity.setSequence( dto.getSequence() );
        entity.setType( dto.getType() );
        entity.setValue( dto.getValue() );
        entity.setBarcodeType( dto.getBarcodeType() );
        return entity;
    }

    public static FieldType copy ( FieldTypeDTO dto )
    {
        FieldType target = new FieldType ( dto.getDescription(), dto.getId() );
        return target;
    }

    public static AnotoPageField copy ( AnotoPageFieldDTO dto )
    {
        AnotoPageField target = new AnotoPageField ( );
        if ( dto.getPage() != null )
            target.setAnotoPage( copy ( dto.getPage() ) );
        return target;
    }

    public static AnotoPage copy ( AnotoPageDTO dto )
    {
        AnotoPage target = new AnotoPage ( copy ( dto.getPad() ), dto.getPageAddress() );
        return target;
    }

    public static Pad copy ( PadDTO dto )
    {
        Pad target = new Pad ( );
        return target;
    }
}

package br.com.mcampos.ejb.core.util;


import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.system.SystemParametersDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.Pad;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.Pgc;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.cloudsystem.locality.state.StateUtil;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.security.accesslog.AccessLogType;
import br.com.mcampos.ejb.cloudsystem.system.entity.FieldType;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.address.AddressUtil;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.Address;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.CivilStateUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.entity.CompanyPosition;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.ContactTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.DocumentTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.TitleUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.entity.Title;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.entity.UserStatus;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.entity.system.SystemParameters;
import br.com.mcampos.ejb.entity.user.UserContact;
import br.com.mcampos.ejb.entity.user.UserDocument;
import br.com.mcampos.ejb.entity.user.Users;

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
        person.setCivilState( CivilStateUtil.createEntity( ( dto.getCivilState() ) ) );
        person.setGender( copy( dto.getGender() ) );
        person.setBirthDate( dto.getBirthDate() );
        person.setTitle( TitleUtil.createEntity( dto.getTitle() ) );
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
        person.setCivilState( CivilStateUtil.createEntity( dto.getCivilState() ) );
        person.setFatherName( dto.getFatherName() );
        person.setGender( copy( dto.getGender() ) );
        person.setInsertDate( null );
        person.setMotherName( dto.getMotherName() );
        person.setTitle( TitleUtil.createEntity( dto.getTitle() ) );
        return person;
    }


    public static Person copy( RegisterDTO dto )
    {
        Person person;

        person = new Person();
        person.setName( dto.getName() );
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
            dto.add( AddressUtil.copy( item ) );
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
        person.setCivilState( CivilStateUtil.copy( entity.getCivilState() ) );
        person.setGender( copy( entity.getGender() ) );
        person.setTitle( TitleUtil.copy( entity.getTitle() ) );
        if ( mustCopyLogin )
            person.setLogin( copy( entity.getLogin(), false ) );
        return person;
    }


    public static UserDocumentDTO copy( UserDocument entity )
    {
        UserDocumentDTO dto = new UserDocumentDTO();
        dto.setDocumentType( DocumentTypeUtil.copy( entity.getDocumentType() ) );
        dto.setCode( entity.getCode() );
        dto.setAdditionalInfo( entity.getAdditionalInfo() );
        return dto;
    }


    public static UserContactDTO copy( UserContact entity )
    {
        UserContactDTO dto = new UserContactDTO();
        dto.setContactType( ContactTypeUtil.copy( entity.getContactType() ) );
        dto.setDescription( entity.getDescription() );
        dto.setComment( entity.getComment() );
        return dto;
    }


    protected static String applyDocumentRules( String document, int type )
    {
        String code;


        code = document.trim();
        switch ( type ) {
        case UserDocument.typeCPF:
            code = code.replaceAll( "[\\-.\\/]", "" );
            break;
        case UserDocument.typeEmail:
            code = code.toLowerCase();
            break;
        }
        return code;
    }

    public static UserDocument copy( UserDocumentDTO dto )
    {
        UserDocument entity = new UserDocument();
        entity.setCode( applyDocumentRules( dto.getCode(), dto.getDocumentType().getId() ) );
        entity.setAdditionalInfo( dto.getAdditionalInfo() );
        entity.setDocumentType( DocumentTypeUtil.createEntity( dto.getDocumentType() ) );
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
        entity.setContactType( ContactTypeUtil.createEntity( dto.getContactType() ) );
        entity.setDescription( dto.getDescription() );
        entity.setComment( dto.getComment() );
        return entity;
    }


    public static UserContact copy( UserContact entity, UserContactDTO dto )
    {
        entity.setContactType( ContactTypeUtil.createEntity( dto.getContactType() ) );
        entity.setDescription( dto.getDescription() );
        entity.setComment( dto.getComment() );
        return entity;
    }

    public static CityDTO copy( City entity )
    {
        CityDTO city;


        if ( entity == null )
            return null;
        city = new CityDTO();
        city.setId( entity.getId() );
        city.setDescription( entity.getDescription() );
        city.setCountryCapital( entity.isCountryCapital() );
        city.setStateCapital( entity.isStateCapital() );
        city.setState( StateUtil.copy( entity.getState() ) );
        return city;
    }

    public static City copy( CityDTO dto )
    {
        City city = null;

        if ( dto != null ) {
            city = new City();
            city.setId( dto.getId() );
            if ( dto.getState() != null )
                city.setState( StateUtil.createEntity( dto.getState() ) );
        }
        return city;
    }


    public static Country copy( CountryDTO dto )
    {
        Country country = new Country();
        country.setCode3( dto.getCode3() );
        country.setId( dto.getId() );
        country.setNumericCode( dto.getNumericCode() );
        return country;
    }

    public static CountryDTO copy( Country entity )
    {
        CountryDTO dto = new CountryDTO();
        dto.setId( entity.getId() );
        dto.setCode3( entity.getCode3() );
        dto.setNumericCode( entity.getNumericCode() );
        return dto;
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
                titles.add( TitleUtil.createEntity( title ) );
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
            titles.add( TitleUtil.copy( title ) );
        }
        GenderDTO dto = new GenderDTO( entity.getId(), entity.getDescription() );
        dto.setTitles( titles );
        return dto;
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
            return new UserTypeDTO( entity.getId(), entity.getDescription() );
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
            UserUtil.addAddresses( user, dto );
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

    public static void copyDocuments( Users user, RegisterDTO dto )
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


    public static FormDTO copy( AnotoForm source )
    {
        if ( source == null )
            return null;

        FormDTO target = new FormDTO( source.getId(), source.getDescription() );

        target.setApplication( source.getApplication() );
        target.setIcrImage( source.getIcrImage() );
        target.setImagePath( source.getImagePath() );
        target.setConcatenatePgc( source.getConcatenatePgc() );
        return target;
    }


    public static AnotoForm copy( FormDTO source )
    {
        if ( source == null )
            return null;

        AnotoForm target = new AnotoForm( source.getId(), source.getApplication().trim(), source.getDescription().trim() );
        target.setIcrImage( source.getIcrImage() );
        target.setImagePath( source.getImagePath() );
        target.setConcatenatePgc( source.getConcatenatePgc() );
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
        String penId = source.getPenId();
        if ( penId != null ) {
            if ( penId.length() > 16 )
                penId = penId.substring( 0, 15 );
        }
        pgc.setPenId( penId );
        pgc.setTimediff( source.getTimeDiff() );
        return pgc;
    }


    public static PgcPage copy( PgcPageDTO dto )
    {
        PgcPage entity = new PgcPage( copy( dto.getPgc() ), dto.getBookId(), dto.getPageId() );
        entity.setAnotoPage( copy( dto.getAnotoPage() ) );
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
        entity.setType( copy( dto.getType() ) );
        return entity;
    }

    public static PgcPageAttachment copy( PgcAttachmentDTO dto )
    {
        PgcPageAttachment entity = new PgcPageAttachment();
        entity.setPgcPage( copy( dto.getPgcPage() ) );
        entity.setSequence( dto.getSequence() );
        entity.setType( dto.getType() );
        entity.setValue( dto.getValue() );
        entity.setBarcodeType( dto.getBarcodeType() );
        return entity;
    }

    public static FieldType copy( FieldTypeDTO dto )
    {
        FieldType target = new FieldType( dto.getDescription(), dto.getId() );
        return target;
    }

    public static AnotoPageField copy( AnotoPageFieldDTO dto )
    {
        AnotoPageField target = new AnotoPageField();
        if ( dto.getPage() != null )
            target.setAnotoPage( copy( dto.getPage() ) );
        target.setHeight( dto.getHeight() );
        target.setName( dto.getName() );
        target.setIcr( dto.getIcr() );
        target.setLeft( dto.getLeft() );
        target.setTop( dto.getTop() );
        if ( dto.getType() != null )
            target.setType( new FieldType( dto.getType().getDescription(), dto.getType().getId() ) );
        target.setWidth( dto.getWidth() );
        return target;
    }

    public static AnotoPage copy( AnotoPageDTO dto )
    {
        AnotoPage target = new AnotoPage( copy( dto.getPad() ), dto.getPageAddress() );
        target.setDescription( dto.getDescription() );
        target.setIcrTemplate( dto.getIcrTemplate() );
        return target;
    }

    public static Pad copy( PadDTO dto )
    {
        Pad target = new Pad( copy( dto.getForm() ), copy( dto.getMedia() ) );
        return target;
    }
}

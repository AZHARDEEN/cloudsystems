package br.com.mcampos.ejb.core.util;


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
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
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
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.security.accesslog.AccessLogType;
import br.com.mcampos.ejb.cloudsystem.system.entity.FieldType;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.entity.CompanyPosition;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.UserStatusUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.UserTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.cloudsystem.user.person.PersonUtil;
import br.com.mcampos.ejb.entity.system.SystemParameters;

import java.io.Serializable;


public final class DTOFactory implements Serializable
{
    public DTOFactory()
    {
        super();
    }


    public static Login copy( LoginDTO dto )
    {
        Login login = new Login();


        login.setPassword( dto.getPassword() );
        login.setPasswordExpirationDate( dto.getPasswordExpirationDate() );
        login.setUserStatus( UserStatusUtil.createEntity( dto.getUserStatus() ) );
        login.setPerson( PersonUtil.createEntity( dto.getPerson() ) );
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
        login.setUserStatus( UserStatusUtil.copy( entity.getUserStatus() ) );
        login.setUserId( entity.getUserId() );
        if ( mustCopyPerson )
            login.setPerson( PersonUtil.copy( entity.getPerson() ) );
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
        dto.setUserType( UserTypeUtil.copy( entity.getUserType() ) );
        dto.setLastUpdate( entity.getUpdateDate() == null ? entity.getInsertDate() : entity.getUpdateDate() );
        return dto;
    }

    public static ListLoginDTO copy( Login entity )
    {
        ListLoginDTO dto;

        dto = new ListLoginDTO();
        dto.setId( entity.getPerson().getId() );
        dto.setName( entity.getPerson().getName() );
        dto.setUserStatus( UserStatusUtil.copy( entity.getUserStatus() ) );
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

        //copy( company, dto, true );

        company.setId( dto.getId() );
        company.setCompanyType( copy( dto.getCompanyType() ) );


        return company;
    }


    public static CompanyDTO copy( Company entity )
    {
        CompanyDTO company = new CompanyDTO();

        //copy( company, entity );
        company.setCompanyType( copy( entity.getCompanyType() ) );

        return company;
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

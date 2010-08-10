package br.com.mcampos.ejb.cloudsystem.anoto.facade;


import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.AnotoSummary;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.resale.DealerDTO;
import br.com.mcampos.dto.resale.ResaleDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.session.PageFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity.AnotoPenUser;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.user.session.AnotoPenUserSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.PGCSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.Pgc;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.entity.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.session.PgcAttachmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.entity.PgcProperty;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.session.PgcPropertySessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPageUtil;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldPK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage.PgcPenPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.resale.ResaleUtil;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.DealerUtil;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.entity.Dealer;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.session.DealerSessionLocal;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.type.entity.DealerType;
import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;
import br.com.mcampos.ejb.cloudsystem.resale.session.ResaleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "EmbratelFacade", mappedName = "CloudSystems-EjbPrj-EmbratelFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class EmbratelFacadeBean extends AbstractSecurity implements EmbratelFacade
{
    protected static final int SystemMessageTypeId = 38;

    protected static final String fieldFend = "Venda cadastrada FEND";
    protected static final String fieldRejeitadoCEP = "Venda rejeitada por CEP inválido";
    protected static final String fieldRejeitadoCredito = "Venda rejeitada por Análise Credito";


    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;


    @EJB
    private AnotoFormSessionLocal formSession;

    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private AnotoPenUserSessionLocal penUserSession;

    @EJB
    private ResaleSessionLocal resaleSession;

    @EJB
    private PageFieldSessionLocal pageFieldSession;

    @EJB
    private PgcPenPageSessionLocal pgcPenPageSession;

    @EJB
    private PgcPropertySessionLocal pgcPropertySession;

    @EJB
    private PgcAttachmentSessionLocal pgcAttachmentSession;

    @EJB
    private PgcFieldSessionLocal pgcFieldSession;

    @EJB
    private PgcPageAttachmentSessionLocal pgcPageAttachmentSession;

    @EJB
    private PGCSessionLocal pgcSession;

    @EJB
    private DealerSessionLocal dealerSession;

    @EJB
    private NewPersonSessionLocal personSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return SystemMessageTypeId;
    }


    private Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        if ( company == null )
            throwException( 1 );
        return company;
    }

    public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
    {
        return AnotoUtils.toFormList( formSession.getAll( getCompany( auth ) ) );
    }

    private List<PenDTO> linkToUser( List<PenDTO> pens ) throws ApplicationException
    {
        if ( SysUtils.isEmpty( pens ) )
            return Collections.emptyList();
        for ( PenDTO pen : pens ) {
            linkToUser( pen );
        }
        return pens;
    }

    private PenDTO linkToUser( PenDTO pen ) throws ApplicationException
    {
        if ( pen == null )
            return null;
        AnotoPenUser penUser = penUserSession.getCurrentUser( pen.getId() );
        if ( penUser != null ) {
            ListUserDTO user = UserUtil.copy( penUser.getPerson() );
            pen.setUser( user );
        }
        else {
            pen.setUser( null );
        }
        return pen;
    }

    public List<ResaleDTO> getResales( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        return ResaleUtil.toDTOList( resaleSession.getAll( company ) );
    }

    public List<AnotoPageFieldDTO> getSearchableFields( AuthenticationDTO auth, FormDTO form ) throws ApplicationException
    {
        authenticate( auth );
        AnotoForm aForm = formSession.get( form.getId() );
        if ( aForm == null )
            return Collections.emptyList();
        List<AnotoPageField> searchables = pageFieldSession.getSearchable( aForm );
        if ( SysUtils.isEmpty( searchables ) )
            return Collections.emptyList();
        return AnotoUtils.toAnotoPageFieldDTO( searchables );
    }


    public List<AnotoResultList> getAllPgcPenPage( AuthenticationDTO auth, Properties props,
                                                   Integer maxRecords ) throws ApplicationException
    {
        authenticate( auth );
        if ( props != null && props.size() > 0 ) {
            /*Trocar o DTO pela entidade*/
            Object value;

            value = props.get( "form" );
            if ( value != null ) {
                AnotoForm entity = formSession.get( ( ( FormDTO )value ).getId() );
                if ( entity != null )
                    props.put( "form", entity );
            }
        }
        List<PgcPage> list = pgcPenPageSession.getAll( props, maxRecords, true );
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<AnotoResultList> resultList = new ArrayList<AnotoResultList>();
        for ( PgcPage page : list ) {
            AnotoResultList item = new AnotoResultList();

            item.setForm( page.getPgc().getPgcPenPages().get( 0 ).getPenPage().getPage().getPad().getForm().toDTO() );
            item.setPen( page.getPgc().getPgcPenPages().get( 0 ).getPenPage().getPen().toDTO() );
            item.setPgcPage( page.toDTO() );
            if ( resultList.contains( item ) == false ) {
                loadProperties( item, page );
                resultList.add( item );
            }
        }
        return resultList;
    }

    private void loadProperties( AnotoResultList item, PgcPage pgcPage ) throws ApplicationException
    {
        if ( item == null )
            return;
        try {
            loadUserData( item );
            List<PgcProperty> prop = pgcPropertySession.get( pgcPage.getPgc().getId(), PgcProperty.cellNumber );
            if ( SysUtils.isEmpty( prop ) == false ) {
                item.setCellNumber( prop.get( 0 ).getValue() );
            }
            prop = pgcPropertySession.getGPS( item.getPgcPage().getPgc().getId() );
            if ( SysUtils.isEmpty( prop ) == false ) {
                item.setLatitude( prop.get( 3 ).getValue() );
                item.setLongitude( prop.get( 4 ).getValue() );
            }
            loadBarCode( item, pgcPage );
            loadAttach( item, pgcPage );
            getExportFields( item, pgcPage );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void loadAttach( AnotoResultList item, PgcPage pgcPage ) throws ApplicationException
    {
        List<PgcAttachment> attachs = pgcAttachmentSession.get( pgcPage.getPgc().getId() );
        item.setAttach( SysUtils.isEmpty( attachs ) == false );
    }

    private void getExportFields( AnotoResultList item, PgcPage page ) throws ApplicationException
    {
        List<AnotoPageField> fields = pageFieldSession.getFieldsToExport();
        if ( SysUtils.isEmpty( fields ) == false ) {
            for ( AnotoPageField field : fields ) {
                PgcFieldPK key = new PgcFieldPK();
                key.setBookId( page.getBookId() );
                key.setPageId( page.getPageId() );
                key.setPgcId( page.getPgcId() );
                key.setName( field.getName() );
                PgcField pgcField = pgcFieldSession.get( key );
                if ( pgcField != null ) {
                    item.getFields().add( pgcField.toDTO() );
                }
            }
        }
    }

    private void loadUserData( AnotoResultList item ) throws ApplicationException
    {
        AnotoPenUser penUser = penUserSession.getUser( item.getPen().getId(), item.getPgcPage().getPgc().getInsertDate() );
        if ( penUser != null ) {
            item.setUserName( penUser.getPerson().getName() );
            if ( SysUtils.isEmpty( penUser.getPerson().getDocuments() ) == false ) {
                for ( UserDocument doc : penUser.getPerson().getDocuments() ) {
                    if ( doc.getDocumentType().getId().equals( DocumentTypeDTO.typeEmail ) ) {
                        item.setEmail( doc.getCode() );
                        break;
                    }
                }
            }
        }

    }

    private void loadBarCode( AnotoResultList item, PgcPage pgcPage ) throws ApplicationException
    {
        List<PgcPageAttachment> list = pgcPageAttachmentSession.getAll( pgcPage );
        if ( SysUtils.isEmpty( list ) == false ) {
            for ( PgcPageAttachment attach : list ) {
                if ( attach.getType().equals( PgcAttachmentDTO.typeBarCode ) ) {
                    item.setBarcodeValue( attach.getValue() );
                    break;
                }
            }
        }
    }

    public AnotoSummary getSummary( AuthenticationDTO auth, Properties props ) throws ApplicationException
    {
        authenticate( auth );
        if ( props != null && props.size() > 0 ) {
            /*Trocar o DTO pela entidade*/
            Object value;

            value = props.get( "form" );
            if ( value != null ) {
                AnotoForm entity = formSession.get( ( ( FormDTO )value ).getId() );
                if ( entity != null )
                    props.put( "form", entity );
            }
        }
        List<Pgc> fields = pgcFieldSession.getAll( props );
        AnotoSummary sum = new AnotoSummary();
        List<Integer> pgcs = new ArrayList<Integer>();
        if ( SysUtils.isEmpty( fields ) == false ) {
            for ( Pgc field : fields ) {
                pgcs.add( field.getId() );
                List attachs = pgcAttachmentSession.get( field.getId() );
                if ( SysUtils.isEmpty( attachs ) == false )
                    sum.addFoto();
                sum.add( pgcFieldSession.summaryType( field ) );
                sum.add( pgcFieldSession.summaryCategory( field ) );
                sum.add( pgcFieldSession.summaryPayment( field ) );
                sum.add( pgcFieldSession.summarySituation( field ) );
            }
        }
        sum.setPgc( pgcs.size() );
        return sum;
    }

    public List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        List<PgcField> fields = pgcSession.getFields( PgcPageUtil.createEntity( page ) );
        if ( SysUtils.isEmpty( fields ) )
            return Collections.emptyList();
        return AnotoUtils.toPgcFieldList( fields );
    }

    private Resale getResale( AuthenticationDTO auth, ResaleDTO resale ) throws ApplicationException
    {
        Company owner = getCompany( auth );
        Resale r = resaleSession.get( owner, resale.getSequence() );
        if ( r == null )
            throwException( 2 );
        return r;
    }

    public List<DealerDTO> getDealers( AuthenticationDTO auth, ResaleDTO resale ) throws ApplicationException
    {
        if ( resale != null ) {
            Resale r = getResale( auth, resale );
            return DealerUtil.toDTOList( dealerSession.getAll( r ), DealerType.typeDealer );
        }
        else {
            Company owner = getCompany( auth );
            return DealerUtil.toDTOList( dealerSession.getAll( owner ), DealerType.typeDealer );
        }
    }

    public DealerDTO myDealerAccount( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Dealer dealer = dealerSession.get( personSession.get( auth.getUserId() ) );
        return DealerUtil.copy( dealer );
    }

    public String getDealerPen( AuthenticationDTO auth, DealerDTO dealer ) throws ApplicationException
    {
        authenticate( auth );
        AnotoPenUser p = penUserSession.getUser( personSession.get( auth.getUserId() ), new Date() );
        return p != null ? p.getPen().getId() : null;
    }
}

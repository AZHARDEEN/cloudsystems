package br.com.mcampos.ejb.cloudsystem.client.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.client.ClientUtil;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.entity.ClientPK;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.MediaUtil;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.user.UserFacadeUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.company.CompanyUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.document.UserDocumentUtil;
import br.com.mcampos.ejb.cloudsystem.user.media.entity.UserMedia;
import br.com.mcampos.ejb.cloudsystem.user.media.session.UserMediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;
import br.com.mcampos.ejb.cloudsystem.user.media.type.session.UserMediaTypeSessionLocal;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ClientFacade", mappedName = "CloudSystems-EjbPrj-ClientFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class ClientFacadeBean extends UserFacadeUtil implements ClientFacade
{
    public static final Integer messageId = 16;


    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private ClientSessionLocal clientSession;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private MediaSessionLocal mediaSession;

    @EJB
    private UserMediaSessionLocal userMediaSession;

    @EJB
    private UserMediaTypeSessionLocal userMediaTypeSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    protected Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Collaborator coll = collaboratorSession.get( auth.getCurrentCompany(), auth.getUserId() );
        return coll.getCompany();
    }

    public List<ClientDTO> getClients( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Client> list = clientSession.getAll( company );
        return ClientUtil.toDTOList( list );
    }

    public List<ClientDTO> getCompanies( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Client> list = clientSession.getAllCompanyClients( company );
        return ClientUtil.toDTOList( list );
    }

    public List<ClientDTO> getPeople( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        List<Client> list = clientSession.getAllPersonClients( company );
        return ClientUtil.toDTOList( list );
    }

    public ClientDTO add( AuthenticationDTO auth, CompanyDTO dto ) throws ApplicationException
    {
        Company myCompany = getCompany( auth );
        Company clientNotManaged = CompanyUtil.createEntity( dto );
        clientNotManaged.setDocuments( UserDocumentUtil.toEntityList( clientNotManaged, dto.getDocumentList() ) );
        Company managedCompany = companySession.get( clientNotManaged );
        if ( managedCompany == null ) {
            clientNotManaged.getDocuments().clear();
            managedCompany = companySession.add( clientNotManaged );
            refreshUserAttributes( managedCompany, dto );
        }
        Client client = new Client( myCompany, managedCompany );
        client = clientSession.add( client );
        return ClientUtil.copy( client );
    }

    public void delete( AuthenticationDTO auth, ClientDTO dto ) throws ApplicationException
    {
        Company myCompany = getCompany( auth );
        if ( myCompany == null )
            throwException( 5 );
        Company client = companySession.get( dto.getClient().getId() );
        if ( client == null )
            throwException( 6 );
        clientSession.delete( new ClientPK( myCompany.getId(), dto.getClientId() ) );
    }

    public MediaDTO getLogo( AuthenticationDTO auth, ClientDTO dto ) throws ApplicationException
    {
        Company myCompany = getCompany( auth );
        Client client = clientSession.get( new ClientPK( myCompany.getId(), dto.getClientId() ) );
        if ( client == null )
            throwException( 6 );
        UserMedia u = userMediaSession.get( client.getClient(), getLogoMediaType() );
        MediaDTO m = ( u != null && u.getMedia() != null ) ? u.getMedia().toDTO() : null;
        if ( m != null ) /*Lazy Load Object*/
            m.setObject( u.getMedia().getObject() );
        return m;
    }

    public void setLogo( AuthenticationDTO auth, ClientDTO dto, MediaDTO mediaDto ) throws ApplicationException
    {
        Company myCompany = getCompany( auth );
        Client client = clientSession.get( new ClientPK( myCompany.getId(), dto.getClientId() ) );
        if ( client == null )
            throwException( 6 );
        Media media = MediaUtil.createEntity( mediaDto );
        mediaSession.add( media );

        UserMedia userMedia = new UserMedia();
        userMedia.setMedia( media );
        userMedia.setUser( client.getClient() );
        userMedia.setType( getLogoMediaType() );
        userMediaSession.add( userMedia );
    }

    private UserMediaType getLogoMediaType() throws ApplicationException
    {
        return userMediaTypeSession.get( UserMediaType.typeLogo );
    }

}

package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.attributes.UserStatus;
import br.com.mcampos.ejb.session.address.AddressTypeSessionLocal;
import br.com.mcampos.ejb.session.address.CitySessionLocal;
import br.com.mcampos.ejb.session.address.StateSessionLocal;
import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;
import br.com.mcampos.ejb.session.user.LoginSessionLocal;
import br.com.mcampos.ejb.session.user.PersonSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.CivilStateSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.ContactTypeSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.DocumentTypeSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.GenderSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.TitleSessionLocal;

import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;


@Stateless( name = "RegisterFacadeSession", mappedName = "CloudSystems-EjbPrj-RegisterFacadeSession" )
@Remote
@Local
public class RegisterFacadeSessionBean implements RegisterFacadeSession,
                                                  RegisterFacadeSessionLocal
{
    @EJB CivilStateSessionLocal civilState;
    @EJB AddressTypeSessionLocal addressType;
    @EJB ContactTypeSessionLocal contactType;
    @EJB DocumentTypeSessionLocal documentType;
    @EJB GenderSessionLocal gender;
    @EJB TitleSessionLocal title;
    @EJB StateSessionLocal state;
    @EJB CitySessionLocal city;
    @EJB LoginSessionLocal login;
    @EJB PersonSessionLocal person;
    @EJB SystemMessagesSessionLocal systemMessage;

    public RegisterFacadeSessionBean()
    {
    }


    protected CivilStateSessionLocal getCivilState()
    {
        return civilState;
    }

    protected AddressTypeSessionLocal getAddressType()
    {
        return addressType;
    }

    protected ContactTypeSessionLocal getContactType()
    {
        return contactType;
    }

    protected DocumentTypeSessionLocal getDocumentType()
    {
        return documentType;
    }

    protected GenderSessionLocal getGender()
    {
        return gender;
    }

    protected TitleSessionLocal getTitle()
    {
        return title;
    }

    protected StateSessionLocal getState()
    {
        return state;
    }

    protected CitySessionLocal getCity()
    {
        return city;
    }

    protected LoginSessionLocal getLogin()
    {
        return login;
    }

    protected PersonSessionLocal getPerson()
    {
        return person;
    }



    public List getAllCivilState ()
    {
        return getCivilState().getAll();
    }

    public List getAllAddressType ()
    {
        return getAddressType().getAll();
    }

    public List getAllContactType ()
    {
        return getContactType().getAll();
    }

    public List getAllDocumentType ()
    {
        return getDocumentType().getAll();
    }

    public List getAllGender ()
    {
        return getGender().getAll();
    }

    public List getAllTitle ()
    {
        return getTitle().getAll();
    }
    
    
    public List getAllState ( Integer countryId )
    {
        if ( SysUtils.isZero( countryId ) )
            systemMessage.throwMessage( 26 );
            
        return getState().getAll( countryId );
    }
    
    public List getAllCity ( Integer countryId, Integer stateId )
    {
        if ( SysUtils.isZero( countryId ) || SysUtils.isZero( stateId ) )
            systemMessage.throwMessage( 26 );
        
        return getCity().getAll( countryId, stateId );
    }
    
    public void createNewLogin ( RegisterDTO login )
    {
        if ( login == null )
            systemMessage.throwMessage( 26 );
        
		Person newPerson;
		
		newPerson = getPerson().createPersonForLogin ( login );
		if ( newPerson == null )
			throw new EJBException ( "Não foi possível criar usuario para o login" );
        getLogin().add( login, newPerson );
    }


    public void addPerson ( PersonDTO person )
    {
        if ( person == null )
            systemMessage.throwMessage( 26 );
        /*
         * Temos uma situação mais complicada.
         * Login é uma entidade diretamente ligada a pessoa, porém não é uma
         * boa tática usar cascade all para este caso
         */
        try {
            if ( person.getId() != null && person.getId() > 0 )
                getPerson().update( person );
            else
                getPerson().add( person );
        }
        catch ( Exception e ) {
            throw new EJBException ( "Erro ao incluir cadastro de pessoa", e );
        }
    }

    
    
    public PersonDTO findUserByDocument ( String document )
    {
        if ( SysUtils.isEmpty( document ) )
            systemMessage.throwMessage( 26 );
        
        return ( getPerson().getByDocument( document ) );
    }

    public LoginDTO makePassword ( String identification ) {
        if ( SysUtils.isEmpty( identification ) )
            systemMessage.throwMessage( 26 );
        
        return getLogin().makeNewPassword( identification );
    }
    
    public void changePassword ( String document, String oldPassword, String newPassword ) {
        if ( SysUtils.isEmpty( document ) || SysUtils.isEmpty( oldPassword ) || SysUtils.isEmpty( newPassword ) )
            systemMessage.throwMessage( 26 );
        
        getLogin().changePassword( document, oldPassword, newPassword );
    }

    public void validateEmail ( String token, String password ) {
        if ( SysUtils.isEmpty( token ) || SysUtils.isEmpty( password ) )
            systemMessage.throwMessage( 26 );
        getLogin().validateEmail( token, password );
    }
    
    public Boolean documentExists ( String document )
    {
        if ( SysUtils.isEmpty( document ) )
            systemMessage.throwMessage( 26 );
        
        return getPerson().getByDocument( document ) == null ? false : true;
    }
    
    public void sendValidationEmail ( String document )
    {
        if ( SysUtils.isEmpty( document ) )
            systemMessage.throwMessage( 26 );
        
        PersonDTO person;
        
        person = findUserByDocument( document );
        if ( person == null )
            throw new EJBException ( "Não existe este usuário." );
        if ( person.getLogin().getUserStatus().getId() != UserStatus.statusEmailNotValidated )
            throw new EJBException ( "Este usuário não permite novo envio de email de confirmação" );
    }
}

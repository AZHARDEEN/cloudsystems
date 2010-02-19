package br.com.mcampos.controller;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.exception.ApplicationRuntimeException;
import br.com.mcampos.util.CPF;


import java.text.ParseException;

import javax.ejb.EJBException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Textbox;

public class RegisterController extends BaseLoginOptionsController
{

    protected static final Integer statusAguardandoConfirmacaoEmail = 3;
    protected static final Integer userTypePerson = 1;

    protected static final int cpfDocumentId = 1;
    protected static final int identityDocumentId = 2;
    protected static final int emailDocumentId = 6;

    private Textbox name;
    private Textbox email;
    private Textbox re_email;
    private Textbox password;
    private Textbox repassword;
    private Textbox cpf;

    public RegisterController( char c )
    {
        super( c );
    }

    public RegisterController()
    {
        super();
    }

    public void onClick$cmdSubmit()
    {
        try {
            if ( validate() && addNewUser() ) {
                gotoPage( "/registered.zul" );
            }
        }
        catch ( WrongValueException e ) {
            HtmlBasedComponent base = ( HtmlBasedComponent )e.getComponent();
            showErrorMessage( e.getMessage() );
            base.setFocus( true );
        }
        catch ( EJBException e ) {
            if ( e.getCause().getCause() instanceof ApplicationRuntimeException ) {
                ApplicationRuntimeException cause = ( ApplicationRuntimeException )e.getCause().getCause();
                showErrorMessage( cause.getMessage() );
            }
            else
                showErrorMessage( e.getMessage() );
        }
    }

    public void debugData() throws ParseException
    {
        name.setValue( "Marcelo de Campos" );
        re_email.setValue( "marcelodecampos@uol.com.br" );
        email.setValue( "marcelodecampos@uol.com.br" );
        repassword.setValue( "123456" );
        password.setValue( "123456" );
        cpf.setValue( "59469390415" );
        recapctcha.setValue( captcha.getValue() );
    }

    public void doAfterCompose( Component c ) throws Exception
    {
        super.doAfterCompose( c );
        if ( name != null )
            name.setFocus( true );
    }

    protected Boolean validate()
    {
        String sName, sEmail, sReEmail, sPassword, sRePassword;


        sName = name.getValue();
        sEmail = email.getValue();
        sReEmail = re_email.getValue();
        sPassword = password.getValue();
        sRePassword = repassword.getValue();

        if ( sName.isEmpty() ) {
            showErrorMessage( "O seu nome completo deve estar preenchido obrigatoriamente." );
            name.focus();
            return false;
        }
        if ( sEmail.equalsIgnoreCase( sReEmail ) == false ) {
            showErrorMessage( "Os emails informados não são iguais. Por favor confirme o email." );
            email.focus();
            return false;
        }
        if ( sPassword.equals( sRePassword ) == false ) {
            showErrorMessage( "As senhas não conferem. Por favor, redigite a senha." );
            password.focus();
            return false;
        }
        if ( validateCPF() == false )
            return false;
        if ( validateCaptcha() == false )
            return false;
        return true;
    }

    protected boolean validateCPF()
    {
        String sCPF;
        boolean bRet;

        sCPF = cpf.getValue();
        sCPF = sCPF.replaceAll( "[.,\\- ]", "" );
        bRet = CPF.isValid( sCPF );
        if ( bRet == false ) {
            showErrorMessage( "O CPF está inválido" );
            cpf.setFocus( true );
        }
        return bRet;
    }


    protected boolean addNewUser()
    {
        RegisterDTO newLogin;

        newLogin = new RegisterDTO();
        newLogin.setName( name.getValue() );
        newLogin.setPassword( password.getValue() );
        newLogin.setRemoteAddr( Executions.getCurrent().getRemoteAddr() );
        newLogin.setRemoteHost( Executions.getCurrent().getRemoteHost() );
        newLogin.addDocument( DocumentTypeDTO.createDocumentTypeCPF(), cpf.getValue() );
        newLogin.addDocument( DocumentTypeDTO.createDocumentTypeEmail(), email.getValue() );
        newLogin.setSessionId( getSessionID() );
        try {
            getLocator().create( newLogin );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
        return true;
    }
}
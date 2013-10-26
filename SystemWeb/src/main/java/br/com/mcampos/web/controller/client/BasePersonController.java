package br.com.mcampos.web.controller.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.sysutils.CPF;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.combobox.CityCombobox;
import br.com.mcampos.web.core.combobox.CivilStateCombobox;
import br.com.mcampos.web.core.combobox.GenderCombobox;
import br.com.mcampos.web.core.combobox.StateCombobox;
import br.com.mcampos.web.core.combobox.TitleCombobox;

public abstract class BasePersonController<T extends BaseSessionInterface> extends UserController<T>
{
	private static final long serialVersionUID = -5360682636302384214L;
	private static final Logger logger = LoggerFactory.getLogger( BasePersonController.class );

	@Wire
	private Textbox cpf;

	@Wire
	private GenderCombobox gender;
	@Wire
	private TitleCombobox title;
	@Wire
	private CivilStateCombobox maritalStatus;
	@Wire
	private CityCombobox bornCity;
	@Wire
	private StateCombobox bornState;
	@Wire
	private Textbox fatherName;
	@Wire
	private Textbox motherName;
	@Wire
	private Textbox nickName;

	protected abstract Person getPerson( String doc );

	protected void show( Person person )
	{
		super.show( person );
		if ( person != null ) {
			getGender( ).find( person.getGender( ) );
			getTitle( ).find( person.getTitle( ) );
			getMaritalStatus( ).find( person.getCivilState( ) );
			getFatherName( ).setValue( person.getFatherName( ) );
			getMotherName( ).setValue( person.getMotherName( ) );
			getNickName( ).setValue( person.getNickName( ) );
			showCPF( person.getDocuments( ) );
			if ( person.getBornCity( ) != null ) {
				getBornState( ).find( person.getBornCity( ).getState( ) );
				getBornCity( ).find( person.getBornCity( ) );
			}
			else {
				getBornState( ).loadLastUsedSelection( );
				getBornCity( ).loadLastUsedSelection( );
			}
		}
		else {
			getCpf( ).setValue( "" );
			getFatherName( ).setValue( "" );
			getMotherName( ).setValue( "" );
			getBornState( ).loadLastUsedSelection( );
			getBornCity( ).loadLastUsedSelection( );
			getMaritalStatus( ).setSelectedIndex( 0 );
			getNickName( ).setValue( "" );
		}
	}

	protected Textbox getCpf( )
	{
		return this.cpf;
	}

	protected GenderCombobox getGender( )
	{
		return this.gender;
	}

	protected TitleCombobox getTitle( )
	{
		return this.title;
	}

	protected CivilStateCombobox getMaritalStatus( )
	{
		return this.maritalStatus;
	}

	protected CityCombobox getBornCity( )
	{
		return this.bornCity;
	}

	protected StateCombobox getBornState( )
	{
		return this.bornState;
	}

	protected Textbox getFatherName( )
	{
		return this.fatherName;
	}

	protected Textbox getMotherName( )
	{
		return this.motherName;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getGender( ).addDetail( getTitle( ) );
		getBornState( ).addDetail( getBornCity( ) );
	}

	protected void showCPF( List<UserDocument> docs )
	{
		this.cpf.setValue( "" );
		for ( UserDocument doc : docs ) {
			if ( doc.getType( ).getId( ).equals( UserDocument.CPF ) ) {
				this.cpf.setValue( doc.getCode( ) );
				break;
			}
		}
	}

	protected boolean validate( Person person )
	{
		if ( super.validate( person ) ) {
			if ( SysUtils.isEmpty( getName( ).getValue( ) ) ) {
				showErrorMessage( "O nome deve estar prenchido", "Cadastro de Pessoas" );
				return false;
			}
			if ( SysUtils.isEmpty( getCpf( ).getValue( ) ) ) {
				showErrorMessage( "O cpf deve estar preenchido", "Cadastro de Pessoas" );
				return false;
			}
			else if ( CPF.isValid( getCpf( ).getValue( ) ) == false ) {
				showErrorMessage( "O cpf deve está inválido", "Cadastro de Pessoas" );
				return false;
			}
		}
		return true;
	}

	protected void updatePerson( Person person )
	{
		if ( person == null ) {
			return;
		}
		super.update( person );
		person.setGender( getGender( ).getSelectedValue( ) );
		person.setTitle( getTitle( ).getSelectedValue( ) );
		person.setCivilState( getMaritalStatus( ).getSelectedValue( ) );
		person.setFatherName( getFatherName( ).getValue( ) );
		person.setMotherName( getMotherName( ).getValue( ) );
		person.setBornCity( getBornCity( ).getSelectedValue( ) );
		person.setNickName( getNickName( ).getValue( ) );
	}

	@Listen( "onBlur=#cpf" )
	public void onBlur( Event evt )
	{
		String doc = this.cpf.getValue( );
		if ( SysUtils.isEmpty( doc ) ) {
			return;
		}
		Person person = getPerson( CPF.removeMask( doc ) );
		if ( person == null ) {
			this.cpf.setValue( doc );
			addDocument( CPF.removeMask( doc ), getDocumentType( UserDocument.CPF ) );
		}
		else {
			show( person );
		}
		if ( evt != null ) {
			logger.info( "onBlur: " + evt.getTarget( ).getId( ) );
			evt.stopPropagation( );
		}
		else {
			logger.warn( "onBlur with evt null " );
		}
	}

	private Textbox getNickName( )
	{
		return nickName;
	}

}

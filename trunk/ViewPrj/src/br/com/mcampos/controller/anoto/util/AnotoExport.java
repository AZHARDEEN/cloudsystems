package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnotoExportSession;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.EJB;

import org.jdom.Document;
import org.jdom.Element;


public class AnotoExport
{
	private List<AnotoResultList> currentList = null;
	private AuthenticationDTO user;

	@EJB
	AnotoExportSession session;


	public AnotoExport( AuthenticationDTO user )
	{
		super();
		setUser( user );
	}

	public AnotoExport( AuthenticationDTO user, List<AnotoResultList> currentList )
	{
		super();
		setUser( user );
		setCurrentList( currentList );
	}


	public Document exportToXML() throws ApplicationException
	{
		if ( SysUtils.isEmpty( getCurrentList() ) )
			return null;

		Document result = null;

		Document doc = new Document();
		for ( AnotoResultList item : getCurrentList() ) {
			result = exportToXML( item, doc );
		}
		return result;
	}

	public Document exportToXML( AnotoResultList item, Document doc ) throws ApplicationException
	{
		String xml = "";

		Element page = createPageElement( item.getPgcPage() );
		Element pages = new Element( "Pages" );
		pages.addContent( page );
		Element book = new Element( "Book" );
		book.addContent( pages );

		Element root = createRootElement( item );
		root.addContent( book );
		doc.addContent( root );
		return doc;
	}

	protected Element createRootElement( AnotoResultList r )
	{
		Element root = new Element( "Form" );
		root.addContent( new Element( "Application" ).setText( r.getForm().getApplication() ) );
		root.addContent( new Element( "Description" ).setText( r.getForm().getApplication() ) );
		root.addContent( new Element( "Pen" ).setText( r.getPen().getId() ) );
		return root;
	}

	protected Element createPageElement( PgcPageDTO pgcPage ) throws ApplicationException
	{
		Element page = new Element( "Page" );
		page.setAttribute( "Address", pgcPage.getAnotoPage().getPageAddress() );

		List<PgcFieldDTO> fields = session.getFields( user, pgcPage );
		Element xmlFields = new Element( "Fields" );
		page.addContent( xmlFields );
		if ( SysUtils.isEmpty( fields ) )
			return page;
		for ( PgcFieldDTO field : fields ) {
			xmlFields.addContent( createField( field ) );
		}
		return page;
	}

	protected Element createField( PgcFieldDTO field )
	{
		Element xmlField = new Element( "Field" );
		xmlField.addContent( new Element( "Name", field.getName() ) );
		xmlField.setAttribute( "type", field.getType().getDescription() );
		String value;
		if ( field.getType().getId() == FieldTypeDTO.typeBoolean )
			value = field.getHasPenstrokes() ? "true" : "false";
		else
			value = SysUtils.isEmpty( field.getRevisedText() ) ? field.getIrcText() : field.getRevisedText();
		xmlField.addContent( new Element( "Value", value ) );
		return xmlField;
	}


	public void setCurrentList( List<AnotoResultList> currentList )
	{
		this.currentList = currentList;
	}

	public List<AnotoResultList> getCurrentList()
	{
		return currentList;
	}

	public void setUser( AuthenticationDTO user )
	{
		this.user = user;
	}

	public AuthenticationDTO getUser()
	{
		return user;
	}
}

package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;


public class AnotoExport
{
	private List<AnotoResultList> currentList = null;


	public AnotoExport()
	{
		super();
	}

	public AnotoExport( List<AnotoResultList> currentList )
	{
		super();
		setCurrentList( currentList );
	}


	public String exportToXML()
	{
		if ( SysUtils.isEmpty( getCurrentList() ) )
			return null;

		String result = "";

		Document doc = new Document();
		for ( AnotoResultList item : getCurrentList() ) {
			result = exportToXML( item, doc );
		}
		return result;
	}

	public String exportToXML( AnotoResultList item, Document doc )
	{
		String xml = "";

		Element page = new Element( "Page" );
		Element pages = new Element( "Pages" );
		pages.addContent( page );
		Element book = new Element( "Book" );
		book.addContent( pages );

		Element root = createRootElement( item );
		root.addContent( book );
		doc.addContent( root );
		return doc.toString();
	}

	protected Element createRootElement( AnotoResultList r )
	{
		Element root = new Element( "Form" );
		root.addContent( new Element( "Application" ).setText( r.getForm().getApplication() ) );
		root.addContent( new Element( "Description" ).setText( r.getForm().getApplication() ) );
		root.addContent( new Element( "Pen" ).setText( r.getPen().getId() ) );
		return root;
	}

	protected Element createPageElement( PgcPageDTO pgcPage )
	{
		Element page = new Element( "Page", pgcPage.getAnotoPage().getPageAddress() );

	}


	public void setCurrentList( List<AnotoResultList> currentList )
	{
		this.currentList = currentList;
	}

	public List<AnotoResultList> getCurrentList()
	{
		return currentList;
	}
}

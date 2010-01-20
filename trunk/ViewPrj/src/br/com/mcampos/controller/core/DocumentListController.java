package br.com.mcampos.controller.core;

import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;

import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class DocumentListController extends BaseClientListController
{
    private Listbox documentList;
    private Combobox documentType;
    private Textbox documentId;
    private Textbox documentComent;
    
    public DocumentListController( char c )
    {
        super( c );
    }

    public DocumentListController()
    {
        super();
    }


    public void onSelect$documentList ()
    {
        Listitem selectedItem;
        List cells;
        
        selectedItem = getDocumentList().getSelectedItem();
        if ( selectedItem != null ) {
            cells = selectedItem.getChildren();
            Iterator iterator = cells.iterator();
            
            if ( iterator.hasNext() )
                if ( documentType != null ) {
                    documentType.setValue ( ( (Listcell) iterator.next()).getLabel() );
                }
            if ( iterator.hasNext() ) 
                if ( documentId != null )
                    documentId.setValue( ( (Listcell) iterator.next()).getLabel() );
            if ( iterator.hasNext() ) 
                if ( documentComent != null )
                    documentComent.setValue ( ( (Listcell) iterator.next()).getLabel() );
        }
    }

    public void setDocumentList( Listbox documentList )
    {
        this.documentList = documentList;
    }

    public Listbox getDocumentList()
    {
        return documentList;
    }


    protected void setSelectedIndex ( Integer nIndex )
    {
        if ( nIndex >= 0 && nIndex < getDocumentList().getItems().size() ) {
            getDocumentList().setSelectedIndex( nIndex );
            onSelect$documentList ();
        }
    }
    
    public void onClick$removeDocument ()
    {
        Integer nIndex;
        
        nIndex = getDocumentList().getSelectedIndex();
        if ( nIndex < 0 ) {
            if ( getDocumentList().getItems().size() > 0 )
                getDocumentList().focus ();
        }
        else {
            getDocumentList().removeItemAt( nIndex );
            if ( getDocumentList().getItems().size() > 0 ) {
                if ( nIndex ==  getDocumentList().getItems().size() )
                    nIndex --;
               setSelectedIndex( nIndex );
            }
        }
    }
    
    public void onClick$updateDocument ()
    {
        Listitem selectedItem;
        List<Listcell> cells;
        Comboitem comboItem;
        
        
        if ( getDocumentList().getSelectedIndex() < 0 ) {
            if ( getDocumentList().getItems().size() > 0 )
                getDocumentList().focus ();
            return;
        }
        selectedItem = getDocumentList().getSelectedItem();
        comboItem = documentType.getSelectedItem();
        
        if ( selectedItem != null && documentType != null && comboItem != null ) {
            int nIndex = 0;
            
            cells = (List<Listcell>)selectedItem.getChildren();
            DocumentTypeDTO dto = ( DocumentTypeDTO ) comboItem.getValue();

            selectedItem.setValue( dto );
            for ( Listcell cell : cells ) {            
                switch ( nIndex ) {
                case 0:
                    cell.setLabel( documentType.getValue() );
                    break;
                case 1:
                    cell.setLabel( documentId.getValue ( ) );
                    break;
                case 2:
                    cell.setLabel( documentComent.getValue ( ) );
                    break;
                }
                nIndex ++;
            }
        }
    }
    
    public void onClick$addDocument ()
    {
        if ( validateDocument () ) {
            Comboitem type = documentType.getSelectedItem();
            
            if ( type != null ) {
                DocumentTypeDTO dto = (DocumentTypeDTO) type.getValue();
                if ( dto != null ) {
                    insertDocument( documentId.getValue(), documentComent.getValue(), dto );
                    documentId.setRawValue( "" ) ;
                    documentComent.setRawValue( "" );
                }
            }
        }
    }

    public Boolean validateDocument () 
    {
        String id;
        if ( documentType.getSelectedIndex() < 0 ) {
            documentType.focus ();
            return false;
        }
        id = documentId.getValue();
        if ( id.isEmpty() ) {
            documentType.focus ();
            return false;
        }
        return true;
    }
    
    protected void insertDocument( String description, String comment, Object obj )
    {
        Listitem item;
        DocumentTypeDTO dto = ( DocumentTypeDTO ) obj;
        
        if ( dto != null ) {
            item = getDocumentList().appendItem( dto.getDisplayName(), dto.getId().toString() );
            item.setValue( dto );
            item.appendChild( new Listcell ( description ) );
            item.appendChild( new Listcell ( comment ) );
        }
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        //getLoaderLocator().loadDocumentType( documentType );
    }

}

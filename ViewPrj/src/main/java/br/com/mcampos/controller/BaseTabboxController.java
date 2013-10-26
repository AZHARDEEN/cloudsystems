package br.com.mcampos.controller;

import java.util.List;
import java.util.ListIterator;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class BaseTabboxController extends Tabbox
{
	public BaseTabboxController( )
	{
		super( );
	}

	protected void addTab( String newTabTitle, String tabName, String panelName, String removeButtonName, String addButtonName, String baseName )
	{
		Tab newTab;
		Tabpanel newTabpanel, oldPanel;
		Tabs addressTabs;
		Toolbarbutton btn;

		try {
			newTab = new Tab( newTabTitle );
			oldPanel = getSelectedPanel( );
			addressTabs = (Tabs) getFellow( tabName );
			newTabpanel = (Tabpanel) getSelectedPanel( ).clone( );
			modifyWindowsID( newTabpanel, baseName );
			newTab.setParent( addressTabs );
			newTabpanel.setParent( getFellow( panelName ) );
			btn = (Toolbarbutton) newTabpanel.getFirstChild( ).getFellowIfAny( removeButtonName );
			setSelectedPanel( newTabpanel );
			if ( btn != null && btn.isVisible( ) == false )
				btn.setVisible( true );
			if ( oldPanel != null ) {
				btn = (Toolbarbutton) oldPanel.getFirstChild( ).getFellowIfAny( addButtonName );
				if ( btn != null && btn.isVisible( ) )
					btn.setVisible( false );
			}
		}
		catch ( Exception e ) {
			e = null;
		}
	}

	protected void modifyWindowsID( Tabpanel t, String baseName )
	{
		Window w;

		w = (Window) t.getFirstChild( );
		if ( w != null && w instanceof Window )
			w.setId( baseName + Integer.toString( getTabs( ).getChildren( ).size( ) ) );
	}

	protected void prepareTab( String comboName )
	{
		List<Tabpanel> panels = ( (List<Tabpanel>) ( (Object) getTabpanels( ).getChildren( ) ) );
		ListIterator<Tabpanel> itr = (ListIterator<Tabpanel>) panels.iterator( );
		Tabpanel tabPanel;
		Combobox comboBox;

		while ( itr.hasNext( ) ) {
			tabPanel = itr.next( );
			if ( tabPanel != null && tabPanel instanceof Tabpanel ) {
				comboBox = (Combobox) tabPanel.getFirstChild( ).getFellowIfAny( comboName );
				if ( comboBox != null && comboBox.getItems( ).size( ) > 0 ) {
					comboBox.setSelectedIndex( 0 );
					getSelectedTab( ).setLabel( comboBox.getSelectedItem( ).getLabel( ) );
				}
			}
		}
	}

	protected void deleteTab( String addButton, String removeButton )
	{
		int nIndex;
		List<Tabpanel> panels;
		Tabpanel currentPanel;
		Toolbarbutton btnAdd, btnRemove;

		nIndex = this.getSelectedIndex( );
		if ( getSelectedPanel( ) != null )
			getSelectedPanel( ).detach( );
		getSelectedTab( ).detach( );
		panels = ( (List<Tabpanel>) ( (Object) getTabpanels( ).getChildren( ) ) );

		if ( nIndex >= panels.size( ) )
			nIndex--;
		if ( nIndex >= 0 )
			this.setSelectedIndex( nIndex );
		currentPanel = getSelectedPanel( );
		if ( currentPanel != null ) {
			btnAdd = (Toolbarbutton) currentPanel.getFirstChild( ).getFellowIfAny( addButton );
			btnRemove = (Toolbarbutton) currentPanel.getFirstChild( ).getFellowIfAny( removeButton );
			if ( btnAdd != null )
				/*
				 * This pannel is the last Pannel???
				 * Yes: Must have a add button
				 * NO.: Mst have just a remove button e Must not have a add Button
				 */
				if ( panels.size( ) == nIndex + 1 ) {
					if ( btnAdd.isVisible( ) == false )
						btnAdd.setVisible( true );
				}
				else if ( btnAdd.isVisible( ) )
					btnAdd.setVisible( false );
		}
	}

	public void changeAddressTabTitle( Combobox combo )
	{
		if ( combo != null && combo.getSelectedIndex( ) >= 0 )
			getSelectedTab( ).setLabel( combo.getSelectedItem( ).getLabel( ) );
	}
}

package br.com.mcampos.web.controller.product;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import br.com.mcampos.ejb.product.ProductSession;
import br.com.mcampos.jpa.product.Price;
import br.com.mcampos.jpa.product.PriceType;
import br.com.mcampos.jpa.product.Product;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.combobox.ProductTypeCombobox;
import br.com.mcampos.web.core.dbwidgets.DBIntbox;
import br.com.mcampos.web.core.dbwidgets.DBTextbox;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public class ProductController extends BaseDBListController<ProductSession, Product>
{
	@Wire
	private Label infoId;

	@Wire
	private Label infoName;

	@Wire
	private Label infoType;

	@Wire
	private Label infoDescription;

	@Wire
	private Label infoPrice1;

	@Wire
	private Label infoPrice2;

	@Wire
	private Label infoPrice3;

	@Wire
	private Label infoPrice4;

	@Wire
	private DBIntbox id;

	@Wire
	private DBTextbox name;

	@Wire
	private ProductTypeCombobox type;

	@Wire
	private DBTextbox description;

	@Wire
	private Decimalbox price1;

	@Wire
	private Decimalbox price2;

	@Wire
	private Decimalbox price3;

	@Wire
	private Decimalbox price4;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2688632760153003994L;

	@Override
	protected void showFields( Product targetEntity )
	{
		infoPrice1.setValue( "" );
		infoPrice2.setValue( "" );
		infoPrice3.setValue( "" );
		infoPrice4.setValue( "" );
		if ( targetEntity != null ) {
			infoId.setValue( targetEntity.getId( ).getId( ).toString( ) );
			infoName.setValue( targetEntity.getName( ) );
			infoType.setValue( targetEntity.getType( ).getDescription( ) );
			infoDescription.setValue( targetEntity.getDescription( ) );

			id.setValue( targetEntity.getId( ).getId( ) );
			name.setValue( targetEntity.getName( ) );
			type.find( targetEntity.getType( ) );
			description.setValue( targetEntity.getDescription( ) );

			NumberFormat dm = DecimalFormat.getCurrencyInstance( SysUtils.Locale_BR( ) );
			targetEntity = getSession( ).loadObjects( getPrincipal( ), targetEntity );
			for ( Price price : targetEntity.getPrices( ) ) {
				switch ( price.getId( ).getPriceTypeId( ) ) {
				case PriceType.typeBuy:
					infoPrice1.setValue( dm.format( price.getValue( ).doubleValue( ) ) );
					price1.setValue( price.getValue( ) );
					break;
				case PriceType.typeMin:
					infoPrice2.setValue( dm.format( price.getValue( ).doubleValue( ) ) );
					price2.setValue( price.getValue( ) );
					break;
				case PriceType.typeNormal:
					infoPrice3.setValue( dm.format( price.getValue( ).doubleValue( ) ) );
					price3.setValue( price.getValue( ) );
					break;
				case PriceType.typeMax:
					infoPrice4.setValue( dm.format( price.getValue( ).doubleValue( ) ) );
					price4.setValue( price.getValue( ) );
					break;
				}
			}
		}
		else {
			infoId.setValue( "" );
			infoName.setValue( "" );
			infoDescription.setValue( "" );
			infoType.setValue( "" );

			price1.setValue( new BigDecimal( 0 ) );
			price2.setValue( new BigDecimal( 0 ) );
			price3.setValue( new BigDecimal( 0 ) );
			price4.setValue( new BigDecimal( 0 ) );

			id.setValue( 0 );
			name.setValue( "" );
			description.setValue( "" );
		}
	}

	@Override
	protected void updateTargetEntity( Product entity )
	{
		if ( entity != null ) {
			if ( entity.getId( ).getCompanyId( ) == null ) {
				entity.getId( ).setCompanyId( getPrincipal( ).getCompanyID( ) );
			}
			if ( entity.getId( ).getId( ) == null ) {
				entity.getId( ).setId( 0 );
			}
			entity.getId( ).setId( id.getValue( ) );
			entity.setType( type.getSelectedValue( ) );
			entity.setName( name.getValue( ) );
			entity.setDescription( description.getValue( ) );
			BigDecimal zero = new BigDecimal( 0 );
			entity.setPrices( new ArrayList<Price>( ) );
			if ( price1.getValue( ).compareTo( zero ) > 0 )
				getPrice( entity, PriceType.typeBuy ).setValue( price1.getValue( ) );
			if ( price2.getValue( ).compareTo( zero ) > 0 )
				getPrice( entity, PriceType.typeMin ).setValue( price2.getValue( ) );
			if ( price3.getValue( ).compareTo( zero ) > 0 )
				getPrice( entity, PriceType.typeNormal ).setValue( price3.getValue( ) );
			if ( price4.getValue( ).compareTo( zero ) > 0 )
				getPrice( entity, PriceType.typeMax ).setValue( price4.getValue( ) );
		}
	}

	private Price getPrice( Product entity, Integer type )
	{
		if ( SysUtils.isEmpty( entity.getPrices( ) ) == false ) {
			for ( Price price : entity.getPrices( ) ) {
				if ( price.getId( ) != null && price.getId( ).getPriceTypeId( ) != null && price.getId( ).getPriceTypeId( ).equals( type ) )
					return price;
			}
		}
		Price price = new Price( );
		price.getId( ).setPriceTypeId( type );
		price.getId( ).setCompanyId( entity.getCompanyId( ) );
		price.getId( ).setProductId( entity.getProductId( ) );
		price.getId( ).setFromDate( new Date( ) );
		return entity.add( price );
	}

	@Override
	protected boolean validateEntity( Product entity, int operation )
	{
		if ( SysUtils.isEmpty( entity.getName( ) ) ) {
			Messagebox.show( "O nome do produto não pode estar vazio", "Nome do produto", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		return true;
	}

	@Override
	protected Class<ProductSession> getSessionClass( )
	{
		return ProductSession.class;
	}

}

package br.com.mcampos.web.core.report;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import net.sf.jasperreports.engine.JasperPrint;

import org.zkoss.util.media.Media;

import br.com.mcampos.sysutils.SysUtils;

public class ReportItem implements Serializable
{
	private static final long serialVersionUID = 4083112495037744567L;

	public static final Integer pdf = 0;
	public static final Integer xml = 1;
	public static final Integer html = 2;

	private String name;
	private Object value;
	private String reportUrl;
	private Integer reportFormat;
	private Media media;
	private JasperPrint print;
	private Map<String, Object> params;

	private transient Callable<? extends Collection<?>> callable;

	public ReportItem( )
	{
		super( );
	}

	public ReportItem( String reportName )
	{
		setName( reportName );
	}

	public String getName( )
	{
		if ( SysUtils.isEmpty( this.name ) ) {
			this.name = "Sem Descrição";
		}
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Object getValue( )
	{
		return this.value;
	}

	public void setValue( Object value )
	{
		this.value = value;
	}

	public String getReportUrl( )
	{
		return this.reportUrl;
	}

	public ReportItem setReportUrl( String reportUrl )
	{
		this.reportUrl = reportUrl;
		return this;
	}

	public Callable<? extends Collection<?>> getCallable( )
	{
		return this.callable;
	}

	public void setCallable( Callable<? extends Collection<?>> callable )
	{
		this.callable = callable;
	}

	public Integer getReportFormat( )
	{
		if ( this.reportFormat == null || this.reportFormat.intValue( ) < 1 ) {
			this.reportFormat = 1;
		}
		return this.reportFormat;
	}

	public void setReportFormat( Integer reportFormat )
	{
		this.reportFormat = reportFormat;
	}

	public Media getMedia( )
	{
		return this.media;
	}

	public void setMedia( Media media )
	{
		this.media = media;
	}

	public JasperPrint getPrint( )
	{
		return this.print;
	}

	public void setPrint( JasperPrint print )
	{
		this.print = print;
	}

	public Map<String, Object> getParams( )
	{
		if ( this.params == null ) {
			this.params = new HashMap<String, Object>( );
		}
		return this.params;
	}

	public void setParams( Map<String, Object> params )
	{
		this.params = params;
	}
}

package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;

import java.util.List;


public class FormDTO extends SimpleTableDTO
{
	/**
	 *
	 */
	private static final long serialVersionUID = -8356914268806695133L;
	String application;
	List<PadDTO> pads;
	private Boolean icrImage;
	private String imagePath;
	private Boolean concatenatePgc;

	public FormDTO( SimpleTableDTO simpleTableDTO )
	{
		super( simpleTableDTO );
	}

	public FormDTO( Integer integer, String string )
	{
		super( integer, string );
	}

	public FormDTO( Integer integer )
	{
		super( integer );
	}

	public FormDTO()
	{
		super();
	}

	public FormDTO setApplication( String ip )
	{
		if ( SysUtils.isEmpty( ip ) )
			throw new InvalidParameterException( "O ip do formulário não pode ser nulo ou vazio" );
		this.application = ip;
		return this;
	}

	public String getApplication()
	{
		return application;
	}

	public void setPads( List<PadDTO> pads )
	{
		this.pads = pads;
	}

	public List<PadDTO> getPads()
	{
		return pads;
	}

	@Override
	public String toString()
	{
		return getId() + "-" + getApplication() + "-" + getDescription();
	}

	public void setIcrImage( Boolean icrImage )
	{
		this.icrImage = icrImage;
	}

	public Boolean getIcrImage()
	{
		if ( icrImage == null )
			icrImage = false;
		return icrImage;
	}

	public void setImagePath( String imagePath )
	{
		this.imagePath = imagePath;
	}

	public String getImagePath()
	{
		return imagePath;
	}

	public void setConcatenatePgc( Boolean concatenatePgc )
	{
		this.concatenatePgc = concatenatePgc;
	}

	public Boolean getConcatenatePgc()
	{
		if ( concatenatePgc == null )
			concatenatePgc = false;
		return concatenatePgc;
	}
}

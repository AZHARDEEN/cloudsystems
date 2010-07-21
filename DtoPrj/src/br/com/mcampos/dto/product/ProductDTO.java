package br.com.mcampos.dto.product;


import br.com.mcampos.dto.core.SimpleTableDTO;

public class ProductDTO extends SimpleTableDTO
{

    private String code;
    private String name;
    private String obs;
    private Boolean visible;
    private ProductTypeDTO type;

    public ProductDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public ProductDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public ProductDTO( Integer integer )
    {
        super( integer );
    }

    public ProductDTO()
    {
        super();
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setObs( String obs )
    {
        this.obs = obs;
    }

    public String getObs()
    {
        return obs;
    }

    public void setVisible( Boolean visible )
    {
        this.visible = visible;
    }

    public Boolean getVisible()
    {
        return visible;
    }

    public void setType( ProductTypeDTO type )
    {
        this.type = type;
    }

    public ProductTypeDTO getType()
    {
        return type;
    }
}

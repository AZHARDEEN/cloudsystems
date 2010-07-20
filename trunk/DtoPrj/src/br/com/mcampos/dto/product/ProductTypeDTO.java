package br.com.mcampos.dto.product;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class ProductTypeDTO extends SimpleTableDTO
{
    private Boolean isDefault;

    public ProductTypeDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public ProductTypeDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public ProductTypeDTO( Integer integer )
    {
        super( integer );
    }

    public ProductTypeDTO()
    {
        super();
    }

    public void setIsDefault( Boolean isDefault )
    {
        this.isDefault = isDefault;
    }

    public Boolean getIsDefault()
    {
        return isDefault;
    }
}

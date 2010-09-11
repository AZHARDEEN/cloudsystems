package br.com.mcampos.dto.accounting;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class CostCenterDTO extends SimpleTableDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9151958802144155441L;
	private CostAreaDTO area;


    public CostCenterDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public CostCenterDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public CostCenterDTO( Integer integer )
    {
        super( integer );
    }

    public CostCenterDTO()
    {
        super();
    }

    public void setArea( CostAreaDTO area )
    {
        this.area = area;
    }

    public CostAreaDTO getArea()
    {
        return area;
    }
}

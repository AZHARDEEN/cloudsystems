package br.com.mcampos.dto.address;

import br.com.mcampos.dto.core.DisplayNameDTO;

public class AddressTypeDTO extends DisplayNameDTO implements Comparable<AddressTypeDTO>
{
    /**
     *
     */
    private static final long serialVersionUID = 5920660631361301995L;
    Integer id;
    String description;

    public AddressTypeDTO()
    {
        super();
    }

    public AddressTypeDTO( Integer id, String description )
    {
        super();
        this.id = id;
        this.description = description;
    }

    public String getDisplayName()
    {
        return getDescription();
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public int compareTo( AddressTypeDTO target )
    {
        if ( target == null || target.getId() == null )
            return 1;
        if ( getId() == null )
            return -1;
        return getId().compareTo( target.getId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
            return false;

        if ( obj instanceof AddressTypeDTO ) {
            AddressTypeDTO dto = ( AddressTypeDTO )obj;

            return getId().equals( dto.getId() );
        }
        return super.equals( obj );
    }

    @Override
    public String toString()
    {
        return getDescription();
    }
}

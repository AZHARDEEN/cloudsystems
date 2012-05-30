package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.DisplayNameDTO;


public class SystemParametersDTO extends DisplayNameDTO implements Comparable<SystemParametersDTO>
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4387801106423854720L;
	private String id;
    private String description;
    private String value;
    
    public SystemParametersDTO() {
        super();
    }

    public SystemParametersDTO( String id, String description, String value ) {
        super();
        setId ( id );
        setDescription( description );
        setValue ( value );
    }

    public int compareTo( SystemParametersDTO o ) {
        return getId().compareTo( o.getId() );
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return getId ();
    }

}

package br.com.mcampos.dto.typing.iab;


import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.user.ListUserDTO;

import java.io.Serializable;

public class IabProblemsDTO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8025404776831411639L;
	private String id;
    private ListUserDTO user_1;
    private ListUserDTO user_2;
    private CityDTO city;
    private String school;
    private Integer status;


    public void setUser_1( ListUserDTO user_1 )
    {
        this.user_1 = user_1;
    }

    public ListUserDTO getUser_1()
    {
        return user_1;
    }

    public void setUser_2( ListUserDTO user_2 )
    {
        this.user_2 = user_2;
    }

    public ListUserDTO getUser_2()
    {
        return user_2;
    }

    public void setCity( CityDTO city )
    {
        this.city = city;
    }

    public CityDTO getCity()
    {
        return city;
    }

    public void setSchool( String school )
    {
        this.school = school;
    }

    public String getSchool()
    {
        return school;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setStatus( Integer status )
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
}

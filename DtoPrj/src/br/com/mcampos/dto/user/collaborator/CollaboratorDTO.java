package br.com.mcampos.dto.user.collaborator;

import br.com.mcampos.dto.user.ListUserDTO;

import java.io.Serializable;

public class CollaboratorDTO implements Serializable
{
    private ListUserDTO company;
    private ListUserDTO collaborator;
    private Integer sequence;

    public CollaboratorDTO()
    {
        super();
    }

    public void setCompany( ListUserDTO company )
    {
        this.company = company;
    }

    public ListUserDTO getCompany()
    {
        return company;
    }

    public void setCollaborator( ListUserDTO collaborator )
    {
        this.collaborator = collaborator;
    }

    public ListUserDTO getCollaborator()
    {
        return collaborator;
    }

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }
}

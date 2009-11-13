package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.system.SystemParametersDTO;
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.ejb.entity.system.SystemParameters;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface TableManagerSession {

    void add( SystemParametersDTO systemParameters );
    void update( SystemParametersDTO systemParameters );
    List<SystemParametersDTO> getAll();
    SystemParametersDTO getSystemParameter( String id );


    void add( AccessLogTypeDTO dto );
    void update( AccessLogTypeDTO systemParameters );
    List<AccessLogTypeDTO> getAllAccessLogType();
    AccessLogTypeDTO getAccessLogType( Integer id );
    Integer getAccessLogTypeMaxValue ();
    public void deleteAccessLogType( Integer id );


    void add( CompanyPositionDTO dto );
    void update( CompanyPositionDTO systemParameters );
    List<CompanyPositionDTO> getAllCompanyPosition();
    CompanyPositionDTO getCompanyPosition( Integer id );
    Integer getCompanyPositionMaxValue ();
    public void deleteCompanyPosition( Integer id );


    void add( CompanyTypeDTO dto );
    void update( CompanyTypeDTO systemParameters );
    List<CompanyTypeDTO> getAllCompanyType();
    CompanyTypeDTO getCompanyType( Integer id );
    Integer getCompanyTypeMaxValue ();
    public void deleteCompanyType( Integer id );

}

package br.com.mcampos.ejb.session.system;

import javax.ejb.Local;

@Local
public interface SystemParametersSessionLocal
{

    /**
     * Obtém o valor do parametro do sistema solicitado. Deve ser utilizado uma 
     * string com o código do parâmetro, que identifica um e somente um parâmetro
     * do sistema. Este código está cadastrado na tabela SystemParemeters
     * 
     * @param id Chave única na tabela de parâmetros do sistema (SystemParameter)
     * @return String com o valor do parâmetro solicitado ou null
     */
    String getValue ( String id );
}

package br.com.mcampos.web;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class Fichamento extends SelectorComposer<Window>
{
	@Wire
	Label assunto;

	@Wire
	Label autor;
	@Wire
	Textbox referencia;
	@Wire
	Textbox ficha;
	@Wire
	Textbox fonte;
	@Wire
	Combobox tipo;

	private static final long serialVersionUID = -1117069499642120024L;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		assunto.setValue( "Inovação Tecnológica" );
		autor.setValue( "Ivan Rocha Neto" );
		fonte.setValue( "http://revistas.utfpr.edu.br/pb/index.php/revedutec-ct/article/viewFile/1014/606" );
		tipo.setSelectedIndex( 0 );
		referencia
				.setValue( "Schumpeter J. A-\"The Theory of Econômic Development\", 1934; "
						+
						"Jantsch, \"Design for Evolution: Self-Organization and Planning in the Life of Human Systems (The International Library of Systems Theory and Philosophy)\" "
						+
						"Rego P. M. \"C&T no Brasil: Políticas e Instituições - Secretaria de Ciência e Tecnologia\"" +
						"" );

		ficha.setValue( "O artigo apresenta os conceitos de inovação tecnológica, para, em seguida, apresentar suas relações " +
				"de interdependência com uma diversidade de fatores ( mercado, consumidores, valores sócios culturais, Estado, etc). " +
				"Um modelo de representação do sistema de inovação é proposto, incluindo o Conselho Nacional de Ciência e Tecnologia como um ator do " +
				"processo. Apresenta-se, então, a situação atual e prespectivas do sistema brasileiro de inovação, onde percebe-se " +
				"uma clara vinculção entre a inovação tecnológica, a capacidade inovativa das empresas e o desempenho da indústria brasileira. " +
				"Nota-se que a perspetiva competitiva-econômica, incluindo o cenário internacional, está ligada à capacidade de inovação do país. " +
				"Demonstra-se uma tendencia negativa quanto a inovação e, dentro deste cenário, aborda algumas possibilidades para reverter " +
				"tal quadro, bem como aponta algumas iniciativas governamentais (PADCT)." );
	}
}

package br.com.mcampos.web.core.session;

import java.io.Serializable;

public interface BeanSessonInterface<BEAN> extends Serializable
{
	BEAN getSession( );
}

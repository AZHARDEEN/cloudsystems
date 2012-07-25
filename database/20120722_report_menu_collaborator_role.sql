--Alteração na tabela collaborator_role

alter table public.collaborator_Role
add CONSTRAINT FK_CollabRole_CompanyRole FOREIGN KEY (usr_id_in, rol_id_in) REFERENCES public.company_role (usr_id_in, rol_id_in);

GRANT SELECT ON public.collaborator_Role TO jreport;

--###############################################################################END OF UPDATE


--Novas Tabelas Criadas
DROP TABLE IF EXISTS public.menu_report CASCADE ;
DROP TABLE IF EXISTS public.report CASCADE ;
CREATE TABLE public.report
(
	rep_id_in            INTEGER NOT NULL ,
	rep_description_ch   VARCHAR(64) NOT NULL ,
	rep_url_ch           VARCHAR(64) NOT NULL ,
CONSTRAINT  XPKreport PRIMARY KEY (rep_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX 
);
GRANT SELECT, INSERT, UPDATE, DELETE ON public.report TO cloud;
GRANT SELECT ON public.report TO jreport;
CREATE TABLE public.menu_report
(
	mnu_id_in            INTEGER NOT NULL ,
	rep_id_in            INTEGER NOT NULL ,
CONSTRAINT  XPKmenu_report PRIMARY KEY (mnu_id_in,rep_id_in) USING INDEX TABLESPACE TB_CLOUD_INDEX ,
CONSTRAINT FK_Menu_MenuReport FOREIGN KEY (mnu_id_in) REFERENCES public.menu (mnu_id_in),
CONSTRAINT FK_Report_MenuReport FOREIGN KEY (rep_id_in) REFERENCES public.report (rep_id_in)
);
COMMENT ON COLUMN public.menu_report.mnu_id_in IS 'Código sequencial do menu.';
GRANT SELECT, INSERT, UPDATE, DELETE ON public.menu_report TO cloud;
GRANT SELECT ON public.menu_report TO jreport;
--###############################################################################END OF UPDATE









ALTER TABLE company
   ADD COLUMN cpn_upload_job_bt boolean DEFAULT false;
update company set    cpn_upload_job_bt = false;
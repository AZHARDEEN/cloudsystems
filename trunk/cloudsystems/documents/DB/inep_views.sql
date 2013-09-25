-- View: inep.distribution_view
DROP VIEW IF EXISTS inep.distribution_view;

CREATE OR REPLACE VIEW inep.distribution_view AS 
 SELECT users.usr_name_ch, 
    inep_revisor.rvs_coordinator_bt, 
    inep_revisor.col_seq_in, 
    collaborator.col_id_in AS userid, 
    inep_distribution.usr_id_in, 
    inep_distribution.pct_id_in, 
    inep_distribution.isc_id_ch, 
    inep_distribution.tsk_id_in, 
    inep_distribution.ids_id_in, 
        CASE
            WHEN inep_distribution.dis_grade_in IS NULL THEN NULL::numeric
            WHEN inep_distribution.dis_grade_in <= 5 THEN inep_distribution.dis_grade_in::numeric
            ELSE 0::numeric
        END AS dis_grade_in
   FROM inep.inep_distribution, 
    inep.inep_revisor, 
    collaborator, 
    users
  WHERE inep_distribution.usr_id_in = inep_revisor.usr_id_in AND inep_distribution.pct_id_in = inep_revisor.pct_id_in AND inep_distribution.col_seq_in = inep_revisor.col_seq_in AND inep_distribution.tsk_id_in = inep_revisor.tsk_id_in AND inep_revisor.usr_id_in = collaborator.usr_id_in AND inep_revisor.col_seq_in = collaborator.col_seq_in AND collaborator.col_id_in = users.usr_id_in;

ALTER TABLE inep.distribution_view
  OWNER TO postgres;
GRANT ALL ON TABLE inep.distribution_view TO postgres;
GRANT SELECT ON TABLE inep.distribution_view TO jreport;
GRANT SELECT, UPDATE, DELETE ON TABLE inep.distribution_view TO cloud;
GRANT ALL ON TABLE inep.distribution_view TO dba_cloud;



-- View: inep.gradeview

DROP VIEW if exists inep.gradeview;

CREATE OR REPLACE VIEW inep.gradeview AS 
 WITH task_grade AS (
         SELECT DISTINCT s.usr_id_in, 
            s.pct_id_in, 
            s.isc_id_ch, 
            ( SELECT d.dis_grade_in AS task1_grade_1
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 1
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS task1_grade1, 
            ( SELECT d.dis_grade_in AS task1_grade_2
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 1
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 1
            LIMIT 1) AS task1_grade2, 
            ( SELECT d.dis_grade_in AS task_1_grade_3
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = true AND d.tsk_id_in = 1 AND d.dis_grade_in IS NOT NULL
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS task1_grade3, 
            ( SELECT d.dis_grade_in AS task2_grade_1
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 2
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS task2_grade1, 
            ( SELECT d.dis_grade_in AS task2_grade_2
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 2
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 1
            LIMIT 1) AS task2_grade2, 
            ( SELECT d.dis_grade_in AS task_1_grade_3
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = true AND d.tsk_id_in = 2 AND d.dis_grade_in IS NOT NULL
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS task2_grade3, 
            ( SELECT d.dis_grade_in AS grade
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 3
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS task3_grade1, 
            ( SELECT d.dis_grade_in AS grade
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 3
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 1
            LIMIT 1) AS task3_grade2, 
            ( SELECT d.dis_grade_in AS task_1_grade_3
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = true AND d.tsk_id_in = 3 AND d.dis_grade_in IS NOT NULL
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS task3_grade3, 
            ( SELECT d.dis_grade_in AS grade
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 4
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS task4_grade1, 
            ( SELECT d.dis_grade_in AS grade
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 4
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 1
            LIMIT 1) AS task4_grade2, 
            ( SELECT d.dis_grade_in AS task_1_grade_3
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = true AND d.tsk_id_in = 4 AND d.dis_grade_in IS NOT NULL
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS task4_grade3
           FROM inep.inep_subscription s
          WHERE s.usr_id_in = 13623 AND s.pct_id_in = 1
          ORDER BY s.usr_id_in, s.pct_id_in, s.isc_id_ch
        )
 SELECT task_grade.usr_id_in, 
    task_grade.pct_id_in, 
    task_grade.isc_id_ch, 
    task_grade.task1_grade1, 
    task_grade.task1_grade2, 
    task_grade.task1_grade3, 
    task_grade.task2_grade1, 
    task_grade.task2_grade2, 
    task_grade.task2_grade3, 
    task_grade.task3_grade1, 
    task_grade.task3_grade2, 
    task_grade.task3_grade3, 
    task_grade.task4_grade1, 
    task_grade.task4_grade2, 
    task_grade.task4_grade3, 
        CASE
            WHEN task_grade.task1_grade3 IS NOT NULL THEN task_grade.task1_grade3
            WHEN task_grade.task1_grade1 > task_grade.task1_grade2 THEN task_grade.task1_grade1
            ELSE task_grade.task1_grade2
        END AS finalgrade1, 
        CASE
            WHEN task_grade.task2_grade3 IS NOT NULL THEN task_grade.task2_grade3
            WHEN task_grade.task2_grade1 > task_grade.task2_grade2 THEN task_grade.task2_grade1
            ELSE task_grade.task2_grade2
        END AS finalgrade2, 
        CASE
            WHEN task_grade.task3_grade3 IS NOT NULL THEN task_grade.task3_grade3
            WHEN task_grade.task3_grade1 > task_grade.task3_grade2 THEN task_grade.task3_grade1
            ELSE task_grade.task3_grade2
        END AS finalgrade3, 
        CASE
            WHEN task_grade.task4_grade3 IS NOT NULL THEN task_grade.task4_grade3
            WHEN task_grade.task4_grade1 > task_grade.task4_grade2 THEN task_grade.task4_grade1
            ELSE task_grade.task4_grade2
        END AS finalgrade4
   FROM task_grade;

ALTER TABLE inep.gradeview
  OWNER TO postgres;
GRANT ALL ON TABLE inep.gradeview TO postgres;
GRANT SELECT ON TABLE inep.gradeview TO jreport;
GRANT SELECT, UPDATE, DELETE ON TABLE inep.gradeview TO cloud;
GRANT ALL ON TABLE inep.gradeview TO dba_cloud;


-- View: inep.gradeview2

DROP VIEW IF EXISTS inep.gradeview2;

CREATE OR REPLACE VIEW inep.gradeview2 AS 
 SELECT DISTINCT s.usr_id_in, 
    s.pct_id_in, 
    s.isc_id_ch, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS task1_grade_1
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 1
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 0
    LIMIT 1) AS task1_grade1, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS task1_grade_2
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 1
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 1
    LIMIT 1) AS task1_grade2, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS task_1_grade_3
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = true AND d.tsk_id_in = 1 AND d.dis_grade_in IS NOT NULL
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 0
    LIMIT 1) AS task1_grade3, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS task2_grade_1
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 2
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 0
    LIMIT 1) AS task2_grade1, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS task2_grade_2
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 2
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 1
    LIMIT 1) AS task2_grade2, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS task_1_grade_3
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = true AND d.tsk_id_in = 2 AND d.dis_grade_in IS NOT NULL
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 0
    LIMIT 1) AS task2_grade3, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS grade
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 3
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 0
    LIMIT 1) AS task3_grade1, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS grade
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 3
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 1
    LIMIT 1) AS task3_grade2, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS task_1_grade_3
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = true AND d.tsk_id_in = 3 AND d.dis_grade_in IS NOT NULL
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 0
    LIMIT 1) AS task3_grade3, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS grade
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 4
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 0
    LIMIT 1) AS task4_grade1, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS grade
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL AND d.tsk_id_in = 4
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 1
    LIMIT 1) AS task4_grade2, 
    ( SELECT 
                CASE
                    WHEN d.dis_grade_in <= 5::numeric THEN d.dis_grade_in
                    WHEN d.dis_grade_in IS NULL THEN NULL::numeric
                    ELSE 0::numeric
                END AS task_1_grade_3
           FROM inep.distribution_view d
      JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
     WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = true AND d.tsk_id_in = 4 AND d.dis_grade_in IS NOT NULL
     ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
    OFFSET 0
    LIMIT 1) AS task4_grade3
   FROM inep.inep_subscription s
  WHERE s.usr_id_in = 13623 AND s.pct_id_in = 1 AND (EXISTS ( SELECT 1
           FROM inep.inep_distribution dd
          WHERE dd.usr_id_in = s.usr_id_in AND s.pct_id_in = s.pct_id_in AND s.isc_id_ch::text = dd.isc_id_ch::text AND dd.ids_id_in <> 1));

ALTER TABLE inep.gradeview2
  OWNER TO postgres;
GRANT ALL ON TABLE inep.gradeview2 TO postgres;
GRANT SELECT ON TABLE inep.gradeview2 TO jreport;
GRANT SELECT, UPDATE, DELETE ON TABLE inep.gradeview2 TO cloud;
GRANT ALL ON TABLE inep.gradeview2 TO dba_cloud;


-- View: inep.revisorextractview

DROP VIEW IF EXISTS inep.revisorextractview;

CREATE OR REPLACE VIEW inep.revisorextractview AS 
 WITH task_grade AS (
         SELECT s.usr_id_in, 
            s.pct_id_in, 
            s.isc_id_ch, 
            s.tsk_id_in, 
            s.col_seq_in, 
            s.ids_id_in, 
            s.dis_grade_in, 
            s.dis_obs_tx, 
            s.dis_insert_dt, 
            s.dis_update_dt, 
            ( SELECT d.dis_grade_in AS grade2
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.tsk_id_in = s.tsk_id_in AND d.col_seq_in <> s.col_seq_in AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = false AND d.dis_grade_in IS NOT NULL
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS grade2, 
            ( SELECT d.dis_grade_in AS grade3
                   FROM inep.inep_distribution d
              JOIN inep.inep_revisor r ON r.usr_id_in = d.usr_id_in AND r.pct_id_in = d.pct_id_in AND r.tsk_id_in = d.tsk_id_in AND r.col_seq_in = d.col_seq_in
             WHERE d.usr_id_in = s.usr_id_in AND d.pct_id_in = s.pct_id_in AND d.isc_id_ch::text = s.isc_id_ch::text AND d.col_seq_in <> s.col_seq_in AND d.tsk_id_in = s.tsk_id_in AND d.dis_grade_in IS NOT NULL AND r.rvs_coordinator_bt = true AND d.dis_grade_in IS NOT NULL
             ORDER BY d.usr_id_in, d.pct_id_in, d.isc_id_ch, d.tsk_id_in, d.col_seq_in
            OFFSET 0
            LIMIT 1) AS grade3
           FROM inep.inep_distribution s
          WHERE s.dis_grade_in IS NOT NULL
          ORDER BY s.usr_id_in, s.pct_id_in, s.isc_id_ch, s.tsk_id_in
        )
 SELECT task_grade.usr_id_in, 
    task_grade.pct_id_in, 
    task_grade.isc_id_ch, 
    task_grade.tsk_id_in, 
    task_grade.col_seq_in, 
    task_grade.ids_id_in, 
    task_grade.dis_grade_in, 
    task_grade.dis_obs_tx, 
    task_grade.dis_insert_dt, 
    task_grade.dis_update_dt, 
    task_grade.grade2, 
    task_grade.grade3, 
        CASE
            WHEN task_grade.ids_id_in = 4 THEN task_grade.grade3
            WHEN task_grade.ids_id_in = 2 AND task_grade.dis_grade_in IS NOT NULL AND task_grade.grade2 IS NOT NULL THEN task_grade.dis_grade_in + task_grade.grade2 / 2
            ELSE NULL::integer
        END AS final_grade
   FROM task_grade;

ALTER TABLE inep.revisorextractview
  OWNER TO postgres;
GRANT ALL ON TABLE inep.revisorextractview TO postgres;
GRANT SELECT ON TABLE inep.revisorextractview TO jreport;
GRANT SELECT, UPDATE, DELETE ON TABLE inep.revisorextractview TO cloud;
GRANT ALL ON TABLE inep.revisorextractview TO dba_cloud;


-- View: inep.subscriptiongradeview

DROP VIEW IF EXISTS inep.subscriptiongradeview;

CREATE OR REPLACE VIEW inep.subscriptiongradeview AS 
 SELECT DISTINCT s.usr_id_in, 
    s.pct_id_in, 
    s.isc_id_ch, 
    inep.nota_final_tarefa(s.usr_id_in, s.pct_id_in, s.isc_id_ch::text, 1) AS nf1, 
    inep.nota_final_tarefa(s.usr_id_in, s.pct_id_in, s.isc_id_ch::text, 2) AS nf2, 
    inep.nota_final_tarefa(s.usr_id_in, s.pct_id_in, s.isc_id_ch::text, 3) AS nf3, 
    inep.nota_final_tarefa(s.usr_id_in, s.pct_id_in, s.isc_id_ch::text, 4) AS nf4
   FROM inep.inep_subscription s
  WHERE s.usr_id_in = 13623 AND s.pct_id_in = 1
  ORDER BY s.usr_id_in, s.pct_id_in, s.isc_id_ch;

ALTER TABLE inep.subscriptiongradeview
  OWNER TO postgres;
GRANT ALL ON TABLE inep.subscriptiongradeview TO postgres;
GRANT ALL ON TABLE inep.subscriptiongradeview TO dba_cloud;
GRANT SELECT ON TABLE inep.subscriptiongradeview TO cloud;
GRANT SELECT ON TABLE inep.subscriptiongradeview TO jreport;

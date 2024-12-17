INSERT INTO employee (email, password, role, employee_rate, max_hours)
VALUES ('patrick@worker.dk', 'Password12345678', 'WORKER', 200, 8),
       ('patrick@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 7),
       ('mads@worker.dk', 'Password12345678', 'WORKER', 200, 9),
       ('mads@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 8);

INSERT INTO project (project_name, budget, project_description, employee_id, material_cost, is_archived)
VALUES ('Website Overhaul', 120000, 'Redesign af hjemmesiden.', 2, 18000.00, FALSE),
       ('App Udvikling', 80000, 'Udvikling af ny app.', 2, 25000.00, FALSE),
       ('Cloud Opsætning', 90000, 'Opsætning af cloud tjenester.', 4, 20000.00, FALSE),
       ('System Opgradering', 50000, 'Opgradering af systemer.', 4, 15000.00, FALSE),
       ('Data Analyse', 60000, 'Analyse af kundedata.', 4, 20000.00, TRUE);

INSERT INTO subproject (subproject_name, subproject_description, project_id, is_archived)
VALUES ('UI Design', 'Brugergrænseflade design.', 1, FALSE),
       ('Backend Optimering', 'Optimering af backend system.', 1, FALSE),
       ('iOS Udvikling', 'Udvikling af iOS version.', 2, FALSE),
       ('Android Udvikling', 'Udvikling af Android version.', 2, FALSE),
       ('Data Migrering', 'Flytning af data til cloud.', 3, FALSE),
       ('System Konfiguration', 'Konfigurering af nye systemer.', 4, FALSE),
       ('API Integration', 'Integration af API.', 4, FALSE),
       ('Rapportering', 'Opret rapporter.', 5, FALSE),
       ('Sikkerhedsoptimering', 'Øg sikkerhedsniveau.', 5, FALSE),
       ('Analyse Design', 'Analyse af resultater.', 5, FALSE);

INSERT INTO task (task_name, start_date, end_date, status, employee_id, actual_hours, estimated_hours, subproject_id, is_archived)
VALUES ('Frontend Design', '2024-11-01', '2024-11-05', 'INPROGRESS', 1, 0, 15, 1, FALSE),
       ('Backend Optimering', '2024-11-06', '2024-11-10', 'NOTSTARTED', 1, 10, 6, 2, FALSE),
       ('Database Setup', '2024-11-11', '2024-11-15', 'INPROGRESS', 1, 16, 18, 3, FALSE),
       ('API Integration', '2024-11-16', '2024-11-20', 'COMPLETE', 1, 0, 17, 4, FALSE),
       ('System Test', '2024-11-21', '2024-11-25', 'NOTSTARTED', 1, 15, 20, 5, FALSE),

       ('UI Komponenter', '2024-11-01', '2024-11-05', 'INPROGRESS', 3, 0, 8, 6, FALSE),
       ('Cloud Opsætning', '2024-11-06', '2024-11-10', 'NOTSTARTED', 3, 14, 23, 7, FALSE),
       ('Sikkerhedstjek', '2024-11-11', '2024-11-15', 'COMPLETE', 3, 0, 22, 8, FALSE),
       ('Performance Test', '2024-11-16', '2024-11-20', 'NOTSTARTED', 3, 12, 7, 9, FALSE),
       ('Analyse Afslutning', '2024-11-21', '2024-11-25', 'INPROGRESS', 3, 0, 24, 10, FALSE);

DROP TABLE IF EXISTS all_dates;
CREATE TABLE all_dates (
                           date_column DATE NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS DateRange;
CREATE TABLE DateRange AS
SELECT *
FROM (
         WITH RECURSIVE date_generator AS (
             SELECT DATE('2023-01-01') AS date_column
             UNION ALL
             SELECT DATE_ADD(date_column, INTERVAL 1 DAY)
             FROM date_generator
             WHERE date_column < '2025-01-01'
         )
         SELECT date_column
         FROM date_generator
         WHERE DAYOFWEEK(date_column) NOT IN (1, 7)
     ) AS a;

DROP VIEW IF EXISTS employee_workload_pr_day;
CREATE VIEW employee_workload_pr_day AS
SELECT
    date_column,
    SUM(hours_per_day) AS total_hours_per_day,
    employee_id,
    MAX(max_hours) AS max_hours_per_employee
FROM (
         SELECT
             task_employee.*,
             DateRange.date_column
         FROM (
                  SELECT
                      task.*,
                      employee.max_hours,
                      estimated_hours / COUNT(DateRange.date_column) AS hours_per_day
                  FROM
                      task
                          JOIN
                      employee
                      ON task.employee_id = employee.employee_id
                          JOIN
                      DateRange
                      ON task.start_date <= DateRange.date_column
                          AND task.end_date >= DateRange.date_column
                  WHERE DAYOFWEEK(DateRange.date_column) NOT IN (1, 7)
                  GROUP BY task.task_id, employee.employee_id
              ) AS task_employee
                  JOIN DateRange
                       ON task_employee.start_date <= DateRange.date_column
                           AND task_employee.end_date >= DateRange.date_column
                           AND DAYOFWEEK(DateRange.date_column) NOT IN (1, 7)
     ) AS h
GROUP BY
    employee_id,
    date_column;

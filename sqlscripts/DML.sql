INSERT INTO employee (email, password, role, employee_rate, max_hours)
VALUES ('patrick@worker.dk', 'Password12345678', 'WORKER', 200, 8),
       ('patrick@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 7),
       ('mads@worker.dk', 'Password12345678', 'WORKER', 200, 9),
       ('mads@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 8);

INSERT INTO project (project_name, budget, project_description, employee_id, material_cost, is_archived)
VALUES ('Website Overhaul', 120000, 'Redesign of the website.', 2, 18000.00, FALSE),
       ('App Development', 80000, 'Development of a new app.', 2, 25000.00, FALSE),
       ('Cloud Setup', 90000, 'Setup of cloud services.', 4, 20000.00, FALSE),
       ('System Upgrade', 50000, 'Upgrading systems.', 4, 15000.00, FALSE),
       ('Data Analysis', 60000, 'Analysis of customer data.', 4, 20000.00, TRUE);

INSERT INTO subproject (subproject_name, subproject_description, project_id, is_archived)
VALUES ('UI Design', 'User interface design.', 1, FALSE),
       ('Backend Optimization', 'Optimization of backend systems.', 1, FALSE),
       ('Android Optimization', 'Optimization of backend systems.', 1, FALSE),
       ('iOS Development', 'Development of iOS version.', 2, FALSE),
       ('Android Development', 'Development of Android version.', 2, FALSE),
       ('Data Migration', 'Migration of data to the cloud.', 3, FALSE),
       ('System Configuration', 'Configuration of new systems.', 4, FALSE),
       ('API Integration', 'Integration of API.', 4, FALSE),
       ('Reporting', 'Create reports.', 5, FALSE),
       ('Security Optimization', 'Increase security level.', 5, FALSE),
       ('Analysis Design', 'Analyze results.', 5, FALSE);

INSERT INTO task (task_name, start_date, end_date, status, employee_id, actual_hours, estimated_hours, subproject_id, is_archived)
VALUES ('Frontend Design', '2024-11-01', '2024-11-03', 'INPROGRESS', 1, 0, 15, 1, FALSE),
       ('Backend Design', '2024-11-01', '2024-11-03', 'INPROGRESS', 1, 0, 15, 1, FALSE),
       ('UI test', '2024-11-01', '2024-11-03', 'NOTSTARTED', 1, 0, 15, 1, FALSE),
       ('Backend Optimization', '2024-11-06', '2024-11-09', 'NOTSTARTED', 1, 0, 6, 2, FALSE),
       ('Database Setup', '2024-11-11', '2024-11-12', 'INPROGRESS', 1, 16, 18, 3, FALSE),
       ('API Integration', '2024-11-16', '2024-11-20', 'INPROGRESS', 1, 16, 17, 4, FALSE),
       ('System Test', '2024-11-21', '2024-11-24', 'NOTSTARTED', 1, 0, 20, 5, FALSE),

       ('UI Components', '2024-11-01', '2024-11-02', 'INPROGRESS', 3, 5, 8, 6, FALSE),
       ('Cloud Setup', '2024-11-06', '2024-11-08', 'NOTSTARTED', 3, 0, 23, 7, FALSE),
       ('Security Check', '2024-11-11', '2024-11-12', 'INPROGRESS', 3, 3, 22, 8, FALSE),
       ('Performance Test', '2024-11-16', '2024-11-18', 'NOTSTARTED', 3, 0, 7, 9, FALSE),
       ('Analysis Completion', '2024-11-21', '2024-11-23', 'INPROGRESS', 3, 6, 24, 10, FALSE);

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

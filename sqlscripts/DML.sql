INSERT INTO employee (email, password, role, employee_rate, max_hours)
VALUES
    ('ama', 'Amalie123', 'PROJECTLEADER', 180, 5),
    ('zuzu', 'Zuhur1234', 'PROJECTLEADER', 200, 6),
    ('worker', 'Worker123', 'WORKER', 250, 7),
    ('worker2@example.com', 'Worker1234', 'WORKER', 500, 8);

INSERT INTO project (project_name, budget, project_description, employee_id, material_cost, is_archived)
VALUES
    ('Corporate Website Overhaul', 120000, 'Redesign and optimize the corporate website for better UX.', 1, 18000.00, FALSE),
    ('Mobile App Launch', 80000, 'Develop and launch a customer-facing mobile application.', 2, 25000.00, FALSE),
    ('Cloud Migration Initiative', 95000, 'Migrate all company data and services to the cloud.', 1, 22000.00, FALSE),
    ('Security Upgrade Project', 60000, 'Enhance the company’s cybersecurity measures.', 2, 15000.00, TRUE);

INSERT INTO subproject (subproject_name, subproject_description, project_id, is_archived)
VALUES
    ('UI/UX Design', 'Design an intuitive user interface for the website.', 1, FALSE),
    ('Backend Refactor', 'Refactor and optimize the backend systems.', 1, FALSE),
    ('iOS App Development', 'Create an iOS version of the mobile app.', 2, FALSE),
    ('Android App Development', 'Create an Android version of the mobile app.', 2, FALSE),
    ('Data Migration', 'Transfer data to the new cloud infrastructure.', 3, FALSE);

INSERT INTO task (task_name, start_date, end_date, status, employee_id, actual_hours, estimated_hours, subproject_id, is_archived)
VALUES
    ('Wireframe Creation', '2024-11-01', '2024-11-05', 'INPROGRESS', 3, 5, 10, 1, FALSE),
    ('Database Optimization', '2024-11-02', '2024-11-06', 'NOTSTARTED', 4, 10, 20, 2, FALSE),
    ('iOS Feature Implementation', '2024-11-03', '2024-11-07', 'COMPLETE', NULL, 15, 15, 3, FALSE),
    ('Android Testing', '2024-11-06', '2024-11-10', 'NOTSTARTED', 4, 100, 15, 4, FALSE),
    ('Cloud Configuration', '2024-11-11', '2024-11-15', 'COMPLETE', NULL, 10, 20, 5, FALSE);

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

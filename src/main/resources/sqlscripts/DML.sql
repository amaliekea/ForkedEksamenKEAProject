INSERT INTO employee (email, password, role, employee_rate, max_hours)
VALUES ('patrick@worker.dk', 'Password14361189', 'WORKER', 200, 8),
       ('patrick@projectleader.dk', 'Password12819692', 'PROJECTLEADER', 200, 7),
       ('mads@worker.dk', 'Password126227378', 'WORKER', 200, 9),
       ('mads@projectleader.dk', 'Password12827938', 'PROJECTLEADER', 200, 8);

INSERT INTO project (project_name, budget, project_description, employee_id, material_cost, is_archived)
VALUES ('Website Overhaul', 120000, 'Redesign of the website.', 2, 18000.00, FALSE),
       ('App Development', 80000, 'Development of a new app.', 2, 25000.00, FALSE),
       ('Cloud Setup', 90000, 'Setup of cloud services.', 4, 20000.00, FALSE),
       ('System Upgrade', 50000, 'Upgrading systems.', 4, 15000.00, FALSE),
       ('Data Analysis', 60000, 'Analysis of customer data.', 4, 20000.00, TRUE),
       ('SEO Optimization', 70000, 'Improving website SEO.', 2, 15000.00, FALSE),
       ('Backend Migration', 95000, 'Migrating backend services to new platform.', 2, 22000.00, FALSE),
       ('Mobile App Testing', 60000, 'Testing of mobile application.', 4, 18000.00, FALSE),
       ('Cybersecurity Audit', 85000, 'Audit of security systems.', 4, 25000.00, FALSE),
       ('Customer Portal', 75000, 'Building a customer self-service portal.', 4, 20000.00, TRUE);

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
       ('Analysis Design', 'Analyze results.', 5, FALSE),
       ('Database Optimization', 'Optimizing database performance.', 1, FALSE),
       ('API Enhancements', 'Improving backend API efficiency.', 1, FALSE),
       ('UI Enhancements', 'Enhancements to the iOS app UI.', 2, FALSE),
       ('Feature Testing', 'Testing Android app features.', 2, FALSE),
       ('Security Improvements', 'Implementing security fixes.', 1, FALSE),
       ('Push Notifications', 'Adding push notifications for Android.', 2, FALSE);

INSERT INTO task (task_name, start_date, end_date, status, employee_id, actual_hours, estimated_hours, subproject_id, is_archived)
VALUES ('Frontend Design', '2024-11-01', '2024-11-03', 'INPROGRESS', 1, 7, 10, 1, FALSE),
       ('Backend Design', '2024-12-04', '2024-12-05', 'INPROGRESS', 1, 3, 11, 1, FALSE),
       ('UI test', '2024-11-18', '2024-11-19', 'NOTSTARTED', 1, 0, 10, 1, FALSE),
       ('end-to-end test', '2024-11-05', '2024-11-06', 'NOTSTARTED', 1, 0, 12, 1, FALSE),
       ('Backend Optimization', '2024-11-06', '2024-11-09', 'NOTSTARTED', 1, 0, 6, 2, FALSE),
       ('Database Setup', '2024-11-11', '2024-11-12', 'INPROGRESS', 1, 0, 13, 3, FALSE),
       ('API Integration', '2024-11-16', '2024-11-20', 'INPROGRESS', 1, 0, 17, 4, FALSE),
       ('deploy web app', '2024-11-16', '2024-11-20', 'INPROGRESS', 1, 0, 17, 1, FALSE),
       ('System Test', '2024-11-21', '2024-11-24', 'NOTSTARTED', 1, 0, 20, 5, FALSE),

       ('UI Components', '2024-11-02', '2024-11-03', 'INPROGRESS', 3, 5, 8, 6, FALSE),
       ('Cloud Setup', '2024-11-06', '2024-11-08', 'NOTSTARTED', 3, 0, 17, 7, FALSE),
       ('Security Check', '2024-11-11', '2024-11-12', 'INPROGRESS', 3, 0, 10, 8, FALSE),
       ('Performance Test', '2024-11-16', '2024-11-18', 'NOTSTARTED', 3, 0, 7, 9, FALSE),
       ('Analysis Completion', '2024-11-21', '2024-11-23', 'INPROGRESS', 3, 6, 14, 10, FALSE),
       ('Performance Audit', '2024-11-07', '2024-11-10', 'NOTSTARTED', 3, 0, 12, 2, FALSE),
       ('Security Patching', '2024-11-08', '2024-11-11', 'INPROGRESS', 3, 0, 15, 1, FALSE),
       ('Error Monitoring Setup', '2024-11-10', '2024-11-13', 'INPROGRESS', 3, 0, 9, 2, FALSE);

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

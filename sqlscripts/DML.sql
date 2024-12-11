INSERT INTO employee (email, password, role, employee_rate, max_hours)
VALUES ('ama', 'Amalie123', 'PROJECTLEADER', 50, 5),
       ('zuzu', 'Zuhur1234', 'PROJECTLEADER', 20, 6),
       ('worker', 'Worker123', 'WORKER', 25, 7),
       ('worker2@example.com', 'Worker123', 'WORKER', 30, 8);

INSERT INTO project (project_name, budget, project_description, employee_id, is_archived)
VALUES ('Project Alpha', 100000, 'Alpha description', 1, FALSE),
       ('Project Beta', 50000, 'Beta description', 2, FALSE),
       ('Project Gamma', 75000, 'Gamma description', 1, FALSE),
       ('Project Delta', 60000, 'Delta description', 2, TRUE);

INSERT INTO subproject (subproject_name, subproject_description, project_id, is_archived)
VALUES ('Alpha Subproject A', 'Alpha A desc', 1, FALSE),
       ('Alpha Subproject B', 'Alpha B desc', 1, FALSE),
       ('Beta Subproject A', 'Beta A desc', 2, FALSE),
       ('Zuzu Subproject A', 'Description for Zuzu Subproject A', 3, FALSE),
       ('Zuzu Subproject B', 'Description for Zuzu Subproject B', 3, FALSE);

INSERT INTO task (task_name, start_date, end_date, status, employee_id, actual_hours, estimated_hours, subproject_id, is_archived)
VALUES ('Task 1', '2024-11-01', '2024-11-05', 'INPROGRESS', 3, 5, 10, 1, FALSE),
       ('Task 2', '2024-11-02', '2024-11-06', 'NOTSTARTED', 4, 10, 20, 2, FALSE),
       ('Task 3', '2024-11-03', '2024-11-07', 'COMPLETE', NULL, 15, 15, 3, FALSE),
       ('Task A2', '2024-11-06', '2024-11-10', 'NOTSTARTED', 4, 100, 15, 4, FALSE),
       ('Task B1', '2024-11-11', '2024-11-15', 'COMPLETE', NULL, 10, 20, 5, FALSE),
       ('Task B2', '2024-11-16', '2024-11-20', 'OVERDUE', NULL, 100, 12, 5, FALSE);

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

SELECT * FROM DateRange;

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
                      estimated_hours / DATEDIFF(end_date, start_date) AS hours_per_day
                  FROM
                      task
                          JOIN
                      employee
                      ON task.employee_id = employee.employee_id
              ) AS task_employee
                  JOIN DateRange
                       ON task_employee.start_date <= DateRange.date_column
                           AND task_employee.end_date >= DateRange.date_column
     ) AS h
GROUP BY
    employee_id,
    date_column;

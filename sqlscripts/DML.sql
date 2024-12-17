INSERT INTO employee (email, password, role, employee_rate, max_hours)
VALUES ('adam@worker.dk', 'Password12345678', 'WORKER', 200, 8),
       ('adam@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 8),
       ('eksamen@worker.dk', 'Password12345678', 'WORKER', 200, 8),
       ('eksamen@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 8),
       ('patrick@worker.dk', 'Password12345678', 'WORKER', 200, 8),
       ('patrick@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 8),
       ('mads@worker.dk', 'Password12345678', 'WORKER', 200, 8),
       ('mads@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 8),
       ('censor@worker.dk', 'Password12345678', 'WORKER', 200, 8),
       ('censor@projectleader', 'Password12345678', 'PROJECTLEADER', 200, 8);

INSERT INTO project (project_name, budget, project_description, employee_id, material_cost, is_archived)
VALUES ('Corporate Website Overhaul', 120000, 'Redesign and optimize the corporate website for better UX.', 1, 18000.00,
        FALSE),
       ('Mobile App Launch', 80000, 'Develop and launch a customer-facing mobile application.', 2, 25000.00, FALSE),
       ('Cloud Migration Initiative', 95000, 'Migrate all company data and services to the cloud.', 1, 22000.00, FALSE),
       ('Security Upgrade Project', 60000, 'Enhance the company’s cybersecurity measures.', 2, 15000.00, TRUE),
       ('Greenhouse Expansion', 12000.00, 'Expanding the current greenhouse facility.', 1, 5000.00, FALSE),
       ('Solar Panel Installation', 25000.00, 'Installing solar panels for renewable energy.', 2, 8000.00, FALSE),
       ('Office Renovation', 18000.00, 'Renovating the company office.', 3, 6000.00, TRUE),
       ('Community Garden', 8000.00, 'Setting up a community garden.', 4, 3000.00, FALSE),
       ('Website Redesign', 10000.00, 'Overhauling the company website.', 5, 4000.00, TRUE),
       ('Product Launch Campaign', 15000.00, 'Marketing campaign for new product launch.', 6, 7000.00, FALSE),
       ('Warehouse Construction', 50000.00, 'Building a new warehouse for inventory.', 1, 20000.00, FALSE),
       ('Cybersecurity Upgrade', 14000.00, 'Improving the company’s IT security.', 2, 5000.00, FALSE),
       ('Annual Conference', 20000.00, 'Organizing the yearly company conference.', 3, 8000.00, TRUE),
       ('Fleet Maintenance', 12000.00, 'Servicing the company’s vehicle fleet.', 4, 4000.00, FALSE),
       ('App Development', 30000.00, 'Developing a mobile application.', 5, 10000.00, TRUE),
       ('Training Program', 9000.00, 'Employee training program.', 6, 3000.00, FALSE),
       ('Retail Store Design', 22000.00, 'Designing a new retail store.', 1, 9000.00, TRUE),
       ('New Product Prototype', 18000.00, 'Developing a prototype for a new product.', 2, 6000.00, FALSE),
       ('Building Retrofit', 40000.00, 'Upgrading building for energy efficiency.', 3, 15000.00, FALSE),
       ('Customer Portal', 17000.00, 'Creating a self-service portal for customers.', 4, 7000.00, TRUE),
       ('Factory Automation', 60000.00, 'Automating the production line.', 5, 25000.00, FALSE),
       ('Research Study', 8000.00, 'Conducting a market research study.', 6, 3000.00, TRUE),
       ('Event Sponsorship', 10000.00, 'Sponsoring a community event.', 1, 4000.00, FALSE),
       ('Supply Chain Optimization', 25000.00, 'Improving supply chain processes.', 2, 10000.00, TRUE);

INSERT INTO subproject (subproject_name, subproject_description, project_id, is_archived)
VALUES ('UI/UX Design', 'Design an intuitive user interface for the website.', 1, FALSE),
       ('Backend Refactor', 'Refactor and optimize the backend systems.', 1, FALSE),
       ('iOS App Development', 'Create an iOS version of the mobile app.', 2, FALSE),
       ('Android App Development', 'Create an Android version of the mobile app.', 2, FALSE),
       ('Data Migration', 'Transfer data to the new cloud infrastructure.', 3, FALSE),
       ('Site Survey', 'Conducting a site survey for expansion.', 1, FALSE),
       ('Greenhouse Foundation', 'Laying the foundation for greenhouse.', 1, FALSE),
       ('Panel Procurement', 'Purchasing solar panels.', 2, FALSE),
       ('Electrical Setup', 'Installing electrical systems for solar panels.', 2, TRUE),
       ('Office Layout Design', 'Creating new office layout designs.', 3, FALSE),
       ('Furniture Procurement', 'Buying furniture for renovated office.', 3, TRUE),
       ('Soil Testing', 'Testing soil for community garden.', 4, FALSE),
       ('Garden Layout Design', 'Designing the layout of the garden.', 4, FALSE),
       ('UI/UX Design', 'Developing new UI/UX for website.', 5, FALSE),
       ('Content Creation', 'Writing content for redesigned website.', 5, TRUE),
       ('Ad Campaign Design', 'Designing ads for the product launch.', 6, FALSE),
       ('Social Media Outreach', 'Promoting the launch on social media.', 6, TRUE),
       ('Foundation Construction', 'Constructing foundation for warehouse.', 1, FALSE),
       ('Roof Installation', 'Installing roof on new warehouse.', 1, TRUE),
       ('Network Security Assessment', 'Assessing current IT security.', 2, FALSE),
       ('Firewall Installation', 'Installing new firewalls.', 2, TRUE),
       ('Conference Agenda Planning', 'Planning agenda for conference.', 3, FALSE),
       ('Venue Booking', 'Booking venue for annual conference.', 3, TRUE),
       ('Fleet Inspection', 'Inspecting vehicles for maintenance.', 4, FALSE),
       ('Repair Scheduling', 'Scheduling repairs for vehicles.', 4, TRUE);

INSERT INTO task (task_name, start_date, end_date, status, employee_id, actual_hours, estimated_hours, subproject_id,
                  is_archived)
VALUES ('Wireframe Creation', '2024-11-01', '2024-11-05', 'INPROGRESS', 3, 5, 10, 1, FALSE),
       ('Database Optimization', '2024-11-02', '2024-11-06', 'NOTSTARTED', 4, 10, 20, 2, FALSE),
       ('iOS Feature Implementation', '2024-11-03', '2024-11-07', 'COMPLETE', NULL, 15, 15, 3, FALSE),
       ('Android Testing', '2024-11-06', '2024-11-10', 'NOTSTARTED', 4, 100, 15, 4, FALSE),
       ('Cloud Configuration', '2024-11-11', '2024-11-15', 'COMPLETE', NULL, 10, 20, 5, FALSE),
       ('Wireframe Creation', '2024-11-01', '2024-11-05', 'INPROGRESS', 3, 5, 10, 1, FALSE),
       ('Database Optimization', '2024-11-02', '2024-11-06', 'NOTSTARTED', 4, 10, 20, 2, FALSE),
       ('iOS Feature Implementation', '2024-11-03', '2024-11-07', 'COMPLETE', NULL, 15, 15, 3, FALSE),
       ('Android Testing', '2024-11-06', '2024-11-10', 'NOTSTARTED', 4, 100, 15, 4, FALSE),
       ('Cloud Configuration', '2024-11-11', '2024-11-15', 'COMPLETE', NULL, 10, 20, 5, FALSE),
       ('UI Mockup Creation', '2024-12-01', '2024-12-03', 'INPROGRESS', 1, 8, 12, 1, FALSE),
       ('API Integration', '2024-12-04', '2024-12-07', 'NOTSTARTED', 2, 0, 10, 2, FALSE),
       ('Performance Testing', '2024-12-08', '2024-12-12', 'COMPLETE', 3, 15, 20, 3, FALSE),
       ('Security Patching', '2024-12-13', '2024-12-15', 'INPROGRESS', 4, 5, 8, 4, TRUE),
       ('Cloud Server Setup', '2024-12-16', '2024-12-20', 'NOTSTARTED', 5, 0, 10, 5, FALSE),
       ('Database Backup', '2024-12-21', '2024-12-22', 'COMPLETE', 6, 2, 4, 6, FALSE),
       ('Feature Deployment', '2024-12-23', '2024-12-25', 'INPROGRESS', 1, 7, 10, 1, FALSE),
       ('Bug Fixing Sprint', '2024-12-26', '2024-12-29', 'NOTSTARTED', 2, 0, 15, 2, FALSE),
       ('UI Color Scheme Finalization', '2024-12-30', '2025-01-02', 'COMPLETE', 3, 5, 7, 3, TRUE),
       ('Content Migration', '2025-01-03', '2025-01-05', 'INPROGRESS', 4, 10, 12, 4, FALSE),
       ('System Logs Analysis', '2025-01-06', '2025-01-10', 'NOTSTARTED', 5, 0, 8, 5, FALSE),
       ('Integration Testing', '2025-01-11', '2025-01-15', 'COMPLETE', 6, 12, 15, 6, TRUE),
       ('Mobile App Analytics', '2025-01-16', '2025-01-20', 'INPROGRESS', 1, 10, 14, 1, FALSE),
       ('Accessibility Review', '2025-01-21', '2025-01-25', 'NOTSTARTED', 2, 0, 9, 2, FALSE),
       ('Workflow Automation', '2025-01-26', '2025-01-30', 'COMPLETE', 3, 20, 20, 3, FALSE),
       ('API Testing', '2025-02-01', '2025-02-05', 'INPROGRESS', 4, 6, 8, 4, TRUE),
       ('Feature Documentation', '2025-02-06', '2025-02-10', 'NOTSTARTED', 5, 0, 6, 5, FALSE),
       ('Module Refactoring', '2025-02-11', '2025-02-15', 'COMPLETE', 6, 15, 15, 6, FALSE),
       ('Unit Testing', '2025-02-16', '2025-02-20', 'INPROGRESS', 1, 10, 12, 1, FALSE),
       ('Customer Feedback Analysis', '2025-02-21', '2025-02-25', 'NOTSTARTED', 2, 0, 7, 2, FALSE),
       ('Data Migration', '2025-02-26', '2025-03-01', 'COMPLETE', 3, 25, 20, 3, TRUE),
       ('Code Review', '2025-03-02', '2025-03-05', 'INPROGRESS', 4, 8, 12, 4, FALSE),
       ('Network Configuration', '2025-03-06', '2025-03-10', 'NOTSTARTED', 5, 0, 15, 5, FALSE),
       ('Performance Optimization', '2025-03-11', '2025-03-15', 'COMPLETE', 6, 18, 18, 6, TRUE),
       ('UI Feedback Integration', '2025-03-16', '2025-03-20', 'INPROGRESS', 1, 12, 16, 1, FALSE),
       ('Bug Prioritization', '2025-03-21', '2025-03-25', 'NOTSTARTED', 2, 0, 10, 2, FALSE),
       ('Prototype Deployment', '2025-03-26', '2025-03-30', 'COMPLETE', 3, 15, 15, 3, FALSE),
       ('Regression Testing', '2025-04-01', '2025-04-05', 'INPROGRESS', 4, 10, 10, 4, TRUE),
       ('Technical Documentation', '2025-04-06', '2025-04-10', 'NOTSTARTED', 5, 0, 5, 5, FALSE),
       ('User Training', '2025-04-11', '2025-04-15', 'COMPLETE', 6, 20, 20, 6, FALSE),
       ('Security Audit', '2025-04-16', '2025-04-20', 'INPROGRESS', 1, 15, 15, 1, FALSE),
       ('Compliance Review', '2025-04-21', '2025-04-25', 'NOTSTARTED', 2, 0, 10, 2, FALSE),
       ('Data Backup', '2025-04-26', '2025-04-30', 'COMPLETE', 3, 10, 10, 3, TRUE),
       ('Feature Testing', '2025-05-01', '2025-05-05', 'INPROGRESS', 4, 8, 8, 4, FALSE),
       ('User Feedback Analysis', '2025-05-06', '2025-05-10', 'NOTSTARTED', 5, 0, 5, 5, FALSE),
       ('Code Refactoring', '2025-05-11', '2025-05-15', 'COMPLETE', 6, 12, 12, 6, FALSE),
       ('UI Redesign', '2025-05-16', '2025-05-20', 'INPROGRESS', 1, 10, 10, 1, TRUE),
       ('Feature Prioritization', '2025-05-21', '2025-05-25', 'NOTSTARTED', 2, 0, 5, 2, FALSE),
       ('Data Analysis', '2025-05-26', '2025-05-30', 'COMPLETE', 3, 15, 15, 3, FALSE),
       ('Performance Monitoring', '2025-06-01', '2025-06-05', 'INPROGRESS', 4, 20, 20, 4, FALSE),
       ('User Interface Testing', '2025-06-06', '2025-06-10', 'NOTSTARTED', 5, 0, 10, 5, FALSE),
       ('System Upgrade', '2025-04-11', '2025-04-15', 'COMPLETE', 6, 20, 20, 6, TRUE),
       ('Stakeholder Review', '2025-04-16', '2025-04-20', 'INPROGRESS', 1, 14, 18, 1, FALSE),
       ('Error Logging Setup', '2025-04-21', '2025-04-25', 'NOTSTARTED', 2, 0, 8, 2, FALSE),
       ('Product Finalization', '2025-04-26', '2025-04-30', 'COMPLETE', 3, 30, 25, 3, TRUE);

DROP TABLE IF EXISTS all_dates;
CREATE TABLE all_dates
(
    date_column DATE NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS DateRange;
CREATE TABLE DateRange AS
SELECT *
FROM (WITH RECURSIVE date_generator AS (SELECT DATE('2023-01-01') AS date_column
                                        UNION ALL
                                        SELECT DATE_ADD(date_column, INTERVAL 1 DAY)
                                        FROM date_generator
                                        WHERE date_column < '2025-01-01')
      SELECT date_column
      FROM date_generator
      WHERE DAYOFWEEK(date_column) NOT IN (1, 7)) AS a;

DROP VIEW IF EXISTS employee_workload_pr_day;
CREATE VIEW employee_workload_pr_day AS
SELECT date_column,
       SUM(hours_per_day) AS total_hours_per_day,
       employee_id,
       MAX(max_hours)     AS max_hours_per_employee
FROM (SELECT task_employee.*,
             DateRange.date_column
      FROM (SELECT task.*,
                   employee.max_hours,
                   estimated_hours / COUNT(DateRange.date_column) AS hours_per_day
            FROM task
                     JOIN
                 employee
                 ON task.employee_id = employee.employee_id
                     JOIN
                 DateRange
                 ON task.start_date <= DateRange.date_column
                     AND task.end_date >= DateRange.date_column
            WHERE DAYOFWEEK(DateRange.date_column) NOT IN (1, 7)
            GROUP BY task.task_id, employee.employee_id) AS task_employee
               JOIN DateRange
                    ON task_employee.start_date <= DateRange.date_column
                        AND task_employee.end_date >= DateRange.date_column
                        AND DAYOFWEEK(DateRange.date_column) NOT IN (1, 7)) AS h
GROUP BY employee_id,
         date_column;

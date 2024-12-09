-- Insert into Employee table
INSERT INTO employee (employee_id, email, password, role, employee_rate, max_hours)
VALUES
    (1, 'alice@example.com', 'Password1', 'PROJECTLEADER', 100, 40),
    (2, 'bob@example.com', 'Secure123', 'WORKER', 80, 35),
    (3, 'charlie@example.com', 'Worker5678', 'WORKER', 70, 30),
    (4, 'diana@example.com', 'Leader999', 'PROJECTLEADER', 120, 45),
    (5, 'eve@example.com', 'StrongPass2', 'WORKER', 75, 25),
    (6, 'frank@example.com', 'Pass8765A', 'WORKER', 85, 30),
    (7, 'grace@example.com', 'TopLeader3', 'PROJECTLEADER', 110, 50),
    (8, 'harry@example.com', 'Harry007X', 'WORKER', 65, 20),
    (9, 'ivy@example.com', 'LeaderXZ', 'PROJECTLEADER', 130, 60),
    (10, 'jack@example.com', 'Jacky789', 'WORKER', 90, 50);

-- Insert into Project table
INSERT INTO project (project_id, project_name, budget, project_description, employee_id, material_cost)
VALUES
    (1, 'Website Development', 10000.00, 'Developing a new website', 1, 2000.00),
    (2, 'Mobile App', 15000.00, 'Building a cross-platform app', 1, 3000.00),
    (3, 'AI Research', 25000.00, 'Research on AI technologies', 2, 5000.00),
    (4, 'Marketing Campaign', 12000.00, 'Marketing a new product', 3, 1500.00),
    (5, 'Backend Revamp', 8000.00, 'Improving server architecture', 4, 2500.00);

-- Insert into Subproject table
INSERT INTO subproject (subproject_id, subproject_name, subproject_description, project_id)
VALUES
    (1, 'Frontend Design', 'Designing the user interface', 1),
    (2, 'API Development', 'Creating APIs for the app', 1),
    (3, 'Data Collection', 'Gathering datasets for AI', 2),
    (4, 'Content Creation', 'Generating ad content', 3),
    (5, 'Database Optimization', 'Optimizing data storage', 4);

-- Insert into Task table
INSERT INTO task (task_id, task_name, start_date, end_date, status, employee_id, actual_hours, estimated_hours, subproject_id)
VALUES
    (1, 'Wireframing', '2023-01-01', '2023-01-10', 'COMPLETE', 1, 20, 15, 1),
    (2, 'API Testing', '2023-01-11', '2023-01-15', 'INPROGRESS', 2, 10, 20, 2),
    (3, 'Data Cleaning', '2023-01-16', '2023-01-20', 'NOTSTARTED', 3, 0, 30, 3),
    (4, 'Ad Copywriting', '2023-01-21', '2023-01-25', 'COMPLETE', 4, 15, 15, 4),
    (5, 'Schema Redesign', '2023-01-26', '2023-01-30', 'OVERDUE', 5, 25, 20, 5);

-- Retrieve data from tables
SELECT * FROM employee;
SELECT * FROM project;
SELECT * FROM subproject;
SELECT * FROM task;
SELECT * FROM spend;

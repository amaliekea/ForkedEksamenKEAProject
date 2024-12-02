-- Indsæt data i Employee-tabellen (2 projektledere og 2 arbejdere)
INSERT INTO employee (email, password, role, employee_rate, max_hours) VALUES
                                                                           ('ama', '123', 'PROJECTLEADER', 50, 40), -- Projektleder 1
                                                                           ('zuzu', '1234', 'PROJECTLEADER', 20, 35), -- Projektleder 2
                                                                           ('worker1@example.com', 'password123', 'WORKER', 25, 30), -- Arbejder 1
                                                                           ('worker2@example.com', 'password456', 'WORKER', 30, 25); -- Arbejder 2

INSERT INTO project (project_name, budget, project_description, employee_id, material_cost, employee_cost, is_archived)
VALUES
    ('Project Alpha', 100000, 'Alpha description', 1, 2000.00, 5000.00, FALSE), -- Aktivt projekt
    ('Project Beta', 50000, 'Beta description', 2, 1500.00, 3000.00, FALSE),   -- Aktivt projekt
    ('Project Gamma', 75000, 'Gamma description', 1, 1000.00, 4000.00, False);  -- Arkiveret projekt

-- Indsæt data i Subproject-tabellen
INSERT INTO subproject (subproject_name, subproject_description, project_id) VALUES
                                                                                 ('Alpha Subproject A', 'Alpha A desc', 1),
                                                                                 ('Alpha Subproject B', 'Alpha B desc', 1),
                                                                                 ('Beta Subproject A', 'Beta A desc', 2);

-- Indsæt data i Task-tabellen
INSERT INTO task (task_name, start_date, end_date, status, employee_id, actual_hours, estimated_hours, subproject_id)
VALUES
    ('Task 1', '2024-11-01', '2024-11-05', 'INPROGRESS', 3, 5, 10, 1), -- Tildeler medarbejder med ID 3
    ('Task 2', '2024-11-02', '2024-11-06', 'NOTSTARTED', 4, 0, 20, 2), -- Tildeler medarbejder med ID 4
    ('Task 3', '2024-11-03', '2024-11-07', 'COMPLETE', NULL, 15, 15, 3); -- Ingen medarbejder tildelt

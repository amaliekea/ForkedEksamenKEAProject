-- Indsæt data i Employee-tabellen
INSERT INTO employee (email, password, role, employee_rate, max_hours) VALUES
                                                                           ('ama', '123', 'PROJECTLEADER', 450, 40), -- Projektleder 1
                                                                           ('zuzu', '1234', 'PROJECTLEADER', 620, 35), -- Projektleder 2
                                                                           ('worker', '123', 'WORKER', 325, 30), -- Arbejder 1
                                                                           ('worker2@example.com', 'password456', 'WORKER', 310, 25), -- Arbejder 2
                                                                           ('lead_alpha@example.com', 'pass123', 'PROJECTLEADER', 570, 40), -- Projektleder 3
                                                                           ('lead_beta@example.com', 'pass456', 'PROJECTLEADER', 390, 45), -- Projektleder 4
                                                                           ('worker3@example.com', 'workerpass', 'WORKER', 458, 30), -- Arbejder 3
                                                                           ('worker4@example.com', 'workerpass2', 'WORKER', 627, 25); -- Arbejder 4

-- Indsæt data i Project-tabellen
INSERT INTO project (project_name, budget, project_description, employee_id, material_cost) VALUES
                                                                                                ('Project Alpha', 100000, 'Alpha description', 1, 2000.00), -- Tilknyttet Projektleder 1
                                                                                                ('Project Beta', 50000, 'Beta description', 2, 1500.00), -- Tilknyttet Projektleder 2
                                                                                                ('Project Gamma', 75000, 'Gamma description', 3, 1800.00), -- Tilknyttet Projektleder 3
                                                                                                ('Project Delta', 60000, 'Delta description', 4, 2200.00); -- Tilknyttet Projektleder 4

-- Indsæt data i Subproject-tabellen
INSERT INTO subproject (subproject_name, subproject_description, project_id) VALUES
                                                                                 ('Alpha Subproject A', 'Alpha A desc', 1),
                                                                                 ('Alpha Subproject B', 'Alpha B desc', 1),
                                                                                 ('Beta Subproject A', 'Beta A desc', 2),
                                                                                 ('Gamma Subproject A', 'Gamma A desc', 3),
                                                                                 ('Gamma Subproject B', 'Gamma B desc', 3),
                                                                                 ('Delta Subproject A', 'Delta A desc', 4),
                                                                                 ('Delta Subproject B', 'Delta B desc', 4);

-- Indsæt data i Task-tabellen
INSERT INTO task (task_name, start_date, end_date, status, employee_id, actual_hours, estimated_hours, subproject_id) VALUES
                                                                                                                          ('Task 1', '2024-11-01', '2024-11-05', 'INPROGRESS', 3, 5, 10, 1), -- Tildeler medarbejder med ID 3
                                                                                                                          ('Task 2', '2024-11-02', '2024-11-06', 'NOTSTARTED', 4, 0, 20, 2), -- Tildeler medarbejder med ID 4
                                                                                                                          ('Task 3', '2024-11-03', '2024-11-07', 'COMPLETE', NULL, 15, 15, 3), -- Ingen medarbejder tildelt
                                                                                                                          ('Task 4', '2024-12-01', '2024-12-10', 'NOTSTARTED', 5, 0, 25, 4), -- Tildeler medarbejder med ID 5
                                                                                                                          ('Task 5', '2024-12-02', '2024-12-12', 'INPROGRESS', 6, 5, 20, 5), -- Tildeler medarbejder med ID 6
                                                                                                                          ('Task 6', '2024-12-03', '2024-12-15', 'OVERDUE', 7, 10, 30, 6), -- Tildeler medarbejder med ID 7
                                                                                                                          ('Task 7', '2024-12-05', '2024-12-20', 'COMPLETE', NULL, 12, 15, 7); -- Ingen medarbejder tildelt

INSERT INTO employee (email, password, role, employee_rate, max_hours)
VALUES ('patrick@worker.dk', 'Password12345678', 'WORKER', 200, 8),
       ('patrick@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 8),
       ('mads@worker.dk', 'Password12345678', 'WORKER', 200, 8),
       ('mads@projectleader.dk', 'Password12345678', 'PROJECTLEADER', 200, 8);

INSERT INTO project (project_name, budget, project_description, employee_id, material_cost, is_archived)
VALUES ('Website Overhaul', 120000, 'Redesign af hjemmesiden.', 1, 18000.00, FALSE),
       ('App Udvikling', 80000, 'Udvikling af ny app.', 2, 25000.00, FALSE),
       ('Cloud Opsætning', 90000, 'Opsætning af cloud tjenester.', 3, 20000.00, FALSE),
       ('System Opgradering', 50000, 'Opgradering af systemer.', 4, 15000.00, FALSE),
       ('Data Analyse', 60000, 'Analyse af kundedata.', 1, 20000.00, TRUE);

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
       ('Backend Optimering', '2024-11-06', '2024-11-10', 'NOTSTARTED', 1, 0, 6, 2, FALSE),
       ('Database Setup', '2024-11-11', '2024-11-15', 'INPROGRESS', 1, 0, 18, 3, FALSE),
       ('API Integration', '2024-11-16', '2024-11-20', 'COMPLETE', 1, 0, 17, 4, FALSE),
       ('System Test', '2024-11-21', '2024-11-25', 'NOTSTARTED', 1, 0, 20, 5, FALSE),

       ('UI Komponenter', '2024-11-01', '2024-11-05', 'INPROGRESS', 3, 0, 8, 6, FALSE),
       ('Cloud Opsætning', '2024-11-06', '2024-11-10', 'NOTSTARTED', 3, 0, 23, 7, FALSE),
       ('Sikkerhedstjek', '2024-11-11', '2024-11-15', 'COMPLETE', 3, 0, 22, 8, FALSE),
       ('Performance Test', '2024-11-16', '2024-11-20', 'NOTSTARTED', 3, 0, 7, 9, FALSE),
       ('Analyse Afslutning', '2024-11-21', '2024-11-25', 'INPROGRESS', 3, 0, 24, 10, FALSE);

CREATE TABLE employee (
                          employee_id INT PRIMARY KEY NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          role VARCHAR(20) DEFAULT 'WORKER',
                          employee_rate INT DEFAULT 0,
                          max_hours INT DEFAULT 0);

CREATE TABLE project (
                         project_id INT PRIMARY KEY NOT NULL,
                         project_name VARCHAR(255) NOT NULL,
                         budget DECIMAL(10, 2) NOT NULL,
                         project_description VARCHAR(255) NOT NULL,
                         employee_id INT,
                         material_cost DECIMAL(10, 2) DEFAULT 0.00,
                         is_archived BOOLEAN DEFAULT FALSE,
                         FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE subproject (
                            subproject_id INT PRIMARY KEY NOT NULL,
                            subproject_name VARCHAR(255) NOT NULL,
                            subproject_description VARCHAR(255) NOT NULL,
                            is_archived BOOLEAN DEFAULT FALSE,
                            project_id INT NOT NULL,
                            FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE task (
                      task_id INT PRIMARY KEY NOT NULL,
                      task_name VARCHAR(255) NOT NULL,
                      start_date DATE,
                      end_date DATE,
                      status VARCHAR(20) DEFAULT 'NOTSTARTED',
                      is_archived BOOLEAN DEFAULT FALSE,
                      employee_id INT,
                      actual_hours INT DEFAULT 0,
                      estimated_hours INT DEFAULT 0,
                      subproject_id INT,
                      FOREIGN KEY (subproject_id) REFERENCES subproject(subproject_id),
                      FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

CREATE VIEW spend AS
SELECT
    project.project_id,
    COALESCE(SUM(task.actual_hours * employee.employee_rate), 0) AS spend
FROM
    project
        JOIN subproject ON project.project_id = subproject.project_id
        JOIN task ON subproject.subproject_id = task.subproject_id
        JOIN employee ON task.employee_id = employee.employee_id
GROUP BY
    project.project_id;

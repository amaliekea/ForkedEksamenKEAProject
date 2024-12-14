DROP DATABASE IF EXISTS project_management;
CREATE DATABASE project_management;
USE project_management;

CREATE TABLE employee (
                          employee_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          role ENUM('PROJECTLEADER', 'WORKER') DEFAULT 'WORKER',
                          employee_rate INT DEFAULT 0,
                          max_hours INT DEFAULT 0,
                          CONSTRAINT chk_password CHECK (
                              LENGTH(password) >= 8 AND -- passwordet er mindst 8 tegn langt.
                              password REGEXP '.*[A-Z].*' AND -- passwordet skal indeholde mindst et stort bogstav
                              password REGEXP '.*[0-9].*' -- Tjekker, at passwordet indeholder mindst et tal
)
    );


CREATE TABLE project (
                         project_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                         project_name VARCHAR(255) NOT NULL,
                         budget DECIMAL(10, 2) NOT NULL,
                         project_description VARCHAR(255) NOT NULL,
                         employee_id INT,
                         material_cost DECIMAL(10, 2) DEFAULT 0.00,
                         is_archived BOOLEAN DEFAULT FALSE,
                         FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE subproject (
                            subproject_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                            subproject_name VARCHAR(255) NOT NULL,
                            subproject_description VARCHAR(255) NOT NULL,
                            is_archived BOOLEAN DEFAULT FALSE,
                            project_id INT NOT NULL,
                            FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE task (
                      task_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
                      task_name VARCHAR(255) NOT NULL,
                      start_date DATE,
                      end_date DATE,
                      status ENUM('INPROGRESS', 'COMPLETE', 'OVERDUE', 'NOTSTARTED') DEFAULT 'NOTSTARTED',
                      is_archived BOOLEAN DEFAULT FALSE,
                      employee_id INT,
                      actual_hours INT DEFAULT 0,
                      estimated_hours INT DEFAULT 0,
                      subproject_id INT,
                      FOREIGN KEY (subproject_id) REFERENCES subproject(subproject_id),
                      FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);

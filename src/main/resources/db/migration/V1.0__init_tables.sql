CREATE TABLE lectors (
                        id BIGSERIAL PRIMARY KEY not null,
                        first_name VARCHAR(255) NOT NULL,
                        second_name VARCHAR(255),
                        last_name VARCHAR(255) NOT NULL,
                        degree VARCHAR(50) NOT NULL,
                        salary numeric(38,2) NOT NULL
);

ALTER TABLE if EXISTS lectors
    ADD CONSTRAINT unique_first_last_name UNIQUE (first_name, last_name);

CREATE TABLE departments (
                            id BIGSERIAL PRIMARY KEY not null,
                            name VARCHAR(255) NOT NULL UNIQUE,
                            head_of_department_id BIGINT
);

ALTER TABLE if EXISTS departments
    ADD CONSTRAINT fk_department_head FOREIGN KEY (head_of_department_id) REFERENCES lectors(id) ON DELETE SET NULL;

CREATE TABLE department_lectors (
                                    department_id BIGINT NOT NULL,
                                    lector_id BIGINT NOT NULL,
                                    PRIMARY KEY (department_id, lector_id),
                                    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE,
                                    CONSTRAINT fk_lector FOREIGN KEY (lector_id) REFERENCES lectors(id) ON DELETE CASCADE
);
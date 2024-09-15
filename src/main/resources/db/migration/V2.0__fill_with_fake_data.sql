INSERT INTO lectors (first_name, second_name, last_name, degree, salary) VALUES
    ('John', 'A.', 'Doe', 'ASSISTANT', 75000.00),
    ('Jane', 'B.', 'Smith', 'ASSOCIATE', 78000.00),
    ('Michael', 'C.', 'Johnson', 'PROFESSOR', 72000.00),
    ('Alice', 'D.', 'Williams', 'ASSISTANT', 70000.00),
    ('Robert', 'E.', 'Brown', 'ASSOCIATE', 73000.00),
    ('Emily', 'F.', 'Davis', 'PROFESSOR', 76000.00),
    ('Daniel', 'G.', 'Miller', 'ASSISTANT', 74000.00),
    ('Sophia', 'H.', 'Garcia', 'ASSOCIATE', 77000.00),
    ('David', 'I.', 'Martinez', 'PROFESSOR', 71000.00);

INSERT INTO departments (name, head_of_department_id) VALUES
    ('Computer Science', (SELECT id FROM lectors WHERE first_name = 'John' AND last_name = 'Doe')),
    ('Mathematics', (SELECT id FROM lectors WHERE first_name = 'Jane' AND last_name = 'Smith')),
    ('Physics', NULL);

INSERT INTO department_lectors (department_id, lector_id) VALUES
    ((SELECT id FROM departments WHERE name = 'Computer Science'), (SELECT id FROM lectors WHERE first_name = 'John' AND last_name = 'Doe')),
    ((SELECT id FROM departments WHERE name = 'Computer Science'), (SELECT id FROM lectors WHERE first_name = 'Michael' AND last_name = 'Johnson')),
    ((SELECT id FROM departments WHERE name = 'Computer Science'), (SELECT id FROM lectors WHERE first_name = 'Alice' AND last_name = 'Williams'));

INSERT INTO department_lectors (department_id, lector_id) VALUES
    ((SELECT id FROM departments WHERE name = 'Mathematics'), (SELECT id FROM lectors WHERE first_name = 'Jane' AND last_name = 'Smith')),
    ((SELECT id FROM departments WHERE name = 'Mathematics'), (SELECT id FROM lectors WHERE first_name = 'Robert' AND last_name = 'Brown')),
    ((SELECT id FROM departments WHERE name = 'Mathematics'), (SELECT id FROM lectors WHERE first_name = 'Emily' AND last_name = 'Davis'));


INSERT INTO department_lectors (department_id, lector_id) VALUES
    ((SELECT id FROM departments WHERE name = 'Physics'), (SELECT id FROM lectors WHERE first_name = 'Daniel' AND last_name = 'Miller')),
    ((SELECT id FROM departments WHERE name = 'Physics'), (SELECT id FROM lectors WHERE first_name = 'Sophia' AND last_name = 'Garcia')),
    ((SELECT id FROM departments WHERE name = 'Physics'), (SELECT id FROM lectors WHERE first_name = 'David' AND last_name = 'Martinez'));
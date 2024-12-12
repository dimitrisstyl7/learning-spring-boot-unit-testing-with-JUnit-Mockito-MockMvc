insert into student(first_name, last_name, email_address)
values ('David', 'Adams', 'david.adams@email.com'),
       ('John', 'Doe', 'john.doe@email.com'),
       ('Ajay', 'Rao', 'ajay.rao@email.com'),
       ('Mary', 'Public', 'mary.public@email.com'),
       ('Maxwell', 'Dixon', 'max.dixon@email.com');

insert into math_grade(student_id, grade)
values (1, 80),
       (1, 90),
       (1, 72);

insert into science_grade(student_id, grade)
values (1, 80),
       (1, 90),
       (1, 72);

insert into history_grade(student_id, grade)
values (1, 80),
       (1, 90),
       (1, 72);
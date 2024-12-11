drop table if exists student;
create table student
(
    id            serial not null primary key,
    first_name    varchar(45) default null,
    last_name     varchar(45) default null,
    email_address varchar(45) default null
);

drop table if exists math_grade;
create table math_grade
(
    id         serial not null primary key,
    student_id int              default null,
    grade      double precision default null
);

drop table if exists science_grade;
create table science_grade
(
    id         serial not null primary key,
    student_id int              default null,
    grade      double precision default null
);

drop table if exists history_grade;
create table history_grade
(
    id         serial not null primary key,
    student_id int              default null,
    grade      double precision default null
);
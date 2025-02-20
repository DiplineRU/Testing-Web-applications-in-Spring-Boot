select *
from students;

-- Получить всех студентов, возраст которых находится между 20 и 40
select *
from students
where age > 20
  and age < 40;

-- Получить всех студентов, но отобразить только список их имен
select name
from students;

-- Получить всех студентов, у которых в имени присутствует буква О (или любая другая).
select *
from students
where name like '%о%';

-- Получить всех студентов, у которых возраст меньше идентификатора.
select name, age, id
from students
where age < students.id;

-- Получить всех студентов упорядоченных по возрасту.
select age, name
from students
ORDER BY age, name;

/*
Dropping the table if it exists. The line is required as CustomerServiceITTest was not dropping the schema between tests
despite our @DirtiesContext(classMode = AFTER_EACH_TEST_METHOD).
In a real world project, we would need a specific schema.sql for tests. And another one for the standard profile/run.
*/
DROP TABLE IF EXISTS customer;

CREATE TABLE customer (id SERIAL PRIMARY KEY, email VARCHAR(255), password VARCHAR(255), role VARCHAR(255));

drop database surveyapidb;
drop user surveyor;
create user  surveyor with password 'password';
create database surveyapidb with template=template0 owner=surveyor;
\connect surveyapidb;


-- psql -U postgres --file surveyapi_db.sql
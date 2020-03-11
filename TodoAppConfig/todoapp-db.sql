--Create our task table
create table Task (
   ID INTEGER IDENTITY PRIMARY KEY,
   Title VARCHAR(200),
   Description VARCHAR(2000),
   State varchar(20)
);

--Insert some example test data
insert into Task(title, description, state)
values ('Read the book ''İnce Memed''', 'Read Yaşar Kemal''s famous book ''İnce Memed''.', 'IN_PROGRESS');

insert into Task(title, description, state)
values ('Read ''My Name is Red''', 'Read the book ''My Name is Red'' from Orhan Pamuk.', 'TODO');

insert into Task(title, description, state)
values ('Watch the ''Captain America'' Movie', 'Watch the ''Captain America Movie'' at Cinema.', 'DONE');

insert into Task(title, description, state)
values ('Plan Holiday', 'Search and make a reservation for holiday in Antalya.', 'TODO');


--Create spring jdbc authentication default table schema
create table users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(50) not null,
    enabled boolean not null
);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

insert into users(username, password, enabled)
values('user','{noop}password', true);
insert into authorities(username, authority)
values('user','USER');
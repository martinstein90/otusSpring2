#drop database if exists library;
#create database library;
#use library;

create table authors(	id integer not null auto_increment, 
						firstname nvarchar(32) not null, 	
						lastname nvarchar(32) not null,
                        primary key(id));
                        
create table genres(id integer not null auto_increment, 
					title nvarchar(32) not null,
                    primary key(id));                
                
create table books(	id integer not null auto_increment, 
					title nvarchar(32) not null, 
                    author_id integer, 
                    genre_id integer,
                    primary key(id),
                    constraint fk_books_authors foreign key(author_id) references authors(id),
                    constraint fk_books_genres foreign key(genre_id) references genres(id));
                    
create unique index unicNameAuthor on authors(firstname, lastname);
create unique index unicTitleBoщk on books(title);
create unique index unicTitleGenre on genres(title);

insert genres(title) values("Пьеса");
insert genres(title) values("Роман");
insert genres(title) values("Автобиография");
insert genres(title) values("Сказка");
insert genres(title) values("Комедия");
insert genres(title) values("Очерк");
select * from genres;

insert authors(firstname, lastname) values("Антон", 	"Чехов");
insert authors(firstname, lastname) values("Евгений", 	"Шварц");
insert authors(firstname, lastname) values("Максим", 	"Горький");
insert authors(firstname, lastname) values("Шадерло", 	"де Лакло");
insert authors(firstname, lastname) values("Анри", 		"де Мопасан");
insert authors(firstname, lastname) values("Федор", 	"Достоевский");
insert authors(firstname, lastname) values("Николай", 	"Левашов");
insert authors(firstname, lastname) values("Александр", "Пушкин");
select * from authors;

insert books(title, author_id, genre_id) values("Вишневый сад",				1, 1);
insert books(title, author_id, genre_id) values("Дядя Ваня",				1, 1);
insert books(title, author_id, genre_id) values("Обыкновенное чудо",		2, 1);
insert books(title, author_id, genre_id) values("Чудак",					3, 2);
insert books(title, author_id, genre_id) values("На дне",					3, 2);
insert books(title, author_id, genre_id) values("Преступление и наказание",	6, 2);
insert books(title, author_id, genre_id) values("Милый друг",				5, 2);
insert books(title, author_id, genre_id) values("Пышка",					5, 2);
insert books(title, author_id, genre_id) values("Россия в кривых зеркалах",	7, 6);
insert books(title, author_id, genre_id) values("Опасные связи",			4, 2);
insert books(title, author_id, genre_id) values("Идиот", 					6, 2);
insert books(title, author_id, genre_id) values("Сказка о рыбаке и рыбке",	8, 4);
select * from books;

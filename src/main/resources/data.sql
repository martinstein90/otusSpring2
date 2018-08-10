insert into genres (title) values('Пьеса');
insert into genres (title) values('Роман');
insert into genres (title) values('Автобиография');
insert into genres (title) values('Сказка');
insert into genres (title) values('Комедия');
insert into genres (title) values('Очерк');
commit;

insert into authors(firstname, lastname) values('Антон', 	'Чехов');
insert into authors(firstname, lastname) values('Евгений', 	'Шварц');
insert into authors(firstname, lastname) values('Максим', 	'Горький');
insert into authors(firstname, lastname) values('Шадерло', 	'де Лакло');
insert into authors(firstname, lastname) values('Анри', 		'де Мопасан');
insert into authors(firstname, lastname) values('Федор', 	'Достоевский');
insert into authors(firstname, lastname) values('Николай', 	'Левашов');
insert into authors(firstname, lastname) values('Александр', 'Пушкин');
commit;

insert into books(title, author_id, genre_id) values('Вишневый сад',				1, 1);
insert into books(title, author_id, genre_id) values('Дядя Ваня',				1, 1);
insert into books(title, author_id, genre_id) values('Обыкновенное чудо',		2, 1);
insert into books(title, author_id, genre_id) values('Чудак',					3, 2);
insert into books(title, author_id, genre_id) values('На дне',					3, 2);
insert into books(title, author_id, genre_id) values('Преступление и наказание',	6, 2);
insert into books(title, author_id, genre_id) values('Милый друг',				5, 2);
insert into books(title, author_id, genre_id) values('Пышка',					5, 2);
insert into books(title, author_id, genre_id) values('Россия в кривых зеркалах',	7, 6);
insert into books(title, author_id, genre_id) values('Опасные связи',			4, 2);
insert into books(title, author_id, genre_id) values('Идиот', 					6, 2);
insert into books(title, author_id, genre_id) values('Сказка о рыбаке и рыбке',	8, 4);
commit;

insert into comments(comment, book_id) values('Супер', 1);
insert into comments(comment, book_id) values('Отстой', 2);
insert into comments(comment, book_id) values('Автор,что курил', 3);
insert into comments(comment, book_id) values('Горшок жив!', 3);
insert into comments(comment, book_id) values('Вот тебе и правда', 5);
commit;
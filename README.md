# guestBook-Vaadin
# default

Гостевая книга предоставляет возможность пользователям оставлять сообщения. Все данные, введенные пользователем, сохраняются в БД (по вашему усмотрению), также в базе данных сохраняются данные о IP пользователя и его браузере.

Форма добавления записи в гостевую книгу должна иметь следующие поля:

●	User Name (цифры и буквы латинского алфавита) – обязательное поле
●	E-mail (формат email) – обязательное поле
●	Homepage (формат url) – необязательное поле
●	CAPTCHA (цифры и буквы латинского алфавита) – изображение и обязательное поле
●	Text (непосредственно сам текст сообщения, HTML тэги недопустимы) – обязательное поле

Сообщения должны выводиться в виде таблицы.
Сообщения должны разбиваться на страницы по 25 сообщений на каждой. 

Приветствуется: возможность сортировки по полям User Name, e-mail и дате добавления, как в порядке убывания, так и в обратном.

Обязательный стек технологий:
1.	Vaadin
2.	JPA (или Hipernate)

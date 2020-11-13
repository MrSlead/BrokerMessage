## Задача 1
Написать тест, который будет отправлять сообщения в брокер и принимать в разных режимах как сессий (AUTO_ACK, TRANSACTED, etc.),
так и сообщений (персистентные и неперсистентные).
Оформить итоговый файл с результатами записи и чтения в каждом режиме.
Объяснить полученные результаты.
Предполагаемое время решения 6-10 часов.<br>

Нужно использовать "tcp://localhost:61616"<br>

<b>No PERSISTENT</b> <br>
Session.AUTO_ACKNOWLEDGE - 8ms<br>
Session.CLIENT_ACKNOWLEDGE - 3ms (message.acknowledge())<br>
Session.DUPS_OK_ACKNOWLEDGE - 5ms<br>
Session.SESSION_TRANSACTED - 5ms (session.commit())<br>

<b>PERSISTENT</b><br>
Session.AUTO_ACKNOWLEDGE - 7ms <br>
Session.CLIENT_ACKNOWLEDGE - 11ms (message.acknowledge())<br>
Session.DUPS_OK_ACKNOWLEDGE - 8ms <br>
Session.SESSION_TRANSACTED - 10ms (session.commit())<br>


## Задача 2
В задаче 1 использовать embedded брокер и VM-коннектор, после чего снова проверить все режимы.
Сравнить скорость работы по сравнению с использованием tcp коннектора. Объяснить полученные результаты.
Предполагаемое время решения 4-6 часов.<br>

Нужно использовать "vm://broker"<br>
Требуется подключить зависимость activemq-kahadb-store<br>

<b>No PERSISTENT</b> <br>
Session.AUTO_ACKNOWLEDGE - 4ms<br>
Session.CLIENT_ACKNOWLEDGE - 4ms (message.acknowledge())<br>
Session.DUPS_OK_ACKNOWLEDGE - 3ms<br>
Session.SESSION_TRANSACTED - 5ms (session.commit())<br>

<b>PERSISTENT</b><br>
Session.AUTO_ACKNOWLEDGE - 5ms <br>
Session.CLIENT_ACKNOWLEDGE - 6ms (message.acknowledge())<br>
Session.DUPS_OK_ACKNOWLEDGE - 5ms <br>
Session.SESSION_TRANSACTED - 8s (session.commit())<br>

### Результаты
Работа через встроенный брокер сообщений происходит производительнее, <br>
выгоднее использовать встроенный, если брокер один.
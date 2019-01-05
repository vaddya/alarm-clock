# Alarm clock

## What

Simple console alarm clock application. 

## Example

```
help
Usage: 
	add - <message> - <dd.mm.yyyy hh:mm>
	cancel <index>
	time
	list
	help
time
05.01.2019 18:58
Total: 0 alarms
add - hello - 05.01.2019 18:59
added
list
Total: 1 alarms
[0] 05.01.2019 18:59 [SCHEDULED]
05.01.2019 18:59: hello
list
Total: 1 alarms
[0] 05.01.2019 18:59 [EXECUTED]
add - hello - 05.01.2019 21:00
added
list
Total: 2 alarms
[0] 05.01.2019 21:00 [SCHEDULED]
[1] 05.01.2019 18:59 [EXECUTED]
cancel 0
list
Total: 2 alarms
[0] 05.01.2019 18:59 [EXECUTED]
[1] 05.01.2019 21:00 [CANCELLED]
```
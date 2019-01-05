package com.vaddya.alarm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AlarmClockApplication {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static final String USAGE = "Usage: \n" +
            "\tadd - <message> - <dd.mm.yyyy hh:mm>\n" +
            "\tcancel <index>\n" +
            "\ttime\n" +
            "\tlist\n" +
            "\thelp";

    private final AlarmStorage storage = new AlarmStorage();

    private class Watcher implements Runnable {

        @Override
        public void run() {
            while (true) {
                for (Alarm alarm : storage.getOverdue()) {
                    System.out.println(formatter.format(alarm.getTime()) + ": " + alarm.getMessage());
                    storage.executed(alarm);
                }
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        new AlarmClockApplication().start();
    }

    public void start() {
        Thread watcher = new Thread(new Watcher());
        watcher.start();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String command = scanner.next();
                switch (command) {
                    case "add": {
                        String[] params = scanner.nextLine().split(" - ");
                        LocalDateTime time = LocalDateTime.from(formatter.parse(params[2]));
                        if (time.isBefore(LocalDateTime.now())) {
                            System.out.println("denied: " + formatter.format(time) + " is in the past");
                            continue;
                        }
                        Alarm alarm = new Alarm(time, params[1], AlarmStatus.SCHEDULED);
                        storage.add(alarm);
                        System.out.println("added");
                        break;
                    }
                    case "cancel": {
                        int index = scanner.nextInt();
                        storage.cancel(index);
                        break;
                    }
                    case "time": {
                        System.out.println(formatter.format(LocalDateTime.now()));
                    }
                    case "list": {
                        Collection<Alarm> alarms = storage.getAll();
                        System.out.println("Total: " + alarms.size() + " alarms");
                        int i = 0;
                        for (Alarm alarm : alarms) {
                            System.out.println(
                                    String.format("[%d] %s [%s]", i, formatter.format(alarm.getTime()), alarm.getStatus())
                            );
                            i++;
                        }
                        break;
                    }
                    case "exit": {
                        watcher.interrupt();
                        watcher.join();
                        return;
                    }
                    default: {
                        System.out.println(USAGE);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

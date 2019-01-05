package com.vaddya.alarm;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

public class AlarmStorage {

    private final Queue<Alarm> alarms;

    public AlarmStorage() {
        alarms = new PriorityQueue<>((a1, a2) -> {
            int comp = Comparator.comparing(Alarm::getStatus).compare(a1, a2);
            if (comp != 0) {
                return comp;
            }
            return Comparator.<Alarm>comparingLong(
                    t -> t.getTime().toInstant(ZoneOffset.UTC).toEpochMilli()
            ).compare(a1, a2);
        });
    }

    public synchronized Collection<Alarm> getAll() {
        return Collections.unmodifiableCollection(alarms);
    }

    public synchronized Collection<Alarm> getOverdue() {
        return alarms.stream()
                .filter(alarm -> alarm.getStatus() == AlarmStatus.SCHEDULED && alarm.getTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public synchronized void cancel(int index) {
        if (index >= alarms.size()) {
            throw new IllegalArgumentException("Wrong index: " + index);
        }
        int i = 0;
        for (Alarm alarm : alarms) {
            if (i == index) {
                Alarm newAlarm = new Alarm(alarm.getTime(), alarm.getMessage(), AlarmStatus.CANCELLED);
                alarms.remove(alarm);
                alarms.offer(newAlarm);
                break;
            }
            i++;
        }
    }

    public synchronized void add(Alarm alarm) {
        alarms.offer(alarm);
    }

    public synchronized void executed(Alarm alarm) {
        Alarm newAlarm = new Alarm(alarm.getTime(), alarm.getMessage(), AlarmStatus.EXECUTED);
        alarms.remove(alarm);
        alarms.offer(newAlarm);
    }

}

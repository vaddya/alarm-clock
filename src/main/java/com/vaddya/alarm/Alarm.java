package com.vaddya.alarm;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Alarm clock
 */
public class Alarm {

    /**
     * Time for which the alarm is set
     */
    private final LocalDateTime time;

    /**
     * Message displaying when the alarm goes off
     */
    private final String message;

    /**
     * Status of the alarm clock
     */
    private final AlarmStatus status;

    public Alarm(LocalDateTime time, String message, AlarmStatus status) {
        this.time = time;
        this.message = message;
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public AlarmStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return time.equals(alarm.time) &&
                message.equals(alarm.message) &&
                status == alarm.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, message, status);
    }
}

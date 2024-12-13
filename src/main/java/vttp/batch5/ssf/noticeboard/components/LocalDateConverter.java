package vttp.batch5.ssf.noticeboard.components;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class LocalDateConverter {
    public LocalDateConverter(){

    }

    public LocalDate convert(long epoch){
        return Instant.ofEpochMilli(epoch).atZone(ZoneId.of("UTC")).toLocalDate();
    }

    public Long convertStringDateToLong(String date){
        LocalDateTime localDateTime = LocalDateTime.parse(date + " 00:00:00",
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZoneId zoneId = ZoneId.of("UTC");
        long epochMilli = localDateTime.atZone(zoneId).toInstant().toEpochMilli();
        return epochMilli;
    }
}


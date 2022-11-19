package quebec.virtualite.kumojin.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;
import static java.time.ZoneId.systemDefault;

public class DateTimeUtils
{
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter FMT_TZ = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm XXX");

    public static String formatTimestamp(Timestamp timestamp, String timezone)
    {
        return FMT_TZ.format(
            moveTimestamp(timestamp.toLocalDateTime(),
                systemDefault(), zone(timezone)));
    }

    public static Timestamp parseTimestamp(String stringValue, String timezone)
    {
        return Timestamp.valueOf(
            moveTimestamp(parse(stringValue, FMT),
                zone(timezone), systemDefault())
                .toLocalDateTime());
    }

    private static ZonedDateTime moveTimestamp(
        LocalDateTime ldt, ZoneId fromZone, ZoneId toZone)
    {
        ZonedDateTime ldtFrom = ldt.atZone(fromZone);
        ZonedDateTime ldtTo = ldtFrom.withZoneSameInstant(toZone);

        return ldtTo;
    }

    private static ZoneId zone(String timezone)
    {
        return ZoneId.ofOffset("GMT", ZoneOffset.of(timezone));
    }
}

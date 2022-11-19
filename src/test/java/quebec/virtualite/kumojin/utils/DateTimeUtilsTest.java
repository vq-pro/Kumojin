package quebec.virtualite.kumojin.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeUtilsTest
{
    private static final String DB_A = "2022-12-25 12:00";
    private static final String DB_B = "2022-12-18 09:00";
    private static final String TS_A = "2022-12-25 12:00";
    private static final String TS_B = "2022-12-18 17:00";
    private static final String TZ_A = "-05:00";
    private static final String TZ_B = "+03:00";

    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Test
    public void formatTimestamp()
    {
        formatTimestamp(TS_A, TZ_A);
        formatTimestamp(TS_B, TZ_B);
    }

    private void formatTimestamp(String stringTimestamp, String timezone)
    {
        // Given
        Timestamp timestamp = DateTimeUtils.parseTimestamp(stringTimestamp, timezone);

        // When
        String result = DateTimeUtils.formatTimestamp(timestamp, timezone);

        // Then
        assertThat(result, equalTo(stringTimestamp + " " + timezone));
    }

    @Test
    public void parseTimestamp()
    {
        parseTimestamp(TS_A, TZ_A, DB_A);
        parseTimestamp(TS_B, TZ_B, DB_B);
    }

    private void parseTimestamp(String timestamp, String timezone, String expectedTimestampDB)
    {
        // When
        Timestamp result = DateTimeUtils.parseTimestamp(timestamp, timezone);

        // Then
        assertThat(DF.format(result), equalTo(expectedTimestampDB));
    }
}

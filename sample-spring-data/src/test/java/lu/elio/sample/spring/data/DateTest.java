package lu.elio.sample.spring.data;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTest.class);

    public DateTest() {
        LOGGER.info("Java time module explained.");
        LOGGER.info("For full explanations, have a look at: https://stackoverflow.com/a/32443004");
    }

    @Test
    public void formatInstant() {
        LOGGER.info("=== Instant ===");

        // 2020-06-06 20:20:49 (UTC)
        Instant instant = Instant.ofEpochSecond(1591474849);
        LOGGER.info("An Instant is a moment on the timeline in UTC, a count of nanoseconds since the epoch of the first moment of 1970 UTC.");

        try {
            DateTimeFormatter.ISO_DATE_TIME.format(instant);
        } catch (UnsupportedTemporalTypeException e) {
            LOGGER.info("An Instant cannot be formatted as is.");
        }

        LOGGER.info("To format an Instant, it should be associated to a Timezone:");
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(instant.atZone(ZoneId.of("UTC"))));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(instant.atZone(ZoneId.of("Europe/Luxembourg"))));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(instant.atZone(ZoneId.systemDefault())));
        LOGGER.info("Note that these values represent exactly the same moments in the timeline.");
    }

    @Test
    public void formatLocalDatetime() {
        LOGGER.info("=== LocalDatetime ===");

        // 2020-06-06 20:20:49
        LocalDateTime localDateTime = LocalDateTime.of(2020, 6, 6, 20, 20, 49);
        LOGGER.info("LocalDateTime is not tied to any time zone (it is not tied to the timeline). It has no real meaning until a locality is applied to it to find a point on the timeline.");

        LOGGER.info(DateTimeFormatter.ISO_DATE_TIME.format(localDateTime));
        LOGGER.info("A LocalDateTime won't display any time zone when formatted.");

        LOGGER.info("To describe a specific point in the timeline, a LocalDateTime should be associated to a time zone:");
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(localDateTime.atZone(ZoneId.of("UTC"))));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(localDateTime.atZone(ZoneId.of("Europe/Luxembourg"))));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(localDateTime.atZone(ZoneId.systemDefault())));
        LOGGER.info("Note that these values represent different moments in the timeline.");
    }

    @Test
    public void formatZonedDatetime() {
        LOGGER.info("=== ZonedDateTime ===");

        // 2020-06-06 20:20:49 (UTC)
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 6, 6, 20, 20, 49, 0, ZoneId.of("UTC"));
        LOGGER.info("A ZonedDateTime is basically an Instant associated to a given time zone");

        LOGGER.info(DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime));
        LOGGER.info("A ZonedDateTime will display the given time zone when formatted.");

        LOGGER.info("A ZonedDateTime can be translated into a different zone:");
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"))));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime.withZoneSameInstant(ZoneId.of("Europe/Luxembourg"))));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime.withZoneSameInstant(ZoneId.systemDefault())));
        LOGGER.info("Note that these values represent exactly the same moments in the timeline.");

        LOGGER.info("A ZonedDateTime can be moved to a different zone:");
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime.withZoneSameLocal(ZoneId.of("UTC"))));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime.withZoneSameLocal(ZoneId.of("Europe/Luxembourg"))));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime.withZoneSameLocal(ZoneId.systemDefault())));
        LOGGER.info("Note that these values represent different moments in the timeline.");
    }

    @Test
    public void convertInstantToLocalDateTime() {
        LOGGER.info("=== Instant -> LocalDateTime ===");

        // 2020-06-06 20:20:49 (UTC)
        Instant instant = Instant.ofEpochSecond(1591474849);

        LOGGER.info("An Instant must be associated to a time zone to be converted into a LocalDateTime: ");
        LocalDateTime utcLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        LOGGER.info(" - {} for UTC", DateTimeFormatter.ISO_DATE_TIME.format(utcLocalDateTime));
        LocalDateTime luxLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Luxembourg"));
        LOGGER.info(" - {} for Europe/Luxembourg", DateTimeFormatter.ISO_DATE_TIME.format(luxLocalDateTime));
        LocalDateTime sysLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LOGGER.info(" - {} for system default", DateTimeFormatter.ISO_DATE_TIME.format(sysLocalDateTime));
        LOGGER.info("Note that these values represent different moments in the timeline.");
    }

    @Test
    public void convertLocalDateTimeToInstant() {
        LOGGER.info("=== LocalDateTime -> Instant ===");

        // 2020-06-06 20:20:49
        LocalDateTime localDateTime = LocalDateTime.of(2020, 6, 6, 20, 20, 49);

        LOGGER.info("An LocalDateTime must be associated to a time zone to be converted into an Instant: ");
        Instant utcInstant = Instant.from(localDateTime.atZone(ZoneId.of("UTC")));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(utcInstant.atZone(ZoneId.of("UTC"))));
        Instant luxInstant = Instant.from(localDateTime.atZone(ZoneId.of("Europe/Luxembourg")));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(luxInstant.atZone(ZoneId.of("Europe/Luxembourg"))));
        Instant sysInstant = Instant.from(localDateTime.atZone(ZoneId.systemDefault()));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(sysInstant.atZone(ZoneId.systemDefault())));
        LOGGER.info("Note that these values represent different moments in the timeline.");
    }

    @Test
    public void convertLocalDateTimeToZonedDateTime() {
        LOGGER.info("=== LocalDateTime -> ZonedDateTime ===");

        // 2020-06-06 20:20:49
        LocalDateTime localDateTime = LocalDateTime.of(2020, 6, 6, 20, 20, 49);

        LOGGER.info("An LocalDateTime must be associated to a time zone to be converted into an ZonedDateTime: ");
        ZonedDateTime utcZonedDateTime = ZonedDateTime.from(localDateTime.atZone(ZoneId.of("UTC")));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(utcZonedDateTime));
        ZonedDateTime luxZonedDateTime = ZonedDateTime.from(localDateTime.atZone(ZoneId.of("Europe/Luxembourg")));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(luxZonedDateTime));
        ZonedDateTime sysZonedDateTime = ZonedDateTime.from(localDateTime.atZone(ZoneId.systemDefault()));
        LOGGER.info(" - {}", DateTimeFormatter.ISO_DATE_TIME.format(sysZonedDateTime));
        LOGGER.info("Note that these values represent different moments in the timeline.");
    }

    @Test
    public void convertZonedDateTimeToLocalDateTime() {
        LOGGER.info("=== ZonedDateTime -> LocalDateTime ===");

        // 2020-06-06 20:20:49
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, 6, 6, 20, 20, 49, 0, ZoneId.of("UTC"));

        LOGGER.info("A ZonedDateTime becomes a LocalDateTime by simply removing the time zone information:");
        LOGGER.info(DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime.toLocalDateTime()));
        LOGGER.info("Note that some information has been lost.");
    }
}

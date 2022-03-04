package persistence;

import model.Week;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Week w = new Week("Your Weekly Tracker");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Week w = new Week("Your Weekly Tracker");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWeek.json");
            writer.open();
            writer.write(w);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWeek.json");
            w = reader.read();
            assertEquals("Your Weekly Tracker", w.getName());
            assertTrue(w.getSunday().isEmpty());
            assertTrue(w.getMonday().isEmpty());
            assertTrue(w.getTuesday().isEmpty());
            assertTrue(w.getWednesday().isEmpty());
            assertTrue(w.getThursday().isEmpty());
            assertTrue(w.getFriday().isEmpty());
            assertTrue(w.getSaturday().isEmpty());
            assertEquals(0, w.getWeeklyConsumption());
            assertEquals(0, w.getLastWeek());
            assertEquals(0, w.getTargetTotal());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Week w = new Week("Your Weekly Tracker");
            w.addSunday("advil");
            w.addWednesday("marijuana");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWeek.json");
            writer.open();
            writer.write(w);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWeek.json");
            w = reader.read();
            assertEquals("Your Weekly Tracker", w.getName());
            assertEquals(1, w.getSunday().size());
            assertEquals(0, w.getMonday().size());
            assertEquals(0, w.getTuesday().size());
            assertEquals(1, w.getWednesday().size());
            assertEquals(0, w.getThursday().size());
            assertEquals(0, w.getFriday().size());
            assertEquals(0, w.getSaturday().size());
            assertEquals(2, w.getWeeklyConsumption());
            assertEquals(0, w.getLastWeek());
            assertEquals(0, w.getTargetTotal());
            assertTrue(w.getSunday().containsKey("advil"));
            assertTrue(w.getWednesday().containsKey("marijuana"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

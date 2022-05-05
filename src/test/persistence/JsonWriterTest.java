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
            assertTrue(w.getSunday().getMap().isEmpty());
            assertTrue(w.getMonday().getMap().isEmpty());
            assertTrue(w.getTuesday().getMap().isEmpty());
            assertTrue(w.getWednesday().getMap().isEmpty());
            assertTrue(w.getThursday().getMap().isEmpty());
            assertTrue(w.getFriday().getMap().isEmpty());
            assertTrue(w.getSaturday().getMap().isEmpty());
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
            w.add(w.getSunday(),"advil");
            w.add(w.getWednesday(),"marijuana");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWeek.json");
            writer.open();
            writer.write(w);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWeek.json");
            w = reader.read();
            assertEquals("Your Weekly Tracker", w.getName());
            assertEquals(1, w.getSunday().getMap().size());
            assertEquals(0, w.getMonday().getMap().size());
            assertEquals(0, w.getTuesday().getMap().size());
            assertEquals(1, w.getWednesday().getMap().size());
            assertEquals(0, w.getThursday().getMap().size());
            assertEquals(0, w.getFriday().getMap().size());
            assertEquals(0, w.getSaturday().getMap().size());
            assertEquals(2, w.getWeeklyConsumption());
            assertEquals(0, w.getLastWeek());
            assertEquals(0, w.getTargetTotal());
            assertTrue(w.getSunday().getMap().containsKey("advil"));
            assertTrue(w.getWednesday().getMap().containsKey("marijuana"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

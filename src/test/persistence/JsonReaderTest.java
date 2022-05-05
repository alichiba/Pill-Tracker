package persistence;

import model.Week;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Week w = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWeek() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWeek.json");
        try {
            Week w = reader.read();
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
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWeek.json");
        try {
            Week w = reader.read();
            assertEquals("Your Weekly Tracker", w.getName());
            assertEquals(1, w.getSunday().getMap().size());
            assertEquals(1, w.getMonday().getMap().size());
            assertEquals(1, w.getTuesday().getMap().size());
            assertEquals(1, w.getWednesday().getMap().size());
            assertEquals(1, w.getThursday().getMap().size());
            assertEquals(1, w.getFriday().getMap().size());
            assertEquals(1, w.getSaturday().getMap().size());
            assertEquals(7, w.getWeeklyConsumption());
            assertEquals(1, w.getLastWeek());
            assertEquals(3, w.getTargetTotal());
            assertTrue(w.getSunday().getMap().containsKey("advil"));
            assertTrue(w.getMonday().getMap().containsKey("advil"));
            assertTrue(w.getTuesday().getMap().containsKey("advil"));
            assertTrue(w.getWednesday().getMap().containsKey("marijuana"));
            assertTrue(w.getThursday().getMap().containsKey("advil"));
            assertTrue(w.getFriday().getMap().containsKey("advil"));
            assertTrue(w.getSaturday().getMap().containsKey("advil"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

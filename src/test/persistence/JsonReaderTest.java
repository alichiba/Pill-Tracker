package persistence;

import model.Week;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

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
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWeek.json");
        try {
            Week w = reader.read();
            assertEquals("Your Weekly Tracker", w.getName());
            assertEquals(1, w.getSunday().size());
            assertEquals(1, w.getMonday().size());
            assertEquals(1, w.getTuesday().size());
            assertEquals(1, w.getWednesday().size());
            assertEquals(1, w.getThursday().size());
            assertEquals(1, w.getFriday().size());
            assertEquals(1, w.getSaturday().size());
            assertEquals(7, w.getWeeklyConsumption());
            assertEquals(1, w.getLastWeek());
            assertEquals(3, w.getTargetTotal());
            assertTrue(w.getSunday().containsKey("advil"));
            assertTrue(w.getMonday().containsKey("advil"));
            assertTrue(w.getTuesday().containsKey("advil"));
            assertTrue(w.getWednesday().containsKey("marijuana"));
            assertTrue(w.getThursday().containsKey("advil"));
            assertTrue(w.getFriday().containsKey("advil"));
            assertTrue(w.getSaturday().containsKey("advil"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

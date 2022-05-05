package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

// Tests for the Week class
class WeekTest {
    Week myWeek;

    @BeforeEach
    void runBefore() {
        myWeek = new Week("new week");
    }

    @Test
    void testWeek() {
        assertTrue(myWeek.getSunday().getMap().isEmpty());
        assertTrue(myWeek.getMonday().getMap().isEmpty());
        assertTrue(myWeek.getTuesday().getMap().isEmpty());
        assertTrue(myWeek.getWednesday().getMap().isEmpty());
        assertTrue(myWeek.getThursday().getMap().isEmpty());
        assertTrue(myWeek.getFriday().getMap().isEmpty());
        assertTrue(myWeek.getSaturday().getMap().isEmpty());
        assertEquals(0, myWeek.getWeeklyConsumption());
        assertEquals(0, myWeek.getLastWeek());
    }

    @Test
    void testAddSunday() {
        myWeek.add(myWeek.getSunday(), "advil");
        assertTrue(myWeek.getSunday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getSunday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveSunday() {
        myWeek.add(myWeek.getSunday(), "advil");
        myWeek.add(myWeek.getSunday(), "paracetamol");

        myWeek.remove(myWeek.getSunday(),"advil");
        assertFalse(myWeek.getSunday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getSunday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.remove(myWeek.getSunday(),"paracetamol");
        assertFalse(myWeek.getSunday().getMap().containsKey("paracetamol"));
        assertEquals(0, myWeek.getSunday().getMap().size());
        assertEquals(0, myWeek.getWeeklyConsumption());

    }


    @Test
    void testAddMonday() {
        myWeek.add(myWeek.getMonday(),"advil");
        assertTrue(myWeek.getMonday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getMonday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveMonday() {
        myWeek.add(myWeek.getMonday(),"advil");
        myWeek.add(myWeek.getMonday(),"paracetamol");

        myWeek.remove(myWeek.getMonday(),"advil");
        assertFalse(myWeek.getMonday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getMonday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.remove(myWeek.getMonday(),"paracetamol");
        assertFalse(myWeek.getMonday().getMap().containsKey("paracetamol"));
        assertEquals(0, myWeek.getMonday().getMap().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }


    @Test
    void testAddTuesday() {
        myWeek.add(myWeek.getTuesday(), "advil");
        assertTrue(myWeek.getTuesday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getTuesday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveTuesday() {
        myWeek.add(myWeek.getTuesday(), "advil");
        myWeek.add(myWeek.getTuesday(),"paracetamol");

        myWeek.remove(myWeek.getTuesday(),"advil");
        assertFalse(myWeek.getTuesday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getTuesday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.remove(myWeek.getTuesday(),"paracetamol");
        assertFalse(myWeek.getTuesday().getMap().containsKey("paracetamol"));
        assertEquals(0, myWeek.getTuesday().getMap().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }


    @Test
    void testAddWednesday() {
        myWeek.add(myWeek.getWednesday(), "advil");
        assertTrue(myWeek.getWednesday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getWednesday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveWednesday() {
        myWeek.add(myWeek.getWednesday(), "advil");
        myWeek.add(myWeek.getWednesday(),"paracetamol");

        myWeek.remove(myWeek.getWednesday(),"advil");
        assertFalse(myWeek.getWednesday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getWednesday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.remove(myWeek.getWednesday(),"paracetamol");
        assertFalse(myWeek.getWednesday().getMap().containsKey("paracetamol"));
        assertEquals(0, myWeek.getWednesday().getMap().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }

    @Test
    void testAddThursday() {
        myWeek.add(myWeek.getThursday(), "advil");
        assertTrue(myWeek.getThursday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getThursday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveThursday() {
        myWeek.add(myWeek.getThursday(), "advil");
        myWeek.add(myWeek.getThursday(),"paracetamol");

        myWeek.remove(myWeek.getThursday(),"advil");
        assertFalse(myWeek.getThursday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getThursday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.remove(myWeek.getThursday(),"paracetamol");
        assertFalse(myWeek.getThursday().getMap().containsKey("paracetamol"));
        assertEquals(0, myWeek.getThursday().getMap().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }


    @Test
    void testAddFriday() {
        myWeek.add(myWeek.getFriday(), "advil");
        assertTrue(myWeek.getFriday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getFriday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveFriday() {
        myWeek.add(myWeek.getFriday(), "advil");
        myWeek.add(myWeek.getFriday(),"paracetamol");

        myWeek.remove(myWeek.getFriday(),"advil");
        assertFalse(myWeek.getFriday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getFriday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.remove(myWeek.getFriday(),"paracetamol");
        assertFalse(myWeek.getFriday().getMap().containsKey("paracetamol"));
        assertEquals(0, myWeek.getFriday().getMap().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }

    @Test
    void testAddSaturday() {
        myWeek.add(myWeek.getSaturday(), "advil");
        assertTrue(myWeek.getSaturday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getSaturday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveSaturday() {
        myWeek.add(myWeek.getSaturday(), "advil");
        myWeek.add(myWeek.getSaturday(),"paracetamol");

        myWeek.remove(myWeek.getSaturday(),"advil");
        assertFalse(myWeek.getSaturday().getMap().containsKey("advil"));
        assertEquals(1, myWeek.getSaturday().getMap().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.remove(myWeek.getSaturday(),"paracetamol");
        assertFalse(myWeek.getSaturday().getMap().containsKey("paracetamol"));
        assertEquals(0, myWeek.getSaturday().getMap().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }

    @Test
    void testUpdateLastWeek() {
        myWeek.updateLastWeek(10);
        assertEquals(10, myWeek.getLastWeek());
    }

    @Test
    void testSetTargetTotal() {
        myWeek.setTargetTotal(10);
        assertEquals(10, myWeek.getTargetTotal());
    }

    @Test
    void testTargetReached() {
        myWeek.setTargetTotal(2);
        assertTrue(myWeek.getWeeklyConsumption() < myWeek.getTargetTotal());
        assertEquals("The weekly consumption is below the target total", myWeek.targetReached());

        myWeek.add(myWeek.getSaturday(), "meth");
        myWeek.add(myWeek.getFriday(), "opium");
        assertEquals(myWeek.getWeeklyConsumption(), myWeek.getTargetTotal());
        assertEquals("The target total has been reached", myWeek.targetReached());

        myWeek.add(myWeek.getWednesday(), "aspirin");
        assertTrue(myWeek.getWeeklyConsumption() > myWeek.getTargetTotal());
        assertEquals("The weekly consumption is above the target total", myWeek.targetReached());
    }

    @Test
    void testToJsonEmptyWeek() {
        assertTrue(myWeek.toJson().has("sunday"));
        assertTrue(myWeek.toJson().has("monday"));
        assertTrue(myWeek.toJson().has("tuesday"));
        assertTrue(myWeek.toJson().has("wednesday"));
        assertTrue(myWeek.toJson().has("thursday"));
        assertTrue(myWeek.toJson().has("friday"));
        assertTrue(myWeek.toJson().has("saturday"));
        assertTrue(myWeek.toJson().has("weeklyConsumption"));
        assertTrue(myWeek.toJson().has("lastWeek"));
        assertTrue(myWeek.toJson().has("targetTotal"));
        assertTrue(myWeek.toJson().has("name"));
        assertEquals("new week", myWeek.toJson().getString("name"));
        assertEquals(0, myWeek.toJson().optInt("weeklyConsumption"));
        assertEquals(0, myWeek.toJson().optInt("lastWeek"));
        assertEquals(0, myWeek.toJson().getInt("targetTotal"));
        assertTrue(myWeek.toJson().optJSONArray("sunday").isEmpty());
        assertTrue(myWeek.toJson().optJSONArray("monday").isEmpty());
        assertTrue(myWeek.toJson().optJSONArray("tuesday").isEmpty());
        assertTrue(myWeek.toJson().optJSONArray("wednesday").isEmpty());
        assertTrue(myWeek.toJson().optJSONArray("thursday").isEmpty());
        assertTrue(myWeek.toJson().optJSONArray("friday").isEmpty());
        assertTrue(myWeek.toJson().optJSONArray("saturday").isEmpty());
    }

    @Test
    void testToJsonWithPills() {
        myWeek.add(myWeek.getSunday(), "tylenol");
        myWeek.add(myWeek.getMonday(), "tylenol");
        myWeek.add(myWeek.getTuesday(), "tylenol");
        myWeek.add(myWeek.getWednesday(), "tylenol");
        myWeek.add(myWeek.getThursday(), "tylenol");
        myWeek.add(myWeek.getFriday(), "tylenol");
        myWeek.add(myWeek.getSaturday(), "tylenol");

        assertEquals("new week", myWeek.toJson().getString("name"));
        assertEquals(7, myWeek.toJson().optInt("weeklyConsumption"));
        assertEquals(0, myWeek.toJson().optInt("lastWeek"));
        assertEquals(0, myWeek.toJson().getInt("targetTotal"));

        assertEquals(1, myWeek.toJson().optJSONArray("sunday").length());
        assertEquals(1, myWeek.toJson().optJSONArray("monday").length());
        assertEquals(1, myWeek.toJson().optJSONArray("tuesday").length());
        assertEquals(1, myWeek.toJson().optJSONArray("wednesday").length());
        assertEquals(1, myWeek.toJson().optJSONArray("thursday").length());
        assertEquals(1, myWeek.toJson().optJSONArray("friday").length());
        assertEquals(1, myWeek.toJson().optJSONArray("saturday").length());
    }

    @Test
    void testPillsToJson() {
        assertTrue(myWeek.pillsToJson(myWeek.getSunday().getMap()).isEmpty());
        myWeek.add(myWeek.getSunday(), "advil");
        assertEquals(1, myWeek.pillsToJson(myWeek.getSunday().getMap()).length());
    }
}
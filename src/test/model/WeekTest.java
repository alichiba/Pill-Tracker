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
        assertTrue(myWeek.getSunday().isEmpty());
        assertTrue(myWeek.getMonday().isEmpty());
        assertTrue(myWeek.getTuesday().isEmpty());
        assertTrue(myWeek.getWednesday().isEmpty());
        assertTrue(myWeek.getThursday().isEmpty());
        assertTrue(myWeek.getFriday().isEmpty());
        assertTrue(myWeek.getSaturday().isEmpty());
        assertEquals(0, myWeek.getWeeklyConsumption());
        assertEquals(0, myWeek.getLastWeek());
    }

    @Test
    void testAddSunday() {
        myWeek.addSunday("advil");
        assertTrue(myWeek.getSunday().containsKey("advil"));
        assertEquals(1, myWeek.getSunday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveSunday() {
        myWeek.addSunday("advil");
        myWeek.addSunday("paracetamol");

        myWeek.removeSunday("advil");
        assertFalse(myWeek.getSunday().containsKey("advil"));
        assertEquals(1, myWeek.getSunday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.removeSunday("paracetamol");
        assertFalse(myWeek.getSunday().containsKey("paracetamol"));
        assertEquals(0, myWeek.getSunday().size());
        assertEquals(0, myWeek.getWeeklyConsumption());

    }


    @Test
    void testAddMonday() {
        myWeek.addMonday("advil");
        assertTrue(myWeek.getMonday().containsKey("advil"));
        assertEquals(1, myWeek.getMonday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveMonday() {
        myWeek.addMonday("advil");
        myWeek.addMonday("paracetamol");

        myWeek.removeMonday("advil");
        assertFalse(myWeek.getMonday().containsKey("advil"));
        assertEquals(1, myWeek.getMonday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.removeMonday("paracetamol");
        assertFalse(myWeek.getMonday().containsKey("paracetamol"));
        assertEquals(0, myWeek.getMonday().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }


    @Test
    void testAddTuesday() {
        myWeek.addTuesday("advil");
        assertTrue(myWeek.getTuesday().containsKey("advil"));
        assertEquals(1, myWeek.getTuesday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveTuesday() {
        myWeek.addTuesday("advil");
        myWeek.addTuesday("paracetamol");

        myWeek.removeTuesday("advil");
        assertFalse(myWeek.getTuesday().containsKey("advil"));
        assertEquals(1, myWeek.getTuesday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.removeTuesday("paracetamol");
        assertFalse(myWeek.getTuesday().containsKey("paracetamol"));
        assertEquals(0, myWeek.getTuesday().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }


    @Test
    void testAddWednesday() {
        myWeek.addWednesday("advil");
        assertTrue(myWeek.getWednesday().containsKey("advil"));
        assertEquals(1, myWeek.getWednesday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveWednesday() {
        myWeek.addWednesday("advil");
        myWeek.addWednesday("paracetamol");

        myWeek.removeWednesday("advil");
        assertFalse(myWeek.getWednesday().containsKey("advil"));
        assertEquals(1, myWeek.getWednesday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.removeWednesday("paracetamol");
        assertFalse(myWeek.getWednesday().containsKey("paracetamol"));
        assertEquals(0, myWeek.getWednesday().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }

    @Test
    void testAddThursday() {
        myWeek.addThursday("advil");
        assertTrue(myWeek.getThursday().containsKey("advil"));
        assertEquals(1, myWeek.getThursday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveThursday() {
        myWeek.addThursday("advil");
        myWeek.addThursday("paracetamol");

        myWeek.removeThursday("advil");
        assertFalse(myWeek.getThursday().containsKey("advil"));
        assertEquals(1, myWeek.getThursday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.removeThursday("paracetamol");
        assertFalse(myWeek.getThursday().containsKey("paracetamol"));
        assertEquals(0, myWeek.getThursday().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }


    @Test
    void testAddFriday() {
        myWeek.addFriday("advil");
        assertTrue(myWeek.getFriday().containsKey("advil"));
        assertEquals(1, myWeek.getFriday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveFriday() {
        myWeek.addFriday("advil");
        myWeek.addFriday("paracetamol");

        myWeek.removeFriday("advil");
        assertFalse(myWeek.getFriday().containsKey("advil"));
        assertEquals(1, myWeek.getFriday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.removeFriday("paracetamol");
        assertFalse(myWeek.getFriday().containsKey("paracetamol"));
        assertEquals(0, myWeek.getFriday().size());
        assertEquals(0, myWeek.getWeeklyConsumption());
    }

    @Test
    void testAddSaturday() {
        myWeek.addSaturday("advil");
        assertTrue(myWeek.getSaturday().containsKey("advil"));
        assertEquals(1, myWeek.getSaturday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());
    }

    @Test
    void testRemoveSaturday() {
        myWeek.addSaturday("advil");
        myWeek.addSaturday("paracetamol");

        myWeek.removeSaturday("advil");
        assertFalse(myWeek.getSaturday().containsKey("advil"));
        assertEquals(1, myWeek.getSaturday().size());
        assertEquals(1, myWeek.getWeeklyConsumption());

        myWeek.removeSaturday("paracetamol");
        assertFalse(myWeek.getSaturday().containsKey("paracetamol"));
        assertEquals(0, myWeek.getSaturday().size());
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

        myWeek.addSaturday("meth");
        myWeek.addFriday("opium");
        assertEquals(myWeek.getWeeklyConsumption(), myWeek.getTargetTotal());
        assertEquals("The target total has been reached", myWeek.targetReached());

        myWeek.addWednesday("aspirin");
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
        myWeek.addSunday("tylenol");
        myWeek.addMonday("tylenol");
        myWeek.addTuesday("tylenol");
        myWeek.addWednesday("tylenol");
        myWeek.addThursday("tylenol");
        myWeek.addFriday("tylenol");
        myWeek.addSaturday("tylenol");

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
        assertTrue(myWeek.pillsToJson(myWeek.getSunday()).isEmpty());
        myWeek.addSunday("advil");
        assertEquals(1, myWeek.pillsToJson(myWeek.getSunday()).length());
    }
}
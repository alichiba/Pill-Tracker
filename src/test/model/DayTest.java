package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayTest {
    Day myDay;

    @BeforeEach
    void runBefore() {
        myDay = new Day("weekday");
    }

    @Test
    void testDay() {
        assertEquals("weekday", myDay.getName());
        assertEquals(0, myDay.getMap().size());
    }
}

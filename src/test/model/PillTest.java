package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PillTest {
    Pill myPill;

    @Test
    void testPill() {
        myPill = new Pill("marijuana");
        assertEquals("marijuana", myPill.getName());
    }
}

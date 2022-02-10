package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests for the Pill class
public class PillTest {
    Pill myPill;

    @BeforeEach
    void runBefore() {
        myPill = new Pill("marijuana");
    }

    @Test
    void testPill() {
        assertEquals("marijuana", myPill.getName());
    }

}

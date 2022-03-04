package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testToJson() {
        assertTrue(myPill.toJson().has("name"));
        assertEquals("marijuana", myPill.toJson().getString("name"));
    }

}

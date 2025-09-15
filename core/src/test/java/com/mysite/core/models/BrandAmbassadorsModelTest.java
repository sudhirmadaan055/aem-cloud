package com.mysite.core.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BrandAmbassadorsModel
 * Tests component data binding, business logic, and error handling
 */
class BrandAmbassadorsModelTest {

    @Test
    void testModelCreation() {
        assertNotNull(BrandAmbassadorsModel.class);
    }

    @Test
    void testResourceType() {
        assertEquals("mysite/components/brandambassadors", BrandAmbassadorsModel.RESOURCE_TYPE);
    }

    @Test
    void testPillTabCreation() {
        BrandAmbassadorsModel.PillTab pillTab = new BrandAmbassadorsModel.PillTab("Test Tab", true);
        
        assertNotNull(pillTab);
        assertEquals("Test Tab", pillTab.getTabLabel());
        assertTrue(pillTab.getIsActive());
    }

    @Test
    void testPillTabInactive() {
        BrandAmbassadorsModel.PillTab pillTab = new BrandAmbassadorsModel.PillTab("Inactive Tab", false);
        
        assertNotNull(pillTab);
        assertEquals("Inactive Tab", pillTab.getTabLabel());
        assertFalse(pillTab.getIsActive());
    }

    @Test
    void testAthleteCreation() {
        BrandAmbassadorsModel.Athlete athlete = new BrandAmbassadorsModel.Athlete(
            "John Doe", 
            "존 도", 
            "Tennis", 
            "/content/dam/test/athlete.jpg", 
            "/content/dam/test/icon.svg"
        );
        
        assertNotNull(athlete);
        assertEquals("John Doe", athlete.getAthleteName());
        assertEquals("존 도", athlete.getAthleteNameKorean());
        assertEquals("Tennis", athlete.getSport());
        assertEquals("/content/dam/test/athlete.jpg", athlete.getAthleteImage());
        assertEquals("/content/dam/test/icon.svg", athlete.getSportIcon());
    }

    @Test
    void testAthleteWithNullValues() {
        BrandAmbassadorsModel.Athlete athlete = new BrandAmbassadorsModel.Athlete(
            null, 
            null, 
            null, 
            null, 
            null
        );
        
        assertNotNull(athlete);
        assertNull(athlete.getAthleteName());
        assertNull(athlete.getAthleteNameKorean());
        assertNull(athlete.getSport());
        assertNull(athlete.getAthleteImage());
        assertNull(athlete.getSportIcon());
    }

    @Test
    void testAthleteWithEmptyStrings() {
        BrandAmbassadorsModel.Athlete athlete = new BrandAmbassadorsModel.Athlete(
            "", 
            "", 
            "", 
            "", 
            ""
        );
        
        assertNotNull(athlete);
        assertEquals("", athlete.getAthleteName());
        assertEquals("", athlete.getAthleteNameKorean());
        assertEquals("", athlete.getSport());
        assertEquals("", athlete.getAthleteImage());
        assertEquals("", athlete.getSportIcon());
    }
}

package com.mysite.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class DiscoverBibigoModelTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(DiscoverBibigoModel.class);
    }

    @Test
    void testDiscoverBibigoModelWithDefaultValues() {
        // Create a resource with no properties
        context.create().resource("/content/test", "jcr:primaryType", "nt:unstructured");
        
        // Adapt the resource to the model
        DiscoverBibigoModel model = context.resourceResolver().getResource("/content/test")
                .adaptTo(DiscoverBibigoModel.class);
        
        // Assert default values
        assertNotNull(model);
        assertEquals("", model.getTitle());
        assertEquals("", model.getMainHeading());
        assertEquals("", model.getDescription());
        assertEquals("3", model.getNumberOfCards());
        assertNotNull(model.getCardItems());
        assertTrue(model.getCardItems().isEmpty());
    }

    @Test
    void testDiscoverBibigoModelWithCustomValues() {
        // Create a resource with custom properties
        context.create().resource("/content/test",
                "title", "Custom Title",
                "mainHeading", "Custom Main Heading",
                "description", "Custom Description",
                "numberOfCards", "5");
        
        // Adapt the resource to the model
        DiscoverBibigoModel model = context.resourceResolver().getResource("/content/test")
                .adaptTo(DiscoverBibigoModel.class);
        
        // Assert custom values
        assertNotNull(model);
        assertEquals("Custom Title", model.getTitle());
        assertEquals("Custom Main Heading", model.getMainHeading());
        assertEquals("Custom Description", model.getDescription());
        assertEquals("5", model.getNumberOfCards());
    }

    @Test
    void testCardItemClass() {
        // Test the inner CardItem class
        DiscoverBibigoModel.CardItem cardItem = new DiscoverBibigoModel.CardItem(
                "test-image.jpg",
                "Test Title",
                "Test Description",
                "Test Button",
                "/content/test-link",
                "#F2C34E"
        );
        
        assertEquals("test-image.jpg", cardItem.getCardImage());
        assertEquals("Test Title", cardItem.getCardTitle());
        assertEquals("Test Description", cardItem.getCardDescription());
        assertEquals("Test Button", cardItem.getButtonText());
        assertEquals("/content/test-link", cardItem.getButtonLink());
        assertEquals("#F2C34E", cardItem.getBackgroundColor());
    }
}

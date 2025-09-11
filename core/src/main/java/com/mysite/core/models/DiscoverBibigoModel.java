package com.mysite.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.Default;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DiscoverBibigoModel {

    // Header Section Fields
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "")
    private String title;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "")
    private String mainHeading;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "")
    private String description;

    // Cards Section Fields
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "3")
    private String numberOfCards;

    private List<CardItem> cardItems;

    @PostConstruct
    protected void init() {
        cardItems = new ArrayList<>();
        
        // Create sample card items for demonstration
        // In a real implementation, these would be populated from the multifield
        cardItems.add(new CardItem(
            "/content/dam/mysite/sample-image-1.jpg",
            "Explore our Recipes",
            "Learn how Bibigo became the world's leading Korean food brand with passion.",
            "explore more",
            "/content/mysite/us/en/recipes",
            "#F2C34E"
        ));
        
        cardItems.add(new CardItem(
            "/content/dam/mysite/sample-image-2.jpg",
            "Explore our Recipes",
            "Learn how Bibigo became the world's leading Korean food brand with passion.",
            "explore more",
            "/content/mysite/us/en/recipes",
            "linear-gradient(135deg, #F2C34E 0%, #734F2F 100%)"
        ));
        
        cardItems.add(new CardItem(
            "/content/dam/mysite/sample-image-3.jpg",
            "Explore our Recipes",
            "Learn how Bibigo became the world's leading Korean food brand with passion.",
            "explore more",
            "/content/mysite/us/en/recipes",
            "linear-gradient(135deg, #F2C34E 0%, #30B700 100%)"
        ));
    }

    // Getters for Header Section
    public String getTitle() {
        return title;
    }

    public String getMainHeading() {
        return mainHeading;
    }

    public String getDescription() {
        return description;
    }

    // Getters for Cards Section
    public String getNumberOfCards() {
        return numberOfCards;
    }

    public List<CardItem> getCardItems() {
        return cardItems;
    }

    // Inner class for Card Items
    public static class CardItem {
        private String cardImage;
        private String cardTitle;
        private String cardDescription;
        private String buttonText;
        private String buttonLink;
        private String backgroundColor;

        public CardItem(String cardImage, String cardTitle, String cardDescription, 
                       String buttonText, String buttonLink, String backgroundColor) {
            this.cardImage = cardImage;
            this.cardTitle = cardTitle;
            this.cardDescription = cardDescription;
            this.buttonText = buttonText;
            this.buttonLink = buttonLink;
            this.backgroundColor = backgroundColor;
        }

        // Getters
        public String getCardImage() {
            return cardImage;
        }

        public String getCardTitle() {
            return cardTitle;
        }

        public String getCardDescription() {
            return cardDescription;
        }

        public String getButtonText() {
            return buttonText;
        }

        public String getButtonLink() {
            return buttonLink;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }
    }
}

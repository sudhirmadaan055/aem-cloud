package com.mysite.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.Default;

import javax.annotation.PostConstruct;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CampaignModel {

    // Content Section Fields
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "Lorem Ipsum")
    private String title;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "Bibigo embodies the spirit of Korean cuisine, bringing families together through shared meals.")
    private String description;

    // Form Section Fields
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "Your Name")
    private String nameLabel;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "Email")
    private String emailLabel;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "Phone")
    private String phoneLabel;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "Company / Field of work")
    private String companyLabel;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "What are you looking for?")
    private String messageLabel;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "keep in touch")
    private String submitButtonText;

    // Media Section Fields
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String backgroundImage;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String logoImage;

    @PostConstruct
    protected void init() {
        // Initialize any required logic here
    }

    // Getters for Content Section
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    // Getters for Form Section
    public String getNameLabel() {
        return nameLabel;
    }

    public String getEmailLabel() {
        return emailLabel;
    }

    public String getPhoneLabel() {
        return phoneLabel;
    }

    public String getCompanyLabel() {
        return companyLabel;
    }

    public String getMessageLabel() {
        return messageLabel;
    }

    public String getSubmitButtonText() {
        return submitButtonText;
    }

    // Getters for Media Section
    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getLogoImage() {
        return logoImage;
    }

    // Utility method to check if component is configured
    public boolean isConfigured() {
        return title != null && !title.isEmpty();
    }
}

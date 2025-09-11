package com.mysite.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Sling Model for Related Articles Component
 * Handles data binding and business logic for the related articles carousel
 */
@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {RelatedArticlesModel.class, ComponentExporter.class},
    resourceType = RelatedArticlesModel.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class RelatedArticlesModel implements ComponentExporter {

    protected static final String RESOURCE_TYPE = "mysite/components/relatedarticles";

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    @Default(values = "Related Articles")
    private String sectionTitle;

    @ValueMapValue
    @Default(values = "Dive deeper into the stories, innovations, and how our products are shaping kitchens, markets, and food culture worldwide.")
    private String sectionDescription;

    @ValueMapValue
    @Default(values = "Explore more")
    private String exploreMoreButtonText;

    @ValueMapValue
    private String exploreMoreButtonLink;

    @ValueMapValue
    private List<Resource> articles;

    @PostConstruct
    protected void init() {
        // Initialize any required logic
    }

    /**
     * Get the section title
     * @return section title
     */
    public String getSectionTitle() {
        return sectionTitle;
    }

    /**
     * Get the section description
     * @return section description
     */
    public String getSectionDescription() {
        return sectionDescription;
    }

    /**
     * Get the explore more button text
     * @return button text
     */
    public String getExploreMoreButtonText() {
        return exploreMoreButtonText;
    }

    /**
     * Get the explore more button link
     * @return button link
     */
    public String getExploreMoreButtonLink() {
        return exploreMoreButtonLink;
    }

    /**
     * Get the list of articles
     * @return list of article resources
     */
    public List<Resource> getArticles() {
        return articles != null ? articles : new ArrayList<>();
    }

    /**
     * Check if the component is properly configured
     * @return true if configured
     */
    public boolean isConfigured() {
        return sectionTitle != null && !sectionTitle.isEmpty();
    }

    /**
     * Get the exported type for SPA support
     * @return resource type
     */
    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
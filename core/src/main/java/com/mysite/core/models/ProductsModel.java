package com.mysite.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
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

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {ProductsModel.class, ComponentExporter.class},
    resourceType = ProductsModel.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class ProductsModel implements ComponentExporter {

    protected static final String RESOURCE_TYPE = "mysite/components/products";

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    @Default(values = "Catalogue")
    private String sectionLabel;

    @ValueMapValue
    @Default(values = "Our Products")
    private String title;

    @ValueMapValue
    @Default(values = "From comforting dumplings to vibrant street food, Bibigo celebrates how consumers around the world make Korean food their own.")
    private String description;

    @ValueMapValue
    @Default(values = "Explore Products")
    private String exploreButtonText;

    @ValueMapValue
    private String exploreButtonLink;

    @ValueMapValue
    private List<Resource> products;

    @PostConstruct
    protected void init() {
        // Initialize any required logic
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getExploreButtonText() {
        return exploreButtonText;
    }

    public String getExploreButtonLink() {
        return exploreButtonLink;
    }

    public List<Resource> getProducts() {
        return products != null ? products : new ArrayList<>();
    }

    public boolean isConfigured() {
        return title != null && !title.isEmpty();
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}

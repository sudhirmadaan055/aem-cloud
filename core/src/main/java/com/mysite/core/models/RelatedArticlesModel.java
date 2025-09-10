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

    public String getSectionTitle() {
        return sectionTitle;
    }

    public String getSectionDescription() {
        return sectionDescription;
    }

    public String getExploreMoreButtonText() {
        return exploreMoreButtonText;
    }

    public String getExploreMoreButtonLink() {
        return exploreMoreButtonLink;
    }

    public List<Resource> getArticles() {
        return articles != null ? articles : new ArrayList<>();
    }

    public boolean isConfigured() {
        return sectionTitle != null && !sectionTitle.isEmpty();
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}

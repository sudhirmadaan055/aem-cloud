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
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    private static final Logger logger = LoggerFactory.getLogger(RelatedArticlesModel.class);

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
    @Default(values = "/content/dam/mysite/illustrations/finger-heart-gesture.svg")
    private String decorativeIllustration;

    @ValueMapValue
    private String[] articlePaths;

    @ChildResource
    private List<Resource> articleItems;

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
     * Get the decorative illustration
     * @return illustration path
     */
    public String getDecorativeIllustration() {
        return decorativeIllustration;
    }

    /**
     * Get the list of articles
     * @return list of article resources
     */
    public List<Resource> getArticles() {
        List<Resource> articles = new ArrayList<>();
        if (articlePaths != null && request != null) {
            for (String path : articlePaths) {
                Resource articleResource = request.getResourceResolver().getResource(path);
                if (articleResource != null) {
                    articles.add(articleResource);
                }
            }
        }
        return articles;
    }

    /**
     * Get articles data from authored multifield content
     * @return list of article data maps
     */
    public List<ArticleData> getArticleData() {
        List<ArticleData> articleDataList = new ArrayList<>();
        
        logger.debug("Article Items: {}", articleItems);
        
        if (articleItems != null) {
            for (Resource articleItem : articleItems) {
                if (articleItem != null) {
                    String title = articleItem.getValueMap().get("articleTitle", "");
                    String date = articleItem.getValueMap().get("articleDate", "");
                    String category = articleItem.getValueMap().get("articleCategory", "");
                    String image = articleItem.getValueMap().get("articleImage", "");
                    String link = articleItem.getValueMap().get("articleLink", "");
                    
                    if (!title.isEmpty()) {
                        // Log the image path for debugging
                        logger.debug("Article Image Path: {}", image);
                        logger.debug("Article Title: {}", title);
                        
                        // Use the full image path as provided in the dialog
                        articleDataList.add(new ArticleData(image, title, date, category, link));
                    }
                }
            }
        }
        
        return articleDataList;
    }

    /**
     * Article data class for Figma design
     */
    public static class ArticleData {
        private final String imageRef;
        private final String title;
        private final String date;
        private final String category;
        private final String link;

        public ArticleData(String imageRef, String title, String date, String category, String link) {
            this.imageRef = imageRef;
            this.title = title;
            this.date = date;
            this.category = category;
            this.link = link;
        }

        public String getImageRef() { return imageRef; }
        public String getTitle() { return title; }
        public String getDate() { return date; }
        public String getCategory() { return category; }
        public String getLink() { return link; }
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
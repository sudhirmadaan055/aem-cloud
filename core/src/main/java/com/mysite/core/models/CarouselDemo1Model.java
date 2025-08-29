/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mysite.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

@Model(adaptables = Resource.class)
public class CarouselDemo1Model {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    
    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "")
    private String title;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "")
    private String subtitle;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "true")
    private Boolean showBackgroundElements;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "true")
    private Boolean showNavigation;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "true")
    private Boolean showViewAllButton;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "View all")
    private String viewAllButtonText;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "")
    private String viewAllButtonLink;

    private List<ArticleCard> articleCards;

    @PostConstruct
    protected void init() {
        articleCards = new ArrayList<>();
        loadArticleCards();
    }

    private void loadArticleCards() {
        Resource articlesResource = currentResource.getChild("articles");
        if (articlesResource != null) {
            Iterator<Resource> articlesIterator = articlesResource.listChildren();
            while (articlesIterator.hasNext()) {
                Resource articleResource = articlesIterator.next();
                ArticleCard article = new ArticleCard();
                
                article.setTitle(getPropertyValue(articleResource, "title"));
                article.setDate(getPropertyValue(articleResource, "date"));
                article.setImage(getPropertyValue(articleResource, "image"));
                article.setLearnMoreText(getPropertyValue(articleResource, "learnMoreText"));
                article.setLearnMoreLink(getPropertyValue(articleResource, "learnMoreLink"));
                
                // Load category tags
                List<String> categoryTags = new ArrayList<>();
                Resource categoryTagsResource = articleResource.getChild("categoryTags");
                if (categoryTagsResource != null) {
                    Iterator<Resource> tagsIterator = categoryTagsResource.listChildren();
                    while (tagsIterator.hasNext()) {
                        Resource tagResource = tagsIterator.next();
                        String tag = getPropertyValue(tagResource, "tag");
                        if (tag != null && !tag.trim().isEmpty()) {
                            categoryTags.add(tag);
                        }
                    }
                }
                article.setCategoryTags(categoryTags);
                
                articleCards.add(article);
            }
        }
    }

    private String getPropertyValue(Resource resource, String propertyName) {
        return resource.getValueMap().get(propertyName, String.class);
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Boolean getShowBackgroundElements() {
        return showBackgroundElements != null ? showBackgroundElements : true;
    }

    public Boolean getShowNavigation() {
        return showNavigation != null ? showNavigation : true;
    }

    public Boolean getShowViewAllButton() {
        return showViewAllButton != null ? showViewAllButton : true;
    }

    public String getViewAllButtonText() {
        return viewAllButtonText != null ? viewAllButtonText : "View all";
    }

    public String getViewAllButtonLink() {
        return viewAllButtonLink;
    }

    public List<ArticleCard> getArticleCards() {
        return articleCards;
    }

    public boolean hasArticleCards() {
        return articleCards != null && !articleCards.isEmpty();
    }

    public static class ArticleCard {
        private String title;
        private String date;
        private String image;
        private String learnMoreText;
        private String learnMoreLink;
        private List<String> categoryTags;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLearnMoreText() {
            return learnMoreText != null ? learnMoreText : "Learn more";
        }

        public void setLearnMoreText(String learnMoreText) {
            this.learnMoreText = learnMoreText;
        }

        public String getLearnMoreLink() {
            return learnMoreLink;
        }

        public void setLearnMoreLink(String learnMoreLink) {
            this.learnMoreLink = learnMoreLink;
        }

        public List<String> getCategoryTags() {
            return categoryTags != null ? categoryTags : new ArrayList<>();
        }

        public void setCategoryTags(List<String> categoryTags) {
            this.categoryTags = categoryTags;
        }
    }
}

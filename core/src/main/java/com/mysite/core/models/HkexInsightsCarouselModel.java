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

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.Default;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HkexInsightsCarouselModel {

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "")
    private String title;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "")
    private String subtitle;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String backgroundImage;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "false")
    private Boolean showNavigationArrows;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "View all")
    private String viewAllButtonText;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String viewAllButtonLink;

    @ChildResource
    private List<Resource> contentItems;

    private List<ContentItem> contentItemsList;

    @PostConstruct
    protected void init() {
        if (contentItems != null && !contentItems.isEmpty()) {
            contentItemsList = new ArrayList<>();
            for (Resource itemResource : contentItems) {
                ContentItem item = new ContentItem();
                item.setItemImage(getPropertyValue(itemResource, "itemImage"));
                item.setItemTitle(getPropertyValue(itemResource, "itemTitle"));
                item.setItemDate(getPropertyValue(itemResource, "itemDate"));
                item.setLearnMoreLink(getPropertyValue(itemResource, "learnMoreLink"));
                
                // Handle category tags
                Resource tagsResource = itemResource.getChild("categoryTags");
                if (tagsResource != null) {
                    List<String> tags = new ArrayList<>();
                    for (Resource tagResource : tagsResource.getChildren()) {
                        String tagText = getPropertyValue(tagResource, "tagText");
                        if (tagText != null && !tagText.isEmpty()) {
                            tags.add(tagText);
                        }
                    }
                    item.setCategoryTags(tags);
                }
                
                contentItemsList.add(item);
            }
        }
    }

    private String getPropertyValue(Resource resource, String propertyName) {
        return resource.getValueMap().get(propertyName, String.class);
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public Boolean getShowNavigationArrows() {
        return showNavigationArrows;
    }

    public String getViewAllButtonText() {
        return viewAllButtonText;
    }

    public String getViewAllButtonLink() {
        return viewAllButtonLink;
    }

    public List<ContentItem> getContentItems() {
        return contentItemsList;
    }

    // Helper methods for HTL template
    public boolean hasHeroContent() {
        return (title != null && !title.isEmpty()) || (subtitle != null && !subtitle.isEmpty());
    }

    public boolean hasContentItems() {
        return contentItemsList != null && !contentItemsList.isEmpty();
    }

    public boolean hasNavigation() {
        return (viewAllButtonLink != null && !viewAllButtonLink.isEmpty()) || 
               (showNavigationArrows != null && showNavigationArrows);
    }

    // Inner class for content items
    public static class ContentItem {
        private String itemImage;
        private String itemTitle;
        private String itemDate;
        private List<String> categoryTags;
        private String learnMoreLink;

        // Getters and Setters
        public String getItemImage() { return itemImage; }
        public void setItemImage(String itemImage) { this.itemImage = itemImage; }

        public String getItemTitle() { return itemTitle; }
        public void setItemTitle(String itemTitle) { this.itemTitle = itemTitle; }

        public String getItemDate() { return itemDate; }
        public void setItemDate(String itemDate) { this.itemDate = itemDate; }

        public List<String> getCategoryTags() { return categoryTags; }
        public void setCategoryTags(List<String> categoryTags) { this.categoryTags = categoryTags; }

        public String getLearnMoreLink() { return learnMoreLink; }
        public void setLearnMoreLink(String learnMoreLink) { this.learnMoreLink = learnMoreLink; }
    }
}

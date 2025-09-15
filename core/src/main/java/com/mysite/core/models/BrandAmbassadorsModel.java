package com.mysite.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    adapters = {BrandAmbassadorsModel.class, ComponentExporter.class},
    resourceType = BrandAmbassadorsModel.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class BrandAmbassadorsModel implements ComponentExporter {

    private static final Logger logger = LoggerFactory.getLogger(BrandAmbassadorsModel.class);
    
    public static final String RESOURCE_TYPE = "mysite/components/brandambassadors";

    @ScriptVariable
    private Page currentPage;

    @ValueMapValue
    @Default(values = "Brand Ambassadors")
    private String title;

    @ValueMapValue
    @Default(values = "Our Athletes")
    private String subtitle;

    @ValueMapValue
    @Default(values = "Champions who redefine what's possible in their sport, setting new benchmarks with every performance. Their energy reflects Bibigo's drive to innovate and inspire.")
    private String description;

    @ValueMapValue
    private String configValue;

    private List<PillTab> pillTabs = new ArrayList<>();
    private List<Athlete> athletes = new ArrayList<>();

    @PostConstruct
    protected void init() {
        logger.debug("Initializing Brand Ambassadors Model");
        
        // Initialize pill tabs with default data
        if (pillTabs.isEmpty()) {
            pillTabs.add(new PillTab("Global Gamechangers", true));
            pillTabs.add(new PillTab("Emerging Voices", false));
            pillTabs.add(new PillTab("Cultural Influencers", false));
        }
        
        // Initialize athletes with sample data
        if (athletes.isEmpty()) {
            athletes.add(new Athlete("Kris Kim", "크리스 킴", "Golf", "/content/dam/mysite/athletes/kris-kim.jpg", "/content/dam/mysite/icons/golf-icon.svg"));
            athletes.add(new Athlete("Freshbella", "크리스 킴", "Breaking", "/content/dam/mysite/athletes/freshbella.jpg", "/content/dam/mysite/icons/breaking-icon.svg"));
            athletes.add(new Athlete("Park Kum Kang", "크리스 킴", "Shotput", "/content/dam/mysite/athletes/park-kum-kang.jpg", "/content/dam/mysite/icons/shotput-icon.svg"));
            athletes.add(new Athlete("Zooty Zoot", "크리스 킴", "Snowboarding", "/content/dam/mysite/athletes/zooty-zoot.jpg", "/content/dam/mysite/icons/snowboarding-icon.svg"));
        }
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getConfigValue() {
        return configValue;
    }

    public List<PillTab> getPillTabs() {
        return pillTabs;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }

    public static class PillTab {
        private String tabLabel;
        private boolean isActive;

        public PillTab(String tabLabel, boolean isActive) {
            this.tabLabel = tabLabel;
            this.isActive = isActive;
        }

        public String getTabLabel() {
            return tabLabel;
        }

        public boolean getIsActive() {
            return isActive;
        }
    }

    public static class Athlete {
        private String athleteName;
        private String athleteNameKorean;
        private String sport;
        private String athleteImage;
        private String sportIcon;

        public Athlete(String athleteName, String athleteNameKorean, String sport, String athleteImage, String sportIcon) {
            this.athleteName = athleteName;
            this.athleteNameKorean = athleteNameKorean;
            this.sport = sport;
            this.athleteImage = athleteImage;
            this.sportIcon = sportIcon;
        }

        public String getAthleteName() {
            return athleteName;
        }

        public String getAthleteNameKorean() {
            return athleteNameKorean;
        }

        public String getSport() {
            return sport;
        }

        public String getAthleteImage() {
            return athleteImage;
        }

        public String getSportIcon() {
            return sportIcon;
        }
    }
}

# [Component Name] - High Complexity Context

## Component Overview
- **Component Name**: [e.g., Product Search & Filter, Location Finder, Multi-step Form]
- **Complexity Level**: High
- **Purpose**: [Brief description of what this component does]
- **Business Value**: [Why this component is needed]
- **Architect Review Required**: ✅ YES

## Business Logic Requirements
- **Search Functionality**: [Search algorithms, indexing requirements]
  - Example: Full-text search, faceted search, auto-complete
- **Filtering Logic**: [Filter types, combinations, performance considerations]
  - Example: Price range, category, brand, availability filters
- **Sorting Options**: [Sort criteria, default sorting]
  - Example: Price, popularity, date, relevance
- **Pagination**: [Page size, navigation, performance]
  - Example: 20 items per page, infinite scroll, load more
- **Caching Strategy**: [What should be cached, cache invalidation]
  - Example: Cache search results for 5 minutes, invalidate on content changes

## Data Sources & APIs
- **Primary Data Source**: [Main content repository or external API]
  - Example: AEM JCR, Product catalog API, CMS API
- **Secondary Data Sources**: [Additional data sources]
  - Example: User preferences, analytics data, inventory system
- **API Endpoints**: [External service endpoints]
  - Example: `/api/products/search`, `/api/locations/nearby`
- **Authentication**: [API keys, OAuth, etc.]
  - Example: API key in OSGi config, OAuth 2.0 for user data
- **Rate Limiting**: [API call limits, throttling]
  - Example: 100 requests per minute, exponential backoff

## Performance Requirements
- **Response Time**: [Expected response times]
  - Example: < 500ms for search results, < 2s for initial page load
- **Concurrent Users**: [Expected load]
  - Example: 1000 concurrent users, 10,000 requests per hour
- **Caching Strategy**: [Browser, CDN, AEM cache]
  - Example: Browser cache for 1 hour, CDN cache for 24 hours
- **Database Queries**: [Query optimization requirements]
  - Example: Indexed fields, query result limits, connection pooling

## Authoring Configuration
- **Filter Configuration**: [What filters can be configured]
  - Example: Enable/disable price filter, set default categories
- **Result Size Limits**: [Maximum results per page]
  - Example: Max 50 results per page, max 1000 total results
- **Sort Options**: [Available sorting criteria]
  - Example: Price, date, popularity, custom field
- **Default Settings**: [Default filter/sort values]
  - Example: Sort by relevance, show 20 results, no filters applied

## Technical Architecture
- **Sling Models**: [Required model classes]
  - Example: SearchModel, FilterModel, ResultModel
- **OSGi Services**: [Custom services needed]
  - Example: SearchService, CacheService, AnalyticsService
- **Client Libraries**: [Frontend JS/CSS requirements]
  - Example: Search functionality, filter interactions, pagination
- **Event Listeners**: [Content change listeners]
  - Example: Listen for product updates, invalidate cache
- **Schedulers**: [Background processing needs]
  - Example: Index content nightly, update analytics hourly

## Security Considerations
- **Input Validation**: [User input sanitization]
  - Example: Sanitize search terms, validate filter values
- **Access Control**: [Who can access what data]
  - Example: Public search, authenticated user preferences
- **API Security**: [Authentication, authorization]
  - Example: Rate limiting, input validation, secure headers
- **Data Privacy**: [GDPR, data handling]
  - Example: Anonymize user data, respect privacy settings

## Monitoring & Analytics
- **Performance Metrics**: [What to track]
  - Example: Search response time, cache hit rate, error rate
- **User Behavior**: [Search patterns, popular filters]
  - Example: Most searched terms, popular filter combinations
- **Error Handling**: [Error scenarios, fallbacks]
  - Example: API timeout fallback, empty result handling
- **Logging Requirements**: [What to log, log levels]
  - Example: Log all searches (INFO), log errors (ERROR)

## Testing Requirements
- **Unit Tests**: [Model and service testing]
  - Example: Test search logic, filter combinations, pagination
- **Integration Tests**: [API integration testing]
  - Example: Test external API calls, database queries
- **Performance Tests**: [Load testing scenarios]
  - Example: 1000 concurrent searches, large result sets
- **User Acceptance Tests**: [Business scenario testing]
  - Example: End-to-end search workflows, filter combinations

## Scalability Considerations
- **Horizontal Scaling**: [How to scale the component]
  - Example: Multiple AEM instances, load balancing
- **Data Growth**: [How to handle increasing data]
  - Example: Database partitioning, content archiving
- **User Growth**: [How to handle more users]
  - Example: CDN usage, caching strategies, API optimization

## Deployment Considerations
- **Environment Configuration**: [Different settings per environment]
  - Example: Dev/staging/prod API endpoints, cache settings
- **Feature Flags**: [Gradual rollout capabilities]
  - Example: Enable new filters gradually, A/B test search algorithms
- **Rollback Strategy**: [How to revert if issues occur]
  - Example: Database rollback, configuration rollback

---

**Template Usage:**
1. Copy this template for each high complexity component
2. Rename file to match component name: `[component-name]-context.md`
3. Fill in all sections with specific details
4. **MANDATORY**: Schedule architect review before development
5. Use this context when generating component code

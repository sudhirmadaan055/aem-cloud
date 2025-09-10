# Content Listing Component - Implementation Context

## Component Overview

The Content Listing component is a high-complexity listing component designed for displaying and managing news articles and content with advanced search, filtering, and pagination capabilities.

## Key Features

### Search Functionality
- Real-time search with debounced input
- Full-text search across content
- Search suggestions and autocomplete
- Clear search functionality

### Filtering System
- Expandable filter categories (Topic, Period)
- Multi-select filter options
- Recently used filters
- Clear all filters functionality
- Filter state persistence

### Sorting Options
- Dynamic sorting by multiple fields
- Ascending/descending order
- URL-based sort state management
- Sort field configuration

### Pagination
- Configurable results per page
- Page navigation controls
- Jump to page functionality
- Results count display
- URL-based pagination state

### View Modes
- List view (default)
- Grid view toggle
- Responsive layout adaptation
- View state persistence

## Technical Implementation

### Backend Architecture
- **BaseListingModel**: Abstract base class for listing functionality
- **ContentListingModel**: Specific implementation for content listing
- **BaseListingServlet**: Abstract servlet for listing operations
- **ContentListingServlet**: Specific servlet for content search
- **ListingService**: OSGi service for search operations

### Frontend Architecture
- **Search Module**: Handles search input and API calls
- **Filter Module**: Manages filter state and interactions
- **Pagination Module**: Handles page navigation
- **Sorting Module**: Manages sort options and state
- **View Module**: Toggles between list and grid views

### Data Flow
1. User interacts with search, filters, or pagination
2. JavaScript captures user input
3. AJAX request sent to servlet endpoint
4. Servlet processes request using ListingService
5. Query Builder executes search
6. Results returned as JSON
7. Frontend updates display with new results

## Configuration Options

### Basic Settings
- **Root Path**: Content root for search
- **Page Title**: Display title for the listing
- **Search Placeholder**: Placeholder text for search input

### Filter Settings
- **Topic Tags**: Configurable topic filters with icons
- **Period Options**: Time-based filter options
- **Filter Icons**: Customizable icons for filter categories

### Display Settings
- **Results Per Page**: Configurable options (5, 10, 25, 50)
- **Sort Fields**: Available sorting options
- **Default Sort**: Default sort field and order
- **Content Type**: Pages or Content Fragments

### Content Settings
- **Show Thumbnail**: Toggle thumbnail display
- **Show Date**: Toggle publication date
- **Show Tags**: Toggle tag display
- **Show Contact Info**: Toggle contact information

## Dialog Structure

### Basic Settings Tab
- Root Path (pathfield)
- Page Title (textfield)
- Search Placeholder (textfield)

### Filter Settings Tab
- Topic Tags (multifield with fieldset)
- Period Options (multifield with fieldset)

### Display Settings Tab
- Results Per Page Options (multifield)
- Default Results Per Page (numberfield)
- Sort Fields (multifield)
- Default Sort Field (select)
- Default Sort Order (select)

### Content Settings Tab
- Content Type (select)
- Show Thumbnail (checkbox)
- Show Date (checkbox)
- Show Tags (checkbox)
- Show Contact Info (checkbox)

## HTL Template Structure

```html
<div class="content-listing" data-search-endpoint="/bin/aem/content-listing/search">
  <!-- Header Section -->
  <div class="content-listing__header">
    <h1 class="content-listing__title">${properties.pageTitle}</h1>
    <div class="content-listing__search">
      <input type="text" placeholder="${properties.searchPlaceholder}" class="content-listing__search-input">
    </div>
  </div>
  
  <!-- Filters Section -->
  <div class="content-listing__filters">
    <!-- Filter categories and options -->
  </div>
  
  <!-- Results Section -->
  <div class="content-listing__results">
    <!-- Results count and sorting -->
    <div class="content-listing__results-header">
      <span class="content-listing__results-count">${results.totalCount} Total Results</span>
      <div class="content-listing__sorting">
        <!-- Sort controls -->
      </div>
    </div>
    
    <!-- Results list -->
    <div class="content-listing__results-list">
      <!-- Individual result items -->
    </div>
  </div>
  
  <!-- Pagination Section -->
  <div class="content-listing__pagination">
    <!-- Pagination controls -->
  </div>
</div>
```

## JavaScript Functionality

### Search Module
- Debounced search input (300ms delay)
- Minimum search length validation
- Loading state management
- Error handling

### Filter Module
- Multi-select filter support
- Filter state management
- Clear all filters
- Recent filters tracking

### Pagination Module
- AJAX-based page loading
- URL state management
- Scroll to top on page change
- Results per page selection

### Sorting Module
- Dynamic sort field selection
- Sort order toggle
- URL state management
- AJAX-based sorting

## CSS Architecture

### BEM Methodology
- Block: `content-listing`
- Elements: `content-listing__header`, `content-listing__search`, etc.
- Modifiers: `content-listing--loading`, `content-listing--error`, etc.

### Responsive Design
- Mobile-first approach
- Breakpoints: 768px, 1024px, 1200px
- Flexible grid system
- Touch-friendly interactions

### Color Scheme
- Primary: #1e3a8a (Dark blue)
- Secondary: #64748b (Gray)
- Accent: #3b82f6 (Blue)
- Text: #1f2937 (Dark gray)
- Background: #ffffff (White)
- Border: #e5e7eb (Light gray)

## Performance Considerations

### Backend Optimization
- Query Builder optimization
- Result caching
- Pagination limits
- Index optimization

### Frontend Optimization
- Debounced search
- Lazy loading
- Image optimization
- Minimal DOM manipulation

## Accessibility Features

### ARIA Support
- ARIA labels for all interactive elements
- ARIA live regions for dynamic content
- ARIA expanded for filter categories
- ARIA selected for active filters

### Keyboard Navigation
- Tab navigation through all elements
- Enter/Space for activation
- Arrow keys for dropdown navigation
- Escape to close expanded elements

### Screen Reader Support
- Semantic HTML structure
- Descriptive text for all elements
- Status announcements for dynamic changes
- Focus management

## Error Handling

### Backend Errors
- Graceful degradation for search failures
- Fallback content display
- Error logging and monitoring
- User-friendly error messages

### Frontend Errors
- Network error handling
- Timeout management
- Loading state indicators
- Retry mechanisms

## Testing Requirements

### Unit Tests
- Service method testing
- Model property validation
- Servlet endpoint testing
- JavaScript function testing

### Integration Tests
- End-to-end search functionality
- Filter interaction testing
- Pagination testing
- Cross-browser compatibility

### Performance Tests
- Large dataset handling
- Search performance
- Filter performance
- Pagination performance

## Deployment Considerations

### AEM Cloud Service
- Cloud-ready configuration
- Performance optimization
- Security compliance
- Monitoring and logging

### Content Migration
- Content structure validation
- Tag migration
- Search index optimization
- Performance testing

## Maintenance Guidelines

### Regular Updates
- Search index optimization
- Performance monitoring
- Security updates
- Content structure validation

### Troubleshooting
- Common issues and solutions
- Performance optimization
- Error resolution
- User training

## Future Enhancements

### Planned Features
- Advanced search filters
- Content recommendations
- Analytics integration
- Mobile app support

### Scalability Considerations
- Large dataset handling
- Multi-site support
- Performance optimization
- Caching strategies

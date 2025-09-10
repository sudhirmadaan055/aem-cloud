# AEM Component Context Files

This directory contains additional context files for medium and high complexity AEM components to provide detailed business logic and technical requirements.

## Directory Structure

```
context/
├── project-information-context.md (existing)
└── component-context/
    ├── medium-complexity/
    │   ├── medium-complexity-template.md
    │   └── [component-name]-context.md
    └── high-complexity/
        ├── high-complexity-template.md
        └── [component-name]-context.md
```

## When to Use Context Files

### Simple Components
- **No context file needed**
- Examples: Text, Image, Button, Banner
- Purely presentational with minimal fields

### Medium Complexity Components
- **Create context file in `medium-complexity/`**
- Examples: Teaser, Carousel, Accordion, Card Grid
- Multiple configurable fields with light business logic

### High Complexity Components
- **Create context file in `high-complexity/`**
- **MANDATORY: Flag for architect review**
- Examples: Product Listing, Search Component, Location Finder, Multi-step Form
- Dynamic data, APIs, search, filtering, pagination

## File Naming Convention

### Format
```
[component-name]-context.md
```

### Rules
- Use **kebab-case** for component names
- Match the component folder name exactly
- Always include `-context.md` suffix
- No spaces or special characters

### Examples
```
product-teaser-context.md
news-listing-context.md
search-filter-context.md
multi-step-form-context.md
```

## Template Usage

### For Medium Complexity Components
1. Copy `medium-complexity/medium-complexity-template.md`
2. Rename to `[component-name]-context.md`
3. Fill in all sections with specific details
4. Focus on business logic and authoring requirements

### For High Complexity Components
1. Copy `high-complexity/high-complexity-template.md`
2. Rename to `[component-name]-context.md`
3. Fill in all sections with specific details
4. **Schedule architect review before development**
5. Focus on performance, scalability, and security

## Context File Content Guidelines

### Required Sections
- **Component Overview**: Name, purpose, business value
- **Business Logic Requirements**: What the component needs to do
- **Authoring Requirements**: What authors can configure
- **Data Sources**: Where data comes from

### Medium Complexity Additional Sections
- **Styling & Layout**: Responsive behavior, themes
- **Integration Points**: Related components, analytics
- **Example Usage Scenarios**: Typical use cases

### High Complexity Additional Sections
- **Performance Requirements**: Response times, load expectations
- **Technical Architecture**: Services, models, client libraries
- **Security Considerations**: Input validation, access control
- **Monitoring & Analytics**: What to track and log
- **Testing Requirements**: Unit, integration, performance tests
- **Scalability Considerations**: How to handle growth

## Integration with Component Development

### During Component Identification (Option 1)
- Analyze component complexity level
- Create appropriate context file if medium/high complexity
- Reference context file in `components.json`

### During Component Development (Option 2)
- Read context file for business requirements
- Generate code based on context specifications
- Ensure all requirements are implemented

## Best Practices

### Content Guidelines
- **Be Specific**: Provide concrete examples and requirements
- **Include Examples**: Show expected behavior and edge cases
- **Consider Performance**: Always think about scalability
- **Security First**: Include security considerations for all components

### Maintenance
- **Keep Updated**: Update context files when requirements change
- **Version Control**: Track changes to context files
- **Review Regularly**: Ensure context files reflect current needs

### Collaboration
- **Share Context**: Make context files available to all team members
- **Architect Review**: Always get architect approval for high complexity components
- **Documentation**: Use context files as living documentation

## Examples

### Medium Complexity Example
```
File: product-teaser-context.md
Component: Product Teaser
Complexity: Medium
Purpose: Display product information with call-to-action
```

### High Complexity Example
```
File: product-search-context.md
Component: Product Search & Filter
Complexity: High
Purpose: Search and filter products with pagination
Architect Review: Required
```

---

**Remember**: Context files are essential for generating accurate, complete AEM components. The more detailed the context, the better the generated code will be.

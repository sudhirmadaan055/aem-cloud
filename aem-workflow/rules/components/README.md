# Component Development Rules

This folder contains rules specific to AEM component development, including structure, dialogs, complexity management, and specialized components.

## Files

### `.cursorrules-aem-components`
- **Purpose**: Component development best practices
- **Usage**: Core rules for HTL, Sling Models, component structure
- **When to use**: Always when developing components

### `.cursorrules-aem-dialogs`
- **Purpose**: Dialog configuration guidelines
- **Usage**: Rules for Touch UI dialogs, field types, validation
- **When to use**: When creating component authoring dialogs

### `.cursorrules-aem-complexity`
- **Purpose**: Complexity level definitions
- **Usage**: Defines Simple, Medium, High complexity levels
- **When to use**: When analyzing component requirements

### `.cursorrules-aem-listing`
- **Purpose**: Listing component specific rules
- **Usage**: Specialized rules for listing/search components
- **When to use**: When developing listing or search components

## Component Complexity Levels

### Simple Components
- Purely presentational, minimal fields
- No dynamic data or complex authoring
- Examples: Text, Image, Button, Banner

### Medium Complexity Components
- Multiple configurable fields
- Light business logic in Sling Models
- Examples: Teaser, Carousel, Accordion, Card Grid

### High Complexity Components
- Dynamic data, APIs, search, filtering
- Require architect review
- Examples: Product Listing, Search Component, Multi-step Form

## Best Practices

1. **Extend Core Components** whenever possible
2. **Use HTL (Sightly)** for rendering
3. **Business logic in Sling Models**, not HTL
4. **Follow BEM methodology** for CSS
5. **Implement proper validation** and error handling

## Integration

These rules work with:
- **Core rules** (`../core/`) - For workflow process
- **Technical rules** (`../technical/`) - For implementation details
- **Context files** (`../../context/`) - For additional requirements

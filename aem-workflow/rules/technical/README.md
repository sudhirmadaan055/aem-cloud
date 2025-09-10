# Technical Implementation Rules

This folder contains technical rules for AEM implementation, including Cloud Service compliance, Sling best practices, internationalization, and project setup.

## Files

### `.cursorrules-aem-cloud`
- **Purpose**: Cloud Service compliance rules
- **Usage**: Rules for AEM as a Cloud Service development
- **When to use**: When developing for AEM Cloud Service

### `.cursorrules-aem-sling`
- **Purpose**: Apache Sling best practices
- **Usage**: Rules for Sling Models, Services, and Sling framework usage
- **When to use**: When implementing backend logic

### `.cursorrules-aem-i18n`
- **Purpose**: Internationalization rules
- **Usage**: Rules for multi-language component development
- **When to use**: When developing components for multiple languages

### `.cursorrules-aem-archetype-generation`
- **Purpose**: Project archetype generation rules
- **Usage**: Rules for creating AEM projects using Maven archetypes
- **When to use**: When setting up new AEM projects
- **Location**: See `../core/.cursorrules-project-creation` for the actual implementation

### `.cursorrules-maven`
- **Purpose**: Maven build and dependency management rules
- **Usage**: Rules for Maven commands, dependency management, and build processes
- **When to use**: When working with Maven builds, dependencies, or troubleshooting build issues
- **Key features**: Maven whitelist support, dependency best practices, build optimization

## Technical Areas Covered

### Cloud Service Compliance
- Performance optimization
- Security best practices
- Scalability considerations
- Deployment guidelines

### Sling Framework
- Sling Model best practices
- OSGi service development
- Resource resolution
- Event handling

### Internationalization
- Multi-language support
- Content translation
- Locale-specific behavior
- RTL language support

### Project Setup
- Maven archetype usage
- Project structure
- Dependency management
- Build configuration

### Maven Build Management
- Maven command execution
- Dependency resolution
- Build optimization
- Troubleshooting build issues

## Best Practices

1. **Follow Cloud Service guidelines** for performance and security
2. **Use proper Sling patterns** for resource handling
3. **Implement i18n from the start** for global components
4. **Use official archetypes** for project setup
5. **Minimize external dependencies** to avoid bundle activation issues
6. **Use Maven whitelist** for approved command execution

## Integration

These rules work with:
- **Core rules** (`../core/`) - For workflow process
- **Component rules** (`../components/`) - For component implementation
- **Context files** (`../../context/`) - For technical requirements

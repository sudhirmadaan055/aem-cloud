# AEM Component Development Workflow

A comprehensive workflow system for developing AEM components with proper complexity management, context files, and best practices.

## 📁 Structure Overview

```
aem-workflow/
├── README.md (this file)
├── rules/                    # Development rules and guidelines
│   ├── core/                 # Core workflow rules
│   │   ├── .cursorrules      # Main workflow options
│   │   ├── .cursorrules-project-creation
│   │   └── .cursorrules-component-workflow
│   ├── components/           # Component development rules
│   │   ├── .cursorrules-aem-components
│   │   ├── .cursorrules-aem-dialogs
│   │   ├── .cursorrules-aem-complexity
│   │   └── .cursorrules-aem-listing
│   ├── technical/            # Technical implementation rules
│   │   ├── .cursorrules-aem-cloud
│   │   ├── .cursorrules-aem-sling
│   │   └── .cursorrules-aem-i18n
│   └── docs/                 # Reference documentation
└── context/                  # Component context files
    ├── component-context/    # Additional context for complex components
    └── project-information-context.md
```

## 🚀 Quick Start

### 1. **Identify Components** (Option 1)
- Analyze Figma designs or images
- Extract component requirements
- Create `components.json` with identified components

### 2. **Develop Components** (Option 2)
- Generate AEM component code from `components.json`
- Follow strict AEM best practices
- Use context files for complex components

### 3. **Styleguide** (Option 3)
- Extract design tokens from designs
- Generate SCSS variables and styles

### 4. **Export** (Option 4)
- Convert Figma designs to PNG
- Save reference images

## 📋 Component Complexity Levels

### **Simple Components**
- **No context file needed**
- Examples: Text, Image, Button, Banner
- Purely presentational with minimal fields

### **Medium Complexity Components**
- **Create context file in `context/component-context/medium-complexity/`**
- Examples: Teaser, Carousel, Accordion, Card Grid
- Multiple configurable fields with light business logic

### **High Complexity Components**
- **Create context file in `context/component-context/high-complexity/`**
- **MANDATORY: Flag for architect review**
- Examples: Product Listing, Search Component, Location Finder, Multi-step Form
- Dynamic data, APIs, search, filtering, pagination

## 📚 Rules & Guidelines

### **Core Rules** (`rules/core/`)
- `.cursorrules` - Main workflow options and process
- `.cursorrules-project-creation` - Project creation assistant
- `.cursorrules-component-workflow` - Component development workflow

### **Component Rules** (`rules/components/`)
- `.cursorrules-aem-components` - Component development best practices
- `.cursorrules-aem-dialogs` - Dialog configuration guidelines
- `.cursorrules-aem-complexity` - Complexity level definitions
- `.cursorrules-aem-listing` - Listing component specific rules

### **Technical Rules** (`rules/technical/`)
- `.cursorrules-aem-cloud` - Cloud Service compliance
- `.cursorrules-aem-sling` - Apache Sling best practices
- `.cursorrules-aem-i18n` - Internationalization rules
- **Project archetype generation** - See `rules/core/.cursorrules-project-creation`

### **Reference Documentation**
- `rules/docs/` - Adobe AEM documentation (PDFs)
- Use for examples and patterns (not strict rules)
- Strict rules defined only in `.cursorrules-*` files

## 🎯 Context Files

### **When to Use**
- **Medium Complexity**: Light business logic, multiple fields
- **High Complexity**: Complex business logic, APIs, search, filtering

### **File Structure**
```
context/component-context/
├── medium-complexity/
│   ├── medium-complexity-template.md
│   └── [component-name]-context.md
└── high-complexity/
    ├── high-complexity-template.md
    └── [component-name]-context.md
```

### **Naming Convention**
- Format: `[component-name]-context.md`
- Use kebab-case: `product-teaser-context.md`
- Match component folder name exactly

## 🔧 Usage Workflow

### **Step 1: Component Identification**
1. Analyze design (Figma URL or image)
2. Determine if it's a Page or Component
3. Identify complexity level
4. Create context file if medium/high complexity
5. Save to `components.json`

### **Step 2: Component Development**
1. Read `components.json` and context files
2. Generate complete AEM component code
3. Follow all rules in `rules/` folder
4. Include HTL, Sling Model, dialog, editConfig
5. Add ClientLibs if needed

### **Step 3: Quality Assurance**
1. Validate against complexity rules
2. Ensure architect review for high complexity
3. Test component functionality
4. Verify responsive behavior
5. Check accessibility compliance

## 📖 Best Practices

### **Component Development**
- **Extend Core Components** whenever possible
- **Use HTL (Sightly)** for rendering
- **Business logic in Sling Models**, not HTL
- **Follow BEM methodology** for CSS
- **Implement proper validation** and error handling

### **Context Files**
- **Be Specific**: Provide concrete examples
- **Include Edge Cases**: Handle unusual scenarios
- **Consider Performance**: Think about scalability
- **Security First**: Include security considerations

### **Collaboration**
- **Share Context**: Make files available to team
- **Architect Review**: Required for high complexity
- **Version Control**: Track all changes
- **Documentation**: Use as living documentation

## 🚨 Important Notes

### **High Complexity Components**
- **MUST** be flagged for architect review
- **MUST** include performance considerations
- **MUST** have comprehensive testing strategy
- **MUST** consider scalability and security

### **File References**
- Update `.cursorrules` to reference new paths
- Update any hardcoded paths in rules
- Ensure all references point to `aem-workflow/`

## 🔄 Migration from Old Structure

If migrating from separate `rules/` and `context/` folders:

1. **Backup existing files**
2. **Copy to new structure** (already done)
3. **Update references** in `.cursorrules`
4. **Test workflow** with new structure
5. **Remove old folders** after verification

## 📞 Support

- **Rules**: Check `rules/` folder for specific guidelines
- **Context**: Use templates in `context/component-context/`
- **Examples**: Reference existing context files
- **Documentation**: Use `rules/docs/` for Adobe references

---

**Remember**: This workflow ensures consistent, high-quality AEM component development with proper complexity management and comprehensive context documentation.

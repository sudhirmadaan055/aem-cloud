# Core Workflow Rules

This folder contains the core workflow rules that define the main AEM component development process.

## Files

### `.cursorrules`
- **Purpose**: Main workflow options and process
- **Usage**: Defines the 4 main options (Identify, Develop, Styleguide, Export)
- **When to use**: Always - this is the entry point for the workflow

### `.cursorrules-project-creation`
- **Purpose**: AEM project creation assistant
- **Usage**: Interactive process for creating new AEM projects
- **When to use**: When creating new AEM projects from scratch

## How to Use

1. **Start with `.cursorrules`** - This defines your main workflow options
2. **Use `.cursorrules-project-creation`** - When you need to create a new AEM project
3. **Follow the interactive process** - Both files guide you through step-by-step processes

## Integration

These core rules work with:
- **Component rules** (`../components/`) - For component development
- **Technical rules** (`../technical/`) - For technical implementation
- **Context files** (`../../context/`) - For additional component context

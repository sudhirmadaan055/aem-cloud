# Feature Grid Component Context

## Component Overview
- **Name**: Feature Grid
- **Type**: Content Grid
- **Complexity**: Medium
- **Purpose**: Display 4 feature cards showcasing brand values

## Design Requirements
- **Layout**: 4-column grid (responsive: 4 → 2 → 1 columns)
- **Cards**: Feature cards with icons, titles, descriptions, and "Learn More" buttons
- **Styling**: Consistent card styling with hover effects

## Data Structure
- **Headline**: Section title
- **Feature Cards** (Multifield):
  - Icon/Image
  - Title
  - Description
  - Learn More Button Text
  - Learn More Button Link

## Technical Implementation
- **HTL Template**: Grid layout with feature card iteration
- **Sling Model**: FeatureGridModel with multifield processing
- **Dialog**: Simple fixedcolumns with multifield for feature cards
- **SCSS**: Grid system with responsive breakpoints

## AEM Integration
- **Component Group**: CJCJ - Content
- **Resource Super Type**: foundation/components/container
- **EditConfig**: Standard with cq:inherit
- **ClientLibs**: Component-specific CSS

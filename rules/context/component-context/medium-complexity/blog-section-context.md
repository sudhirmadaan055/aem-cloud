# Blog Section Component Context

## Component Overview
- **Name**: Blog Section
- **Type**: Content Section
- **Complexity**: Medium
- **Purpose**: Display blog post cards with images, titles, dates, and excerpts

## Design Requirements
- **Layout**: 2-column grid (responsive: 2 → 1 columns)
- **Cards**: Blog post cards with featured images, titles, dates, excerpts, and "Read More" buttons
- **Styling**: Consistent card styling with hover effects

## Data Structure
- **Headline**: Section title
- **Blog Posts** (Multifield):
  - Featured Image
  - Post Title
  - Post Date
  - Post Excerpt
  - Read More Button Text
  - Post Link

## Technical Implementation
- **HTL Template**: Grid layout with blog post iteration
- **Sling Model**: BlogSectionModel with multifield processing
- **Dialog**: Simple fixedcolumns with multifield for blog posts
- **SCSS**: Grid system with responsive breakpoints

## AEM Integration
- **Component Group**: CJCJ - Content
- **Resource Super Type**: foundation/components/container
- **EditConfig**: Standard with cq:inherit
- **ClientLibs**: Component-specific CSS

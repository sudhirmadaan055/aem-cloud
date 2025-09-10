# Product Carousel Component Context

## Component Overview
- **Name**: Product Carousel
- **Type**: Content Carousel
- **Complexity**: Medium
- **Purpose**: Horizontal carousel of product cards with e-commerce functionality

## Design Requirements
- **Layout**: Horizontal scrollable carousel (5+ products)
- **Cards**: Product cards with images, names, descriptions, prices, and "Add to Cart" buttons
- **Navigation**: Carousel navigation controls
- **Styling**: Consistent product card styling with hover effects

## Data Structure
- **Headline**: Section title
- **Products** (Multifield):
  - Product Image
  - Product Name
  - Product Description
  - Price
  - Add to Cart Button Text
  - Product Link

## Technical Implementation
- **HTL Template**: Carousel layout with product card iteration
- **Sling Model**: ProductCarouselModel with multifield processing
- **Dialog**: Simple fixedcolumns with multifield for products
- **SCSS**: Carousel system with responsive breakpoints
- **JavaScript**: Carousel navigation functionality

## AEM Integration
- **Component Group**: CJCJ - Content
- **Resource Super Type**: foundation/components/container
- **EditConfig**: Standard with cq:inherit
- **ClientLibs**: Component-specific CSS and JS

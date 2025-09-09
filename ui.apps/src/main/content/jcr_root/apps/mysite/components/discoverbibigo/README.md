# Discover Bibigo Component

A promotional component featuring a header section with title, main heading, and description, followed by a responsive grid of cards. Each card displays an image, title, description, and call-to-action button.

## Features

- **Responsive Design**: Optimized for desktop, tablet, and mobile devices
- **Authorable Content**: Easy content management through AEM authoring interface
- **Customizable Cards**: Configurable number of cards (1-6) with individual content
- **Brand Colors**: Consistent Bibigo branding with green and yellow color palette
- **Modern Typography**: Uses Malik Trial for headings and Averta Std PE for body text
- **Interactive Elements**: Hover effects and smooth transitions
- **Dynamic Layout**: Individual card rotations (4deg, 0deg, -4deg) with organic blob clip-path for images

## Component Structure

### Header Section
- **Title**: Small green text (e.g., "Know More")
- **Main Heading**: Large uppercase heading (e.g., "Discover Bibigo")
- **Description**: Body text about Bibigo's features

### Cards Grid
- **Number of Cards**: Configurable from 1 to 6 cards
- **Card Content**: Each card includes:
  - Circular/rounded image
  - Card title
  - Card description
  - "explore more" button with link

## Authoring

### Dialog Tabs

#### Header Tab
- **Title**: Text field for the small title
- **Main Heading**: Text field for the main heading
- **Description**: Textarea for the description text

#### Cards Tab
- **Number of Cards**: Dropdown to select 1-6 cards
- **Card Items**: Multifield with the following fields:
  - **Card Image**: Image upload field
  - **Card Title**: Text field for card title
  - **Card Description**: Textarea for card description
  - **Button Text**: Text field for button text
  - **Button Link**: Path field for button link
  - **Background Style**: Dropdown with options:
    - Yellow (#F2C34E)
    - Yellow to Brown Gradient
    - Yellow to Green Gradient

## Design Tokens

### Colors
- Primary Green: `#257A58`
- Dark Green: `#004122`
- Text Green: `#003017`
- Yellow: `#F2C34E`
- White: `#FFFFFF`
- Background: `#FFF8EB`

### Typography
- Headings: Malik Trial, 800 weight
- Body: Averta Std PE, 400/700 weight

### Spacing
- Section Padding: 48px
- Card Gap: 32px
- Card Padding: 32px
- Text Gap: 8px

### Border Radius
- Cards: 64px
- Images: 80px
- Buttons: 16px

### Layout Specifications
- **Card Width**: Fixed 528px per card (desktop)
- **Image Height**: 264px
- **Button Height**: 40px
- **Card Gap**: 20px between cards (desktop)
- **Container Padding**: 48px top/bottom, 120px left/right
- **Card Rotations**: First card -4deg, middle card 0deg, third card +4deg

### Visual Effects
- **Card Rotations**: Individual card rotations (4deg, 0deg, -4deg) for dynamic layout
- **Image Tilt**: +2deg rotation with blob clip-path
- **Button Tilt**: +1deg rotation for dynamic feel
- **Clip-path**: Organic blob shape for images only
- **Fallback Images**: Gradient backgrounds with text when images fail to load

## Development

### Files Created
- `discoverbibigo.html` - HTL template
- `_cq_dialog/discoverbibigo.xml` - Authoring dialog
- `_cq_editConfig.xml` - Edit configuration
- `.content.xml` - Component definition

### Java Model
- `DiscoverBibigoModel.java` - Sling Model with proper injection strategies
- `DiscoverBibigoModelTest.java` - Unit tests

### Styling
- `_discoverbibigo.scss` - Component styles with responsive design
- Local test file: `ui.frontend/src/main/webpack/static/discoverbibigo/index.html`

## Usage

1. **Add to Page**: Drag the "Discover Bibigo" component from the components browser
2. **Configure Header**: Edit the title, main heading, and description in the Header tab
3. **Configure Cards**: Set the number of cards and add content for each card in the Cards tab
4. **Upload Images**: Add images for each card using the image upload field
5. **Set Links**: Configure button links for each card
6. **Customize Colors**: Use the color picker to set individual card background colors

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)
- Mobile browsers (iOS Safari, Chrome Mobile)

## Accessibility

- Proper heading hierarchy (h2, h3)
- Alt text for images
- Keyboard navigation support
- High contrast color combinations
- Responsive design for all screen sizes

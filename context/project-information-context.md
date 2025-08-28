# ImportantConfigurations

> **Note:** For all front-end code generation (HTML, CSS, JavaScript), you must follow the rules defined in [frontend-rules.md](./frontend-rules.md).

## use the following values when creating any component
Project Name: mysite
Sling model package prefix in Java: com.mysite.core.models
Clientlib categories: mysite.site
Component group name: My Sample Site - Content

# Project Structure:
Important folder structure of the project is as follows; do read all the contents under these folders before creating any component:
mysite
├── core (all the java classes to be created under this folder)
├── ui.apps   
    ├── src\main\content\jcr_root\apps\mysite\components (Sightly code, component defnition xml file, component dialog xml file, cq:editconfig file to be created under this folder)
├── ui.frontend
    ├── src\main\webpack\components (css and js files for component to be created under this folder; naming convention should be <component-name>.css and <component-name>.js;)
├── ui.config (Environment specific config files to be created under this folder)
├── pom.xml (all the dependencies to be created under this folder)

# Additional Development Steps:
- **Always generate an `index.html` file** for each new component in the frontend components directory. This file should allow local testing of the component without requiring an AEM environment.
- **localHTMLFolder** Generated `index.html` location will be `ui.frontend/src/main/webpack/static/<folder name>/<component-name>/index.html`
- **Every `index.html` in the localHTMLFolder must include the following scripts and styles:**
  - `/clientlib-base/css/base.css`
  - `/clientlib-dependencies/css/dependencies.css`
  - `/clientlib-dependencies/js/dependencies.js`
  - `/clientlib-base/js/base.js`
- **Ensure the HTML structure is identical** between the Sightly (HTL) template and the generated `index.html` test file. This guarantees consistency in appearance and behavior between local development and AEM deployment.


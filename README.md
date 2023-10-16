# <span style=" font-family=Virgil, Segoe UI Emoji; font-size:90px; ">📖</span> Book-Library
Unsere Buchverwaltungs-App ist die perfekte Lösung, um deine persönliche Bibliothek zu organisieren und den Überblick über deine Lieblingsbücher zu behalten.  
Mit dieser benutzerfreundlichen Anwendung kannst du mühelos all deine Bücher erfassen, verwalten und entdecken.  
Verpasse nie wieder den Titel eines Buches oder den Autor, den du gerne lesen möchtest.  
Unsere Buchverwaltungs-App macht es einfach, deine Leidenschaft für Bücher zu genießen und eine gut organisierte Bibliothek zu pflegen.  
Entdecke, lese und genieße Bücher auf eine neue Art und Weise - mit unserer Buchverwaltungs-App!

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=coverage)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)  
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)  
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)  
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=bugs)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=neuefische_ffm-java-23-1-team-project-3-backend&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=neuefische_ffm-java-23-1-team-project-3-backend)

## Configuration
### Environment Variables for Run
#### Cloudinary
* `CLOUDINARY_URL`
  * Configuration string for cloudinary with API secret, API key and cloud name
  * Can be empty (but must be defined) to disable usage of Cloudinary
    * URLs meant for downloading an image and uploading it to Cloudinary will be stored directly into database
    * locals files meant uploading to Cloudinary will be converted into data urls
* `CLOUDINARY_FOLDER`
  * folder that will be used for storage inside of Cloudinary
  * to separate images of different run configurations / scenarios
#### Mongo DB
* `MONGO_DB_URI`
  * URL of used MongoDB
* `MONGO_DB_NAME`
  * name of used database on DB server above
  * to separate databases of different run configurations / scenarios
### GitHub Secrets 
* `DOCKERHUB_PASSWORD`
  * used for pushing build docker image to docker in workflow "Deploy to render" (`.github/workflows/deploy.yml`)
* `RENDER_DEPLOY_URL`
  * used for redeployment of docker image in workflow "Deploy to render" (`.github/workflows/deploy.yml`)
* `SONAR_TOKEN`
  * used for code scanning of Sonar Cloud (`.github/workflows/sonar-frontend.yml`, `.github/workflows/sonar-backend.yml`)

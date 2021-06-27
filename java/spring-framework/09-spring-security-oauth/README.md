# Spring security OAuth2 exercise
## Tools setup
1. Create project base folder at `c:\Projects\java-code-base`. We will call this path as `<PROJECT_DIR>` later
   You can change to your expected folder base later
   
2. Install necessary tools for development into `<PROJECT_DIR>\tools`
    - Download the tools from the following links : 
        1. Maven latest version: https://maven.apache.org/download.cgi
        2. OpenJDK 11: https://ci.elcanet.local/artifactory/prj_sso_generic_public/jdk/adoptopenjdk/11/windows/
		   Other versions can be downloaded at https://ci.elcanet.local/artifactory/prj_sso_generic_public/jdk/adoptopenjdk/
		   We use the OpenJDK versions hosted by ELCA (8 & 11) for safety and security. In case your use an older/newer version (e.g. JAVA 12,13 or Oracle JDK) please check and download directly from AdoptOpenJDK page or Oracle page.

3. IntelliJ setup
	- Make sure `File > Project Structure ... > Project Settings > Project` set Project SDK & language to the expected JDK you installed above and Project compiler output set to target folder of maven compiler.
	- Make sure the following plugins are installed:
		+ Sonarlint. Please also make sure that your project links to the Sonar server so that the rules set can be synched.
		+ Lombok (Please note that IntelliJ version `2020.2` is not compatible with version `0.30-2020.1`)
		+ MapStruct Support
	- Make sure that you enable Annotation processing for <strong>ALL the necessary modules</strong> in the project.
	    ![](_static/annotation-processor-support-by-IDE.jpg)
## Common commands in the project
### Build project
`mvn clean install -DskipTests -Pvn-dev`
### Run project
Create Spring boot configuration
- Main class : `vn.elca.codebase.CodeBaseDevModeWebApplication`
- Active profiles: `vn-dev`
![](_static/configuration-to-run-locally.jpg)
## Exercise
1. Use project `client` to setup an OAuth2 client to use GitHub account to login to application (using Authorization code grant type)
2. Use project `authorization-server` and `resource-server` to setup an OAuth server using Password code grant type
    - To test the server, use Postman create the following request to get the access token
    URL: `http://localhost:8081/oauth/token?grant_type=password&username=john&password=12345`
    - Then use the following request to use the token to get the resource
    URL: `http://localhost:8081/oauth/check_token?token=97d2e718-bb8b-47f5-ae49-8d4e039e9f05`
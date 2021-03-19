# Big Brother .CORE automation test framework
#### This tool has been developed for the purpose of Clickatell.

### Main goals:
- API automation testing (REST API services)
- - - -
### Contents:

**1.** Test environment preparation

**2.** The instructions of usage

**3.** Test framework structure

**4.** Additional tools 

**5.** Additional recommendations
- - - -
### Test environment preparation:
**1.**  Install java JDK as a code language (version: 8) [link to download java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- 1.1 set jdk path to 'JAVA_HOME' environment variable
- 1.2 add %JAVA_HOME%\bin to 'Path' environment variable
- - - -
**2.** Install MAVEN as a project builder (version: 3) [link to download maven](http://maven.apache.org/download.cgi)
- 2.1 set maven path to 'MAVEN_HOME' environment variable
- 2.2 add %MAVEN_HOME%\bin to 'Path' environment variable
- - - -
**3.** ```mvn clean install -U -DskipTests=true``` - run this command from the start to ensure that all project dependencies installed and up to date
- - - -
### The instructions of usage:
#### 1. Below script commands allow you to run tests in different ways:
##### All tests :

```mvn clean test -Dsuite='name of your particular suite' -P 'name of your particular profile'```

e.g.: ```mvn clean test -Dsuite=core_api_regression -P uat```

or

e.g.: ```mvn clean test -P uat``` (-Dsuite=core_api_regression set by default)

- - - - 
##### Particular suite:
Currently evaluable suites are: 
- core_api_regression
- core_api_smoke
- core_transactions_vendorsandclients_success
- core_transactions_only_success


Currently evaluable profiles are (list of appropriate configuration e.g. credentials, different links, etc...):
 - 'qa'
 - 'dev'
 - 'uat'


```mvn clean test -Dsuite='name of your particular suite' -P 'name of your particular profile'```

e.g. ```mvn clean test -Dsuite=core_api_smoke -Puat```
##### Particular suite for a specific test plan:

```mvn clean test -Dsuite='name of your particular suite' -Djira.logging=true -Dbrowser.remote=true -Djira.test.plan='ticket number of test plan in Jira' -P 'name of your particular profile'```

e.g. ```mvn clean test -Dsuite=core_api_smoke -Djira.logging=true -Dbrowser.remote=true -Djira.test.plan=TECH-82628 -Puat```
- - - -
##### Single test:

```mvn clean test -Dtest='package name of test class'.'class name' -P 'name of your particular profile'```

e.g.: ```mvn clean test -Dtest=api.transaction_lookup.TransactionLookupTest```
- - - -
##### Additional run information:
Test framework has ability to run tests remotely using Selenoid and can set tests results into appropriate Jira test plan (to be able to do it we need to specify additional keys in the run script):
Currently evaluable keys: 
- ```-Dbrowser.remote=true``` - enabled remote run (disabled by default locally, enabled by default remotely)
- ```-Djira.logging=true``` - enabled setting results into Jira (disabled by default locally, enabled by default remotely)
- ```-Dbrowser.remote.url=http://localhost:4444/wd/hub``` - in case of Selenoid containers set up locally (by default: [http://10.248.10.46:4445/](http://10.248.10.46:4445/))

e.g.: ```mvn clean test -Dsuite=core_api_smoke -Djira.logging=true -Dbrowser.remote=true -Dbrowser.remote.url=http://localhost:4444/wd/hub -Djira.test.plan=TECH-82628 -Puat```

Selenoid UI interface can be find following: [http://10.248.10.46:8080/#/](http://10.248.10.46:8080/#/)

Selenoid UI could be used for manual test need to test application on different browser versions, currently present the last 5 versions of: chrome, firefox, opera.
- - - -
#### 2. Report generation:
After tests run use Allure report generator: 
- ```mvn allure:report``` - to get it into base project directory (the command generates a package *allure-result* into the based framework directory and allow to lunch report manually via IDE or other tool which can render HTML document)

- ```mvn allure:serve``` - to get it on your localhost (will open automatically in your browser)
- - - -
#### 3. CI: 
As continuous integration tool was chosen GitLab:
- [Repository with framework source code](http://gitlab1a.prod.eu-west-1.aws.clickatell.com/test-automation/bigbrotherautomation_kaizentests)
- [Existing pipelines](http://gitlab1a.prod.eu-west-1.aws.clickatell.com/test-automation/bigbrotherautomation_kaizentests/-/pipelines)
- [Existing jobs](http://gitlab1a.prod.eu-west-1.aws.clickatell.com/test-automation/bigbrotherautomation_kaizentests/-/jobs)
- [Pages feature for getting view of .html report]() - todo

GitLab jobs can be parameterized.
Available variables for parametrization:
- SUITE
- JIRA_TEST_PLAN
- ENVIRONMENT
- - - -
### Test framework structure:
#### 1. Packages structure:
Test framework was build as multi modules project and include such modules:
- - - -
**1.** **main** - module include packages with the main code structure:

 - **api** - package for work with API services include: 
   - *clients* - package with code, which creates connection and make requests to needed endpoints, e.g. ProductLookup, ReserveAndTransact, etc...
   
   - *controls* - package with code, which except API responses from the clients, can parse them, and transfer needed data in the tests
   
   - *domains* - package include api.models of some objects or JSON files which are used for serialization and deserialization (converting objects and files into java objects and vise versa)
        - *repositories* - realization of previous abstract domain api.models
        - *models* - models
    
 - **db** - package for work with DB layer: 
   - *clients* - todo
   - *custom queries* - todo
   - *engine* - package with specific classes which are like a api.models of some application small objects at ones, need for some searching, filtering, etc..., to avoid code duplication
   - *entities* - todo
   - *enums* - todo
   - *properties* - todo
 
 - **util** - package include packages with the util classes/static helpers
- - - -
**2.** **test** - module include packages with the automation tests structure:
 - **.core**
   - api
   - db

- - - -
**3.** **pom.xml** - a *Project Object Model* or *POM* is the fundamental unit of work in Maven. It is an XML file that contains information about the project and configuration details used by Maven to build the project. It contains default values for most projects
- - - -
**4.** **.gitignore** - It's a list of files you want git to ignore in your work directory, file specifies intentionally untracked files that Git should ignore. Files already tracked by Git are not affected
- - - -
**5.** **README.md** - file contains information about other files in a directory, otherwise it is the automation project documentation in our case
- - - -
#### 2. Libraries using:
**1.** **TestNG** - is a testing framework inspired from JUnit and NUnit but introducing some new functionality that makes it more powerful and easier to use

**2.** **AssertJ** - is a library that provides a fluent interface for writing assertions. Its main goal is to improve test code readability and make maintenance of tests easier

**3.** **Log4j** - is a reliable, fast and flexible logging framework

**4.** **Selenium** - is a free (open source) automated testing suite for web applications across different browsers and platforms. Selenium is not just a single tool but a suite of software's, each catering to different testing needs of an organization

**5.** **Lombok** - is a java library that automatically plugs into your editor and build tools, spicing up your java. Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more

**6.** **Allure** - is a flexible lightweight multi-language test report tool that not only shows a very concise representation of what have been tested in a neat web report form, but allows everyone participating in the development process to extract maximum of useful information from everyday execution of tests

**7.** **RestAssured** - is a library that provides a domain-specific language (DSL) for writing powerful, maintainable tests for RESTful APIs

**8.** **Jackson** -  is a high-performance JSON processor for Java. Its developers extol the combination of fast, correct, lightweight, and ergonomic attributes of the library. Jackson provides many ways of working including simple POJO converted to/from JSON for simple cases. Jackson provides a set of annotations for mapping too

**9.** **JavaFaker** - is a library that can be used to generate a wide array of real-looking data from addresses to popular culture references

**10.** **Hibernate** - is an object-relational mapping tool for the Java programming language. It provides a framework for mapping an object-oriented domain model to a relational database

**11.** **JPA** - the Java ORM standard for storing, accessing, and managing Java objects in a relational database

**12.** **Atlassian API client** - is a library that can be used to integrate the test project and Jira task/bug tracker system
- - - -
### Additional tools which need to be installed:
- IDE, we recommend to use IntelliJ IDEA (free version: *Community*) for tests development needs [link to download and install](https://www.jetbrains.com/idea/download/#section=windows)

- Lombok Plugin (plugin needs to be installed, annotation processing needs tobe launched)

- Markdown support (plugin needs to get a proper *Readme.md*/documentation file view)
- - - -
### Additional information:
- [API documentation (swagger):](http://dev.swagger-api.clickatelllabs.com/?urls.primaryName=Dev#/)
- [Selenoid Documentation](https://aerokube.com/selenoid/latest/)
- - - -
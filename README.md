# Big Brother is Watching



## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Java 1.8

IDE of your choice


### Cloning


How to clone the repository 

```
git clone http://gitlab1a.prod.eu-west-1.aws.clickatell.com/test-automation/bigbrotherautomation.git
```


## Running the tests
How to run test per environment 

### QA

MVN command for QA

```
mvn clean test -P qa
```

### dev

MVN command for QA

```
mvn clean test -P dev
```


## Built With

* [RestAssured](http://rest-assured.io/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Selenium](https://www.seleniumhq.org/) - Webdriver management
* [Selenide](https://selenide.org/) - Selenium wrapped to handle threads
* [TestNG](https://testng.org/doc/documentation-main.html) - Test Wrapper
* [klov](https://https://extentreports.com/docs/klov/) - Test Wrapper

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

COMING SOON!!

## Authors

* **Adam Bethlehem** - *Initial work/Technical Lead* 
* **Juan-Claude Botha** - *Main technical Contributor* - [PurpleBooth](https://github.com/PurpleBooth)



## License

This project is property of Clickatekk

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc

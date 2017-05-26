# Gutenberg

[![Build Status](https://travis-ci.org/ERPedersen/Gutenberg.svg?branch=master)](https://travis-ci.org/ERPedersen/Gutenberg)

___________________________________________________________

This project covers the semester courses database and testing.
The project produced here use the Gutenberg project data.

The project have been Created by 
- Rune V. Zimsen
- Dennis Rønnebæk
- Emil R. Pedersen
- Nicolai V. Bonderup 
- Ebbe V. Nielsen.

The assignment for the database course project can be found [here](https://github.com/HelgeCPH/db_course_nosql/blob/master/lecture_notes/Project%20Description.ipynb).

To find the documentation for the Database course click [here](https://github.com/ERPedersen/Gutenberg/blob/master/db-documentation.md).

The assignment for testing course project can be found [here](Semester%20project%20Testing.md).

To find the documentation for the Testing course click [here](https://github.com/ERPedersen/Gutenberg/blob/master/reflections.pdf).

To find the github for the frontend of the project click [here](https://github.com/ERPedersen/GutenbergWeb).

To see the project in action click [here](http://zesty.emilrosenius.dk/).

We have used CI with the project [Travis-CI.com](https://travis-ci.org/ERPedersen/Gutenberg).

To see the maven output see with full java documentation click [here](https://erpedersen.github.io/Gutenberg/) 

____________________________________________________________

The project consists of a Mongo database, a MySQL database, a back-end and a front-end. 


#### Back-end
The backend is a three-layer application written in Java 8, which exposes a REST API, that allows interaction with both the MongoDB and SQL database. The back-end uses JUnit4 for unit testing combined with the frameworks Mockito for mocking/stubbing and Hamcrest for matching.

#### Front-end
The front-end is a web-application created with Typescript, HTML and SCSS in Angular 4. The front-end uses Karma to run Jasmine unit-tests in the PhantomJS browser. It also uses protractor to perform e2e tests on the entire system.

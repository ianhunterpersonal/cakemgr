Cake Manager Micro Service (fictitious)
=======================================

A summer intern started on this project but never managed to get it finished.
The developer assured us that some of the above is complete, but at the moment accessing the /cakes endpoint
returns a 404, so getting this working should be the first priority.

Requirements:
* By accessing the root of the server (/) it should be possible to list the cakes currently in the system. This must be presented in an acceptable format for a human to read.

* It must be possible for a human to add a new cake to the server.

* By accessing an alternative endpoint (/cakes) with an appropriate client it must be possible to download a list of
the cakes currently in the system as JSON data.

* The /cakes endpoint must also allow new cakes to be created.

Comments:
* We feel like the software stack used by the original developer is quite outdated, it would be good to migrate the entire application to something more modern.
* Would be good to change the application to implement proper client-server separation via REST API.

Bonus points:
* Tests
* Authentication via OAuth2
* Continuous Integration via any cloud CI system
* Containerisation


Original Project Info
=====================

To run a server locally execute the following command:

`mvn jetty:run`

and access the following URL:

`http://localhost:8282/`

Feel free to change how the project is run, but clear instructions must be given in README
You can use any IDE you like, so long as the project can build and run with Maven or Gradle.

The project loads some pre-defined data in to an in-memory database, which is acceptable for this exercise.  There is
no need to create persistent storage.


Submission
==========

Please provide your version of this project as a git repository (e.g. Github, BitBucket, etc).

Alternatively, you can submit the project as a zip or gzip. Use Google Drive or some other file sharing service to
share it with us.

Please also keep a log of the changes you make as a text file and provide this to us with your submission.

Good luck!

==========================================================================================

Updates to the Project (Ian Hunter 1/6/2021)

Documentation
============
This email constitutes a getting started, and has been appended to README.txt. In addition
logged changes are in a file UPDATES.txt in the root folder.

Download
========
With git installed, git clone https://github.com/ianhunterpersonal/cakemgr.git
shoulddownload the source code into a local folder cakemgr

Build and Test
===========
Ensure maven is installed and available on command line

In downloaded folder, execute

   mvn clean compile test

to build the application and run unit and integration tests.

Running
======
This is a basic Spring Boot application and to run it use maven...

   mvn spring-boot:run

This will start the web server in the application on port 8282

Connecting from client
======================
The REST API for the application is on end point 'cakes' and supports listing the current cakes and adding a new cake.
As an example client, POSTMAN can be used and the following request examples should work...

GET http://localhost:8282/cakes - Lists all cakes in database in JSON format

POST http://localhost:8282/cakes - Adds a new cake to the database.
Example JSON for the Body is...

{ "title" : "NewTitle1", "desc" : "NewDescription", "image" : "url"}

The updated Cake in the database is returned as JSON. The title should be unique within the database, and URL must not be empty

Note - if an error occurs, you get a simple message which can be used to determine the problem.

Outstanding Issue
=================

The following outstanding issues are declared...

1. OAuth not implemented
2. No containerisation
3. No CI attempted.
4. Test coverage is high, but could do with further investigation
















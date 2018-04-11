# Notification #
As I left the Motogymkhana Netherlands Society, that doesn't pay me for the service, I took the server at www.gymcomp.com down. I'm sorry.

# motogymkhana time registration server #

Server for the Moto Gymkhana android app for a national competition.

The server accommodates multiple seasons, countries. It has an api interface for the android app, for managment via the app, and for the UI. It also has a registration page, login page (not entirely finished), user profiles.

The app uses a server at api.gymcomp.com, where race results are stored. You can view the race results at www.gymcomp.com.

The server uses JPA/Hibernate on a Postgres database, it uses Restlet for the api endpoints, Guice for dependency injection.

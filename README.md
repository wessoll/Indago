(In development)

Indago
======

Bot using Selenium for performing actions like clicking and typing in a browser to end up with a condition that a page must match.

How It Should Work?
======

Say for example that you want to know if their is a change in the timetable of your class. Instead of checking every evening you can now use this web application (which uses Selenium at the backend) to do this automatically for you.

You provide some actions in the web interface. Like go to http://xxx.xx, click x, click x, and match x on the page. If that "test" was successful and the page matches your criteria (for example the page contains the name of your class or teacher), it can send you an email to let you know this. Then you can schedule the action to run every hour, every day or whatever you like.

This app distinguishes itself from other "bots" because you can actually provide actions to perform like clicking and sending keys (which allows you to login for example). Instead of only getting the page source and then verify if it matches something.

It uses Tomcat on the server side. Selenium is used to automate the actions in the browser. I first used MySQL for the database side, but then I thought, wouldn't it be nice if I use MongoDB (NoSQL) instead to strengthen my knowledge about it.

Contribute
======

Please feel free to contribute to this project, contact me!

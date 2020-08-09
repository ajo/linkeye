# LinkEye
Linkeye is a URL shortener / click tracking application.
It allows a user to create custom links to any URL, and gain visibility into usage of that link.

## Purpose
Linkeye provides a lightweight easy way of branding and tracking links for your social media, blog, or online store.

## Demo
[A demo](https://linkeye.herokuapp.com/) of the  latest build is hosted on Heroku. The first load may take a 
moment as the Dyno sleeps when not in use. This version is limited in that all links lead to this project to prevent spam,
and the default admin account cannot be modified.

## Usage
Links follow the pattern http://[domain]/links/[path].
If you created a link with the path "google" in the demo then the link would be https://linkeye.herokuapp.com/link/google

# MovieViewer

![Home Demo](https://github.com/ryanzhou7/MovieViewer/tree/master/app/media/home.gif?raw=true)

To run, sign up for https://www.themoviedb.org/ account and get new API key. In the gradle.properties's project properties file save

THEMOVIEDB_API_KEY="YOURKEY"

The default config of the app gradle build has

buildConfigField("String", "API_KEY", THEMOVIEDB_API_KEY)

which will take the api key and replace it in the project.
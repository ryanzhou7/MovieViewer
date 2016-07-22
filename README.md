# MovieViewer


## What's this? 
<img src="https://raw.githubusercontent.com/ryanzhou7/MovieViewer/master/app/media/home.gif" width=300>

MovieViewer allows the user to see the most popular or top rated movies and save them as a favorite or for future watching. Clicking on a movie additionally shows plot synopsis, user rating, and trailers (which the user can watch through Youtube).

## To Run 

Sign up for https://www.themoviedb.org/ account and get new API key. In the gradle.properties's project properties file save

THEMOVIEDB_API_KEY="YOURKEY"

The default config of the app gradle build has

buildConfigField("String", "API_KEY", THEMOVIEDB_API_KEY)

which will take the api key and replace it in the android project.

### Project 2 of the 
### Android Developer Nanodegree by Google Program
[![udacity][1]][2]

[1]: ./app/media/android_developer_udacity_logo
[2]: https://www.udacity.com/course/android-developer-nanodegree--nd801

## Libraries
* [Butter Knife](https://github.com/JakeWharton/butterknife)
* [Google GSON](https://github.com/google/gson)
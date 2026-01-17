# TubeKeeper
![logo](/resources/images/logoConFondo.png)

TubeKeeper is the best project we have ever done (it is the first one).

It consists of an app that is able to download your videos or playlists from Youtube to your preferred local destination folder. It is able to download them at your desired resolution and it can even download multiple videos/playlists at the same time (I know, impressive).

## How does it even open?

For this application we used Java 1.21 and Gradle to build it. The GUI has been programmed in Java Swing (which was quite the same as medieval torture). We used it as the project was for a university subject and we were kind of forced to do it.

So the backend was a pretty horrible experience as the subject asked us to use JDBC and some recursive functions (and we all had a total of 0 experience in that). 
For the downloads we used a thread that is created when you put a valid URL in the main page text box so that the app doesn't explode and crash when navigating different views while downloading your videos. The videos are stored in your preferred location (can be changed in the settings) and also in the Downloads view, which stores the metadata in a DB located in resources/db/tubekeeper.db and is configured in the DB files in the src folder.

Probably you won't be able to see the thumbnail of your videos if you aren't connected to the Internet. That is because we are lazy enough to not have stored the thumbnail data so that the app takes it from the Internet every time it opens (yeah I know, big brain idea). 

## So what can I do with TubeKeeper?

The question is: Is there anything impossible with TubeKeeper? You can even fry an egg for your breakfast if you have enough TubeKeepers opened downloading the extended version from LOTR (https://www.youtube.com/watch?v=UHzF5KnoN20). PD: Warner Bros, I hate you for deleting the video.

Being realistic, you can download and manage your videos/playlists with our app. Although it seems pretty simple and useless, the videos can be searched and ordered easily. The playlists view is also very user friendly, letting the user see their playlists and the videos inside them. (Sorry, we were so lazy that we didn't care about the users and didn't add the possibility to watch the videos from the Playlist view. You can only watch them from the Downloads view).

Don't search for easter eggs, we also were lazy enough to not put any.

## So that's it? A little illegal download manager?

I wouldn't say it that way. It suits better calling it "A solver app that quits you from searching your local videos in the shitty default interface. It even lets you download them without having to open unknown webs that might sell your data on the dark web".

(I mean, if you use Youtube you already have sold your soul to Google but it's okay if you don't want to believe it.)

## Why are you using Swing instead of any other good interface tools?

Next question.

## Aren't you going to give us instructions?

Yeah, enter the TubeKeeper directory and do ./gradlew run. That runs the app and installs like 30 GB of malware (it's a joke, pls use our app).

## Have you done it alone?

NO! WE HAVE A TEAM (students) WORKING (being enslaved) HAPPILY (no) ON IT!

3 people have developed this project (to the date of writing this): @adrianisabal, @bombitron and me, @fragi0.

Of course couldn't not mention the creator of the API Javatube (don't search that word alone in Google): Felipe Ucelli (hope I wrote it right).
You have his links here:
 

    API: https://github.com/felipeucelli/Javatube

    GitHub: https://github.com/felipeucelli


## Okay here you have an easter egg

Are you happy now? Let's go, use the app and you will be truly happy. 
Don't forget to read the documentation in /doc. And if you don't speak Spanish, you can either take lessons or translate it (to your choice).

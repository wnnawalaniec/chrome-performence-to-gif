#What is this?
Simple app to extract screens form Google Chrome performance tool and glue them into gif.

#How to build?
**Windows:**

`gradlew.bat build`

**Linux**

`
chmod +x gradlew
./gradlew build
`

#How to use it?
1. Open Google Chrome
2. Press F12 to open Developer Tools
3. Go to Performance tab
4. Click record button
5. Do some stuff
6. Stop recording
7. Export performance profile to `.json`
8. Run `java -jar build/libs/gif-maker-1.0-SNAPSHOT.jar ~/profile.json`
9. Done, checkout `result.gif`
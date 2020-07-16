## What is this?
Simple app to extract screens form Google Chrome performance tool and glue them into gif.

## How to build?
### Windows

`gradlew.bat build`

### Linux

`
chmod +x gradlew
./gradlew build
`

## How to use it?
1. Open Google Chrome
1. Press F12 to open Developer Tools
1. Go to Performance tab
1. Click record button
1. Do some stuff
1. Stop recording
1. Export performance profile to `.json`
1. Run `java -jar build/libs/gif-maker-1.0-SNAPSHOT.jar ~/profile.json`
1. Done, checkout `result.gif`
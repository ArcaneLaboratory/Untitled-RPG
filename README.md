# UntitledRPG
The beginnings of a top-down 2D RPG game, built in Java using libgdx.

## Instructions for Build and Use

Steps to build and/or run the software:

1. Install Java
2. Clone repo from git
3. Use `gradle build` to get dependencies
4. Use `gradle lwjgl:run` to run the program or `gradle lwlgl:jar` to build a jar

Instructions for using the software:

1. Use WASD to move
2. Move mouse to adjust facing direction
3. Left click to attack enemies

## Development Environment

To recreate the development environment, you need the following software and/or libraries with the specified versions:

* IntelliJ Community Edition
* Java 21
* libgdx and other dependency libraries (install through gradle)

## Useful Websites to Learn More

I found these websites useful in developing this software:

* [libgdx Wiki](https://libgdx.com/wiki/)

## Future Work

The following items I plan to fix, improve, and/or add to this project in the future:

* [ ] Additional Levels
* [ ] Proper Inventory System
* [ ] Better Enemy AI
* [ ] Clean up spaghetti code

#

#### Original gdx-liftoff generated readme preserved below in case I forget how to do things... 

## UntitledRPG

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

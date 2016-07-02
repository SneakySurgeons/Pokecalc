# Pokecalc

[![Build Status](https://travis-ci.org/SneakySurgeons/Pokecalc.svg?branch=master)](https://travis-ci.org/SneakySurgeons/Pokecalc) [![Dependencies](https://www.versioneye.com/user/projects/5777e2b068ee07003cb5d6ba/badge.svg)](https://www.versioneye.com/user/projects/5777e2b068ee07003cb5d6ba) [![license](https://img.shields.io/github/license/SneakySurgeons/pokecalc.svg?maxAge=2592000)](https://github.com/SneakySurgeons/Pokecalc/blob/master/LICENSE)


This repository contains the code for the pokecalc site.

## Getting Started

 - Make sure you have JDK 8 installed
 - Clone the code
 - Open a terminal in the directory of the repository and run `gradlew run`
 - Try it at port 8080!
 - Optional: The project contains IntelliJ IDEA project files, you can use them to open the project with IntelliJ

Starting the server with `gradlew run` will download all dependencies and build the program including all templates and
assets. To load changed resources, you'd have to restart the server. Instead you can use the following properties to
enable Developer Mode:

|Property    |Default Value |Description                                                                    |
|------------|--------------|-------------------------------------------------------------------------------|
|SQLITE_PATH |pokedex.sqlite|The path to the sqlite file                                                    |
|HTTP_PORT   |8080          |The port for the HTTP-server                                                   |
|DEVELOP     |false         |Enables Developer Mode (loads files from local filesystem instead of classpath)|
|DEVELOP_PATH|<none>        |The path where the resources are located                                       |

You can set them like that:

    gradlew run -DDEVELOP=true -DDEVELOP_PATH="." -DHTTP_PORT=80

If you want toe save some space, you can also use the command `gradlew develop` which sets up Developer Mode
automatically (and points the DEVELOPER_PATH to the default resource path).

### Database

Pokecalc uses a local copy of the [Veekun](//github.com/veekun/pokedex) SQLite Database. A copy is provided inside the
repository but you might want to create your own database because it's not necessarily up to date.

## Contributing

If you have questions about the project, feel free to open an issue.

Please refer to [CONTRIBUTING](https://github.com/SneakySurgeons/Pokecalc/wiki/Contributing) for notes about
contributions.

## License

See [LICENSE](//github.com/SneakySurgeons/Pokecals/blob/master/LICENSE)

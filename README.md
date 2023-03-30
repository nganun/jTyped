# jTyped

Use Java implants some quick operations through keys typed.

## Build

maven-assembly-plugin

- Add dependency in pom.xml
- Specified the main class enter in pom.xml

```xml

<plugin>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>2.2</version>
    <configuration>
        <archive>
            <manifest>
                <mainClass>com.nganun.Main</mainClass>
            </manifest>
        </archive>
        <descriptorRefs>
            <descriptorRef>
                jar-with-dependencies
            </descriptorRef>
        </descriptorRefs>
    </configuration>
</plugin>
```

- build

```shell
mvn assembly:assembly
```

## Run jar

- start

```cmd
@echo off
start javaw -jar xxx.jar
exit
```

- kill

```shell
tasklist | grep javaw
taskkill /f /im javaw.exe
```

## TODO

- Hotstring

  - [x] Execute command by hostring
  - [x] Restart the main function
  - [ ] Read the properties live time

- Dict
  - [ ] When typed `;;word` and typed `Enter`, append the word to Excel file.

## Functions

### Main process
  - `;stop` : stop the key listener
  - `;start` : start the key listener
  - `;restart` : restart the key listener
  - `;exit` : close the application

### Hotstinrg process

the blow all need be config in `.properties` file, if this file not exist, this application will create it under `user.home` directory.

- Hotstring

```properties
;hw=Hello World!
```
typed `;hw`, then print `Hello World!`

- Command by System

```properties
;np=cmd /c notepad
```
typed `;np`, then open the notepad

- Command by Java

```properties
;dd=
```

typed `;dd`, then print `yyyyMMdd` format string, need implemented in Java



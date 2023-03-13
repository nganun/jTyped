# jHotstring
Use Java implants the hotstring of AutoHotKey

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
                <mainClass>com.nganun.hotstring.HotstringListenercom.nganun.hotstring.HotstringListener</mainClass>
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

- [ ] Execute command by hostring
- [ ] Restart the main function
- [ ] Read the properties live time




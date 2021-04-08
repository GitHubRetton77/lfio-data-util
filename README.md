# lfio-data-util
Lightfeature IO Kanban Data Backup and Restore Code Challenge

Github Code URL: https://github.com/GitHubRetton77/lfio-data-util 

## Installation Dependencies

##### Java (programming language)

> Installation guide homepage: https://www.oracle.com/java/technologies/javase-downloads.html  
> Required version: 1.8 and up

##### Maven (project management)

> Installation guide: https://maven.apache.org/install.html 
> Version: I dont' think Maven's version matter but the version in my computer is 3.6.3

## Run Instructions (when all dependencies are installed)
1. From a fresh clone from Github, on a terminal, navigate to the root of the project and get code dependencies and build the project using Maven by running  `mvn -U clean package`
2. `mvn -U clean package` will generate the machine code in form of classes inside a directory named `target` on the root of the project
3. Run the (kanban service)[https://github.com/GitHubRetton77/lfio-service] because this project will access the same in memory database.
4. The encryption config cipher `Shift` and backup file path `Loads` are compiled together with the classes. To change those, look for file `cipher-configuration.json`. I've preconfigured `Loads` to look for the data backup file on the same directory where the program is executed for convenience. 
Change it accordingly, if wanted. The backup data file will also be created on the same directory where the program is ran.   
5. When kanban service is running and desire configs are set in this project, time to run it. One of the machine code file inside `target` is `lfio-data-util-1.0.0.jar`. Again on a terminal, run the kanban service with `java -jar JAR_LOCATION` replacing `JAR_LOCATION` with the location where `lfio-data-util-1.0.0.jar` is created in your computer like ` 
java -jar /Users/user/code/lfio-data-util-1.0.0.jar`
6. This tool runs quick when everything is fine. There should be start up logs and then shut down logs right after. When there are no errors, the process was successful. On first run, the file set on `Loads` should be created with the current kanban service data and will be created on the same directory where this program is ran. On subsequent runs, now that the `Loads` file exist, the kanban service database will be restored to the previous stored state.



## Notes
- Installations of dependent stuff maybe tricky due to versions and compatibility. I'm not using most up to date versions of things because I did not need to update. States of our computers could affect at times. Please let me know if these instructions are not working smoothly.
- I've tried deleting my local copy of the project and started from scatch by following the steps I've written here and worked for me.
- I believe I implemented all of the required specifications.
- This is working with the other server component which is the kanban (service/API)[https://github.com/GitHubRetton77/lfio-service]


# Book Library Application

Sample Book library application.
Application provides REST API that allows:
- Searching book by ISBN/ID
- Searching books by categories they belong to
- Listing authors with their average rating

## Application Framework
- Spring

## Testing Libraries
- JUnit5
- Mockito
- RestAssured

## Design Patterns
- Builder - Helps us in building objects. The pattern provides fluent API for building objects and allows us omitting optional parameters.
- Template method - Allows us build a skeleton of a method in a base class and let subclasses override some steps in the main method without changing the overall structure of the method.
- Strategy - Lets us define a family of algorithms for a given problem, put each algorithm in separate class and make them interchangeable

## Building

To build the project use following command

```
mvn clean install
```

## Running

After building the application run one of the following commands to start it (Application runs on port: 8080) 

#### Default

```
java -jar target/Book-Library-1.0-SNAPSHOT.jar
```
By default application starts with empty repositories.

#### Static Json File

```
java -jar target/Book-Library-1.0-SNAPSHOT.jar --jsonFile path/file.json
```
When using ```--jsonFile path/file.json``` option application gets data from provided json file

#### Google Books API

```
java -jar target/Book-Library-1.0-SNAPSHOT.jar --googleBooks
```
When using ```--googleBooks``` option application gets data from google books API

# Database course semester project

## Gutenberg

This is our documentation to the database course semester project at Copenhagen Business Academy. We have made this project in combination with the Testing course. The documentation for the testing course and answers can be found [here (testing course documentation)](Testing%20course%20documentation.md), a link to the main repository can be found [here(main repo)](https://github.com/ERPedersen/Gutenberg). 

### Context of this document.

- Which database engines are used.
- Structurse of application
- How data is modeled.
- How data is imported.
- Steps taken for optimizing the databases.
- Behavior of query test set.
- Recommendation for database.
- Conclusion.

___________________________

## Which database engines are used?

We have chosen to work with a relational- (MySQL) and a document- (MongoDB) database. The databases are hosted at digital ocean. We chose the MySQL and MongoDB since they where the ones that we thought were best supported and that we could best do the tasks with. Furthermore the data given matches a relational model and therefore MySQL should be the best choice, except for the querying of the lat/long where MongoDB comes with a build in functionality to calculate points within a geographical area.

## Application structure

Here you can see a diagram that shows the application structure.
![Application overview](http://i.imgur.com/Z6Z6Bzg.png)

**Our application resides on a Digital Ocean droplet, which serves the following applications:**

- NGINX 1.10.0
- Tomcat 8.5.15
- MySQL 5.7.18
- MongoDB 3.2.13

NGINX serves our Angular front end application on (http://zesty.emilrosenius.dk)[http://zesty.emilrosenius.dk], and our "CDN" on (http://cdn.zesty.emilrosenius.dk)[http://cdn.zesty.emilrosenius.dk]. The "CDN" hosts the text files of the books, which can be downloaded through the front end application.

Tomcat serves our Java Application which exposes a RESTful API on (http://zesty.emilrosenius.dk:8080)[http://zesty.emilrosenius.dk:8080]. The Java Application uses MySQL and MongoDB to store and query the data.

## Data modeling

Our relational database is modeled after the following:

### Conceptual model

![Conceptual ER diagram](http://i.imgur.com/B7sqd9b.png)

### ER model
![ERModel](http://i.imgur.com/ohqxzbQ.png)

In the above models we can see that author to book has a many to many relation, and location to book has a many to many relation.

### Relational schema
![Relational schema](http://i.imgur.com/fGykjcN.png)

Using the E/R Diagram we created the database structure shown in the relational schema above. As we have two many-to-many relations we had to add two junction tables. The attributes in the junction table consist of a composite primary key from the two primary keys from the tables with the many-to-many relation, and two foreign keys pointing to previously mentioned primary keys.

Our MongoDB database doesn't use a schema but is divided in collections. We only have one collection where all data is stored in each document. The documents has the following data:

```json
{
  "_id": ObjectID("0b8c73c27c83"),
  "UID": 123123124,
  "title": "The Declaration of Independence",
  "text": "123123124.txt",
  "author": [ 
    {
      "UID": 12312412, 
      "name": "John Wick"
    },
    // ...
  ],
  "locations": [ 
      {
        "UID": 123912312,
        "name": "London",
        "loc": {
          "type": "POINT", 
          "coordinates":  [ 12.823498234, 34.382423894 ] 
      }
  ]
}
```



## Data import

We created a Java application that scraped the data from the books, checked for each word and added them to a hash set and then checked which cities was mentioned in that book. Afterwards we generated the data needed and stored it in a json formatted text documents. Then we could import the data into the MySQL and MongoDB databases. The reason for separating these task were partly to reduce the load on the Digital Ocean VM, as scraping in combination with insertion in each database would demand to many resources, and partly because the imports is quicker when using optimized import functions for each database. Below are the commands used for importing.

*Command used to import the json files into the MySQL database:*

> 'LOAD DATA LOCAL INFILE '**file-path**' INTO TABLE **table-name**;'

*Command used to make the MongoDB collection:*

> 'nicolai@gutenberg-e0be8:~$ mongoimport --db gutenberg --colletion books --file /json/full/booksJsonFull.txt --jsonArray --drop'

## Database optimization

For optimizing the query results on the MySQL database, besides having indexes on the primary keys we added a FULLTEXT index on book title, author name and city name. This was to speed up querying on the common attributes used in the four different queries and to be used with our fuzzy searches.

On the MongoDB we added indexes on all string attributes. This was because it was easisets to use a command that would add indexes on all string attributes, and we needed indexes on a lot of them. This is the command for adding indexes on all text attributes in the collection. For the lat/long querying we have added a geospatial index on a loc object which contains coordinates which consists of latitude and longitude, and type 'point' that the geospatial index uses to search for locations.

```javascript
db.collection.createIndex( { "$**": "text" } )
```

## Query Test Set Behaviour

We used this [post](https://stackoverflow.com/questions/8994718/mysql-longitude-and-latitude-query-for-other-rows-within-x-mile-radius) on Stack Overflow as inspiration to do our geolocation query in MySQL.

We used this [post](https://docs.mongodb.com/manual/reference/operator/aggregation/geoNear/) from MongoDB documentation to our geolocation query in MongoDB.

We have made five searches on the four given endpoints and collected the performance data and listed it up here:

|        Query        |   DB    | Avg. 100% (ms) | Avg. Gaussian (ms) |
| :-----------------: | :-----: | :------------: | :----------------: |
|  Books from author  |  MySQL  |   **28,68**    |       **28**       |
|  Books from author  | MongoDB |     52,76      |         52         |
|   Books from city   |  MySQL  |   **86,08**    |      **273**       |
|   Books from city   | MongoDB |     449,72     |        767         |
| Books from lat long |  MySQL  |      3950      |        4160        |
| Books from lat long | MongoDB |   **859,52**   |      **1560**      |
| Locations from book |  MySQL  |   **34,96**    |       **51**       |
| Locations from book | MongoDB |      45,2      |         63         |

## Database recommendations

From the results of our rest API test we can see that MySQL outperforms MongoDB on all accounts except for the lat/long query. This is probably because of the build in functionality for querying geospatially indexed lat/longs in MongoDB whereas the MySQL does not include this kind of functionality, instead we had to use a mathematical calculation on the querying which is not optimized.

After working with the Gutenberg data chunk in 3-4 weeks, we found that it was easiest to write the database queries in MongoDB, but as soon as it came to backend operations in Java on SQL or Mongo, creation of endpoints, the SQL was far easier to make code for, and the SQL expressions, or calls, are directly translatable.

[Here we see the queries for MySQL implemented in JAVA](https://github.com/ERPedersen/Gutenberg/blob/master/src/main/java/main/dao/BookDAOMySQL.java)

[Here we see the queries for MongoDB implemented in JAVA](https://github.com/ERPedersen/Gutenberg/blob/master/src/main/java/main/dao/BookDAOMongo.java)

Another point to chose the SQL DB is it would be much easier to find people who have a knowledge in SQL, rather than the document database, MongoDB. The SQL database also force the developer, by being strongly typed, to think about how to load data and how to map it, whereas the MongoDB is more fluent in the data structure. We found that MongoDB in general has given us quite a few problems. Viewing back on the decision to use MongoDB we would have preferred to use a graph database, since a graph database would have been better suited to deal with the data we were given. 

## Conclusion

The data we had to work with was primarily suited for a relational database. This was also apparent from the test results. So in this particular case the MySQL database would be the better choice over MongoDB. 

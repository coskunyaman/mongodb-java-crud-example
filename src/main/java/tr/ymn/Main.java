package tr.ymn;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import tr.ymn.mongodbcrud.CollectionOperation;
import tr.ymn.mongodbcrud.UserOperation;

public class Main {
    public static String databaseName = "testdb";
    public static String collectionName = "testcollection";

    public static void main(String[] args) {

        System.out.println("Connecting mongodb");

        MongoClient mongoClient = MongoClients.create("mongodb://root:rootpwd@localhost:27017/" + "?authSource=admin");

        /**
         *  UserOperation
         */
        UserOperation userOperation = new UserOperation(mongoClient);
        /**
         * Create User
         */
        userOperation.createUser(databaseName, "testuser", "testpwd");


        /**
         * CollectionOperation
         */
        CollectionOperation collectionOperation = new CollectionOperation(mongoClient);

        /**
         * Add Data
         */
        Document document = new Document("playerName", "Ronaldo")
                .append("age", 40)
                .append("nationality", "Filipino")
                .append("JerseyNumber", 23)
                .append("position", "Guard");
        collectionOperation.addDatabaseCollectionAndDocument(databaseName, collectionName, document);

        /**
         * Read Data
         */
        Document documentReaded = collectionOperation.getData(databaseName, collectionName);
        if (documentReaded != null) {
            System.out.println("Readed data : " + documentReaded.toJson());
        } else {
            System.out.println("No results found.");
        }

        /**
         * Update Data
         */
        collectionOperation.updateData(databaseName, collectionName);

        /**
         * Delete Data
         */
        collectionOperation.deleteData(databaseName, collectionName);

        /**
         *  Drop Collection
         */
        collectionOperation.dropCollection(databaseName, collectionName);

        /**
         * Drop User
         */
        userOperation.dropUser(databaseName, "testuser");
        System.exit(1);
    }
}
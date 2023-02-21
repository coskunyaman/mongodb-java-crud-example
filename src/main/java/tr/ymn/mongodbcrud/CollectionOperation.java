package tr.ymn.mongodbcrud;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class CollectionOperation {
    private MongoClient mongoClient;

    public CollectionOperation(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public void addDatabaseCollectionAndDocument(String databaseName, String collectionName, Document doc) {
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        db.getCollection(collectionName).insertOne(doc);
        System.out.println("Add Database, Collection And Data Success : " + collectionName);
    }

    public Document getData(String databaseName, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        Bson projectionFields = Projections.fields(
                Projections.include("id", "playerName", "age", "nationality", "JerseyNumber", "position"),
                Projections.excludeId());
        Document document = collection.find(eq("playerName", "Ronaldo"))
                .projection(projectionFields)
                .sort(Sorts.descending("playerName"))
                .first();
        return document;

    }

    public void updateData(String databaseName, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = db.getCollection(collectionName);

        Document query = new Document().append("playerName",  "Ronaldo");

        Bson updates = Updates.combine(
                Updates.set("age", 80)
        );
        UpdateOptions options = new UpdateOptions().upsert(true);
        try {
            UpdateResult result = collection.updateOne( query, updates, options);
            System.out.println("Modified document count: " + result.getModifiedCount());
            System.out.println("Upserted id: " + result.getUpsertedId());
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }
    }
    public void deleteData(String databaseName, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = db.getCollection(collectionName);

            Bson query = eq("playerName",  "Ronaldo");
            try {
                DeleteResult result = collection.deleteOne(query);
                System.out.println("Deleted document count: " + result.getDeletedCount());
            } catch (MongoException me) {
                System.err.println("Unable to delete due to an error: " + me);
            }
    }
    public void dropCollection(String databaseName, String collectionName) {
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        collection.drop();
        System.out.println("Collection dropped successfully");
    }
}

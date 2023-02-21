package tr.ymn.mongodbcrud;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.Collections;

public class UserOperation {
    private MongoClient mongoClient;

    public UserOperation(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    /**
     * Create User
     *
     * @param databaseName Database Name
     * @param userName     User Name
     * @param passwd       Password
     */
    public void createUser(String databaseName, String userName, String passwd) {

        MongoDatabase db = mongoClient.getDatabase(databaseName);

        BasicDBObject command = new BasicDBObject("createUser", userName)
                .append("pwd", passwd)
                .append("roles", Collections.singletonList(new BasicDBObject("role", "dbOwner").append("db", databaseName)));
        db.runCommand(command);

        System.out.println("Create User Success");
    }

    /**
     * Drop User
     *
     * @param databaseName Database Name
     * @param userName     User Name
     */
    public void dropUser(String databaseName, String userName) {
        MongoDatabase db = mongoClient.getDatabase(databaseName);

        BasicDBObject command = new BasicDBObject("dropUser", userName);
        db.runCommand(command);

        System.out.println("Drop User Success");
    }

}

package designpattern.creational.singleton;

class Database {
    private static int id;
    private static Database instance;

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
            instance.id = 100;
        }
        return instance;
    }

    public void query(String sql) {
        System.out.println("executing SQL query ( " + sql + " ) on Database: " + this.instance.id);
    }
}

public class SingletonPattern {
    public static void demo() {
        Database DB1 = Database.getInstance();
        DB1.query("SELECT...");

        Database DB2 = Database.getInstance();
        DB2.query("UPDATE...");
    }
}

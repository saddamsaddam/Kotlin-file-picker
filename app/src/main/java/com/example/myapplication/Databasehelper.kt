import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mydatabase3.db"
        private const val DATABASE_VERSION = 1
    }


    override fun onCreate(db: SQLiteDatabase) {
        try {
            // Create your database tables here
            db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT)")
        } catch (e: Exception) {
            // Log any exceptions that occur during table creation
            Log.e("DatabaseHelper", "Error creating table: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema upgrades here
    }

    // Function to insert a new user into the "users" table
    fun insertUser(name: String) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("name", name)

        // Insert the data into the "users" table
        db.insert("users", null, values)

        // Close the database connection
        db.close()
    }
}

// Example of how to use the DatabaseHelper to insert a user
fun insertUserData(context: Context, userName: String) {
    val dbHelper = DatabaseHelper(context)

    // Insert a new user into the "users" table
    dbHelper.insertUser(userName)
}
// Function to check if a table exists in the database
fun isTableExists(context: Context, tableName: String): Boolean {
    val dbHelper = DatabaseHelper(context) // Replace with your database helper class
    val db: SQLiteDatabase = dbHelper.readableDatabase

    val query = "SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'"
    val cursor: Cursor = db.rawQuery(query, null)

    val tableExists = cursor.moveToFirst()
    cursor.close()
    db.close()

    return tableExists
}
// Function to fetch all user names from the "users" table
fun getAllUserNames(context: Context): List<String> {
    val dbHelper = DatabaseHelper(context)
    val db = dbHelper.readableDatabase
    val userNames = mutableListOf<String>()

    val query = "SELECT name FROM users"
    val cursor = db.rawQuery(query, null)

    while (cursor.moveToNext()) {
        val name = cursor.getString(cursor.getColumnIndex("name"))
        userNames.add(name)
    }

    cursor.close()
    db.close()

    return userNames
}

// Example of how to use the DatabaseHelper to create the database and insert data


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mydatabase5.db"
        private const val DATABASE_VERSION = 1
    }


    override fun onCreate(db: SQLiteDatabase) {
        try {
            // Create your database tables here
            db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT)")

            // Create the new table with "name" and "file" columns
            db.execSQL("CREATE TABLE IF NOT EXISTS filetable (id INTEGER PRIMARY KEY, name TEXT, file BLOB)")
        } catch (e: Exception) {
            // Log any exceptions that occur during table creation
            Log.e("DatabaseHelper", "Error creating table: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema upgrades here
    }

    // Function to insert a new user into the "users" table
    fun insertUser(name: String, fileData: ByteArray) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("name", name)
        values.put("file", fileData)

        // Insert the data into the "filetable"
        db.insert("filetable", null, values)

        // Close the database connection
        db.close()
    }
    fun getAllDataFromFileTable(): List<Pair<String, ByteArray>> {
        val db = readableDatabase
        val data = mutableListOf<Pair<String, ByteArray>>()

        val query = "SELECT name, file FROM filetable"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val fileData = cursor.getBlob(cursor.getColumnIndex("file"))

            data.add(Pair(name, fileData))
        }

        cursor.close()
        db.close()

        return data
    }
}

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
// Function to fetch all data (name and file) from the "filetable" table


// Example of how to use the DatabaseHelper to create the database and insert data


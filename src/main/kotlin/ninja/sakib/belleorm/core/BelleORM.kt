package ninja.sakib.belleorm.core

import ninja.sakib.belleorm.callbacks.Callback
import java.sql.Connection
import java.sql.DriverManager

/**
 * := Coded with love by Sakib Sami on 9/27/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */


/**
 * Base class of BelleORM API
 * It handles database connection
 * and serves queries
 */
class BelleORM {
    private var databaseName: String? = null    // Name of the database
    private var databasePath: String? = null    // Path to database
    private var connection: Connection? = null  // Connection to database

    /**
     * Constructor to initialize class with database name
     * In this case user home path will be used as database path
     * @param databaseName Name of the database to connect
     */
    constructor(databaseName: String) {
        this.databaseName = databaseName

        connect()
    }

    /**
     * Constructor to initialize class with database name & database path
     * @param databaseName Name of the database to connect
     * @param databasePath Path to database
     */
    constructor(databaseName: String, databasePath: String) {
        this.databaseName = databaseName
        this.databasePath = databasePath

        connect()
    }

    /**
     * Method to initialize connection to database
     */
    private fun connect() {
        if (databaseName!!.endsWith(".db").not())
            databaseName = "$databaseName.db"

        if (databasePath == null || databasePath!!.isEmpty())
            databasePath = getUserHomeDirectory()

        val dbUrl: String
        if (isAndroidPlatform()) {
            Class.forName("org.sqldroid.SQLDroidDriver")
            dbUrl = "jdbc:sqldroid:$databasePath/$databaseName"
        } else {
            Class.forName("org.sqlite.JDBC")
            dbUrl = "jdbc:sqlite:$databasePath/$databaseName"
        }

        connection = DriverManager.getConnection(dbUrl)
    }

    /**
     * Method to save data
     * @param clazz Class object which data want to add
     */
    fun save(clazz: Any): Boolean {
        return BelleORMQuery(connection!!).save(clazz)
    }

    /**
     * Method to save data with callback
     * @param clazz Class object which data want to add
     * @param callback will be fired on task complete
     */
    fun save(clazz: Any, callback: Callback) {
        BelleORMQuery(connection!!).save(clazz, callback)
    }

    /**
     * Method to update value
     * @param clazz value of which class going to update
     * @param updater holds update condition and values
     */
    fun update(clazz: Any, updater: BelleORMUpdater): Boolean {
        return BelleORMQuery(connection!!).update(clazz, updater)
    }

    /**
     * Method to update value with callback async
     * @param clazz value of which class going to update
     * @param updater holds update condition and values
     * @param callback will be fired on task complete
     */
    fun update(clazz: Any, updater: BelleORMUpdater, callback: Callback) {
        BelleORMQuery(connection!!).update(clazz, updater, callback)
    }

    /**
     * Method to get data of specific clazz
     * @param clazz which class of data will provide
     * @return MutableList of type Any
     */
    fun find(clazz: Any): MutableList<Any> {
        return BelleORMQuery(connection!!).get(clazz)
    }

    /**
     * Method to get data of specific clazz based of provided condition
     * @param clazz which class of data will provide
     * @param condition condition will be used to get data
     * @return MutableList of type Any
     */
    fun find(clazz: Any, condition: BelleORMCondition): MutableList<Any> {
        return BelleORMQuery(connection!!).get(clazz, condition)
    }

    /**
     * Method to delete data of specific clazz
     * @param clazz which type of data will be deleted
     */
    fun delete(clazz: Any): Boolean {
        return BelleORMQuery(connection!!).delete(clazz)
    }

    /**
     * Method to delete data of specific clazz based on condition
     * @param clazz which type of data will be deleted
     * @param condition will be used to delete data
     */
    fun delete(clazz: Any, condition: BelleORMCondition): Boolean {
        return BelleORMQuery(connection!!).delete(clazz, condition)
    }

    /**
     * Method to delete data of specific clazz with callback
     * @param clazz which type of data will be deleted
     * @param callback will be fired on task complete
     */
    fun delete(clazz: Any, callback: Callback) {
        BelleORMQuery(connection!!).delete(clazz, callback)
    }

    /**
     * Method to delete data of specific clazz based on condition with callback
     * @param clazz which type of data will be deleted
     * @param condition will be used to delete data
     * @param callback will be fired on task complete
     */
    fun delete(clazz: Any, condition: BelleORMCondition, callback: Callback) {
        BelleORMQuery(connection!!).delete(clazz, condition, callback)
    }

    /**
     * Method to drop table
     * @param clazz which type of table will be deleted
     */
    fun drop(clazz: Any): Boolean {
        return BelleORMQuery(connection!!).drop(clazz)
    }

    /**
     * Method to drop table with callback
     * @param clazz which type of table will be deleted
     * @param callback will be fired on task complete
     */
    fun drop(clazz: Any, callback: Callback) {
        BelleORMQuery(connection!!).drop(clazz, callback)
    }

    /**
     * Method to get number of objects of a specific type
     * @param clazz which type of data will count
     */
    fun count(clazz: Any): Long {
        return BelleORMQuery(connection!!).count(clazz, null)
    }

    /**
     * Method to get number of objects of a specific type
     * @param clazz which type of data will count
     * @param condition if you want to apply any condition to count or pass null
     */
    fun count(clazz: Any, condition: BelleORMCondition?): Long {
        return BelleORMQuery(connection!!).count(clazz, condition)
    }

    /**
     * Method to close database connection
     */
    fun close() {
        if (connection != null) {
            connection!!.close()
        }
    }
}

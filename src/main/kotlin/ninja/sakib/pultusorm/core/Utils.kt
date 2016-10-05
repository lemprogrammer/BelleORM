package ninja.sakib.pultusorm.core

import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.NotNull
import ninja.sakib.pultusorm.annotations.PrimaryKey
import ninja.sakib.pultusorm.annotations.Unique
import ninja.sakib.pultusorm.exceptions.PultusORMException
import java.lang.reflect.Field
import java.lang.reflect.Type

/**
 * := Coded with love by Sakib Sami on 9/27/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

var isDebugEnabled = true
val objectToJsonConverter: Gson = GsonBuilder().create()

fun log(key: String, value: String) {
    if (isDebugEnabled)
        println("$key :: $value")
}

fun getUserHomeDirectory(): String {
    return System.getProperty("user.home")
}

fun isValidObject(value: Any): Boolean {
    return true
}

fun throwback(msg: String) {
    throw PultusORMException(msg)
}

fun typeToSQL(value: Type): String {
    if (value == Int::class.java)
        return "INTEGER"
    else if (value == String::class.java)
        return "TEXT"
    else if (value == Char::class.java)
        return "varchar"
    else if (value == Double::class.java)
        return ""
    else if (value == Float::class.java)
        return ""
    else return ""
}

fun booleanToSQL(value: Type): Int {
    return 0
}

fun isBoolean(value: Type): Boolean {
    return value == Boolean::class.java
}

fun isString(value: Type): Boolean {
    return value == String::class.java
}

fun isInt(value: Type): Boolean {
    return value == Int::class.java
}

fun isLong(value: Type): Boolean {
    return value == Long::class.java
}

fun isDouble(value: Type): Boolean {
    return value == Double::class.java
}

fun isFloat(value: Type): Boolean {
    return value == Float::class.java
}

fun isChar(value: Type): Boolean {
    return value == Char::class.java
}

fun isPrimaryKey(value: Field): Boolean {
    return value.getDeclaredAnnotation(PrimaryKey::class.java) != null
}

fun isPrimaryKeyEnabled(value: Field): String {
    if (value.getDeclaredAnnotation(PrimaryKey::class.java) != null)
        return "PRIMARY KEY"
    return ""
}

fun processAutoIncrement() {

}

fun isAutoIncrement(value: Field): Boolean {
    return value.getDeclaredAnnotation(AutoIncrement::class.java) != null
}

fun isAutoIncrementEnabled(value: Field): String {
    if (value.getDeclaredAnnotation(AutoIncrement::class.java) != null)
        return "AUTOINCREMENT"
    return ""
}

fun isNotNullEnabled(value: Field): String {
    if (value.getDeclaredAnnotation(NotNull::class.java) != null)
        return "NOT NULL"
    return ""
}

fun isUniqueEnabled(value: Field): String {
    if (value.getDeclaredAnnotation(Unique::class.java) != null)
        return "UNIQUE"
    return ""
}

fun parseClassName(value: Any): String {
    var className = value.toString()
    if (className.indexOf('.') != -1) {
        className = className.substring(className.lastIndexOf('.') + 1)
    }
    return className
}

fun objectAsJson(value: Any): JsonObject {
    return Json.parse(objectToJsonConverter.toJson(value)).asObject()
}

fun jsonAsObject(clazz: Any, value: JsonObject): Any {
    return objectToJsonConverter.fromJson(value.toString(), clazz.javaClass)
}
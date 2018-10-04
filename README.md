# BelleORM [![](https://jitpack.io/v/ninja.sakib/BelleORM.svg)](https://jitpack.io/#ninja.sakib/BelleORM)
A Sqlite ORM library for Kotlin, Java & Android.

Status : Active<br>
Version : v1.8

## Features
Currently implemented:

* Insert
* Retrieve
* Update
* Delete
* Drop

### Usages
In your build file add
##### Gradle
```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
And
```gradle
dependencies {
    implementation 'ninja.sakib:BelleORM:v1.8'
}
```

##### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
	</repository>
</repositories>
```
And
```xml
<dependency>
    <groupId>ninja.sakib</groupId>
    <artifactId>BelleORM</artifactId>
    <version>v1.8</version>
</dependency>
```

In case you need jar download is available [here](https://jitpack.io/ninja/sakib/BelleORM/v1.8/BelleORM-v1.8.jar) .

More option can be found [here](https://jitpack.io/#ninja.sakib/BelleORM/v1.8).

### Examples

##### Open database connection
##### Kotlin
```kotlin
In Kotlin,
val pultusORM: PultusORM = PultusORM("test.db", "/Users/s4kib/")
val pultusORM: PultusORM = PultusORM("test.db")    // DB will take place in user.home directory

In Android,
val appPath: String = getApplicationContext().getFilesDir().getAbsolutePath()  // Output : /data/data/application_package_name/files/
val pultusORM: PultusORM = PultusORM("test.db", appPath)
```
##### Java
```java
In Java,
PultusORM orm = new PultusORM("test.db", "/Users/s4kib/")
PultusORM orm = new PultusORM("test.db", )  // DB will take place in user.home directory

In Android,
String appPath = getApplicationContext().getFilesDir().getAbsolutePath()  // Output : /data/data/application_package_name/files/
val orm = new PultusORM("test.db", appPath)
```

##### Insert value
```kotlin
class Student {
    @PrimaryKey
    @AutoIncrement
    var studentId: Int = 0
    var name: String? = null
    var department: String? = null
    var cgpa: Double = 0.0
    var dateOfBirth: java.util.Date? = null
    @Ignore
    var section: String? = null
}

val student: Student = Student()
student.name = "Sakib Sayem"
student.department = "CSE"
student.cgpa = 2.3
student.dateOfBirth = Date()
pultusORM.save(student)
pultusORM.close()
```

##### Retrieve Values
```kotlin
val students = pultusORM.find(Student())
for (it in students) {
    val student = it as Student
    println(student.studentId)
    println(student.name)
    println(student.department)
    println(student.cgpa)
    println(student.dateOfBirth)
    println()
}
```

###### Result
```
1
Sakib Sayem
CSE
2.3
Wed Sep 27 23:21:52 BDT 2017
```

##### Retrieve values based on condition
```kotlin
val condition: PultusORMCondition = PultusORMCondition.Builder()
            .eq("name", "sakib")
            .and()
            .greaterEq("cgpa", 18)
            .or()
            .startsWith("name", "sami")
            .sort("name", PultusORMQuery.Sort.DESCENDING)
            .sort("department", PultusORMQuery.Sort.ASCENDING)
            .build()

val students = pultusORM.find(Student(), condition)
for (it in students) {
    val student = it as Student
    println("${student.studentId}")
    println("${student.name}")
}
```

##### Update value
```kotlin
// values will be updated based on this condition
val condition: PultusORMCondition = PultusORMCondition.Builder()
            .eq("name", "Sakib")
            .build()

val updater: PultusORMUpdater = PultusORMUpdater.Builder()
            .set("name", "Sayan Nur")
            .condition(condition)   // condition is optional
            .build()

pultusORM.update(Student(), updater)
```

##### Delete values
```kotlin
pultusORM.delete(Student())
```

##### Drop Table
```kotlin
pultusORM.drop(Student())
```

**Check out more examples & API docs [here](https://s4kibs4mi.github.io/BelleORM/)**

---

#### Note
Tables will be created on fly if not exists using class name
and columns based on
class fields.<br>
Currently supported types:

* String
* Int
* Long
* Float
* Double
* Boolean
* Date (java.util)

Autoincrement annotated fields values will be skipped
as that will be handled by sqlite.

### License
Copyright &copy;  Sakib Sami

Distributed under [MIT](https://github.com/s4kibs4mi/BelleORM/blob/master/LICENSE) license

### Patreon Me !!!
If you want to support this project [Patreon Me!](https://www.patreon.com/s4kibs4mi)

### Buy Me a Coffee
[Checkout with Paypal](https://www.paypal.me/s4kib/10)

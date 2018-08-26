package ninja.sakib.pultusorm

import ninja.sakib.pultusorm.callbacks.Callback
import ninja.sakib.pultusorm.core.*
import ninja.sakib.pultusorm.exceptions.PultusORMException
import ninja.sakib.pultusorm.models.Student
import org.joda.time.DateTime
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * := Coded with love by Sakib Sami on 10/7/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

class PultusORMTest : Callback {
    val pultusORM: PultusORM = PultusORM("univ.db")

    @Before
    fun setUp() {
        enableDebugMode(true)
    }

    @Test
    fun saveTest() {
        val student = Student()
        student.setName("Sakib Sayem")
        student.department = "CSE"
        student.cgpa = 3.3
        student.selction = "A"
        student.email = "s4kibs4mi@gmail.com"
        student.website = "https://www.sakib.ninja"
        student.dateOfBirth = Date()
        student.createdAt = Calendar.getInstance().time
        student.updatedAt = Calendar.getInstance().time

        val result = pultusORM.save(student)
        Assert.assertTrue(result)
    }

    @Test
    fun saveWithDateTest() {
        val student = Student()
//        student.name = "Sakib Sayem"
        student.cgpa = 2.3
        student.email = "s4kibs4mi@gmail.com"
        student.website = "https://www.sakib.ninja"
        student.dateOfBirth = DateTime.now().toDate()

        val result = pultusORM.save(student)
        Assert.assertTrue(result)
    }

    @Test
    fun saveTestWithCallback() {
        val student: Student = Student()
//        student.name = "Nur"
        student.department = "CSE"
        student.cgpa = 3.7

        pultusORM.save(student, this)
    }

    @Test
    fun findAll() {
        val students = pultusORM.find(Student())
        for (it in students) {
            val student = it as Student
            println(student.studentId)
            println(student.getName())
            println(student.department)
            println(student.cgpa)
            println(student.email)
            println(student.website)
            println(student.dateOfBirth)
            println(student.createdAt)
            println(student.updatedAt)
            println(student.selction)
            println()
        }
    }

    @Test
    fun findWithCondition() {
        val condition: PultusORMCondition = PultusORMCondition.Builder()
                .eq("name", "sakib")
                .and()
                .greaterEq("cgpa", 15.34)
                .or()
                .startsWith("name", "sami")
                .sort("name", PultusORMQuery.Sort.DESCENDING)
                .sort("department", PultusORMQuery.Sort.ASCENDING)
                .build()

        println(condition.rawQuery())

        val students = pultusORM.find(Student(), condition)
        for (it in students) {
            val student = it as Student
            println("${student.studentId}")
//            println("${student.name}")
        }
    }

    @Test
    fun updateValue() {
        val condition: PultusORMCondition = PultusORMCondition.Builder()
                .contains("name", "Sakib")
                .build()

        val updater: PultusORMUpdater = PultusORMUpdater.Builder()
                .set("name", "Sayan Nur")
                .condition(condition)
                .build()

        pultusORM.update(Student(), updater)
    }

    @Test
    fun deleteValue() {
        val result = pultusORM.delete(Student())
        Assert.assertTrue(result)
    }

    @Test
    fun dropTable() {
        pultusORM.drop(Student())
    }

    @Test
    fun count() {
        val condition = PultusORMCondition.Builder()
                .eq("cgpa", 3.3)
                .build()
        val count = pultusORM.count(Student(), condition)
        println("Items : $count")
    }

    @Test
    fun limit() {
        val condition = PultusORMCondition.Builder()
                .eq("cgpa", 2.3)
                .limit(2)
                .build()
        val students = pultusORM.find(Student(), condition)
        println("Items : ${students.size}")
    }

    override fun onSuccess(type: PultusORMQuery.Type) {
        println("${type.name} success")
    }

    override fun onFailure(type: PultusORMQuery.Type, exception: PultusORMException) {
        Assert.fail("${type.name} - ${exception.message}")
    }

    @After
    fun tearDown() {

    }
}

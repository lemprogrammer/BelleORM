package ninja.sakib.belleorm.models

import ninja.sakib.belleorm.annotations.AutoIncrement
import ninja.sakib.belleorm.annotations.Ignore
import ninja.sakib.belleorm.annotations.PrimaryKey
import java.util.*

/**
 * := Coded with love by Sakib Sami on 10/6/16.
 * := s4kibs4mi@gmail.com
 * := www.sakib.ninja
 * := Coffee : Dream : Code
 */

class Student : Contact() {
    @PrimaryKey
    @AutoIncrement
    var studentId: Int = 0
    private var name: String? = null
    var department: String? = null
    var cgpa: Double = 0.0
    var dateOfBirth: Date? = null
    @Ignore
    var selction: String? = null

    fun getName(): String? {
        return name
    }

    fun setName(v: String) {
        this.name = v
    }

    var createdAt: Date? = null
    var updatedAt: Date? = null
}

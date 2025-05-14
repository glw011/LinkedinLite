import data.User
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MainTests {

    @Test
    fun recommendedStudentsInitializedWithZeroValues() {
        val students = listOf("Alice", "Bob", "Charlie")
        val recommendedStudents = students.associateWith { 0 }.toMutableMap()

        assertEquals(3, recommendedStudents.size)
        assertTrue(recommendedStudents.all { it.value == 0 })
    }

    @Test
    fun top5StudentsWithHighestValuesAreReturned() {
        val recommendedStudents = mutableMapOf(
            "Alice" to 10,
            "Bob" to 20,
            "Charlie" to 15,
            "Delta" to 25,
            "Eve" to 5,
            "Frank" to 30
        )

        val top5Students = recommendedStudents.entries
            .sortedByDescending { it.value }
            .take(5)
            .map { it.key }

        assertEquals(listOf("Frank", "Delta", "Bob", "Charlie", "Alice"), top5Students)
    }

    @Test
    fun currentUserOrgTagsFlattensTagsCorrectly() {
        val user = User(
            associates = mutableListOf(
                User(tags = mutableListOf("Tag1", "Tag2")),
                User(tags = mutableListOf("Tag3"))
            )
        )

        val currentUserOrgTags = user.associates.flatMap { it.tags }

        assertEquals(listOf("Tag1", "Tag2", "Tag3"), currentUserOrgTags)
    }

    @Test
    fun userIdAutoIncrementsCorrectly() {
        val user1 = User(name = "John")
        val user2 = User(name = "Jane")
        val user3 = User(name = "Alice")

        assertEquals(1, user1.getId())
        assertEquals(2, user2.getId())
        assertEquals(3, user3.getId())
    }
}
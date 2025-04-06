package data

open class Entity

/**
 * This class represents a person in our system.
 *
 * @param email The email address of the person. Must be a valid email format.
 * @constructor Creates a new Person object.
 */
public data class Person(val email: String) : Entity() {}

/**
 * This class represents an organization.
 *
 * @param name The name of the organization.
 */
public data class Organization(val name: String) : Entity() {}

/**
 * This function queries the database for entities matching the given search text.
 *
 * @param searchText The text to search for.
 * @return A list of [Entity] objects matching the search criteria.
 * @see Person
 * @see Organization
 */
fun queryDB(searchText: String): List<Entity> {
    /*
        My idea for this was to query the DB and
        return the top 50 or so best matches.
    */
    return emptyList()
}
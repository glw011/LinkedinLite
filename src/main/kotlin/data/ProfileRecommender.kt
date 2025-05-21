
import data.User
import model.ModelManager
import model.Org
import model.Student
import model.UserType
import service.OrgService
import service.StudentService

class ProfileRecommender {
    companion object {
        private fun filterStudentRecommendation(user: User, allUsers: MutableList<Student>) {
            allUsers.removeIf { it.id == user.getId() }
            val model: model.User
            if (user.getAccountType() == UserType.STUDENT) {
                model = StudentService().getStudentById(user.getId())
            } else {
                model = OrgService().getOrgById(user.getId())
            }
            val studentFollowingList = model.getFollowingList().toList()
                .filter { ModelManager.getUserType(it) == UserType.STUDENT }
                .map { StudentService().getStudentById(it) }
            allUsers.removeIf { studentFollowingList.contains(it) }
        }
        private fun filterOrgRecommendation(user: User, allUsers: MutableList<Org>) {
            allUsers.removeIf { it.id == user.getId() }
            val model: model.User
            if (user.getAccountType() == UserType.STUDENT) {
                model = StudentService().getStudentById(user.getId())
            } else {
                model = OrgService().getOrgById(user.getId())
            }
            val orgFollowingList = model.getFollowingList().toList()
                .filter { ModelManager.getUserType(it) == UserType.ORG }
                .map { OrgService().getOrgById(it) }
            allUsers.removeIf { orgFollowingList.contains(it) }
        }


        private fun calculateStudentToOrgScores(
            student: Student,
            allOrganizations: List<Org>
        ): HashMap<Org, Int> {
            val orgScores = hashMapOf<Org, Int>()
            for (org in allOrganizations) {
                var currentScore = 0

                // Increase score if users have the same tags
                val studentTags = student.interests.toList().map {
                    ModelManager.getInterest(it).name
                }
                val orgTags = org.interests.toList().map {
                    ModelManager.getInterest(it).name
                }
                for (tag in studentTags) {
                    if (orgTags.contains(tag)) {
                        currentScore++
                    }
                }

                // Increase score if users have the same school
                if (student.getSchool() == org.getSchool()) {
                    currentScore++
                }

                // Increase score if users are following the same students
                val studentStudentFollowingList = student.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.STUDENT }
                    .map { StudentService().getStudentById(it) }
                    .toSet()
                val orgStudentFollowingList = org.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.STUDENT }
                    .map { StudentService().getStudentById(it) }
                    .toSet()
                currentScore += studentStudentFollowingList.intersect(orgStudentFollowingList).size

                // Increase score if users are following the same organizations
                val studentOrgFollowingList = student.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.ORG }
                    .map { OrgService().getOrgById(it) }
                    .toSet()
                val orgOrgFollowingList = org.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.ORG }
                    .map { OrgService().getOrgById(it) }
                    .toSet()
                currentScore += studentOrgFollowingList.intersect(orgOrgFollowingList).size

                // Add the score to the map
                orgScores[org] = currentScore
            }
            return orgScores
        }
        private fun calculateStudentToStudentScores(
            student: Student,
            allStudents: List<Student>
        ): HashMap<Student, Int> {
            val studentScores = hashMapOf<Student, Int>()
            for (otherStudent in allStudents) {
                var currentScore = 0

                // Increase score if students have the same tags
                val studentTags = student.interests.toList().map {
                    ModelManager.getInterest(it).name
                }
                val otherStudentTags = otherStudent.interests.toList().map {
                    ModelManager.getInterest(it).name
                }
                for (tag in studentTags) {
                    if (otherStudentTags.contains(tag)) {
                        currentScore++
                    }
                }

                // Increase score if students are in the same organization
                // and if the student's tags match the other student's
                // organization's tags
                val studentOrganizations = student.orgList.toList().map {
                    OrgService().getOrgById(it)
                }
                val otherStudentOrganizations = otherStudent.orgList.toList().map {
                    OrgService().getOrgById(it)
                }
                for (org in studentOrganizations) {
                    if (otherStudentOrganizations.contains(org)) {
                        currentScore++
                    } else {
                        val orgTags = org.interests.toList().map {
                            ModelManager.getInterest(it).name
                        }
                        for (tag in orgTags) {
                            if (otherStudentTags.contains(tag)) {
                                currentScore++
                            }
                        }
                    }
                }

                // Increase score if students are following the same accounts
                val studentFollowingList = student.getFollowingList().toList()
                    .map { StudentService().getStudentById(it) }
                val otherStudentFollowingList = otherStudent.getFollowingList().toList()
                    .map { StudentService().getStudentById(it) }
                for (followingUser in studentFollowingList) {
                    if (otherStudentFollowingList.contains(followingUser)) {
                        currentScore++
                    }
                }

                // Increase score if students are in the same school
                val studentSchool = student.getSchool()
                val otherStudentSchool = otherStudent.getSchool()
                if (studentSchool == otherStudentSchool) {
                    currentScore++
                }

                // Increase score if the students' organizations have the same tags
                val studentOrgTags = studentOrganizations.flatMap { it.interests }.toSet()
                val otherStudentOrgTags = otherStudentOrganizations.flatMap { it.interests }.toSet()
                currentScore += studentOrgTags.intersect(otherStudentOrgTags).size

                // Add the score to the map
                studentScores[otherStudent] = currentScore
            }
            return studentScores
        }
        private fun calculateOrgToOrgScores(org: Org, allOrgs: List<Org>): HashMap<Org, Int> {
            val orgScores = hashMapOf<Org, Int>()
            for (otherOrg in allOrgs) {
                var currentScore = 0

                // Increase score if organizations have the same tags
                val orgTags = org.interests.toList().map {
                    ModelManager.getInterest(it).name
                }
                val otherOrgTags = otherOrg.interests.toList().map {
                    ModelManager.getInterest(it).name
                }
                for (tag in orgTags) {
                    if (otherOrgTags.contains(tag)) {
                        currentScore++
                    }
                }

                // Increase score if organizations are following the same students
                val orgStudentFollowingList = org.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.STUDENT }
                    .map { StudentService().getStudentById(it) }
                    .toSet()
                val otherOrgStudentFollowingList = otherOrg.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.STUDENT }
                    .map { StudentService().getStudentById(it) }
                    .toSet()
                currentScore += orgStudentFollowingList.intersect(otherOrgStudentFollowingList).size

                // Increase score if organizations are following the same organizations
                val orgOrgFollowingList = org.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.ORG }
                    .map { OrgService().getOrgById(it) }
                    .toSet()
                val otherOrgOrgFollowingList = otherOrg.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.ORG }
                    .map { OrgService().getOrgById(it) }
                    .toSet()
                currentScore += orgOrgFollowingList.intersect(otherOrgOrgFollowingList).size

                // Increase score if organizations are in the same school
                val orgSchool = org.getSchool()
                val otherOrgSchool = otherOrg.getSchool()
                if (orgSchool == otherOrgSchool) {
                    currentScore++
                }

                // Increase score if the organizations have the same members
                val orgMembers = org.membersList.toList()
                    .map { StudentService().getStudentById(it) }
                    .toSet()
                val otherOrgMembers = otherOrg.membersList.toList()
                    .map { StudentService().getStudentById(it) }
                    .toSet()
                currentScore += orgMembers.intersect(otherOrgMembers).size

                // Increase score if the organizations' members have the same tags
                val orgMemberTags = orgMembers.flatMap { it.interests }.toSet()
                val otherOrgMemberTags = otherOrgMembers.flatMap { it.interests }.toSet()
                currentScore += orgMemberTags.intersect(otherOrgMemberTags).size

                // Add the score to the map
                orgScores[otherOrg] = currentScore
            }
            return orgScores
        }
        private fun calculateOrgToStudentScores(
            org: Org,
            allStudents: List<Student>
        ): HashMap<Student, Int> {
            val studentScores = hashMapOf<Student, Int>()
            for (student in allStudents) {
                var currentScore = 0

                // Increase score if users have the same tags
                val orgTags = org.interests.toList().map {
                    ModelManager.getInterest(it).name
                }
                val studentTags = student.interests.toList().map {
                    ModelManager.getInterest(it).name
                }
                for (tag in orgTags) {
                    if (studentTags.contains(tag)) {
                        currentScore++
                    }
                }

                // Increase score if users are in the same school
                val orgSchool = org.getSchool()
                val studentSchool = student.getSchool()
                if (orgSchool == studentSchool) {
                    currentScore++
                }

                // Increase score if users are following the same students
                val orgStudentFollowingList = org.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.STUDENT }
                    .map { StudentService().getStudentById(it) }
                    .toSet()
                val studentStudentFollowingList = student.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.STUDENT }
                    .map { StudentService().getStudentById(it) }
                    .toSet()
                currentScore += orgStudentFollowingList.intersect(studentStudentFollowingList).size

                // Increase score if users are following the same organizations
                val orgOrgFollowingList = org.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.ORG }
                    .map { OrgService().getOrgById(it) }
                    .toSet()
                val studentOrgFollowingList = student.getFollowingList().toList()
                    .filter { ModelManager.getUserType(it) == UserType.ORG }
                    .map { OrgService().getOrgById(it) }
                    .toSet()
                currentScore += orgOrgFollowingList.intersect(studentOrgFollowingList).size

                // Add the score to the map
                studentScores[student] = currentScore
            }
            return studentScores
        }

        private fun calculateStudentScores(user: User, allUsers: List<Student>): HashMap<Student, Int> {
            if (user.getAccountType() == UserType.STUDENT) {
                return calculateStudentToStudentScores(StudentService().getStudentById(user.getId()), allUsers)
            } else {
                return calculateOrgToStudentScores(OrgService().getOrgById(user.getId()), allUsers)
            }
        }

        private fun calculateOrgScores(user: User, allUsers: List<Org>): HashMap<Org, Int> {
            if (user.getAccountType() == UserType.STUDENT) {
                return calculateStudentToOrgScores(StudentService().getStudentById(user.getId()), allUsers)
            } else {
                return calculateOrgToOrgScores(OrgService().getOrgById(user.getId()), allUsers)
            }
        }

        private fun getAllStudents(): List<Student> {
            return StudentService.getAllStudents()
        }
        private fun getPossibleStudents(user: User): List<Student> {
            val possibleStudents = getAllStudents().toMutableList()
            filterStudentRecommendation(user, possibleStudents)
            return possibleStudents
        }
        fun recommendStudents(user: User, n: Int): List<Student> {
            val possibleStudents = getPossibleStudents(user)
            val userScores = calculateStudentScores(user, possibleStudents)
            val topScores = userScores.values
                .toSet()
                .toList()
                .sortedByDescending { it }
                .take(n)
            val usersWithTopScores: HashMap<Int, List<Student>> = hashMapOf()
            for (score in topScores) {
                val usersWithTopScore = userScores.entries
                    .filter { it.value == score }
                    .map { it.key }
                usersWithTopScores[score] = usersWithTopScore
            }
            val recommendedUsers = mutableListOf<Student>()
            val userLists = usersWithTopScores.values.toMutableList().asReversed()
            while (recommendedUsers.size < n && userLists.isNotEmpty()) {
                val potentialUsers = userLists[0]
                val recommendationSlotsLeft = n - recommendedUsers.size

                if (potentialUsers.size <= recommendationSlotsLeft) {
                    recommendedUsers.addAll(potentialUsers)
                    if (potentialUsers.size == recommendationSlotsLeft) {
                        break
                    } else {
                        userLists.removeAt(0)
                    }
                } else {
                    recommendedUsers.addAll(potentialUsers.shuffled().take(recommendationSlotsLeft))
                    break
                }
            }
            return recommendedUsers
        }

        fun recommendOrganizations(user: data.User, n: Int): List<Org> {
            val possibleOrganizations = getPossibleOrganizations(user)
            val userScores = calculateOrgScores(user, possibleOrganizations)
            val topScores = userScores.values
                .toSet()
                .toList()
                .sortedByDescending { it }
                .take(n)
            val usersWithTopScores: HashMap<Int, List<Org>> = hashMapOf()
            for (score in topScores) {
                val usersWithTopScore = userScores.entries
                    .filter { it.value == score }
                    .map { it.key }
                usersWithTopScores[score] = usersWithTopScore
            }
            val recommendedUsers = mutableListOf<Org>()
            val userLists = usersWithTopScores.values.toMutableList()
            while (recommendedUsers.size < n && userLists.isNotEmpty()) {
                val potentialUsers = userLists[0]
                val recommendationSlotsLeft = n - recommendedUsers.size

                if (potentialUsers.size <= recommendationSlotsLeft) {
                    recommendedUsers.addAll(potentialUsers)
                    if (potentialUsers.size == recommendationSlotsLeft) {
                        break
                    } else {
                        userLists.removeAt(0)
                    }
                } else {
                    recommendedUsers.addAll(potentialUsers.shuffled().take(recommendationSlotsLeft))
                    break
                }
            }
            return recommendedUsers
        }
        private fun getPossibleOrganizations(user: User): List<Org> {
            val possibleOrganizations = getAllOrganizations().toMutableList()
            filterOrgRecommendation(user, possibleOrganizations)
            return possibleOrganizations
        }
        fun getAllOrganizations(): List<Org> {
            return OrgService.getAllOrgs().values.toList()
        }
        fun recommendUsers(user: data.User, n: Int): List<User> {
            return listOf()
        }
    }
}
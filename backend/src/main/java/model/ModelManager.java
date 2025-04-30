package model;

import util.DBConnection2;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

// Database Object: Responsible for initial mapping of database and altering mapping as needed
public class ModelManager {
    private static HashMap<Integer, School> allSchools;
    private static HashMap<String, Integer> schoolByName;

    private static HashMap<Integer, Major> allMajors;
    private static HashMap<String, Integer> majorByName;

    private static HashMap<Integer, FoS> allFields;
    private static HashMap<String, Integer> fieldByName;

    private static HashMap<Integer, Skill> allSkills;
    private static HashMap<String, Integer> skillByName;

    private static HashMap<Integer, Interest> allInterests;
    private static HashMap<String, Integer> interestByName;

    private static HashMap<Integer, UserType> userTypeById;
    private static HashMap<String, Integer> userIdByEmail;

    public ModelManager() throws SQLException{
        allSchools = new HashMap<>();
        schoolByName = new HashMap<>();

        allMajors = new HashMap<>();
        majorByName = new HashMap<>();

        allFields = new HashMap<>();
        fieldByName = new HashMap<>();

        allSkills = new HashMap<>();
        skillByName = new HashMap<>();

        allInterests = new HashMap<>();
        interestByName = new HashMap<>();

        userTypeById = new HashMap<>();
        userIdByEmail = new HashMap<>();

        populateLists();
    }

    private static void populateLists() throws SQLException{
        populateUsersLists();
        populateSchoolsList();
        populateFieldsList();
        populateSkillsList();
        populateInterestsList();
        populateMajorsList();
    }


    private static void populateUsersLists() throws SQLException{
        String sqlStr = "SELECT * FROM Users";
        ResultSet users = DBConnection2.queryDB(sqlStr);

        while(users.next()){
            int id = users.getInt("user_id");
            String email = users.getString("email");
            String type = users.getString("type");
            UserType userType = (type.equals("org")) ? UserType.ORG : UserType.STUDENT;

            userTypeById.putIfAbsent(id, userType);
            userIdByEmail.putIfAbsent(email, id);
        }

        users.close();
    }
    private static void populateSchoolsList() throws SQLException {
        String sqlStr = "SELECT * FROM Schools";
        ResultSet schools = DBConnection2.queryDB(sqlStr);

        while(schools.next()){
            School currSchool = new School(
                    schools.getInt("school_id"),
                    schools.getAsciiStream("name").toString(),
                    schools.getAsciiStream("city").toString(),
                    schools.getAsciiStream("state").toString(),
                    schools.getAsciiStream("country").toString(),
                    schools.getInt("zip")
            );
            allSchools.putIfAbsent(currSchool.getId(), currSchool);
            schoolByName.putIfAbsent(currSchool.getName(), currSchool.getId());
        }

        schools.close();
    }
    private static void populateFieldsList() throws SQLException{
        String sqlStr = "SELECT * FROM Fields_of_Study";
        ResultSet fields = DBConnection2.queryDB(sqlStr);

        while(fields.next()){
            FoS currField = new FoS(
                    fields.getInt("fos_id"),
                    fields.getAsciiStream("fos_name").toString()
            );
            allFields.putIfAbsent(currField.getID(), currField);
            fieldByName.putIfAbsent(currField.getName(), currField.getID());
        }

        fields.close();
    }
    private static void populateSkillsList() throws SQLException{
        String sqlStr = "SELECT * FROM Skills";
        ResultSet skills = DBConnection2.queryDB(sqlStr);

        while(skills.next()){
            int currSkillId = skills.getInt("skill_id");
            String currSkillName = skills.getAsciiStream("skill_name").toString();

            String currSqlStr = String.format("SELECT * FROM Skill_FoS WHERE skill_id = %d", currSkillId);
            ResultSet field = DBConnection2.queryDB(currSqlStr);

            field.next();
            int currFieldId = field.getInt("fos_id");
            FoS currField = allFields.get(currFieldId);
            field.close();

            Skill currSkill = new Skill(
                    currSkillId,
                    currSkillName,
                    currField
            );
            allSkills.putIfAbsent(currSkill.getID(), currSkill);
            skillByName.putIfAbsent(currSkill.getName(), currSkill.getID());
            currField.addRelatedSkill(currSkill);
        }

        skills.close();
    }
    private static void populateInterestsList() throws SQLException{
        String sqlStr = "SELECT * FROM Interests";
        ResultSet interests = DBConnection2.queryDB(sqlStr);

        while(interests.next()){
            int currInterestId = interests.getInt("interest_id");
            String currInterestName = interests.getAsciiStream("interest_name").toString();

            String currSqlStr = String.format("SELECT * FROM Interest_FoS WHERE interest_id = %d", currInterestId);
            ResultSet field = DBConnection2.queryDB(currSqlStr);

            //TODO: Determine if interest can belong to multiple fields (add while(field.next()) loop) or only 1 field
            field.next();
            int currFieldId = field.getInt("fos_id");
            FoS currField = allFields.get(currFieldId);
            field.close();

            Interest currInterest = new Interest(
                    currInterestId,
                    currInterestName,
                    currField
            );
            allInterests.putIfAbsent(currInterest.getID(), currInterest);
            interestByName.putIfAbsent(currInterest.getName(), currInterest.getID());
            currField.addRelatedInterest(currInterest);
        }

        interests.close();
    }
    private static void populateMajorsList() throws SQLException{
        String sqlStr = "SELECT * FROM Majors";
        ResultSet majors = DBConnection2.queryDB(sqlStr);

        while(majors.next()){
            FoS currFoS = allFields.get(majors.getInt("fos_id"));
            Major currMajor = new Major(majors.getInt("major_id"), majors.getString("major_name"), currFoS);
            allMajors.putIfAbsent(currMajor.getID(), currMajor);
            majorByName.putIfAbsent(currMajor.getName(), currMajor.getID());
        }

        majors.close();
    }


    /**
     * Method returning formated SQL query created from passed parameters which can then be executed by Statement object
     * ARGS:
     *   - columns := Array of column names, e.g. [1]{"student_id AS id", "fname", "lname"} or [2]{"*"} for all columns
     *   - table := table to select from (can be union of n tables so long as correctly constructed)
     *       e.g. [1]"Students" or [2]"Students JOIN Users ON Students.student_id = Users.user_id"
     *   - conditions := conditions to apply to table to select desired result(s)
     *       ** WILL "AND" 2+ CONDITIONS TOGETHER
     *       e.g. [1]{"student_id = 5", "fname = S*"} or if no conditions needed [2]{}
     *       ** If "OR" is needed, must format as {"student_id = 5 OR student_id = 6", "major_id <= 3"}
     * RETURNS:
     *   String --> formatted to query database using passed args
     *   The example args used above would return...
     *       [1]: "SELECT student_id AS id, fname, lname FROM Students WHERE student_id = 5 AND fname = S*"
     *       [2]: "SELECT * FROM Students JOIN Users ON Students.student_id = Users.user_id"
     * */
    public String getSqlQuery(String[] columns, String table, String[] conditions){
        String columnStr = "";
        for(int i=0; i<columns.length; i++){
            columnStr = String.join(columnStr, columns[i]);
            columnStr += (i == (columns.length-1)) ? "" : ", ";
        }


        String conditionStr = (conditions == null) ? "" : " WHERE ";
        for(int i = 0; i < Objects.requireNonNull(conditions).length; i++){
            conditionStr = String.join(conditionStr, conditions[i]);
            conditionStr += (i == (conditions.length-1)) ? "" : " AND ";
        }

        return String.format("SELECT %s FROM %s%s", columnStr, table, conditionStr);
    }

    public static UserType getUserType(int id){return userTypeById.get(id);}
    public static Integer getUserId(String email){return userIdByEmail.get(email);}
    public static void mapNewUser(String email, Integer id, UserType type){
        userTypeById.putIfAbsent(id, type);
        userIdByEmail.putIfAbsent(email, id);
    }

    public static School getSchool(int id){return allSchools.get(id);}
    public static int getSchoolIdByName(String name){return schoolByName.get(name);}
    public static School getSchoolByName(String name){
        if(schoolByName.containsKey(name)) return allSchools.get(schoolByName.get(name));
        return null;
    }

    public static FoS getFoS(int id){return allFields.get(id);}
    public static int getFoSIdByName(String name){return fieldByName.get(name);}
    public static FoS getFoSByName(String name){
        if(fieldByName.containsKey(name)) return allFields.get(fieldByName.get(name));
        return null;
    }

    public static Skill getSkill(int id){return allSkills.get(id);}
    public static int getSkillIdByName(String name){return skillByName.get(name);}
    public static Skill getSkillByName(String name){
        if(skillByName.containsKey(name)) return allSkills.get(skillByName.get(name));
        return null;
    }

    public static Interest getInterest(int id){return allInterests.get(id);}
    public static int getInterestIdByName(String name){return interestByName.get(name);}
    public static Interest getInterestByName(String name){
        if(interestByName.containsKey(name)) return allInterests.get(interestByName.get(name));
        return null;
    }

    public static Major getMajor(int id){return allMajors.get(id);}
    public static int getMajorIdByName(String name){return majorByName.get(name);}
    public static Major getMajorByName(String name){
        if(majorByName.containsKey(name)) return allMajors.get(majorByName.get(name));
        return null;
    }
}

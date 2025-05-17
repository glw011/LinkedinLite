package model;

import util.DBConnection2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Responsible for static items in the DB that cannot be altered by a user by storing them as their respective
 * java objects modeling them and then mapping those objects to a hashmap where their unique id is used as a
 * key to retrieve the item's model representing it.
 *
 * Additionally, maintains a map of all users that is updated whenever a new user is registered, allowing for
 * the unique user_id of any user to be retrieved via their email address and the user type ('student' or 'org')
 * to be retrieved via their user_id.
 */
public class ModelManager {
    private static final boolean DEBUG = true;

    // initialize hashmaps for mapping/storing all static items contained in DB
    private static HashMap<Integer, School> allSchools = new HashMap<>();
    private static HashMap<String, Integer> schoolByName = new HashMap<>();

    private static HashMap<Integer, Major> allMajors = new HashMap<>();
    private static HashMap<String, Integer> majorByName = new HashMap<>();

    private static HashMap<Integer, FoS> allFields = new HashMap<>();
    private static HashMap<String, Integer> fieldByName = new HashMap<>();

    private static HashMap<Integer, Skill> allSkills = new HashMap<>();
    private static HashMap<String, Integer> skillByName = new HashMap<>();

    private static HashMap<Integer, Interest> allInterests = new HashMap<>();
    private static HashMap<String, Integer> interestByName = new HashMap<>();

    private static HashMap<Integer, UserType> userTypeById = new HashMap<>();
    private static HashMap<String, Integer> userIdByEmail = new HashMap<>();

    public static void initModelManager() throws SQLException{
        populateLists();
    }

    // Populate all mappings (Schools, Fields, Skills, Interests, Majors, User IDs and emails) on server start
    private static void populateLists() throws SQLException{
        populateUsersLists();
        populateSchoolsList();
        populateFieldsList();
        populateSkillsList();
        populateInterestsList();
        populateMajorsList();
    }

    // Get user id, email address, and type of all users stored in DB and add to respective hashmap
    private static void populateUsersLists() throws SQLException{
        //String sqlStr = "SELECT * FROM Users JOIN User_Verify ON Users.user_id = User_Verify.user_id";
        String sqlStr = "SELECT * FROM User_Verify";
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
    // Get data for all schools in DB, create School obj for each, and map each school's id to its obj in hashmap
    private static void populateSchoolsList() throws SQLException {
        String sqlStr = "SELECT * FROM Schools";
        ResultSet schools = DBConnection2.queryDB(sqlStr);

        while(schools.next()){
            School currSchool = new School(
                    schools.getInt("school_id"),
                    schools.getString("name"),
                    schools.getString("city"),
                    schools.getString("state"),
                    schools.getString("country")
            );
            if(DEBUG) System.out.println("Adding "+currSchool.getName()+" to allSchools HashMap");
            allSchools.putIfAbsent(currSchool.getId(), currSchool);
            schoolByName.putIfAbsent(currSchool.getName(), currSchool.getId());
        }

        schools.close();
    }
    // Get data for all fields (field of study) in DB, create FoS obj for each, and map each id to its obj in hashmap
    private static void populateFieldsList() throws SQLException{
        String sqlStr = "SELECT * FROM FoS";
        ResultSet fields = DBConnection2.queryDB(sqlStr);

        while(fields.next()){
            FoS currField = new FoS(
                    fields.getInt("fos_id"),
                    fields.getString("fos_name")
            );
            allFields.putIfAbsent(currField.getID(), currField);
            fieldByName.putIfAbsent(currField.getName(), currField.getID());
        }

        fields.close();
    }
    // Get data for all skills in DB, create Skill obj for each, and map each id to its obj in hashmap
    private static void populateSkillsList() throws SQLException{
        String sqlStr = "SELECT * FROM Skills";
        ResultSet skills = DBConnection2.queryDB(sqlStr);

        while(skills.next()){
            int currSkillId = skills.getInt("skill_id");
            String currSkillName = skills.getString("skill_name");

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
    // Get data for all interests in DB, create Interest obj for each, and map each id to its obj in hashmap
    private static void populateInterestsList() throws SQLException{
        String sqlStr = "SELECT * FROM Interests";
        ResultSet interests = DBConnection2.queryDB(sqlStr);

        while(interests.next()){
            int currInterestId = interests.getInt("interest_id");
            String currInterestName = interests.getString("interest_name");

            String currSqlStr = String.format("SELECT * FROM Interest_FoS WHERE interest_id = %d", currInterestId);
            ResultSet field = DBConnection2.queryDB(currSqlStr);

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
    // Get data for all majors in DB, create Major obj for each, and map each id to its obj in hashmap
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

    // Functions to get user type by id, id by email, and map a new user when they are inserted into db
    public static UserType getUserType(int id){return userTypeById.get(id);}
    public static Integer getUserId(String email){return userIdByEmail.get(email);}
    public static void mapNewUser(String email, Integer id, UserType type){
        userTypeById.putIfAbsent(id, type);
        userIdByEmail.putIfAbsent(email, id);
    }

    // Functions to get school obj by id, get school id by name, get school obj by name
    public static School getSchool(int id){return allSchools.get(id);}
    public static int getSchoolIdByName(String name){
        if(schoolByName.containsKey(name))return schoolByName.get(name);
        return -1;
    }
    public static String[] getAllSchoolsList(){
        String[] schoolList = new String[schoolByName.size()];
        int i = 0;

        for(String name : schoolByName.keySet()){
            schoolList[i++] = name;
        }
        return schoolList;
    }
    public static List<School> getAllSchoolsObjects(){
        School[] schoolList = new School[allSchools.size()];
        int i = 0;

        for(School curr : allSchools.values()){
            schoolList[i++] = curr;
        }
        return Arrays.asList(schoolList);
    }
    public static School getSchoolByName(String name){
        if(schoolByName.containsKey(name)) return allSchools.get(schoolByName.get(name));
        return null;
    }

    // Functions to get FoS obj by id, get FoS id by name, get FoS obj by name
    public static FoS getFoS(int id){return allFields.get(id);}
    public static int getFoSIdByName(String name){return fieldByName.get(name);}
    public static String[] getAllFoSList(){
        String[] fosList = new String[fieldByName.size()];
        int i = 0;

        for(String name : fieldByName.keySet()){
            fosList[i++] = name;
        }
        return fosList;
    }
    public static List<FoS> getAllFoSObjects(){
        FoS[] fosList = new FoS[allFields.size()];
        int i = 0;

        for(FoS curr : allFields.values()){
            fosList[i++] = curr;
        }
        return Arrays.asList(fosList);
    }
    public static FoS getFoSByName(String name){
        if(fieldByName.containsKey(name)) return allFields.get(fieldByName.get(name));
        return null;
    }

    // Functions to get Skill obj by id, get skill id by name, get Skill obj by name
    public static Skill getSkill(int id){return allSkills.get(id);}
    public static int getSkillIdByName(String name){
        if(skillByName.containsKey(name)) return skillByName.get(name);
        return -1;
    }
    public static String[] getAllSkillsList(){
        String[] skillList = new String[skillByName.size()];
        int i = 0;

        for(String name : skillByName.keySet()){
            skillList[i++] = name;
        }
        return skillList;
    }
    public static List<Skill> getAllSkillsObjects(){
        Skill[] skillList = new Skill[allSkills.size()];
        int i = 0;

        for(Skill curr : allSkills.values()){
            skillList[i++] = curr;
        }
        return Arrays.asList(skillList);
    }
    public static Skill getSkillByName(String name){
        if(skillByName.containsKey(name)) return allSkills.get(skillByName.get(name));
        return null;
    }

    // Functions to get Interest obj by id, interest id by name, Interest obj by name
    public static Interest getInterest(int id){return allInterests.get(id);}
    public static int getInterestIdByName(String name){
        if(interestByName.containsKey(name)) return interestByName.get(name);
        return -1;
    }
    public static String[] getAllInterestsList(){
        String[] interestList = new String[interestByName.size()];
        int i = 0;

        for(String name : interestByName.keySet()){
            interestList[i++] = name;
        }
        return interestList;
    }
    public static List<Interest> getAllInterestsObjects(){
        Interest[] interestList = new Interest[allInterests.size()];
        int i = 0;

        for(Interest curr : allInterests.values()){
            interestList[i++] = curr;
        }
        return Arrays.asList(interestList);
    }
    public static Interest getInterestByName(String name){
        if(interestByName.containsKey(name)) return allInterests.get(interestByName.get(name));
        return null;
    }

    // Functions to get Major obj by id, major id by name, Major obj by name
    public static Major getMajor(int id){return allMajors.get(id);}
    public static int getMajorIdByName(String name){
        if(majorByName.containsKey(name)) return majorByName.get(name);
        return -1;
    }
    public static String[] getAllMajorsList(){
        String[] majorList = new String[majorByName.size()];
        int i = 0;

        for(String name : majorByName.keySet()){
            majorList[i++] = name;
        }
        return majorList;
    }
    public static List<Major> getAllMajorsObjects(){
        Major[] majorList = new Major[allMajors.size()];
        int i = 0;

        for(Major curr : allMajors.values()){
            majorList[i++] = curr;
        }
        return Arrays.asList(majorList);
    }
    public static Major getMajorByName(String name){
        if(majorByName.containsKey(name)) return allMajors.get(majorByName.get(name));
        return null;
    }
}

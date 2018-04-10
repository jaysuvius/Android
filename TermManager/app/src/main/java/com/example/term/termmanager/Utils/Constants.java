package com.example.term.termmanager.Utils;

public class Constants {

    public static String TERMS_TABLE = "Terms";
    public static final String TERM_ID = "_id";
    public static final String TERM_TITLE = "Title";
    public static final String TERM_START_DATE = "StartDate";
    public static final String TERM_END_DATE = "EndDate";
    public static final String TERM_SORT_COLUMN = "Title";

    public static String TERMS_TABLE_CREATE = "CREATE TABLE " + TERMS_TABLE + " (" +
            TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TERM_TITLE +" TEXT, " +
            TERM_START_DATE + " TEXT," +
            TERM_END_DATE + " TEXT" +
            ")";

    public static String[] TERM_COLUMNS = {TERM_ID, TERM_TITLE, TERM_START_DATE, TERM_END_DATE};
    
    public static String COURSES_TABLE = "Courses";
    public static final String COURSE_ID = "_id";
    public static final String COURSE_TITLE = "Title";
    public static final String COURSE_START_DATE = "StartDate";
    public static final String COURSE_START_ALERT = "StartAlert";
    public static final String COURSE_END_DATE = "EndDate";
    public static final String COURSE_END_ALERT = "EndAlert";
    public static final String COURSE_STATUS = "Status";
    public static final String COURSE_TERM_ID = "TermId";
    public static final String COURSE_SORT_COLUMN = "Title";


    public static String COURSES_TABLE_CREATE = "CREATE TABLE " + COURSES_TABLE + " (" +
            COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COURSE_TITLE +" TEXT, " +
            COURSE_START_DATE + " TEXT," +
            COURSE_START_ALERT + " BIT," +
            COURSE_END_DATE + " TEXT," +
            COURSE_END_ALERT + " BIT," +
            COURSE_STATUS + " TEXT," +
            COURSE_TERM_ID + " INTEGER" +
            ")";

    public static String[] COURSE_COLUMNS = {COURSE_ID, COURSE_TITLE, COURSE_START_DATE, COURSE_START_ALERT, COURSE_END_DATE, COURSE_END_ALERT, COURSE_STATUS, COURSE_TERM_ID};
    
    public static String ASSESSMENTS_TABLE = "Assessments";
    public static final String ASSESSMENT_ID = "_id";
    public static final String ASSESSMENT_TITLE = "Title";
    public static final String ASSESSMENT_COURSE_ID = "CourseId";
    public static final String IS_OBJECTIVE = "IsObjective";
    public static final String IS_PERFORMANCE = "IsPerformance";
    public static final String ASSESSMENT_GOAL_DATE = "GoalDate";
    public static final String ASSESSMENT_GOAL_ALERT = "GoalAlert";
    public static final String ASSESSMENT_DUE_DATE = "DueDate";
    public static final String ASSESSMENT_DUE_ALERT = "DueAlert";
    public static final String ASSESSMENT_SORT_COLUMN = "CourseId";


    public static String ASSESSMENTS_TABLE_CREATE = "CREATE TABLE " + ASSESSMENTS_TABLE + " (" +
            ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ASSESSMENT_TITLE + " TEXT, " +
            ASSESSMENT_COURSE_ID +" INTEGER, " +
            IS_OBJECTIVE + " INTEGER," +
            IS_PERFORMANCE + " INTEGER," +
            ASSESSMENT_GOAL_DATE + " TEXT,"+
            ASSESSMENT_GOAL_ALERT + " BIT,"+
            ASSESSMENT_DUE_DATE + " TEXT,"+
            ASSESSMENT_DUE_ALERT + " BIT"+
            ")";

    public static String[] ASSESSMENT_COLUMNS = {ASSESSMENT_ID, ASSESSMENT_TITLE, ASSESSMENT_COURSE_ID, IS_OBJECTIVE, IS_PERFORMANCE, ASSESSMENT_GOAL_DATE, ASSESSMENT_GOAL_ALERT, ASSESSMENT_DUE_DATE, ASSESSMENT_DUE_ALERT};

    public static String[] STATUS_TYPES = {"In Progress", "Completed", "Dropped", "Plan to Take"};

    public static String NOTES_TABLE = "Notes";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_COURSE_ID = "CourseId";
    public static final String NOTE_ASSESSMENT_ID = "AssessmentId";
    public static final String NOTE = "Note";
    public static final String NOTE_SORT_COLUMN = "Note";

    public static String NOTES_TABLE_CREATE = "CREATE TABLE " + NOTES_TABLE + " (" +
            NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOTE_COURSE_ID +" INTEGER, " +
            NOTE_ASSESSMENT_ID +" INTEGER, " +
            NOTE + " TEXT" +
            ")";

    public static String[] NOTE_COLUMNS = {NOTE_ID, NOTE_COURSE_ID, NOTE_ASSESSMENT_ID, NOTE};

    public static String IMAGES_TABLE = "Images";
    public static final String IMAGE_ID = "_id";
    public static final String IMAGE_COURSE_ID = "CourseId";
    public static final String IMAGE_ASSESSMENT_ID = "AssessmentId";
    public static final String IMAGE = "Image";
    public static final String IMAGE_SORT_COLUMN = "_id";

    public static String IMAGES_TABLE_CREATE = "CREATE TABLE " + IMAGES_TABLE + " (" +
            IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            IMAGE_COURSE_ID +" INTEGER, " +
            IMAGE_ASSESSMENT_ID +" INTEGER, " +
            IMAGE + " BLOB" +
            ")";

    public static String[] IMAGE_COLUMNS = {IMAGE_ID, IMAGE_COURSE_ID, IMAGE_ASSESSMENT_ID, IMAGE};

    public static String MENTORS_TABLE = "Mentors";
    public static final String MENTOR_ID = "_id";
    public static final String MENTOR_NAME = "Name";
    public static final String MENTOR_PHONE = "Phone";
    public static final String MENTOR_EMAIL = "Email";
    public static final String MENTOR_ASSOCIATED_ENTITY_ID = "EntityId";
    public static final String MENTOR_SORT_COLUMN = "Name";

    public static String MENTORS_TABLE_CREATE = "CREATE TABLE " + MENTORS_TABLE + " (" +
            MENTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MENTOR_NAME +" TEXT, " +
            MENTOR_PHONE +" TEXT, " +
            MENTOR_EMAIL + " TEXT," +
            MENTOR_ASSOCIATED_ENTITY_ID + " INTEGER" +
            ")";

    public static String[] MENTOR_COLUMNS = {MENTOR_ID, MENTOR_NAME, MENTOR_PHONE, MENTOR_EMAIL, MENTOR_ASSOCIATED_ENTITY_ID};

}

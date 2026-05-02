package com.airtribe.learntrack.constants;

public final class MenuOptions {

    // Main menu
    public static final int MAIN_STUDENT_MENU = 1;
    public static final int MAIN_COURSE_MENU = 2;
    public static final int MAIN_ENROLLMENT_MENU = 3;
    public static final int MAIN_EXIT = 0;

    // Student menu
    public static final int STUDENT_ADD = 1;
    public static final int STUDENT_VIEW_ALL = 2;
    public static final int STUDENT_SEARCH_BY_ID = 3;
    public static final int STUDENT_UPDATE = 4;
    public static final int STUDENT_DEACTIVATE = 5;
    public static final int STUDENT_BACK = 0;

    // Course menu
    public static final int COURSE_ADD = 1;
    public static final int COURSE_VIEW_ALL = 2;
    public static final int COURSE_SEARCH_BY_ID = 3;
    public static final int COURSE_TOGGLE_STATUS = 4;
    public static final int COURSE_BACK = 0;

    // Enrollment menu
    public static final int ENROLLMENT_ENROLL = 1;
    public static final int ENROLLMENT_VIEW_BY_STUDENT = 2;
    public static final int ENROLLMENT_VIEW_ALL = 3;
    public static final int ENROLLMENT_COMPLETE = 4;
    public static final int ENROLLMENT_CANCEL = 5;
    public static final int ENROLLMENT_BACK = 0;

    private MenuOptions() {
    }
}

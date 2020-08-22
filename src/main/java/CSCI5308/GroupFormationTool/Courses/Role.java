package CSCI5308.GroupFormationTool.Courses;

public enum Role {

    INSTRUCTOR {
        public String toString() {
            return "Instructor";
        }
    },
    GUEST {
        public String toString() {
            return "Guest";
        }
    },
    ADMIN {
        public String toString() {
            return "Admin";
        }
    },
    STUDENT {
        public String toString() {
            return "Student";
        }
    },
    TA {
        public String toString() {
            return "TA";
        }
    }

}

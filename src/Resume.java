import java.util.TreeSet;

public class Resume{
    Information information;
    TreeSet<Education> education;
    TreeSet<Experience> experience;

    private Resume(ResumeBuilder builder) {
        information = builder.information;
        education = builder.education;
        experience = builder.experience;
    }

    public String toString() {
        return "" + information.toString() + "Education\n" + education + "\nExperience\n" + experience;
    }

    public static class ResumeBuilder {
        Information information = null;
        TreeSet<Education> education = new TreeSet<>();
        TreeSet<Experience> experience = new TreeSet<>();

        public ResumeBuilder information(Information information) {
            this.information = information;
            return this;
        }

        public ResumeBuilder addEducation(Education education) {
            this.education.add(education);
            return this;
        }

        public ResumeBuilder addExperience(Experience experience) {
            this.experience.add(experience);
            return this;
        }

        public Resume build() {
            if(information != null)
                return new Resume(this);
            else
                throw new ResumeIncompleteException();
        }

        static class ResumeIncompleteException extends RuntimeException{
            public ResumeIncompleteException(String message) {
                super(message);
            }
            public ResumeIncompleteException(){
                this("Resume is missing information or there is no entry in education");
            }
        }
    }


}

package hackust.education.educationapp;

public class Student {
    private int id;
    private String name;
    private String subject;
    private String rating;

    Student(int id, String name, String subject, String rating) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}

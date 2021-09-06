package socketassignment;

public class Data {
    public int number;
    public String name;
    public String faculty;
    public String course;
    public String degree;
    public int code;

    public String toSerializeString() {
        return String.format("**%d\t%s\t%s\t%s\t%s\t%d", number, name, faculty, course, degree, code);
    }
}

package MCMClass;

public class Author {
    private String id, name, nationality,homeTown,note;
    private int  yearOfBirth;

    public Author(String id, String name, String nationality, String homeTown, String note, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.homeTown = homeTown;
        this.note = note;
        this.yearOfBirth = yearOfBirth;
    }

    public Author() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}

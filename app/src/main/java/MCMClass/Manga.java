package MCMClass;

public class Manga {
    private String idCollection, idChapter, chapter, type, status, note, date;
    private int price;

    public Manga(String idCollection, int price, String note, String status, String type, String chapter, String idChapter,String date) {
        this.idCollection = idCollection;
        this.price = price;
        this.note = note;
        this.status = status;
        this.type = type;
        this.chapter = chapter;
        this.idChapter = idChapter;
        this.date = date;
    }

    public Manga() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdCollection() {
        return idCollection;
    }

    public void setIdCollection(String idCollection) {
        this.idCollection = idCollection;
    }

    public String getIdChapter() {
        return idChapter;
    }

    public void setIdChapter(String idChapter) {
        this.idChapter = idChapter;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

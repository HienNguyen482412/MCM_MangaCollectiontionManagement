package MCMClass;

public class MangaCollection {
    private String id, name, publisherId, authorId, status, date, note;
    private byte[] image;

    public MangaCollection(String id, String name, String publisherId, String authorId, String status, String date, String note, byte[] image) {
        this.id = id;
        this.name = name;
        this.publisherId = publisherId;
        this.authorId = authorId;
        this.status = status;
        this.date = date;
        this.note = note;
        this.image = image;
    }

    public MangaCollection() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

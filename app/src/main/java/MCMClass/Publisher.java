package MCMClass;

public class Publisher {
    private String id, name, status, phoneNumber, address, note;

    public Publisher(String id, String name, String status, String phoneNumber, String address, String note) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.note = note;
    }

    public Publisher() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package BusinessObjects;


public class Mail {
    private String addressee;
    private String subject;
    private String text;

    public Mail(String addressee, String subject, String text) {
        this.addressee = addressee;
        this.subject = subject;
        this.text = text;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Mail contents:" + "\naddressee: " + addressee + "\nsubject: " + subject + "\ntext: " + text;
    }
}

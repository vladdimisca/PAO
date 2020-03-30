package project.model;

public class Student extends Client {
    private final static Integer discount = 22;

    public Student(String clientName) {
        super(clientName);
    }

    @Override
    public Double getPrice(Double price) {
        return super.getPrice(price) * (100 - discount) / 100;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientName='" + clientName + '\'' +
                ", clientId=" + clientId +
                ", discountType=Student" +
                '}';
    }
}

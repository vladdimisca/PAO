package project.model;

public class Child  extends Client {
    private final static Integer discount = 40;

    public Child(String clientName) {
        super(clientName);
    }

    public Child(Integer id, String clientName) {
        super(id, clientName);
    }

    @Override
    public Double getPrice(Double price) {
        return super.getPrice(price) * (100 - discount) / 100;
    }

    @Override
    public String getDiscountType() {
        return "Child";
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientName='" + clientName + '\'' +
                ", clientId=" + clientId +
                ", discountType=Child" +
                '}';
    }
}

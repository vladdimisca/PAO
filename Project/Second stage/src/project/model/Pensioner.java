package project.model;

public class Pensioner extends Client {
    private final static Integer discount = 30;

    public Pensioner(String clientName) {
        super(clientName);
    }

    public Pensioner(Integer id, String clientName) {
        super(id, clientName);
    }

    @Override
    public Double getPrice(Double price) {
        return super.getPrice(price) * (100 - discount) / 100;
    }

    @Override
    public String getDiscountType() {
        return "Pensioner";
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientName='" + clientName + '\'' +
                ", clientId=" + clientId +
                ", discountType=Pensioner" +
                '}';
    }
}

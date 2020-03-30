package project.model;

public class Client {
    protected static Integer id = 0;
    protected String clientName;
    protected final Integer clientId;

    public Client(String clientName) {
        this.clientName = clientName;
        this.clientId = ++id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public Double getPrice(Double price) {
        return price;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientName='" + clientName + '\'' +
                ", clientId=" + clientId +
                ", discountType=No discount" +
                '}';
    }
}

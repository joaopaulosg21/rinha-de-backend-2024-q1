package projeto.api.backend2.model;

public class Client {
    private int id;

    private int saldo;

    private int limite;

    public Client() {}

    public Client(int id, int saldo, int limite) {
        this.id = id;
        this.saldo = saldo;
        this.limite = limite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    
}

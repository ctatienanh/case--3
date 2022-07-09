package Modal;

public class Cart {

    private Account account;

    private Ve ve;

    public Cart() {
    }

    public Cart(Account account, Ve ve) {
        this.account = account;
        this.ve = ve;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
    }
}

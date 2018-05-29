package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by huangxiao on 2017/5/31.
 */
@Entity
@Table(name = "user", schema = "AQM")
public class User {

    @Id @Column(name = "username", length = 16, nullable = false)
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    private String username;

    @Column(name = "password", length = 32, nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Stock.class)
    @JoinTable(
            name = "user_stock",                                                // 关联表名
            joinColumns = @JoinColumn(name = "username", nullable = false),     // 维护端外键
            inverseJoinColumns = @JoinColumn(name = "code", nullable = false),  // 被维护端外键
            foreignKey = @ForeignKey(name = "user_foreign_key"),
            inverseForeignKey = @ForeignKey(name = "stock_foreign_key")
    )
    private Set<Stock> stocks;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void removeStock(Stock stock) {
        stocks.remove(stock);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == User.class) {
            User target = (User) obj;
            return target.getUsername().equals(getUsername());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

}

package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by huangxiao on 2017/5/14.
 */
@Entity
@Table(name = "stock", schema = "AQM")
public class Stock {

    @Id
    @Column(name = "code", length = 6, nullable = false)
    @GenericGenerator(name = "generator", strategy = "assigned")
    @GeneratedValue(generator = "generator")
    private String code;

    @Column(name = "name", length = 8, nullable = false)
    private String name;

    public Stock() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == Stock.class) {
            Stock target = (Stock) obj;
            return target.getCode().equals(getCode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }

}

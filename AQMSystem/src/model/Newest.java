package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by huangxiao on 2017/6/13.
 */
@Entity
@Table(name = "newest", schema = "AQM")
public class Newest {

    @Id
    @Column(name = "code", length = 6, nullable = false)
    private String code;

    @Column(name = "name", length = 16, nullable = false)
    private String name;

    @Column(name = "change_percent", nullable = false)
    private double changePercent;
    private double open;
    private double high;
    private double low;
    private double trade;
    private int volume;

    public Newest() {
    }

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

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(double changePercent) {
        this.changePercent = changePercent;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getTrade() {
        return trade;
    }

    public void setTrade(double trade) {
        this.trade = trade;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == KData.class) {
            KData target = (KData) obj;
            return target.getCode().equals(getCode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getCode().hashCode();
    }

}

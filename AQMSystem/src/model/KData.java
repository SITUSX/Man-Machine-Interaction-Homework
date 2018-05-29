package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by huangxiao on 2017/5/14.
 */
@Entity
@Table(name = "k_data", schema = "AQM")
public class KData implements Serializable {

    @Id
    @Column(name = "code", length = 6, nullable = false)
    private String code;
    @Id
    @Column(name = "date", nullable = false)
    private Date date;

    private double open;
    private double high;
    private double low;
    private double close;
    private int volume;
    @Column(name = "adj_close", nullable = false)
    private double adjClose;

    public KData(){
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(double adjClose) {
        this.adjClose = adjClose;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == KData.class) {
            KData target = (KData) obj;
            return target.getCode().equals(getCode())
                    && target.getDate().equals(getDate());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getCode().hashCode() * 31
                + getDate().hashCode();
    }
}

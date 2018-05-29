package model;

import org.hibernate.annotations.GenericGenerator;
import vo.BlockVO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by huangxiao on 2017/5/15.
 */
@Entity
@Table(name = "block", schema = "AQM")
public class Block implements Serializable {

    @Id
    @Column(name = "block_name", length = 16, nullable = false)
    private String blockName;

    @Id
    @Column(name = "block_type", length = 16, nullable = false)
    private String blockType;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Stock.class)
//    @JoinTable(
//            name = "block_stock",                               // 关联表名
//            joinColumns = {
//                    @JoinColumn(name = "block_name", nullable = false),
//                    @JoinColumn(name = "block_type", nullable = false)
//            },                                                  // 维护端外键
//            inverseJoinColumns = @JoinColumn(name = "code"),    // 被维护端外键
//            foreignKey = @ForeignKey(name = "block_foreign_key"),
//            inverseForeignKey = @ForeignKey(name = "stock_foreign_key")
//    )
//    private Set<Stock> stocks;

    public Block() {
    }

    public Block(BlockVO vo) {
        this.blockName = vo.blockName;
        this.blockType = vo.blockType.getBlockForShort();
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == Block.class) {
            Block target = (Block) obj;
            return target.getBlockType().equals(getBlockType())
                    && target.getBlockName().equals(getBlockName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getBlockType().hashCode() * 31
                + getBlockName().hashCode();
    }

}

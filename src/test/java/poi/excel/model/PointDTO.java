package poi.excel.model;


import com.foutin.poi.excel.annotation.ExcelField;
import com.foutin.poi.excel.annotation.ExcelSheet;

/**
 * @author xingkai.fan
 * @description
 * @date 2019/6/10 14:00
 */
@ExcelSheet(name = "points2")
public class PointDTO {
    @ExcelField(name = "source_point_ukid")
    private Long sourcePointUkid;

    @ExcelField(name = "target_point_ukid")
    private Long targetPointUkid;


    public PointDTO(Long sourcePointUkid, Long targetPointUkid) {
        this.sourcePointUkid = sourcePointUkid;
        this.targetPointUkid = targetPointUkid;
    }

    public PointDTO() {
    }

    public Long getSourcePointUkid() {
        return sourcePointUkid;
    }

    public void setSourcePointUkid(Long sourcePointUkid) {
        this.sourcePointUkid = sourcePointUkid;
    }

    public Long getTargetPointUkid() {
        return targetPointUkid;
    }

    public void setTargetPointUkid(Long targetPointUkid) {
        this.targetPointUkid = targetPointUkid;
    }

    @Override
    public String toString() {
        return "PointDTO{" +
                "sourcePointUkid=" + sourcePointUkid +
                ", targetPointUkid=" + targetPointUkid +
                '}';
    }
}

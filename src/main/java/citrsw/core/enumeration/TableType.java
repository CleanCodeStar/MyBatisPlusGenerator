package citrsw.core.enumeration;

/**
 * 表类型
 * <br/>
 * Created in 2019-09-02 22:33
 *
 * @author Zhenfeng Li
 */
public enum TableType {
    /**
     * 表
     */
    TABLE("TABLE"),
    /**
     * 视图
     */
    VIEW("VIEW");
    /**
     * 中文描述
     */
    private String value;

    /**
     * 私有构造
     *
     * @param value 值
     */
    private TableType(String value) {
        this.value = value;
    }

    /**
     * 定义方法,返回值
     */
    public String getValue() {
        return value;
    }
}

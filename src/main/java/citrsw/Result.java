package citrsw;


import lombok.Data;

import java.io.Serializable;

/**
 * 返回对象
 *
 * @author Zhenfeng Li
 * @version 1.0.0
 * @date 2019-09-19 09:02:33
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -2492072809889519824L;

    /**
     * 响应编码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 不允许直接创建
     */
    private Result() {
    }

    /**
     * 不允许直接创建
     */
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> build(Integer code, String msg) {
        return new Result<T>(code, msg, null);
    }

    public static <T> Result<T> build(Integer code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }

    /**
     * build保存成功返回结果
     */
    public static <T> Result<T> buildSaveOk(T data) {
        return new Result<T>(Msg.OK, Msg.TEXT_SAVE_OK, data);
    }

    /**
     * build修改成功返回结果
     */
    public static <T> Result<T> buildUpdateOk(T data) {
        return new Result<T>(Msg.OK, Msg.TEXT_UPDATE_OK, data);
    }

    /**
     * build删除成功返回结果
     */
    public static <T> Result<T> buildDeleteOk(T data) {
        return new Result<T>(Msg.OK, Msg.TEXT_DELETE_OK, data);
    }

    /**
     * build查询成功返回结果
     */
    public static <T> Result<T> buildQueryOk(T data) {
        return new Result<T>(Msg.OK, Msg.TEXT_QUERY_OK, data);
    }

    /**
     * build成功返回结果
     */
    public static <T> Result<T> buildOk(String msg, T data) {
        return new Result<T>(Msg.OK, msg, data);
    }

    /**
     * build失败返回结果
     */
    public static <T> Result<T> buildFail(String msg) {
        return new Result<T>(Msg.FAIL, msg, null);
    }

    /**
     * 返回消息内部类
     */
    public interface Msg {
        /**
         * 成功返回
         */
        Integer OK = 200;
        String TEXT_SAVE_OK = "保存成功";
        String TEXT_UPDATE_OK = "修改成功";
        String TEXT_DELETE_OK = "删除成功";
        String TEXT_QUERY_OK = "查询成功";

        /**
         * 失败返回
         */
        Integer FAIL = 300;

        /**
         * token失效
         */
        Integer TOKEN_FAIL = 430;
        String TEXT_TOKEN_INVALID_FAIL = "请重新登录";

        /**
         * 异常返回
         */
        Integer SYSTEM_FAIL = 500;
        String TEXT_SYSTEM_FAIL = "呀~~数据跑丢了~~";

    }
}

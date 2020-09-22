package citrsw.core.common;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * <br/>
 * Created in 2019-09-02 23:20
 *
 * @author Zhenfeng Li
 */
public class CoreUtils {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 将下划线转首字母大写的驼峰规则字符串
     *
     * @param target 目标字符串
     * @return 转为驼峰命名后的字符串
     */
    public static String getUpperCaseFirstName(String target) {
        if (StringUtils.isNotEmpty(target) && target.contains("_")) {
            //先全部转换为小写
            target = target.toLowerCase();
            String[] split = target.split("_");
            StringBuilder builder = new StringBuilder();
            for (String s : split) {
                if (StringUtils.isNotEmpty(s)) {
                    builder.append(toUpperFirstCase(s));
                }
            }
            target = builder.toString();
        }
        return target;
    }

    /**
     * 将下划线转首字母小写的驼峰规则字符串
     *
     * @param target 目标字符串
     * @return 转为驼峰命名后的字符串
     */
    public static String getLowerCaseFirstName(String target) {
        target = getUpperCaseFirstName(target);
        if (StringUtils.isNotEmpty(target)) {
            target = toLowerFirstCase(target);
        }
        return target;
    }

    /**
     * 将首字母小写的字符串转换为首字母大写
     *
     * @param target 目标字符串
     * @return 首字母大写后的字符串
     */
    public static String toUpperFirstCase(String target) {
        if (StringUtils.isNotEmpty(target)) {
            char[] chars = target.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            target = String.valueOf(chars);
        }
        return target;
    }

    /**
     * 将首字母大写的字符串转换为首字母小写
     *
     * @param target 目标字符串
     * @return 首字母小写后的字符串
     */
    public static String toLowerFirstCase(String target) {
        if (StringUtils.isNotEmpty(target)) {
            char[] chars = target.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            target = String.valueOf(chars);
        }
        return target;
    }


    /**
     * 驼峰转下划线 把appId转换成app_id
     */

    public static String camelToUnderLine(final String value) {
        Matcher matcher = humpPattern.matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}

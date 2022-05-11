package apose.javadog.net.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Locale;

/**
 * 简历-基础信息
 * @author: hdx
 * @Date: 2022-05-10 09:49
 * @version: 1.0
 **/
@Data
@Accessors(chain = true)
public class BaseInfo {
    /**
    * 姓名
    */
    private String name;
    /**
     * 性别.
     */
    private String sex;
    /**
     * 生日.
     */
    private String birth;
    /**
     * 籍贯.
     */
    private String nativePlace;
    /**
     * 国籍.
     */
    private String nation;
    /**
     * 身高.
     */
    private String height;
    /**
     * 体重.
     */
    private String weight;
    /**
     * 婚否.
     */
    private String marriage;
    /**
     * 技术特长.
     */
    private List<String> specialtys;
    /**
     * 外语等级.
     */
    private String languageLevel;
}


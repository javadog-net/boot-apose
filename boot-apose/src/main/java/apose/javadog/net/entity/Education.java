package apose.javadog.net.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 简历-教育背景
 * @author: hdx
 * @Date: 2022-05-10 10:30
 * @version: 1.0
 **/
@Data
@Accessors(chain = true)
public class Education {
    /**
     * 起止时间
     */
    private String startEndTime;
    /**
     * 学校
     */
    private String school;
    /**
     * 专业
     */
    private String professional;
    /**
     * 教育形式
     */
    private String educationalForm;
    /**
     * 是否全日制
     */
    private String fullTime;
}

package apose.javadog.net.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 简历-工作经历
 * @author: hdx
 * @Date: 2022-05-10 11:43
 * @version: 1.0
 **/
@Data
@Accessors(chain = true)
public class WorkExperience {
    /**
     * 起止时间
     */
    private String startEndTime;

    /**
     * 工作单位
     */
    private String workUnit;

    /**
     * 职位
     */
    private String position;

    /**
     * 离职原因
     */
    private String resignation;

}

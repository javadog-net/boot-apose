package apose.javadog.net.entity;

import lombok.Data;

import java.util.List;

/**
 * @author: hdx
 * @Date: 2023-06-15 09:33
 * @version: 1.0
 **/
@Data
public class Recommend {

    private String informationDepartment;

    private List<President> listPresident ;
}

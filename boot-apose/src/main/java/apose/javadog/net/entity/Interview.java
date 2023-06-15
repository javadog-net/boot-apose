package apose.javadog.net.entity;
import lombok.Data;

import java.util.List;

@Data
public class Interview {

    private BaseInfo baseInfo;

    private List<Education> educations;

    private List<WorkExperience> workExperiences;

    private Recommend recommend;
}

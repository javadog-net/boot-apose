package apose.javadog.net.controller;

import apose.javadog.net.entity.BaseInfo;
import apose.javadog.net.entity.Education;
import apose.javadog.net.entity.Interview;
import apose.javadog.net.entity.WorkExperience;
import cn.hutool.core.util.CharsetUtil;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ReportingEngine;
import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/word")
@Slf4j
public class WordController {

    @GetMapping("/pdf")
    void pdf(HttpServletResponse response){
        // 获取资源doc路径下的简历interview.doc模板
        final  ClassPathResource classPathResource = new ClassPathResource("doc\\interview.doc");
        // 组装数据
        final Document doc;
        try (InputStream inputStream = classPathResource.getInputStream();
             ServletOutputStream outputStream = response.getOutputStream()) {
            // 文件名称
            String fileName = URLEncoder.encode("帅锅的简历.pdf", CharsetUtil.UTF_8);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/octet-stream;charset=UTF-8");
            // 将输入流转为doc
            doc = new Document(inputStream);
            // doc构建
            DocumentBuilder builder = new DocumentBuilder(doc);
            // 定位书签位置
            builder.moveToBookmark("AVATAR");
            // 插入图片
            builder.insertImage("https://portrait.gitee.com/uploads/avatars/user/491/1474070_javadog-net_1616995139.png!avatar30");
            // 定位LANGUAGE_LEVEL4书签位置
            builder.moveToBookmark("LANGUAGE_LEVEL4");
            // 设置字符名称
            builder.getFont().setName("Wingdings");
            // 设置字符大小
            builder.getFont().setSize(14);
            // 对号字符
            builder.write("\uF0FE");
            // 定位LANGUAGE_LEVEL6书签位置
            builder.moveToBookmark("LANGUAGE_LEVEL6");
            // 设置字符名称
            builder.getFont().setName("Wingdings");
            builder.getFont().setSize(20);
            builder.write("□");
            doc.updateFields();
            final ReportingEngine engine = new ReportingEngine();
            // 将数据填充至模板
            engine.buildReport(doc, getInterviewData(), "data");
            // 转pdf
            doc.save(outputStream, SaveFormat.PDF);
        } catch (Exception e) {
            log.error("生成报告异常，异常信息：{}", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private Interview getInterviewData(){
        Interview interview = new Interview();
        this.getBaseInfo(interview);
        this.getEducations(interview);
        this.getWorkExperiences(interview);
        return interview;
    }

    /**
     * @Description: 组装基本数据
     * @Param: [interview]
     * @return: [apose.javadog.net.entity.Interview]
     * @Author: hdx
     * @Date: 2022/5/10 15:39
     */
    private void getBaseInfo(Interview interview){
        // 基本数据
        BaseInfo baseInfo = new BaseInfo();
        List<String> listStr = new ArrayList<>();
        listStr.add("后端技术栈：有较好的Java基础，熟悉SpringBoot,SpringCloud,springCloud Alibaba等主流技术，Redis内存数据库、RocketMq、dubbo等，熟悉JUC多线程");
        listStr.add("后端模板引擎：thymeleaf、volocity");
        listStr.add("前端技术栈：熟练掌握ES5/ES6/、NodeJs、Vue、React、Webpack、gulp");
        listStr.add("其他技术栈: 熟悉python+selenium、electron");
        baseInfo.setName("狗哥")
                .setBirth("1993年5月14日")
                .setHeight("180")
                .setWeight("70")
                .setNation("汉")
                .setSex("男")
                .setNativePlace("济南")
                .setMarriage("已婚")
                .setSpecialtys(listStr);
        interview.setBaseInfo(baseInfo);
    }
    /**
     * @Description: 组装教育经历
     * @Param: [interview]
     * @return: [apose.javadog.net.entity.Interview]
     * @Author: hdx
     * @Date: 2022/5/10 15:40
     */
    private void getEducations(Interview interview){
        // 高中
        List<Education> educations = new ArrayList<>();
        Education education = new Education();
        education.setStartEndTime("2009-2012")
                .setSchool("山东省实验中学")
                .setFullTime("是")
                .setProfessional("理科")
                .setEducationalForm("普高");
        educations.add(education);
        // 大学
        Education educationUniversity = new Education();
        educationUniversity.setStartEndTime("2012-2016")
                .setSchool("青岛农业大学")
                .setFullTime("是")
                .setProfessional("计算机科学与技术")
                .setEducationalForm("本科");
        educations.add(educationUniversity);
        interview.setEducations(educations);
    }

    /**
     * @Description: 组装工作经历
     * @Param: [interview]
     * @return: [apose.javadog.net.entity.Interview]
     * @Author: hdx
     * @Date: 2022/5/10 15:40
     */
    private void getWorkExperiences(Interview interview){
        // 工作记录
        List<WorkExperience> workExperiences = new ArrayList<>();
        WorkExperience workExperience = new WorkExperience();
        workExperience.setStartEndTime("2009-2012")
                .setWorkUnit("青岛XXX")
                .setPosition("开发")
                .setResignation("有更好的学习空间，向医疗领域拓展学习纬度");
        workExperiences.add(workExperience);
        interview.setWorkExperiences(workExperiences);
    }
}

### 前言
#### ⏲️本文阅读时长：约10分钟
#### 🎯主要目标：
##### 1.实现Springboot与aspose-words整合，填充word模板并转化PDF;
##### 2.前端vue整合vue-pdf实现PDF预览及下载
#### word模板重点(详见图示)
##### 1.单属性赋值
##### 2.List循环赋值
##### 3.图片插入
##### 4.对勾特殊符号插入
![](https://img.javadog.net/blog/boot-apose/69a561b0ee644ca5a23cb72368767555_tplv-k3u1fbpfcp-zoom-1.png)
***
### 干货代码
#### 源码
[https://gitee.com/javadog-net/boot-apose.git](https://gitee.com/javadog-net/boot-apose.git)
| 文件夹 | 描述| 
| :--- | :--- | 
| boot-apose| java后台|
| vue-apose| 前端vue| 

#### 对应工具下载
| 工具 | 描述| 地址|
| :--- | :--- | :--- | 
| aspose-words-19.1| word三方库|[https://download.csdn.net/download/baidu_25986059/85390408](https://download.csdn.net/download/baidu_25986059/85390408) | 
| javadog-vue-pdf| 因原版vue-pdf有兼容错误，此版本为本人修订自用版| [https://www.npmjs.com/package/javadog-vue-pdf](https://www.npmjs.com/package/javadog-vue-pdf)| 

***
### 结果预览
![](https://img.javadog.net/blog/boot-apose/f3668bed24e2478fbe0b39f192d7c0b4_tplv-k3u1fbpfcp-zoom-1.gif)
#### 模板填充前空word模板
![我还是没有放下你](https://img.javadog.net/blog/boot-apose/3b073c52d76a4e22bf387097930ab37a_tplv-k3u1fbpfcp-zoom-1.png)
#### 代码填充后word模板
![](https://img.javadog.net/blog/boot-apose/d7bd4a1109d44ea1bfeacc50a6c88ec1_tplv-k3u1fbpfcp-zoom-1.png)


#### web端vue预览的html的pdf
![](https://img.javadog.net/blog/boot-apose/38ca3cfa035644f18ba78c32594de775_tplv-k3u1fbpfcp-zoom-1.png)

#### 最终填充后下载的pdf
![爱你不只是说说而已](https://img.javadog.net/blog/boot-apose/1323d7afb9834ca6bd6aec3031d086d9_tplv-k3u1fbpfcp-zoom-1.png)
***
### 技术涉及
#### 💁‍♂️后端框架
| 技术 | 名称 | 参考网站 |
| :--- | :--- | :--- |
| Spring Boot | MVC框架 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| Maven | 项目构建 | [http://maven.apache.org](http://maven.apache.org/) |
| aspose-words| 本地依赖word工具包| [https://download.csdn.net/download/baidu_25986059/85390408](https://downloads.aspose.com/words/java) |
| lombok| Java库 | [https://projectlombok.org/](https://projectlombok.org/) |
| hutool | 工具类 | [http://hutool.mydoc.io](http://hutool.mydoc.io) |

#### 💁‍♀️前端框架
| 技术 | 名称 | 参考网站 |
| :--- | :--- | :--- |
| VUE| MVVM框架 | [https://cn.vuejs.org//](https://cn.vuejs.org//) |
| Element UI| UI库 | [https://element.eleme.cn/2.0/#/zh-CN](https://element.eleme.cn/2.0/#/zh-CN) |
| javadog-vue-pdf| PDF文件在线预览库(个人修复兼容版) | [https://www.npmjs.com/package/javadog-vue-pdf](https://www.npmjs.com/package/javadog-vue-pdf) |
| axios| 基于promise网络请求库 | [http://www.axios-js.com/](http://www.axios-js.com/) |
***
### 正文
> 虽然浪费的时间有点多，不过磨刀不误砍柴工

#### 前置条件
 - 后台springboot基础项目
 - vue基础项目
>⭐ 如没有基础代码可以直接下载狗哥[Gitee源码](https://gitee.com/javadog-net/boot-apose.git)

#### 步骤解析
##### 后台
###### 1.下载对应的[aspose-words-19.1-jdk16.jar](https://download.csdn.net/download/baidu_25986059/85390408)，加入POM本地依赖
> 因原版收费且会有水印等不确定因素，直接下载jar包本地依赖或者上传私服
> 
![我还在原地等你](https://img.javadog.net/blog/boot-apose/e94b5ffa28274c209ec93949e8904679_tplv-k3u1fbpfcp-zoom-1.png)
```xml
 <!-- 本地依赖 aspose-words-->
        <dependency>
            <groupId>com.aspose</groupId>
            <artifactId>aspose-words</artifactId>
            <classifier>jdk16</classifier>
            <scope>system</scope>
            <version>1.0</version>
            <systemPath>${project.basedir}/src/main/resources/lib/aspose-words-19.1-jdk16.jar</systemPath>
        </dependency>
```
###### 2.放置模板文件到资源路径下
![](https://img.javadog.net/blog/boot-apose/5542432dbdd34b16a590c6bdfcc94162_tplv-k3u1fbpfcp-zoom-1.png)

###### 3.controller读取模板文件并填充数据
 1. 读取模板并将输入流转为doc,并设置文件名及返回类型
![](https://img.javadog.net/blog/boot-apose/cfc2569f87de41ac8d560d41fb79a104_tplv-k3u1fbpfcp-zoom-1.png)
 2. 定位【照片】书签位置，插入图片
![](https://img.javadog.net/blog/boot-apose/b7331f8344084209ba998cd89715c1da_tplv-k3u1fbpfcp-zoom-1.png)
 3. 定位【等级】书签位置，插入对应字符
![](https://img.javadog.net/blog/boot-apose/7e7325aa214e43798ae5c42d410834be_tplv-k3u1fbpfcp-zoom-1.png)
书签插入参考如下
-  找到需要插入的图片的地方，鼠标焦点聚焦
![](https://img.javadog.net/blog/boot-apose/590aa9ef106e445ca4182bff578528fb_tplv-k3u1fbpfcp-zoom-1.png)

-  点击【插入】找到书签并点击,然后录入书签名，并点击添加
![](https://img.javadog.net/blog/boot-apose/9fe6ac4ed92c4c63a97c6feaedb37bbf_tplv-k3u1fbpfcp-zoom-1.png)
-  检查书签是否添加成功

 - 更新doc

 ![](https://img.javadog.net/blog/boot-apose/0e95af09890b4ef8b5bf2a50163fccda_tplv-k3u1fbpfcp-zoom-1.png)
  - 将基础数据填充后并转为PDF

![](https://img.javadog.net/blog/boot-apose/0e95af09890b4ef8b5bf2a50163fccda_tplv-k3u1fbpfcp-zoom-1.png)
> 详见如下代码
```java
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
import java.util.List;

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

```

##### 前端
###### 1.下载对应的依赖包
> npm install
###### 2.在vue.config.js中配置代理
```js
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    port: 1026,
    proxy: {
      '/': {
        target: 'http://localhost:8082', //请求本地 需要ipps-boot后台项目
        ws: false,
        changeOrigin: true
      }
    }
  }
})
```
> npm install
###### 3.在main.js引入所需插件
```js
import Vue from 'vue'
import App from './App.vue'
Vue.config.productionTip = false
import axios from 'axios'
Vue.prototype.$http = axios
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
new Vue({
  render: h => h(App),
}).$mount('#app')
```
###### 4.页面引入vue-pdf组件
```html
  <pdf v-if="showPdf" ref="pdf" :src="pdfUrl" :page="currentPage" @num-pages="pageCount=$event"
               @page-loaded="currentPage=$event" @loaded="loadPdfHandler">
   </pdf>
```
###### 5.页面中使用axios调取接口获取数据
👽**注意responseType类型为blob**
```js
this.$http({
      method: 'get',
      url: `/word/pdf`,
      responseType: 'blob'
    }).then(res=>{
      console.log(res)
      this.pdfUrl = this.getObjectURL(res.data)
      console.log(this.pdfUrl)
      const loadingTask = pdf.createLoadingTask(this.pdfUrl)
      // // 注意这里一定要这样写
      loadingTask.promise.then(load => {
        this.numberPage = load.numPages
      }).catch(err => {
        console.log(err)
      })
      this.loading = false;
    })
```
> 页面完整代码如下

```html
<template>
  <div class="pdf_wrap">
    <template>
      <el-form ref="form" label-width="80px">
        <div style='text-align: center;margin: 30px;' v-if="loading">
          数据加载中...
        </div>
        <div v-if="loading==false" style="display: flex;align-items: center;">
          <div style="flex: 1;"></div>
          <el-button size="mini" @click="changePdfPage(0)" type="primary">上一页</el-button>
          <div style="position: relative; margin: 0px 10px; top: -10px;">
            {{currentPage}} / {{pageCount}}  共 {{numberPage}} 页
          </div>
          <el-button size="mini" @click="changePdfPage(1)" type="primary">下一页</el-button>
          <el-button size="mini" @click='print' type="primary">打印</el-button>
        </div>
        <div v-show="loading==false">
          <pdf v-if="showPdf" ref="pdf" :src="pdfUrl" :page="currentPage" @num-pages="pageCount=$event"
               @page-loaded="currentPage=$event" @loaded="loadPdfHandler">
          </pdf>
        </div>
      </el-form>
    </template>
  </div>
</template>

<script>
import pdf from 'javadog-vue-pdf'
export default {
  components: {
    pdf
  },
  data () {
    return {
      loading: true,
      showPdf: false,
      currentPage: 1, // pdf文件页码
      pageCount: 1, // pdf文件总页数
      fileType: 'pdf', // 文件类型
      pdfUrl: '',
      numberPage:1
    }
  },
  mounted () {
    this.showPdf = true;
    this.$http({
      method: 'get',
      url: `/word/pdf`,
      responseType: 'blob'
    }).then(res=>{
      console.log(res)
      this.pdfUrl = this.getObjectURL(res.data)
      console.log(this.pdfUrl)
      const loadingTask = pdf.createLoadingTask(this.pdfUrl)
      // // 注意这里一定要这样写
      loadingTask.promise.then(load => {
        this.numberPage = load.numPages
      }).catch(err => {
        console.log(err)
      })
      this.loading = false;
    })
  },
  methods: {
    print(){
      this.$refs.pdf.print(600)
    },
    getObjectURL(file) {
      let url = null
      if (window.createObjectURL !== undefined) { // basic
        url = window.createObjectURL(file)
      } else if (window.webkitURL !== undefined) { // webkit or chrome
        // try {
        let blob = new Blob([file], {
          type: "application/pdf"
        });
        url = window.URL.createObjectURL(blob)
        console.log(url)
      } else if (window.URL !== undefined) { // mozilla(firefox)
        try {
          url = window.URL.createObjectURL(file)
        } catch (error) {
          console.log(error)
        }
      }
      return url
    },
    changePdfPage(val) {
      console.log(val)
      if (val === 0 && this.currentPage > 1) {
        this.currentPage--
        // console.log(this.currentPage)
      }
      if (val === 1 && this.currentPage < this.pageCount) {
        this.currentPage++
        // console.log(this.currentPage)
      }
    },
    // pdf加载时
    loadPdfHandler() {
      console.log('jiazai')
      this.currentPage = 1 // 加载的时候先加载第一页
      this.loading = false;
    }
  }
}
</script>
<style scoped>
.pdf_wrap {
  background: #fff;
  height: 100vh;
  width: 80vh;
  margin: 0 auto;
}
.pdf_list {
  height: 80vh;
  overflow: scroll;
}
button {
  margin-bottom: 20px;
}
</style>
```

### 异常情况
#### 1.vue-pdf原版与webpack版本问题，会启动不起来，所以本狗才偷梁换柱，改了一下并自用
#### 2.aspose-words-19.1-jdk16.jar 如果采用官网的maven依赖，可能需要自助破解或交费使用
### 成果展示
![](https://img.javadog.net/blog/boot-apose/a345cf59b3344fffbc60120226bc9f77_tplv-k3u1fbpfcp-zoom-1.gif)
我是JavaDog，谢谢博友耐心看完, 抽空来我狗窝🐕瞅瞅呗 [www.javadog.net](https://www.javadog.net)，关注我的微信公众号有惊喜哦╰(*°▽°*)╯！
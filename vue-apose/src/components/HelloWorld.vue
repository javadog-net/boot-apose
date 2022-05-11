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
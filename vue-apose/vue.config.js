
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

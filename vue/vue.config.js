const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 关闭报错的遮罩层
  devServer: {
    // 配置 start
    client: {
      overlay: false
    }
  }
})

<template>
  <div style="min-height: 100vh">
    <el-container style="min-height: 100vh; border: 1px solid #eee">
      <el-aside :width="sideWidth" style="background-color: rgb(238, 241, 246)">
				<Aside :logoTextShow="logoTextShow" :isCollapse="isCollapse"/>
      </el-aside>

      <el-container>
	      <el-header style="background-color: #fff; border-bottom: #ccc 1px solid">
					<Header :collapse="collapse" :collapseBtnClass="collapseBtnClass"/>
	      </el-header>

        <el-main>
	        <!-- 表示当前页面子路由会在router-view内显示 -->
					<router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.headerBg{
  background: #eee!important;
}
</style>
<script>
// @ is an alias to /src

import Aside from "../components/Aside.vue"
import Header from "@/components/Header.vue";

export default {
  name: 'HomeVue',
  data() {
    return {
      // global
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: "200px",
      logoTextShow: true,
	    user: {}
    }
  },
	components: {
		Aside,
		Header
	},
	methods: {
		// getUser(){
		// 	let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
		// 	if(username){
		// 		// 后台获取User数据
		// 		this.request.get("/user/" + username).then(res => {
		// 			this.user = res.data
		// 		})
		// 	}
		// },
    collapse(){
      this.isCollapse = !this.isCollapse
	    console.log(this.sideWidth)
      if(this.isCollapse){
        this.sideWidth = "64px"
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      }else{
        this.sideWidth = "200px"
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    }
  }
}
</script>

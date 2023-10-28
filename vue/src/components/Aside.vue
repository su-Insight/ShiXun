<template>
	<el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
	         :collapse="isCollapse"
	         background-color="rgb(48, 65, 86)"
	         text-color="#fff"
	         active-text-color="#ffd04b"
	         :collapse-transition="false"
	         router
	         class="el-menu-vertical-demo">
		<div style="height: 60px; line-height: 60px; text-align: center">
			<img src="../assets/logo.png" style="width: 20px">
			<b style="color: white; padding: 0 10px" v-show="logoTextShow">后台管理系统</b>
		</div>
		<el-submenu index="/">
			<template slot="title">
				<i class="el-icon-house"></i>
				<span slot="title">主页</span>
			</template>
		</el-submenu>
		<el-submenu index="2">
			<template slot="title">
				<i class="el-icon-menu"></i>
				<span slot="title">系统管理</span>
			</template>
			<el-menu-item index="/user">
					<i class="el-icon-user"></i>
					<span slot="title">用户管理</span>
			</el-menu-item>
		</el-submenu>
	</el-menu>
</template>

<script>
export default {
	name: "Aside",
	props: {
		isCollapse: Boolean,
		logoTextShow: Boolean
	}
}
</script>

<style scoped>

</style>
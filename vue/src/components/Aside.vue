<template>
	<el-menu :default-openeds="opens" style="min-height: 100%; overflow-x: hidden"
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
		<div v-for="item in menus" :key="item.id">
			<div v-if="item.path">
				<el-menu-item :index="item.path">
					<i :class="item.icon"></i>
					<span slot="title">{{ item.name }}</span>
				</el-menu-item>
			</div>
			<div v-else>
				<el-submenu :index="item.id+''">
					<template slot="title">
						<i :class="item.icon"></i>
						<span slot="title">{{ item.name }}</span>
					</template>
					<div v-for="i in item.children">
						<el-menu-item :index="i.path">
							<i :class="i.icon"></i>
							<span slot="title">{{ i.name }}</span>
						</el-menu-item>
					</div>
				</el-submenu>
			</div>
		</div>
	</el-menu>
</template>

<script>
export default {
	name: "Aside",
	props: {
		isCollapse: Boolean,
		logoTextShow: Boolean,
	},
	data(){
		return{
			menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
			opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
		}
	}
}
</script>

<style scoped>

</style>
<template>
	<div style="font-size: 12px; line-height: 60px; display: flex; position: relative">
		<div style="flex: 1; font-size: 18px">
			<button :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></button>
		</div>

		<el-breadcrumb separator="/" style="position: absolute; left: 40px; top: 24px; font-size: 16px">
			<el-breadcrumb-item v-for="item in breadCrubList" :key="item.path" :to="item.path" style="cursor: pointer">{{ item.meta.title }}</el-breadcrumb-item>
		</el-breadcrumb>

		<el-dropdown style="width: 70px; cursor: pointer">
			<span>王小虎</span>
			<i class="el-icon-arrow-down" style="margin-left: 5px"></i>
			<el-dropdown-menu slot="dropdown">
				<el-dropdown-item>个人信息</el-dropdown-item>
				<el-dropdown-item>退出</el-dropdown-item>
			</el-dropdown-menu>
		</el-dropdown>
	</div>

</template>

<script>
export default {
	name: "Header",
	props: {
		collapseBtnClass: String,
		collapse: Function
	},
	data() {
		return {
			paths: []
		}
	},
	computed: {
		breadCrubList() {
			return this.$route.matched;
		}
	},
	created() {
		console.log(this.$route)
	}
}
</script>

<style scoped>

</style>
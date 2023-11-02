<template>
	<div style="font-size: 12px; line-height: 60px; height: 60px; display: flex; position: relative">
		<div style="flex: 1; font-size: 18px">
			<button :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></button>
		</div>

		<el-breadcrumb separator="/" style="position: absolute; left: 40px; top: 24px; font-size: 16px">
			<el-breadcrumb-item v-for="item in breadCrubList" :key="item.path" style="cursor: pointer"><router-link :to="item.path">{{ item.meta.title }}</router-link></el-breadcrumb-item>
		</el-breadcrumb>

		<el-dropdown style="cursor: pointer; display: flex; align-content: center; color: #777">
			<div style="height: 40px; margin: 10px 10px 0;"><el-avatar :src="user.avatarUrl"></el-avatar></div>
			<div><span>{{ user.nickname }}</span></div>
			<div><i class="el-icon-arrow-down" style="margin-left: 5px"></i></div>
			<el-dropdown-menu slot="dropdown">
				<el-dropdown-item>
					<router-link to="/profile" style="text-decoration: none; color: #555">个人信息</router-link>
				</el-dropdown-item>
				<el-dropdown-item>
					<div @click="logout">退出</div>
				</el-dropdown-item>
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
			paths: [],
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
		}
	},
	computed: {
		breadCrubList() {
			return this.$route.matched;
		}
	},
	// created() {
		// console.log(this.$route)
		// window.addEventListener("storage", this.handleLocalStorageChange)
	// },
	// destroyed() {
		// window.removeEventListener("storage", this.handleLocalStorageChange)
	// },
	mounted() {
		let that = this;
		window.addEventListener("setItemEvent", function (e) {
			console.log("e", e)
			if (e.key === "reload") {
				that.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {};
			}
		})
	},

methods: {
		logout(){
			localStorage.clear()
			this.$router.push("/login")
			this.$message.success("退出成功")
		}
	}
}
</script>

<style scoped>
.el-avatar>img{
	width: 100%;
}
</style>
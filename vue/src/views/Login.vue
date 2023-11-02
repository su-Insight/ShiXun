<template>
	<div class="wrapper">
		<div style="background-color: #fff; padding: 30px; width: 20%; margin: 17% auto; border-radius: 10px; height: 30%;
								box-shadow: 1px 2px 1px #777">
			<el-form :model="user" :rules="rules" ref="userForm" style="height:100%; display: flex; justify-content: space-around; flex-direction: column">
				<div style="text-align: center; margin: 10px 0">登 录</div>
				<el-form-item prop="username"><el-input size="large" prefix-icon="el-icon-user" placeholder="请输入用户名" v-model="user.username" @keyup.enter.native="login"></el-input></el-form-item>
				<el-form-item prop="password"><el-input size="large" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="user.password" @keyup.enter.native="login"></el-input></el-form-item>
				<el-form-item style="text-align: center">
					<el-button type="primary" size="medium" autocomplete="off" @click="login">登录</el-button>
					<el-button type="primary" size="medium" autocomplete="off" @click="$router.push('/register')">注册</el-button>
				</el-form-item>
			</el-form>

		</div>
	</div>
</template>

<script>
export default {
	name: "Login",
	data (){
		return {
			user: {},
			rules: {
				username: [
					{required: true, message: '请输入用户名', trigger: 'blur'},
					{min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
				],
				password: [
					{required: true, message: '请输入密码', trigger: 'blur'},
					{min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
				]
			}
		}
	},
	methods: {
		login(){
			this.$refs['userForm'].validate((valid) => {
				if (valid) {
					this.request.post("/user/login", this.user).then(res => {
						if(res.code === '200'){
							localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
							this.$router.push("/home")
						}else {
							this.$message.error(res.msg)
						}
					})
				} else {
					return false;
				}
			});
		},
	}
}
</script>

<style scoped>
	.wrapper{
		height: 100vh;
		background-image: linear-gradient(to bottom right, #FC4668, #3F5EFB);
		overflow: hidden;
	}
	.el-form-item{
		margin-bottom: 22px;
	}
</style>
<template>
	<div class="wrapper">
		<div style="background-color: #fff; padding: 30px; width: 25%; margin: 13% auto; border-radius: 10px; height: 55%;
								box-shadow: 1px 2px 1px #777">
			<el-form :model="user" :rules="rules" ref="userForm" style="height:100%; display: flex; justify-content: space-around; flex-direction: column">
				<div style="text-align: center; margin: 10px 0">注 册</div>
				<el-form-item prop="username"><el-input size="large" prefix-icon="el-icon-user" placeholder="请输入用户名" v-model="user.username" @keyup.enter.native="register"></el-input></el-form-item>
				<el-form-item prop="password"><el-input size="large" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="user.password" @keyup.enter.native="register"></el-input></el-form-item>
				<el-form-item prop="confirmPassword"><el-input size="large" prefix-icon="el-icon-lock" placeholder="请输入确认密码" show-password v-model="user.confirmPassword" @keyup.enter.native="register"></el-input></el-form-item>
				<el-form-item prop="email"><el-input size="large" prefix-icon="el-icon-message" placeholder="请输入邮箱" v-model="user.email" @keyup.enter.native="register"></el-input></el-form-item>
				<el-form-item prop="phone"><el-input size="large" prefix-icon="el-icon-phone" placeholder="请输入手机号" v-model="user.tel" @keyup.enter.native="register"></el-input></el-form-item>
				<el-form-item style="text-align: center">
					<el-button type="primary" size="medium" autocomplete="off" @click="register">注册</el-button>
					<el-button type="warning" size="medium" autocomplete="off" @click="$router.push('login')">已有账号，去登录</el-button>
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
				],
				confirmPassword: [
					{required: true, message: '请输入确认密码', trigger: 'blur'},
					{min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
				]
			}
		}
	},
	methods: {
		register(){
			this.$refs['userForm'].validate((valid) => {
				if (valid) {
					if(this.user.password !== this.user.confirmPassword){
						this.$message.error("两次输入的密码不一致")
						return false
					}
					this.request.post("/user/register", this.user).then(res => {
						if(res.code === '200'){
							this.$message.success("注册成功，请登录")
							this.$router.push("/login")
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
.el-input{
	width: 80%;
	margin-left: 10%;
}
</style>
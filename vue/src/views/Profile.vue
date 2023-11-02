<template>
	<div style="width: 400px; box-shadow: 1px 1px 2px 2px #eee; padding: 30px; border-radius: 10px; margin: 10% auto">
		<el-form label-width="25%" size="small" style="width: 100%">
			<el-upload
					ref="upload"
					class="avatar-uploader"
					action="http://localhost:8088/user/avatar"
					:headers="headers"
					:show-file-list="false"
					:on-change="handleChange"
					:on-success="handleAvatarSuccess"
					:auto-upload="false"
					:before-upload="beforeAvatarUpload"
			>
				<img v-if="imageUrl" :src="imageUrl" class="avatar">
				<i v-else class="el-icon-plus avatar-uploader-icon"></i>
			</el-upload>
			<el-form-item label="用户名">
				<el-input v-model="user.username" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="昵称">
				<el-input v-model="user.nickname" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="邮箱">
				<el-input v-model="user.email" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="电话">
				<el-input v-model="user.phone" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="地址">
				<el-input v-model="user.address" autocomplete="off"></el-input>
			</el-form-item>
			<div style="display: flex; flex-direction: row; justify-content: space-around">
				<el-button type="primary" size="mini" @click="saveProfile">确认修改</el-button>
			</div>
		</el-form>
	</div>
</template>

<script>
export default {
	name: "Profile",
	data() {
		return {
			user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
			imageUrl: ""
		}
	},
	created() {
		this.imageUrl = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).avatarUrl : ""
		this.headers = {
			"token": localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : null
		}
	},
	methods: {
		saveProfile(){
			this.$refs.upload.submit();
			this.request.post("/user", this.user).then(res => {
				if (res.code === '200'){
					this.$message.success("信息保存成功")
					console.log(res.data)
					localStorage.setItem("user", JSON.stringify(res.data))
					localStorage.setItem("reload", true)
					this.user = res.data
				}else {
					this.$message.error("信息保存失败")
				}
			})
		},
		handleChange(file){
			this.imageUrl = URL.createObjectURL(file.raw);
		},
		handleAvatarSuccess(res, file) {
			if(res.code === '200'){
				localStorage.setItem("user", JSON.stringify(res.data))
				localStorage.setItem("reload", true)
				this.$message.success("头像上传成功")
				this.user = res.data
			}else{
				this.$message.error(res.msg)
			}
		},
		beforeAvatarUpload(file) {
			const isJPG = file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png';
			const isLt2M = file.size / 1024 / 1024 < 3;
			if (!isJPG) {
				this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
			}
			if (!isLt2M) {
				this.$message.error('上传头像图片大小不能超过 3MB!');
			}
			return isJPG && isLt2M;
		}
	}
}
</script>

<style scoped>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}
	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}
	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}
	.avatar {
		width: 108px;
		height: 108px;
		display: block;
		border-radius: 10px;
	}
	.avatar-uploader{
		display: flex;
		justify-content: space-around;
		margin-bottom: 22px;
	}

</style>
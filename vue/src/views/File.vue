<template>
	<div>

		<!-- 搜索 -->
		<div style="margin: 10px 0">
			<el-input style="width: 250px" suffix-icon="el-icon-search" placeholder="请输入搜索内容" v-model="content" @keyup.enter.native="loadPage"></el-input>
			<el-button style="margin-left: 10px" type="primary" @click="loadPage">搜素</el-button>
			<el-button type="warning" @click="reset">重置</el-button>
			<el-popconfirm
					confirm-button-text='确认'
					cancel-button-text='取消'
					icon="el-icon-info"
					icon-color="red"
					title="确认删除用户信息？"
					@confirm="delBatch"
			>
				<el-button style="float: right;margin: 0 5px" type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
			</el-popconfirm>

			<el-button style="float: right;margin: 0 5px" type="primary" @click="addHandle">新增<i class="el-icon-circle-plus-outline"></i></el-button>

		</div>

		<el-dialog title="文档信息" :visible.sync="dialogFormVisible" width="30%">
			<el-upload
					ref="upload"
					class="upload-demo"
					drag
					action="http://localhost:8088/file/upload"
					:auto-upload="false"
					:limit="1"
					:data="form"
					:on-change="handleChange"
					:on-success="handleAvatarSuccess"
					:on-remove="handleRemove">
				<i class="el-icon-upload"></i>
				<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
				<!--					<div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>-->
			</el-upload>
			<el-form label-width="80px" size="small" style="width: 80%; margin: 30px 10% 0">
				<el-form-item label="文档名称">
					<el-input v-model="form.name" autocomplete="off" width="50px"></el-input>
				</el-form-item>
				<el-form-item label="文档类型">
					<el-input :disabled="true" v-model="form.type" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="文档大小">
					<el-input :disabled="true" v-model="form.size" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="文档描述">
					<el-input type="textarea" v-model="form.des" placeholder="在此输入文档描述" autocomplete="off"></el-input>
				</el-form-item>
				<div style="display: flex; flex-direction: row; justify-content: space-around">
					<el-button type="primary" @click="save">确 定</el-button>
					<el-button @click="cancle">取 消</el-button>
				</div>
			</el-form>
		</el-dialog>


		<el-table :data="tableData"
		          @selection-change="handleSelectionChange"
		          border stripe>

			<el-table-column
					type="selection"
					width="55">
			</el-table-column>
			<el-table-column prop="id" label="ID" width="140">
			</el-table-column>
			<el-table-column prop="name" label="文件名称" width="140">
			</el-table-column>
			<el-table-column prop="type" label="文件类型" width="120">
			</el-table-column>
			<el-table-column prop="size" label="文件大小(KB)">
			</el-table-column>
			<el-table-column prop="url" label="下载地址">
			</el-table-column>
			<el-table-column prop="des" label="文件描述">
			</el-table-column>
			<el-table-column prop="enable" label="启用">
				<template slot-scope="scope">
						<el-switch
								v-model="scope.row.enable"
								active-color="#13ce66"
								inactive-color="#ccc"
								active-value="1"
								inactive-value="0"
								@change="switchEnable(scope.row)">
						</el-switch>
				</template>
			</el-table-column>
			<el-table-column label="操作" align="center">
				<template slot-scope="scope">
					<el-button type="success" slot="reference" style="margin: 0 10px" @click="download(scope.row.url)">下载<i class="el-icon-download"></i></el-button>
					<el-popconfirm
							confirm-button-text='确认'
							cancel-button-text='取消'
							icon="el-icon-info"
							icon-color="red"
							title="确认删除用户信息？"
							@confirm="delHandle(scope.row.id)">
						<el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
					</el-popconfirm>
				</template>
			</el-table-column>
		</el-table>
		<!-- 分页 -->
		<div style="padding: 10px 0">
			<el-pagination
					@size-change="handleSizeChange"
					@current-change="handleCurrentChange"
					:current-page="pageNum"
					:page-sizes="[5, 10, 20, 50]"
					:page-size="pageSize"
					layout="total, sizes, prev, pager, next, jumper"
					:total="total">
			</el-pagination>
		</div>
	</div>
</template>

<script>
export default {
	name: "File",
	data() {
		return {
			// global
			dialogFormVisible: false,
			form: {},
			multipleSelection: [],

			// page
			content: "",
			tableData: [],
			total: 0,
			pageNum: 1,
			pageSize: 5,
		}
	},
	created() {
		this.loadPage()
	},
	methods: {
		switchEnable(file){
			this.request.post("/file", file).then(res => {
				if (res.code === '200'){
					this.$message.success(file.enable === "1" ? "启用成功" : "禁用成功")
					this.loadPage()
				}else {
					this.$message.error(res.msg)
				}
			})
		},
		download(url){
			console.log(url)
			window.open(url)
		},
		handleAvatarSuccess(res){
			console.log(res)
			if(res.code === '200'){
				this.$message.success("上传成功")
				this.loadPage()
			}else{
				this.$message.error(res.msg)
			}
		},
		handleChange(file){
			// 上传文件自动更新文件信息
			// 删除文件后缀
			this.$set(this.form, "name", file.raw.name.substring(0, file.raw.name.lastIndexOf(".")))
			this.$set(this.form, "type", file.raw.type)
			this.$set(this.form, "size", String(parseFloat((file.raw.size/1024).toFixed(2))) + "KB")
			this.$set(this.form, "res", "")
			// this.form.name = file.raw.name
			// this.form.type = file.raw.type
			// this.form.size = file.raw.size
			console.log(this.form.name)
		},
		handleRemove(){
			this.form = {}
		},
		cancle(){
			// 清空上传列表
			this.$refs.upload.uploadFiles = []
			this.dialogFormVisible = false
			this.loadPage()
		},
		delHandle(id){
			this.request.delete("/file/" + id).then(res => {
				if (res.code === '200'){
					this.$message.success("删除成功！")
					this.loadPage()
				}else {
					this.$message.error("删除失败！")
				}
			})
		},
		save(){
			this.$refs.upload.submit()
			this.dialogFormVisible = false
			this.request.post("/file", this.form).then(res => {
				if (res.code === '200'){
					this.$message.success("保存成功！")
					this.loadPage()
				}else {
					this.$message.error("保存失败！")
				}
			})
		},
		addHandle(){
			this.dialogFormVisible = true
			this.form = {}
		},
		delBatch(){
			let ids = this.multipleSelection.map(v => v.id)
			this.request.post("/file/del/batch", ids).then(res => {
				if (res.code === '200'){
					this.$message.success("删除成功！")
					this.loadPage()
				}else {
					this.$message.error("删除失败！")
				}
			})
		},
		reset(){
			this.content = ""
			this.loadPage()
		},
		loadPage(){
			// 请求分页查询数据
			this.request.get("/file/page",{
				params: {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					content: this.content
				}
			}).then(res => {
				console.log(res.data.records)
				this.tableData = res.data.records
				this.total = res.data.total
			})
			// fetch("http://localhost:8088/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&content="+this.content).then(res => res.json()).then(res => {
			//   console.log(res)
			//   this.tableData = res.records
			//   this.total = res.total
			// })
		},
		handleSizeChange(pageSize){
			console.log(pageSize)
			this.pageSize = pageSize
			this.loadPage()
		},
		handleCurrentChange(pageNum){
			console.log(pageNum)
			this.pageNum = pageNum
			this.loadPage()
		},
		handleSelectionChange(val){
			console.log(val)
			this.multipleSelection = val
		}
	}
}
</script>

<style>
	#btn>div{
		margin: 0;
		display: flex;
		flex-direction: row;
		justify-content: space-between
	}
	.el-upload-dragger{
		padding: 10px;
		width: 80%;
	}
	.el-upload-list{
		width: 80%;
	}
	.el-upload{
		display: flex;
		justify-content: center;
		width: 100%;
	}
	.upload-demo{
		display: flex;
		align-items: center;
		flex-direction: column;
	}
</style>
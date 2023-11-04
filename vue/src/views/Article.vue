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

		<el-dialog title="文章信息" :visible.sync="dialogFormVisible" width="90%" height="90px">
			<el-form label-width="80px" size="small" style="width: 80%; margin: 0 10%">
				<el-form-item label="文章标题"><el-input v-model="form.name" autocomplete="off" width="50px"></el-input></el-form-item>
				<el-form-item label="文章内容"><mavon-editor ref="md" v-model="form.content" autocomplete="off" :ishljs="true" @imgAdd="imgAdd"></mavon-editor></el-form-item>
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
			<el-table-column prop="name" label="文章标签" width="140">
			</el-table-column>
			<el-table-column prop="content" label="文章内容">
				<template slot-scope="scope">
					<el-button @click="view(scope.row.content)" type="primary">查看内容</el-button>
				</template>
			</el-table-column>
			<el-table-column prop="user" label="发布人" width="120">
			</el-table-column>
			<el-table-column prop="time" label="发布时间">
			</el-table-column>
			<el-table-column label="操作" align="center">
				<template slot-scope="scope">
					<el-button type="success" @click="editHandle(scope.row)" style="margin: 0 10px">编辑<i class="el-icon-edit"></i></el-button>
					<el-popconfirm
							confirm-button-text='确认'
							cancel-button-text='取消'
							icon="el-icon-info"
							icon-color="red"
							title="确认删除用户信息？"
							@confirm="delHandle(scope.row.id)"
					>
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

		<el-dialog title="文章信息" :visible.sync="dialogContentVisible" width="60%">
			<el-card>
				<mavon-editor
								class="md"
								:value="articleContent"
								:subfirld="false"
								:defaultOpen="'preview'"
								:toolbarsFlag="false"
								:editable="false"
								:scrollStyle="true"
								:ishljs="true"
				></mavon-editor>
			</el-card>
		</el-dialog>
	</div>
</template>

<script>
import axios from "axios";

export default {
	name: "Article",
	data() {
		return {
			// global
			dialogFormVisible: false,
			dialogContentVisible: false,
			form: {},

			// page
			content: "",
			tableData: [],
			total: 0,
			pageNum: 1,
			pageSize: 5,
			articleContent: ''
		}
	},
	created() {
		this.loadPage()
	},
	methods: {
		view(content){
			this.articleContent = content
			this.dialogContentVisible = true
		},
		// 绑定@ImgApp event
		imgAdd(pos, $file){
			let $vm = this.$refs.md
			// 图片上传到服务器
			const formData = new FormData();
			formData.append('file', $file);
			axios({
				url: 'http://localhost:8088/file/upload',
				method: 'post',
				data: formData,
				headers: {'Content-Type': 'multipart/form-data'},
			}).then((res) => {
				console.log("res.data",res.data.data)
				// 将返回的url替换到文本位置
				$vm.$img2Url(pos, res.data.data);
			})
		},
		cancle(){
			this.dialogFormVisible = false
			this.loadPage()
		},
		delHandle(id){
			this.request.delete("/article/" + id).then(res => {
				if (res.code === '200'){
					this.$message.success("删除成功！")
					this.loadPage()
				}else {
					this.$message.error("删除失败！")
				}
			})
		},
		save(){
			this.dialogFormVisible = false
			let formError = this.form
			this.request.post("/article", this.form).then(res => {
				if (res.code === '200'){
					this.$message.success("保存成功！")
					this.form = {}
					this.loadPage()
				}else {
					this.$message.error("用户名已存在")
					this.form = formError
				}
			})
		},
		addHandle(){
			this.dialogFormVisible = true
		},
		editHandle(row){
			this.form = row
			this.dialogFormVisible = true
		},
		delBatch(){
			let ids = this.multipleSelection.map(v => v.id)
			this.request.post("/article/del/batch", ids).then(res => {
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
			this.request.get("/article/page",{
				params: {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					content: this.content
				}
			}).then(res => {
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
			this.pageSize = pageSize
			this.loadPage()
		},
		handleCurrentChange(pageNum){
			this.pageNum = pageNum
			this.loadPage()
		},
		handleSelectionChange(val){
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
</style>
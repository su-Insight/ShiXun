<template>
	<div>

		<!-- 搜索 -->
		<div style="margin: 10px 0">
			<!-- 搜索下拉菜单 -->
			<el-select v-model="category" style="width: 75px;margin-right: 10px">
				<el-option
						v-for="item in options"
						:key="item.value"
						:label="item.label"
						:value="item.value">
				</el-option>
			</el-select>
			<el-input style="width: 250px" suffix-icon="el-icon-search" placeholder="请输入搜索内容" v-model="content" @keyup.enter.native="loadPage"></el-input>
			<el-button style="margin-left: 10px" type="primary" @click="loadPage">搜素</el-button>
			<el-button type="warning" @click="reset">重置</el-button>

			<el-button style="float: right;margin: 0 5px" type="primary">导出<i class="el-icon-top"></i></el-button>
			<el-button style="float: right;margin: 0 5px" type="primary">导入<i class="el-icon-bottom"></i></el-button>
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

		<el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
			<el-form label-width="80px" size="small">
				<el-form-item label="用户名">
					<el-input v-model="form.username" autocomplete="off" width="50px"></el-input>
				</el-form-item>
				<el-form-item label="昵称">
					<el-input v-model="form.nickname" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="邮箱">
					<el-input v-model="form.email" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="电话">
					<el-input v-model="form.phone" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="地址">
					<el-input v-model="form.address" autocomplete="off"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="cancle">取 消</el-button>
				<el-button type="primary" @click="save">确 定</el-button>
			</div>
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
			<el-table-column prop="username" label="用户名" width="140">
			</el-table-column>
			<el-table-column prop="nickname" label="昵称" width="120">
			</el-table-column>
			<el-table-column prop="email" label="邮箱">
			</el-table-column>
			<el-table-column prop="phone" label="电话">
			</el-table-column>
			<el-table-column prop="address" label="地址">
			</el-table-column>
			<el-table-column label="操作" align="center">
				<template slot-scope="scope">
					<el-button type="success" @click="editHandle(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
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
	</div>
</template>

<script>
export default {
	name: "User",
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

			// select
			options: [{
				value: 'all',
				label: '全部'
			}, {
				value: 'username',
				label: '姓名'
			}, {
				value: 'nickname',
				label: '昵称'
			}, {
				value: 'email',
				label: '邮箱'
			}, {
				value: 'phone',
				label: '手机号'
			}, {
				value: 'address',
				label: '地址'
			}],
			category: 'all'
		}
	},
	created() {
		this.loadPage()
	},
	methods: {
		cancle(){
			this.dialogFormVisible = false
			this.loadPage()
		},
		delHandle(id){
			this.request.delete("/user/" + id).then(res => {
				if (res){
					this.$message.success("删除成功！")
					this.loadPage()
				}else {
					this.$message.error("删除失败！")
				}
			})
		},
		save(){
			this.dialogFormVisible = false
			this.request.post("/user", this.form).then(res => {
				if (res){
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
		editHandle(row){
			this.form = row
			this.dialogFormVisible = true
		},
		delBatch(){
			let ids = this.multipleSelection.map(v => v.id)
			this.request.post("/user/del/batch", ids).then(res => {
				if (res){
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
			this.request.get("/user/page",{
				params: {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					content: this.content,
					category: this.category
				}
			}).then(res => {
				this.tableData = res.records
				this.total = res.total
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

<style scoped>

</style>
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

			<el-button style="float: right;margin: 0 5px" type="primary" @click="addHandle()">新增主菜单<i class="el-icon-circle-plus-outline"></i></el-button>

		</div>

		<el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
			<el-form label-width="80px" size="small" style="width: 80%; margin: 0 10%">
				<el-form-item label="菜单名称">
					<el-input v-model="form.name" autocomplete="off" width="50px"></el-input>
				</el-form-item>
				<el-form-item label="访问路径" v-if="showPath">
					<el-input v-model="form.path" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="菜单图标">
					<el-select clearable v-model="form.icon" placeholder="请选择菜单图标" style="width: 100%">
						<el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
							<i :class="item.value" style="margin: 0 10px"></i><span>{{ item.name }}</span>
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="页面路径" v-if="showPath">
					<el-input v-model="form.pagePath" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="菜单描述">
					<el-input v-model="form.description" autocomplete="off"></el-input>
				</el-form-item>
				<div style="display: flex; flex-direction: row; justify-content: space-around">
					<el-button type="primary" @click="save">确 定</el-button>
					<el-button @click="cancle">取 消</el-button>
				</div>
			</el-form>
		</el-dialog>

		<el-table :data="tableData" row-key="id"
		          @selection-change="handleSelectionChange"
		          border stripe>
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column prop="id" label="ID"></el-table-column>
			<el-table-column prop="name" label="菜单名称"></el-table-column>
			<el-table-column prop="path" label="访问路径"></el-table-column>
			<el-table-column label="菜单图标">
				<template slot-scope="scope">
					<i :class="scope.row.icon" style="font-size: 20px"></i>
				</template>
			</el-table-column>
			<el-table-column prop="pagePath" label="页面路径"></el-table-column>
			<el-table-column prop="description" label="菜单描述"></el-table-column>
			<el-table-column label="操作" align="center" width="400">
				<template slot-scope="scope">
					<el-button type="primary" @click="addHandle(scope.row.id)" style="margin: 0 10px" v-if="!scope.row.pid && !scope.row.path">新增子菜单<i class="el-icon-plus"></i></el-button>
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

	</div>
</template>

<script>
export default {
	name: "Menu",
	data() {
		return {
			showPath: true,
			options: [],
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
		cancle(){
			this.dialogFormVisible = false
			this.loadPage()
			this.showPath = true
		},
		delHandle(id){
			this.request.delete("/menu/" + id).then(res => {
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
			this.showPath = true
			this.request.post("/menu", this.form).then(res => {
				if (res.code === '200'){
					this.$message.success("保存成功！")
					this.loadPage()
				}else {
					this.$message.error(res.msg)
				}
			})
		},
		addHandle(pid){
			this.showPath = true
			this.form = {}
			if(pid){
				this.form.pid = pid
				this.showPath = false
			}
			this.dialogFormVisible = true
		},
		editHandle(row){
			this.form = row
			this.dialogFormVisible = true

			this.request.get("/menu/icons").then(res => {
				this.options = res.data
			})
			this.showPath = !row.path ? false : true
		},
		delBatch(){
			let ids = this.multipleSelection.map(v => v.id)
			this.request.post("/menu/del/batch", ids).then(res => {
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
			// 查询数据
			this.request.get("/menu",{
				params: {
					content: this.content,
				}
			}).then(res => {
				console.log(res)
				this.tableData = res.data
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
#btn>div{
	margin: 0;
	display: flex;
	flex-direction: row;
	justify-content: space-between
}
</style>
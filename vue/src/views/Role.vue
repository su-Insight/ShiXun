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

		<el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
			<el-form label-width="80px" size="small" style="width: 80%; margin: 0 10%">
				<el-form-item label="角色名称">
					<el-input v-model="form.name" autocomplete="off" width="50px"></el-input>
				</el-form-item>
				<el-form-item label="角色描述">
					<el-input v-model="form.description" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="唯一标识">
					<el-input v-model="form.flag" autocomplete="off"></el-input>
				</el-form-item>
				<div style="display: flex; flex-direction: row; justify-content: space-around">
					<el-button type="primary" @click="save">确 定</el-button>
					<el-button @click="cancle">取 消</el-button>
				</div>
			</el-form>
		</el-dialog>

		<el-dialog title="权限分配" :visible.sync="menuVisible" width="25%">
			<el-tree
					ref="tree"
					:data="menuData"
					show-checkbox
					node-key="id"
					:props="props"
					:default-expanded-keys="expends"
					:default-checked-keys="checks"
					style="margin-left: 10%">
				<span class="custom-tree-node" slot-scope="{ node, data}">
					<span><i :class="data.icon"></i>{{ data.name }}</span>
				</span>
			</el-tree>
			<div style="display: flex; flex-direction: row; justify-content: space-around; margin-top: 30px">
				<el-button type="primary" @click="saveRoleMenu">确 定</el-button>
				<el-button @click="cancle">取 消</el-button>
			</div>
		</el-dialog>

		<el-table :data="tableData"
		          @selection-change="handleSelectionChange"
		          border stripe>
			<el-table-column
					type="selection"
					width="55">
			</el-table-column>
			<el-table-column prop="id" label="ID">
			</el-table-column>
			<el-table-column prop="name" label="角色名称">
			</el-table-column>
			<el-table-column prop="description" label="描述">
			</el-table-column>
			<el-table-column prop="flag" label="唯一标识">
			</el-table-column>
			<el-table-column label="操作" align="center" width="380">
				<template slot-scope="scope">
					<el-button type="info" @click="selectMenu(scope.row)" style="margin: 0 10px">权限管理<i class="el-icon-menu"></i></el-button>
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
	</div>
</template>

<script>
export default {
	name: "Role",
	data() {
		return {
			expends: [],
			checks: [],
			menuData: [],
			roleId: 0,
			roleFlag: "",
			props: {
				label: "name"
			},
			// global
			menuVisible: false,
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
		selectMenu(row){
			this.roleId = row.id
			this.roleFlag = row.flag
			this.menuVisible = true
			// 请求菜单数据
			this.request.get("/menu").then(res => {
				this.menuData = res.data

				// 把后台返回的菜单数据处理成id数组
				this.expends = this.menuData.map(v => v.id)
			})

			// 请求用户绑定菜单数据
			this.request.get("/role/roleMenu/" + this.roleId).then(res => {
				this.checks = res.data

				this.request.get("/menu/ids").then(r => {
					const ids = r.data
					ids.forEach(id => {
						if(!this.checks.includes(id)){
							this.$refs.tree.setChecked(id, false)
						}
					})
				})
			})
		},
		cancle(){
			this.dialogFormVisible = false
			this.menuVisible = false
			this.loadPage()
		},
		delHandle(id){
			this.request.delete("/role/" + id).then(res => {
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
			this.request.post("/role", this.form).then(res => {
				if (res.code === '200'){
					this.$message.success("保存成功！")
					this.form = {}
					this.loadPage()
				}else {
					this.$message.error(res.msg)
					this.form = formError
				}
			})
		},
		saveRoleMenu(){
			this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
				if(res.code === "200"){
					this.menuVisible = false
					this.$message.success("绑定成功")
					// 当前登录为超级管理员，则重启
					if(this.roleFlag === JSON.parse(localStorage.getItem("user")).role){
						localStorage.removeItem("user");
						localStorage.removeItem("menus");
						this.$router.push("/login"); // 执行路由跳转
					}
				}else{
					this.$message.success("绑定失败")
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
			this.request.post("/role/del/batch", ids).then(res => {
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
			this.request.get("/role/page",{
				params: {
					pageNum: this.pageNum,
					pageSize: this.pageSize,
					content: this.content,
				}
			}).then(res => {
				console.log(res)
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

<style scoped>
#btn>div{
	margin: 0;
	display: flex;
	flex-direction: row;
	justify-content: space-between
}
</style>
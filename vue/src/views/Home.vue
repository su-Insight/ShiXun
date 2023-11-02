<template>
	<div>
		<!-- 卡片区域 -->
		<div>
			<el-row :gutter="20" :span="10" style="margin-bottom: 40px">
				<el-col :span="6">
					<el-card>
						45
						<el-tag class="el-icon-arrow-down"></el-tag>
					</el-card>
				</el-col>
				<el-col :span="6">
					<el-card>
						45
					</el-card>
				</el-col>
				<el-col :span="6">
					<el-card>
						45
					</el-card>
				</el-col>
				<el-col :span="6">
					<el-card>
						45
					</el-card>
				</el-col>
			</el-row>
		</div>

		<div id="main" style="width: 500px; height: 400px"></div>
		<div ref="mapChartsDom" style="width: 800px; height: 600px"></div>
	</div>
</template>

<script>
import * as echarts from "echarts"
import getMap from '../utils/getMap'
export default {
	name: "Home",
	data(){
		return {

		}
	},
	// 页面元素渲染后触发
	mounted() {

		// 初始化统计图对象
		let myMapChart = echarts.init(this.$refs["mapChartsDom"]);
		// 显示 loading 动画
		myMapChart.showLoading();
		// 再得到数据的基础上，进行地图绘制
		getMap.then(res => {
			// 得到结果后，关闭动画
			myMapChart.hideLoading();
			// 注册地图(数据放在axios返回对象的data中哦)
			echarts.registerMap('China', res.data);
			let mapOption = {
				visualMap: {
					min: 1,
					max: 150,
					realtime: false,
					calculable: true,
					inRange: {
						color: ['#1E9600', '#FFF200', '#FF0000']
					}
				},
				title:{
					text:'访客来源'
				},
				tooltip: {},// 配置提示框，有这个配置项即可
				series: [
					{
						name: '访客数量',
						type: 'map',
						map: 'China',// 这个是上面注册时的名字哦，registerMap（'这个名字保持一致'）
						label: {
							show: false
						},
						data: [
							{ name: "北京市", value: 177 },
							{ name: "天津省", value: 42 },
							{ name: "河北省", value: 102 },
							{ name: "山西省", value: 81 },
							{ name: "内蒙古自治区", value: 47 },
							{ name: "辽宁省", value: 67 },
							{ name: "吉林省", value: 82 },
							{ name: "黑龙江省", value: 66 },
							{ name: "上海市", value: 0 },
							{ name: "江苏省", value: 92 },
							{ name: "浙江省", value: 114 },
							{ name: "安徽省", value: 109 },
							{ name: "福建省", value: 116 },
							{ name: "江西省", value: 91 },
							{ name: "山东省", value: 119 },
							{ name: "河南省", value: 137 },
							{ name: "湖北省", value: 116 },
							{ name: "湖南省", value: 114 },
							{ name: "重庆市", value: 91 },
							{ name: "四川省", value: 125 },
							{ name: "贵州省", value: 62 },
							{ name: "云南省", value: 83 },
							{ name: "西藏自治区", value: 9 },
							{ name: "陕西省", value: 80 },
							{ name: "甘肃省", value: 56 },
							{ name: "青海省", value: 10 },
							{ name: "宁夏回族自治区", value: 18 },
							{ name: "新疆维吾尔自治区", value: 67 },
							{ name: "广东省", value: 123 },
							{ name: "广西省", value: 59 },
							{ name: "海南省", value: 14 },
							{ name: "台湾省", value: 14 }
						]
					}
				]
			};
			myMapChart.setOption(mapOption);
		})


		let chartDom = document.getElementById('main');
		let myChart = echarts.init(chartDom);
		let option;

		option = {
			tooltip: {
				trigger: 'item'
			},
			legend: {
				top: '5%',
				left: 'center'
			},
			series: [
				{
					name: 'Access From',
					type: 'pie',
					radius: ['40%', '70%'],
					avoidLabelOverlap: false,
					itemStyle: {
						borderRadius: 10,
						borderColor: '#fff',
						borderWidth: 2
					},
					label: {
						show: false,
						position: 'center'
					},
					emphasis: {
						label: {
							show: true,
							fontSize: 40,
							fontWeight: 'bold'
						}
					},
					labelLine: {
						show: false
					},
					data: [
						{ value: 1048, name: 'Search Engine' },
						{ value: 735, name: 'Direct' },
						{ value: 580, name: 'Email' },
						{ value: 484, name: 'Union Ads' },
						{ value: 300, name: 'Video Ads' }
					]
				}
			]
		};

		this.request.get("/echarts/test").then(res => {
			// 填充数据
			for(let i = 0; i < res.data.x.length; i++){
				option.series[0].data[i].name = res.data.x[i];
				option.series[0].data[i].value = res.data.y[i];
			}
			myChart.setOption(option);
		})
	}
}
</script>

<style scoped>

</style>
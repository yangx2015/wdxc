<template>
	<div>
		<fullcalendar 
			:events='events'
			lang ='zh'
			title='日历标题'
			@dayClick="dayClick"
			@eventClick='eventClick'>
			<div slot="fc-header-left" style="background-color: #f00;">
				123
			</div>
			<div slot="fc-header-right" style="background-color: #f00;">
				456
			</div>
			<div slot="fc-body-card" style="background-color: #f00;">
				798
			</div>
		</fullcalendar>
	</div>
</template>

<script>
	import fullcalendar from 'vue-fullcalendar'
	export default {
		name: '',
		components: {
			fullcalendar
		},
		data() {
			return {
				events: [{
						title: 'event1',
						start: '2018-01-01',
						cssClass: 'family',
						YOUR_DATA: {}
					},
					{
						title: 'event1',
						start: '2018-01-01',
						cssClass: 'family',
						YOUR_DATA: {}
					},
					{
						title: 'event2',
						start: '2018-01-06',
						end: '2018-01-08',
						cssClass: ['family', 'career'],
						YOUR_DATA: {}
					}
				]
			}
		},
		methods:{
			dayClick(event){
				log('天事件',event.toLocaleString(
				))
			},
			eventClick(event){
				log('备注事件',event)
			}
		}
	}
</script>

<style>

</style>
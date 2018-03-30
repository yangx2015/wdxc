<template>
    <div id="main" class="app-main">
        <router-view></router-view>
    </div>
</template>

<script>
    import SockJS from 'sockjs-client';
    // 'sockjs-client' 必须与package.json文件当中dependencies 当中的一模一样
    import Stomp from '@stomp/stompjs';

    // let host = "127.0.0.1"
    let host = "192.168.31.180"

    var socket = new SockJS("http://"+host+"/gps");

    /**
     * 建立成功的回调函数
     */
    socket.onopen = function() {
        console.log('open');
    };

    /**
     * 服务器有消息返回的回调函数
     */
    socket.onmessage = function(e) {
        console.log('message', e.data);
    };

    /**
     * websocket链接关闭的回调函数
     */
    socket.onclose = function() {
        console.log('close');
    };

    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/sendgps',  function(data) { //订阅消息
            console.log(data);
        });
    });

    export default {
        data () {
            return {
                theme: this.$store.state.app.themeColor
            };
        },
        mounted () {

        },
        beforeDestroy () {

        },
        methods: {

        }
    };
</script>

<style>
@import "//at.alicdn.com/t/font_605313_dytag0ll15eqxgvi.css";
html,body{
    width: 100%;
    height: 100%;
    background: #f0f0f0;
    overflow: hidden;
}
.app-main{
    width: 100%;
    height: 100%;
}
</style>

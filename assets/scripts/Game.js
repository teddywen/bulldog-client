var boot = require('boot')
cc.Class({
    extends: cc.Component,

    properties: {
        uploadedObjectKey: '', 
        fetchedObjectKey: '', 
        score: {
            default: null, 
            type: cc.Label
        }, 
    },

    // use this for initialization
    onLoad: function () {
        // var self = this;
        // self.score.string = "on start";
        // // var pomelo = window.pomelo;
        // var host = "192.168.31.66";
        // var port = "3010";
        // pomelo.init({
        //     host: host,
        //     port: port,
        //     log: true
        // }, function() {
        //     pomelo.request("connector.entryHandler.entry", "hello pomelo", function(data) {
        //         self.score.string = data.msg;
        //     });
        // });
        // pomelo.disconnect();
        
        // 注册原生调用处理逻辑
        var self = this;
        c2j.getInstance().attach(c2j.EVENT.RECORD_STARTED, function() {
            console.log('record started');
            self.score.string = 'now recording...';
        });
        c2j.getInstance().attach(c2j.EVENT.RECORD_STOPPED, function() {
            console.log('record stopped');
            self.score.string = 'record uploading...';
        });
        c2j.getInstance().attach(c2j.EVENT.RECORD_UPLOADED, function(objectKey) {
            console.log('record uploaded: ' + objectKey);
            self.uploadedObjectKey = objectKey;
            self.score.string = 'ready to fetch';
        });
        c2j.getInstance().attach(c2j.EVENT.RECORD_FETCH_STARTED, function(objectKey) {
            console.log('record fetch started: ' + objectKey);
            self.score.string = 'now fetching...';
        });
        c2j.getInstance().attach(c2j.EVENT.RECORD_FETCHED, function(objectKey) {
            console.log('record fetched: ' + objectKey);
            self.fetchedObjectKey = objectKey;
            if (self.uploadedObjectKey != self.fetchedObjectKey) {
                self.uploadedObjectKey = self.fetchedObjectKey = '';
                self.score.string = 'record again';
            } else {
                self.score.string = 'ready to play';
            }
        });
        c2j.getInstance().attach(c2j.EVENT.RECORD_PLAY_STARTED, function(objectKey) {
            console.log('record play started: ' + objectKey);
            self.score.string = 'playing...';
        });
        c2j.getInstance().attach(c2j.EVENT.RECORD_PLAY_STOPPED, function() {
            console.log('record play stopped');
            self.score.string = 'ready to play';
        });

        // 进入游戏
        j2c.getInstance().enterGame(2, 1);
        j2c.getInstance().setRound(3);
    }, 

    // called every frame, uncomment this function to activate update callback
    update: function (dt) {

    }
});

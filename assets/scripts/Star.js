cc.Class({
    extends: cc.Component,

    properties: {
        pickRadius: 0
    },

    // use this for initialization
    onLoad: function () {
        this.node.on(cc.Node.EventType.TOUCH_START, this.onTouchStart, this);
        this.node.on(cc.Node.EventType.TOUCH_END, this.onTouchEnd, this);
        this.node.on(cc.Node.EventType.TOUCH_CANCEL, this.onTouchCancel, this);
    },

    onTouchStart: function(event) {
        console.log('touch start');
        j2c.getInstance().startRecord();
    }, 

    onTouchEnd: function(event) {
        console.log('touch end');
        j2c.getInstance().stopRecord();
    }, 

    onTouchCancel: function(event) {
        console.log('touch cancel');
        j2c.getInstance().stopRecord();
    }, 
    
    // getPlayerDistance: function () {
    //     var playerPos = this.game.player.getPosition();
    //     return cc.pDistance(this.node.getPosition(), playerPos);
    // }, 
    
    // onPicked: function () {
    //     this.game.spawnNewStar();
    //     this.game.gainScore();
    //     this.node.destroy();
    // }, 

    // called every frame, uncomment this function to activate update callback
    update: function (dt) {
        // if (this.getPlayerDistance() < this.pickRadius) {
        //     this.onPicked();
        //     return;
        // }
        
        // 变暗
        // var opacityRatio = 1 - this.game.timer / this.game.starDuration;
        // var minOpacity = 50;
        // this.node.opacity = minOpacity + Math.floor(opacityRatio * (255 - minOpacity));
    }
});

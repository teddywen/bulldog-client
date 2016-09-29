cc.Class({
    extends: cc.Component,

    properties: {
        game: {
            default: null, 
            type: cc.Node
        }, 
        score: {
            default: null, 
            type: cc.Label
        }
    }, 

    onLoad: function() {
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
});
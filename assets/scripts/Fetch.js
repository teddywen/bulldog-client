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
    },

    onTouchStart: function(event) {
        console.log('touch start');
        this.touchStart = true;
        if (this.game.getComponent('Game').uploadedObjectKey) {
            j2c.getInstance().fetchRecord(this.game.getComponent('Game').uploadedObjectKey);
        }
    }, 
});
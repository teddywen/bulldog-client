var c2j = {
    instance: null, 
    getInstance: function() {
        if (!c2j.instance) {
            c2j.instance = new Behavior();
        }
        return c2j.instance;
    }, 
    EVENT: {
        RECORD_UPLOADED: 'record_uploaded', 
        RECORD_FETCH_STARTED: 'record_fetch_started', 
        RECORD_FETCHED: 'record_fetched', 
        RECORD_STARTED: 'record_started', 
        RECORD_STOPPED: 'record_stopped', 
        RECORD_PLAY_STARTED: 'record_play_started', 
        RECORD_PLAY_STOPPED: 'record_play_stopped'
    }
};

var Behavior = function() {};
Behavior.prototype = {
    handlers: {}, 
    hasAttached: function(event, func) {
        if (!this.handlers.hasOwnProperty(event)) {
            this.handlers[event] = [];
        }
        var detach_key = this.handlers[event].findIndex(function(element, index, array) {
            return element == func;
        });
        return detach_key != -1;
    }, 
    attach: function(event, func) {
        if (!this.hasAttached(event, func)) {
            this.handlers[event].push(func);
        }
    }, 
    detach: function(event, func) {
        if (this.hasAttached(event, func)) {
            var detach_key = this.handlers[event].findIndex(function(element, index, array) {
                return element == func;
            });
            if (detach_key != -1) {
                this.handlers[event].splice(detach_key, 1);
            }
        }
    }, 
    detachAll: function(event) {
        if (this.handlers.hasOwnProperty(event)) {
            this.handlers[event] = [];
        }
    },
    raise: function() {
        var event = arguments[0];
        Array.prototype.splice.call(arguments, 0, 1);
        var self_args = arguments;
        if (!this.handlers.hasOwnProperty(event)) {
            console.log('raise ' + event + ' failed');
            return;
        }
        this.handlers[event].forEach(function(element, index, array) {
            element.apply(null, self_args);
        });
    }
};
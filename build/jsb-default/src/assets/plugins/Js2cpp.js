var j2c = {
    instance: null,
    getInstance: function () {
        if (!j2c.instance) {
            j2c.instance = new hc.Bridge();
        }
        return j2c.instance;
    }
};

// 适配非native
if (!cc.sys.isNative) {
    j2c.instance = {
        /**
         * @method stopRecord
         */
        stopRecord: function (
        ) {
        },

        /**
         * @method setRound
         * @param {int} arg0
         */
        setRound: function (
            round
        ) {
        },

        /**
         * @method fetchRecord
         * @param {char} arg0
         */
        fetchRecord: function (
            objectKey
        ) {
        },

        /**
         * @method startRecord
         */
        startRecord: function (
        ) {
        },

        /**
         * @method stopPlayRecord
         */
        stopPlayRecord: function (
        ) {
        },

        /**
         * @method enterGame
         * @param {int} arg0
         * @param {int} arg1
         */
        enterGame: function (
            gameId,
            uid
        ) {
        },

        /**
         * @method startPlayRecord
         * @param {char} arg0
         */
        startPlayRecord: function (
            objectKey
        ) {
        },

        /**
         * @method Bridge
         * @constructor
         */
        Bridge: function (
        ) {
        },

    };
}

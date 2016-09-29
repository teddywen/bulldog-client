#include "base/ccConfig.h"
#ifndef __happycandlesbridgebindings_h__
#define __happycandlesbridgebindings_h__

#include "spidermonkey/jsapi.h"
#include "spidermonkey/jsfriendapi.h"

extern JSClass  *jsb_Bridge_class;
extern JSObject *jsb_Bridge_prototype;

bool js_happycandlesbridgebindings_Bridge_constructor(JSContext *cx, uint32_t argc, jsval *vp);
void js_happycandlesbridgebindings_Bridge_finalize(JSContext *cx, JSObject *obj);
void js_register_happycandlesbridgebindings_Bridge(JSContext *cx, JS::HandleObject global);
void register_all_happycandlesbridgebindings(JSContext* cx, JS::HandleObject obj);
bool js_happycandlesbridgebindings_Bridge_stopRecord(JSContext *cx, uint32_t argc, jsval *vp);
bool js_happycandlesbridgebindings_Bridge_setRound(JSContext *cx, uint32_t argc, jsval *vp);
bool js_happycandlesbridgebindings_Bridge_fetchRecord(JSContext *cx, uint32_t argc, jsval *vp);
bool js_happycandlesbridgebindings_Bridge_startRecord(JSContext *cx, uint32_t argc, jsval *vp);
bool js_happycandlesbridgebindings_Bridge_stopPlayRecord(JSContext *cx, uint32_t argc, jsval *vp);
bool js_happycandlesbridgebindings_Bridge_enterGame(JSContext *cx, uint32_t argc, jsval *vp);
bool js_happycandlesbridgebindings_Bridge_startPlayRecord(JSContext *cx, uint32_t argc, jsval *vp);
bool js_happycandlesbridgebindings_Bridge_Bridge(JSContext *cx, uint32_t argc, jsval *vp);

#endif // __happycandlesbridgebindings_h__

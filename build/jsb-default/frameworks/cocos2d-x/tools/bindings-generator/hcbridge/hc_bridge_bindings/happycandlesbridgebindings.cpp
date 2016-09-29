#include "happycandlesbridgebindings.hpp"
#include "cocos2d_specifics.hpp"
#include "hc_bridge.h"

template<class T>
static bool dummy_constructor(JSContext *cx, uint32_t argc, jsval *vp) {
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    JS::RootedValue initializing(cx);
    bool isNewValid = true;
    if (isNewValid)
    {
        TypeTest<T> t;
        js_type_class_t *typeClass = nullptr;
        std::string typeName = t.s_name();
        auto typeMapIter = _js_global_type_map.find(typeName);
        CCASSERT(typeMapIter != _js_global_type_map.end(), "Can't find the class type!");
        typeClass = typeMapIter->second;
        CCASSERT(typeClass, "The value is null.");

        JS::RootedObject proto(cx, typeClass->proto.ref());
        JS::RootedObject parent(cx, typeClass->parentProto.ref());
        JS::RootedObject _tmp(cx, JS_NewObject(cx, typeClass->jsclass, proto, parent));
        
        T* cobj = new T();
        js_proxy_t *pp = jsb_new_proxy(cobj, _tmp);
        AddObjectRoot(cx, &pp->obj);
        args.rval().set(OBJECT_TO_JSVAL(_tmp));
        return true;
    }

    return false;
}

static bool empty_constructor(JSContext *cx, uint32_t argc, jsval *vp) {
    return false;
}

static bool js_is_native_obj(JSContext *cx, uint32_t argc, jsval *vp)
{
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    args.rval().setBoolean(true);
    return true;
}
JSClass  *jsb_Bridge_class;
JSObject *jsb_Bridge_prototype;

bool js_happycandlesbridgebindings_Bridge_stopRecord(JSContext *cx, uint32_t argc, jsval *vp)
{
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    JS::RootedObject obj(cx, args.thisv().toObjectOrNull());
    js_proxy_t *proxy = jsb_get_js_proxy(obj);
    Bridge* cobj = (Bridge *)(proxy ? proxy->ptr : NULL);
    JSB_PRECONDITION2( cobj, cx, false, "js_happycandlesbridgebindings_Bridge_stopRecord : Invalid Native Object");
    if (argc == 0) {
        cobj->stopRecord();
        args.rval().setUndefined();
        return true;
    }

    JS_ReportError(cx, "js_happycandlesbridgebindings_Bridge_stopRecord : wrong number of arguments: %d, was expecting %d", argc, 0);
    return false;
}
bool js_happycandlesbridgebindings_Bridge_setRound(JSContext *cx, uint32_t argc, jsval *vp)
{
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    bool ok = true;
    JS::RootedObject obj(cx, args.thisv().toObjectOrNull());
    js_proxy_t *proxy = jsb_get_js_proxy(obj);
    Bridge* cobj = (Bridge *)(proxy ? proxy->ptr : NULL);
    JSB_PRECONDITION2( cobj, cx, false, "js_happycandlesbridgebindings_Bridge_setRound : Invalid Native Object");
    if (argc == 1) {
        int arg0 = 0;
        ok &= jsval_to_int32(cx, args.get(0), (int32_t *)&arg0);
        JSB_PRECONDITION2(ok, cx, false, "js_happycandlesbridgebindings_Bridge_setRound : Error processing arguments");
        cobj->setRound(arg0);
        args.rval().setUndefined();
        return true;
    }

    JS_ReportError(cx, "js_happycandlesbridgebindings_Bridge_setRound : wrong number of arguments: %d, was expecting %d", argc, 1);
    return false;
}
bool js_happycandlesbridgebindings_Bridge_fetchRecord(JSContext *cx, uint32_t argc, jsval *vp)
{
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    bool ok = true;
    JS::RootedObject obj(cx, args.thisv().toObjectOrNull());
    js_proxy_t *proxy = jsb_get_js_proxy(obj);
    Bridge* cobj = (Bridge *)(proxy ? proxy->ptr : NULL);
    JSB_PRECONDITION2( cobj, cx, false, "js_happycandlesbridgebindings_Bridge_fetchRecord : Invalid Native Object");
    if (argc == 1) {
        const char* arg0 = nullptr;
        std::string arg0_tmp; ok &= jsval_to_std_string(cx, args.get(0), &arg0_tmp); arg0 = arg0_tmp.c_str();
        JSB_PRECONDITION2(ok, cx, false, "js_happycandlesbridgebindings_Bridge_fetchRecord : Error processing arguments");
        cobj->fetchRecord(arg0);
        args.rval().setUndefined();
        return true;
    }

    JS_ReportError(cx, "js_happycandlesbridgebindings_Bridge_fetchRecord : wrong number of arguments: %d, was expecting %d", argc, 1);
    return false;
}
bool js_happycandlesbridgebindings_Bridge_startRecord(JSContext *cx, uint32_t argc, jsval *vp)
{
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    JS::RootedObject obj(cx, args.thisv().toObjectOrNull());
    js_proxy_t *proxy = jsb_get_js_proxy(obj);
    Bridge* cobj = (Bridge *)(proxy ? proxy->ptr : NULL);
    JSB_PRECONDITION2( cobj, cx, false, "js_happycandlesbridgebindings_Bridge_startRecord : Invalid Native Object");
    if (argc == 0) {
        cobj->startRecord();
        args.rval().setUndefined();
        return true;
    }

    JS_ReportError(cx, "js_happycandlesbridgebindings_Bridge_startRecord : wrong number of arguments: %d, was expecting %d", argc, 0);
    return false;
}
bool js_happycandlesbridgebindings_Bridge_stopPlayRecord(JSContext *cx, uint32_t argc, jsval *vp)
{
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    JS::RootedObject obj(cx, args.thisv().toObjectOrNull());
    js_proxy_t *proxy = jsb_get_js_proxy(obj);
    Bridge* cobj = (Bridge *)(proxy ? proxy->ptr : NULL);
    JSB_PRECONDITION2( cobj, cx, false, "js_happycandlesbridgebindings_Bridge_stopPlayRecord : Invalid Native Object");
    if (argc == 0) {
        cobj->stopPlayRecord();
        args.rval().setUndefined();
        return true;
    }

    JS_ReportError(cx, "js_happycandlesbridgebindings_Bridge_stopPlayRecord : wrong number of arguments: %d, was expecting %d", argc, 0);
    return false;
}
bool js_happycandlesbridgebindings_Bridge_enterGame(JSContext *cx, uint32_t argc, jsval *vp)
{
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    bool ok = true;
    JS::RootedObject obj(cx, args.thisv().toObjectOrNull());
    js_proxy_t *proxy = jsb_get_js_proxy(obj);
    Bridge* cobj = (Bridge *)(proxy ? proxy->ptr : NULL);
    JSB_PRECONDITION2( cobj, cx, false, "js_happycandlesbridgebindings_Bridge_enterGame : Invalid Native Object");
    if (argc == 2) {
        int arg0 = 0;
        int arg1 = 0;
        ok &= jsval_to_int32(cx, args.get(0), (int32_t *)&arg0);
        ok &= jsval_to_int32(cx, args.get(1), (int32_t *)&arg1);
        JSB_PRECONDITION2(ok, cx, false, "js_happycandlesbridgebindings_Bridge_enterGame : Error processing arguments");
        cobj->enterGame(arg0, arg1);
        args.rval().setUndefined();
        return true;
    }

    JS_ReportError(cx, "js_happycandlesbridgebindings_Bridge_enterGame : wrong number of arguments: %d, was expecting %d", argc, 2);
    return false;
}
bool js_happycandlesbridgebindings_Bridge_startPlayRecord(JSContext *cx, uint32_t argc, jsval *vp)
{
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    bool ok = true;
    JS::RootedObject obj(cx, args.thisv().toObjectOrNull());
    js_proxy_t *proxy = jsb_get_js_proxy(obj);
    Bridge* cobj = (Bridge *)(proxy ? proxy->ptr : NULL);
    JSB_PRECONDITION2( cobj, cx, false, "js_happycandlesbridgebindings_Bridge_startPlayRecord : Invalid Native Object");
    if (argc == 1) {
        const char* arg0 = nullptr;
        std::string arg0_tmp; ok &= jsval_to_std_string(cx, args.get(0), &arg0_tmp); arg0 = arg0_tmp.c_str();
        JSB_PRECONDITION2(ok, cx, false, "js_happycandlesbridgebindings_Bridge_startPlayRecord : Error processing arguments");
        cobj->startPlayRecord(arg0);
        args.rval().setUndefined();
        return true;
    }

    JS_ReportError(cx, "js_happycandlesbridgebindings_Bridge_startPlayRecord : wrong number of arguments: %d, was expecting %d", argc, 1);
    return false;
}
bool js_happycandlesbridgebindings_Bridge_constructor(JSContext *cx, uint32_t argc, jsval *vp)
{
    JS::CallArgs args = JS::CallArgsFromVp(argc, vp);
    bool ok = true;
    Bridge* cobj = new (std::nothrow) Bridge();
    TypeTest<Bridge> t;
    js_type_class_t *typeClass = nullptr;
    std::string typeName = t.s_name();
    auto typeMapIter = _js_global_type_map.find(typeName);
    CCASSERT(typeMapIter != _js_global_type_map.end(), "Can't find the class type!");
    typeClass = typeMapIter->second;
    CCASSERT(typeClass, "The value is null.");
    JS::RootedObject proto(cx, typeClass->proto.ref());
    JS::RootedObject parent(cx, typeClass->parentProto.ref());
    JS::RootedObject obj(cx, JS_NewObject(cx, typeClass->jsclass, proto, parent));
    args.rval().set(OBJECT_TO_JSVAL(obj));
    // link the native object with the javascript object
    jsb_new_proxy(cobj, obj);
    if (JS_HasProperty(cx, obj, "_ctor", &ok) && ok)
        ScriptingCore::getInstance()->executeFunctionWithOwner(OBJECT_TO_JSVAL(obj), "_ctor", args);
    return true;
}

void js_Bridge_finalize(JSFreeOp *fop, JSObject *obj) {
    CCLOGINFO("jsbindings: finalizing JS object %p (Bridge)", obj);
    JSContext *cx = ScriptingCore::getInstance()->getGlobalContext();
    JS::RootedObject jsobj(cx, obj);
    auto proxy = jsb_get_js_proxy(jsobj);
    if (proxy) {
        Bridge *nobj = static_cast<Bridge *>(proxy->ptr);

        if (nobj) {
            jsb_remove_proxy(proxy);
            delete nobj;
        }
        else jsb_remove_proxy(proxy);
    }
}
void js_register_happycandlesbridgebindings_Bridge(JSContext *cx, JS::HandleObject global) {
    jsb_Bridge_class = (JSClass *)calloc(1, sizeof(JSClass));
    jsb_Bridge_class->name = "Bridge";
    jsb_Bridge_class->addProperty = JS_PropertyStub;
    jsb_Bridge_class->delProperty = JS_DeletePropertyStub;
    jsb_Bridge_class->getProperty = JS_PropertyStub;
    jsb_Bridge_class->setProperty = JS_StrictPropertyStub;
    jsb_Bridge_class->enumerate = JS_EnumerateStub;
    jsb_Bridge_class->resolve = JS_ResolveStub;
    jsb_Bridge_class->convert = JS_ConvertStub;
    jsb_Bridge_class->finalize = js_Bridge_finalize;
    jsb_Bridge_class->flags = JSCLASS_HAS_RESERVED_SLOTS(2);

    static JSPropertySpec properties[] = {
        JS_PSG("__nativeObj", js_is_native_obj, JSPROP_PERMANENT | JSPROP_ENUMERATE),
        JS_PS_END
    };

    static JSFunctionSpec funcs[] = {
        JS_FN("stopRecord", js_happycandlesbridgebindings_Bridge_stopRecord, 0, JSPROP_PERMANENT | JSPROP_ENUMERATE),
        JS_FN("setRound", js_happycandlesbridgebindings_Bridge_setRound, 1, JSPROP_PERMANENT | JSPROP_ENUMERATE),
        JS_FN("fetchRecord", js_happycandlesbridgebindings_Bridge_fetchRecord, 1, JSPROP_PERMANENT | JSPROP_ENUMERATE),
        JS_FN("startRecord", js_happycandlesbridgebindings_Bridge_startRecord, 0, JSPROP_PERMANENT | JSPROP_ENUMERATE),
        JS_FN("stopPlayRecord", js_happycandlesbridgebindings_Bridge_stopPlayRecord, 0, JSPROP_PERMANENT | JSPROP_ENUMERATE),
        JS_FN("enterGame", js_happycandlesbridgebindings_Bridge_enterGame, 2, JSPROP_PERMANENT | JSPROP_ENUMERATE),
        JS_FN("startPlayRecord", js_happycandlesbridgebindings_Bridge_startPlayRecord, 1, JSPROP_PERMANENT | JSPROP_ENUMERATE),
        JS_FS_END
    };

    JSFunctionSpec *st_funcs = NULL;

    jsb_Bridge_prototype = JS_InitClass(
        cx, global,
        JS::NullPtr(),
        jsb_Bridge_class,
        js_happycandlesbridgebindings_Bridge_constructor, 0, // constructor
        properties,
        funcs,
        NULL, // no static properties
        st_funcs);

    // add the proto and JSClass to the type->js info hash table
    JS::RootedObject proto(cx, jsb_Bridge_prototype);
    jsb_register_class<Bridge>(cx, jsb_Bridge_class, proto, JS::NullPtr());
}
void register_all_happycandlesbridgebindings(JSContext* cx, JS::HandleObject obj) {
    // Get the ns
    JS::RootedObject ns(cx);
    get_or_create_js_obj(cx, obj, "hc", &ns);

    js_register_happycandlesbridgebindings_Bridge(cx, ns);
}


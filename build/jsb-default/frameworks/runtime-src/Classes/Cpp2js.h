#ifndef __CPP2JS_H__
#define __CPP2JS_H__

class Cpp2js
{
private:
    static Cpp2js* m_instance;

public:

    Cpp2js();
	~Cpp2js();

    void recordStarted();
    void recordStopped();
	void recordUploaded(const char*);
    void recordFetchStarted(const char*);
    void recordFetched(const char*);
    void recordPlayStarted(const char*);
    void recordPlayStopped();

    static Cpp2js* getInstance();
};

#endif

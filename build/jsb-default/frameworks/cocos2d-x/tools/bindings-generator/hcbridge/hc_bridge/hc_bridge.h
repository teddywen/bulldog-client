#ifndef __HC_BRIDGE_H__
#define __HC_BRIDGE_H__

class Bridge
{
public:

    Bridge();
	~Bridge();

	void startRecord();
	void stopRecord();
    void fetchRecord(const char*);
    void startPlayRecord(const char*);
    void stopPlayRecord();
    void enterGame(int, int);
    void setRound(int);
};

#endif

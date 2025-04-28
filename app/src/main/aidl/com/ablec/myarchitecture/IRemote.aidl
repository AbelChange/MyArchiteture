// IRemote.aidl
package com.ablec.myarchitecture;
import com.ablec.myarchitecture.data.AidlData;

interface IRemote {
    String plus(int a, int b);

    void send(in AidlData data);

    void receive(out AidlData data);

    oneway void async(int a);

    void transferFile(in ParcelFileDescriptor pfd);

}
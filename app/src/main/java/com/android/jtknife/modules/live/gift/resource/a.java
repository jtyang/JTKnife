package com.android.jtknife.modules.live.gift.resource;

import com.android.jtknife.modules.live.gift.EnvironmentHelper;
import com.android.jtknife.modules.live.gift.model.GiftModel;
import com.android.jtknife.modules.live.gift.model.anim.AnimItem;
import com.android.jtknife.modules.live.gift.model.anim.GiftAnimEffect;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/1
 */
public class a {
    private static final String a = a.class.getSimpleName();

    public static c a(GiftModel giftModel) {
        c cVar = new c();
        File file = new File(EnvironmentHelper.j + new File(FileDownloadUtils.getDefaultSaveFilePath(giftModel.getEffect())).getName());
        if (file.exists()) {
            File file2 = new File(file, "animation_android.json");
            if (file2.exists()) {
                try {
                    GiftAnimEffect giftAnimEffect = (GiftAnimEffect) new Gson().fromJson(b(file2.getAbsolutePath()), GiftAnimEffect.class);
                    for (AnimItem animItem : giftAnimEffect.getAnimItem()) {
                        switch (animItem.getType()) {
                            case 1:
                                if (new File(file, animItem.getName()).exists()) {
                                    break;
                                }
                                cVar.a(false);
                                return cVar;
                            default:
                                break;
                        }
                    }
                    cVar.a(giftAnimEffect);
                    cVar.a(true);
                    return cVar;
                } catch (Throwable e) {
//                    m.a(a, e);
                }
            }
        }
        cVar.a(false);
        return cVar;
    }

    public static String b(String str) {
        BufferedReader bufferedReader;
        IOException e;
        Throwable th;
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder.append(readLine);
                } catch (IOException e2) {
                    e = e2;
                }
            }
            bufferedReader.close();
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e3) {
                }
            }
        } catch (IOException e4) {
            IOException iOException = e4;
            bufferedReader = null;
            e = iOException;
            try {
                e.printStackTrace();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e5) {
                    }
                }
                return stringBuilder.toString();
            } catch (Throwable th2) {
                th = th2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e6) {
                    }
                }
//                throw th;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            bufferedReader = null;
            th = th4;
//            if (bufferedReader != null) {
//                bufferedReader.close();
//            }
//            throw th;
        }
        return stringBuilder.toString();
    }
}

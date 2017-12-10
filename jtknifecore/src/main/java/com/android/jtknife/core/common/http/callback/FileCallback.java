package com.android.jtknife.core.common.http.callback;

import com.android.jtknife.core.common.http.model.HttpResponse;
import com.android.jtknife.core.common.http.service.AppRequest;
import com.android.jtknife.core.common.http.service.AppResponse;
import com.android.jtknife.core.common.http.utils.FileUtils;
import com.android.jtknife.core.common.http.utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/10
 */
public abstract class FileCallback extends AppResponse {

    public static final String DM_TARGET_FOLDER = File.separator + "download" + File.separator; //下载目标文件夹

    private String folder; //目标文件存储的文件夹路径
    private String fileName;//目标文件存储的文件名

    public FileCallback(String folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }

    @Override
    public void onSuccess(AppRequest request, HttpResponse response) {

        File dir = new File(folder);
        FileUtils.createFolder(dir);
        File file = new File(dir, fileName);
        FileUtils.delFileOrFolder(file);

        InputStream bodyStream = null;
        byte[] buffer = new byte[8192];
        FileOutputStream fileOutputStream = null;
        try {
            bodyStream = response.getBody();
            if (bodyStream == null) {
                onError(-1, "IOException");
                return;
            }

            int len;
            fileOutputStream = new FileOutputStream(file);
            while ((len = bodyStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
        } catch (Exception e) {
            onError(-1, "IOException");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bodyStream);
            IOUtils.closeQuietly(fileOutputStream);
        }
        onSuccess();
    }

    public abstract void onSuccess();

}

package cn.inphoto.dbentity.util;

import java.util.List;

public class PythonInfo {
    private String zipFileName;
    private List<String> fileList;

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public PythonInfo(String zipFileName, List<String> fileList) {
        this.zipFileName = zipFileName;
        this.fileList = fileList;
    }
}

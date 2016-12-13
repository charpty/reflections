/**
 * Copyright(C) 2016 Fugle Technology Co. Ltd. All rights reserved.
 */
package org.reflection.reflection;

/**
 * @author CaiBo
 * @version $Id: StandardFieldInfo.java 15564 2016-05-31 01:20:21Z CaiBo $
 * @since 2016年5月31日 上午9:15:57
 */
final class StandardFieldInfo implements FieldInfo {
    private String fieldPath;
    private String hostPath;

    public StandardFieldInfo(String fieldPath, String hostPath) {
        this.fieldPath = fieldPath;
        this.hostPath = hostPath;
    }

    @Override
    public String getPath() {
        return fieldPath;
    }

    public void setFieldPath(String fieldPath) {
        this.fieldPath = fieldPath;
    }

    public void setHostPath(String hostPath) {
        this.hostPath = hostPath;
    }

    @Override
    public String getHostPath() {
        return hostPath;
    }
}

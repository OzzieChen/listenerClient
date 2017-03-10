package net.xssu.client.service.impl;

import com.sun.istack.internal.NotNull;
import net.xssu.client.entity.ScanTask;
import net.xssu.client.service.IScanService;

import java.io.File;
import java.util.List;

public class NormalScanServiceImpl implements IScanService {
    public List<String> generateScanConfig(@NotNull ScanTask task) {
        return null;
    }

    public File scan(ScanTask task, List<String> commands, String processDirectory, String processName) {
        return null;
    }
}

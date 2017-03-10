package net.xssu.client.service;

import com.sun.istack.internal.NotNull;
import net.xssu.client.entity.ScanTask;

import java.io.File;
import java.util.List;

public interface IScanService {
    /**
     * Generate Scan config file.
     * WARN: May BLOCK in here if redis haven't finished synchronize in service detection mode
     */
    public List<String> generateScanConfig(@NotNull ScanTask task);

    /**
     * Start scan task
     *
     * @param task             scan task
     * @param commands         scaning commands
     * @param processDirectory scanner process(eg. masscan) file path
     * @param processName
     * @return files.output file if scan finished, null if failed
     */
    public File scan(ScanTask task, List<String> commands, String processDirectory, String processName);
}

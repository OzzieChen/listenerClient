package net.xssu.client.service;

import java.io.File;

public interface IMQSendResultService {
    public boolean sendResult(Integer taskId, String shard, File outputFile);
}

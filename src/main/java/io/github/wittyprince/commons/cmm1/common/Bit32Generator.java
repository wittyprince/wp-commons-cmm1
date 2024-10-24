package io.github.wittyprince.commons.cmm1.common;

import java.util.concurrent.TimeUnit;

/**
 * id generator
 *
 * @author WangChen
 * Created on 2023/10/8
 * @since 1.0.1
 */
public class Bit32Generator {
    private static long workerId;
    private static long datacenterId;
    private static long sequence = 0L;
    private static long twepoch = 1288834974657L;
    private static long workerIdBits = 5L;
    private static long datacenterIdBits = 5L;
    private static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private static long sequenceBits = 12L;
    private static long workerIdShift = sequenceBits;
    private static long datacenterIdShift = sequenceBits + workerIdBits;
    private static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private static long sequenceMask = -1L ^ (-1L << sequenceBits);
    private static long lastTimestamp = -1L;

    static {
        workerId = 1;  // 设置 workerId 和 datacenterId
        datacenterId = 1;
    }

    public static synchronized String nextBid() {
        return String.valueOf(nextId());
    }

    public static synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            // 时钟回拨异常
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
